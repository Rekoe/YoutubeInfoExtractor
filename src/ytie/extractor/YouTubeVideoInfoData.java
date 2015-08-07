package ytie.extractor;

/**
 * Created by Jacob on 4/7/2015.
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import ytie.extractor.exceptions.PlayerNotFoundException;
import ytie.extractor.exceptions.DataNotFoundException;
import ytie.format.Format;
import ytie.format.FormatBuilder;
import ytie.util.HTTPUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class YouTubeVideoInfoData implements YouTubeVideoInfoRepository {
    private static final Pattern ageRestrictPattern = Pattern.compile("player-age-gate-content\">");
    private static final Pattern stsPattern = Pattern.compile("\"sts\"\\s*:\\s*(\\d+)");
    private static final Pattern ytPlayerPattern = Pattern.compile(";ytplayer\\.config\\s*=\\s*(\\{.*?\\});");
    private static final Pattern ytPlayerUrlPattern = Pattern.compile("ytplayer\\.config.*?\"url\"\\s*:\\s*(\"[^\"]+\")");
    private static final Pattern assetsPattern = Pattern.compile("\"assets\":.+?\"js\":\\s*(\"[^\"]+\")");
    private static final String proto = "https";

    private Decrypter decrypter = new Decrypter();

    public void getInfoByID(String videoId, YoutubeVideoInfoCallback callback) {
        VideoInfoData info = new VideoInfoData();
        String stringUrl = String.format("%s://www.youtube.com/watch?v=%s&gl=US&hl=en&has_verified=1&bpctr=9999999999", proto, videoId);
        String videoWebpage = null;
        String embededWebpage = null;
        List<Format> formats;
        Boolean ageGate = false;
        Map<String, String> videoInfo = null;

        try {
            info.setVideoID(videoId);
            videoWebpage = HTTPUtility.downloadPageSource(stringUrl);
            if (ageRestrictPattern.matcher(videoWebpage).find()) {
                ageGate = true;
                videoInfo = getVideoInfoEmbed(videoId);
                embededWebpage = getVideoEmbedPageSource(videoId);
            } else {
                ageGate = false;
                Matcher ytPlayerMatcher = ytPlayerPattern.matcher(videoWebpage);
                if (!ytPlayerMatcher.find()) {
                    videoInfo = getVideoInfoEmbed(videoId);
                } else {
                    String jsonBody = ytPlayerMatcher.group(1);
                    JsonParser parser = new JsonParser();
                    JsonObject ytPlayerConfig = (JsonObject) parser.parse(jsonBody);
                    JsonElement argsElement = ytPlayerConfig.get("args");
                    JsonObject args = argsElement.getAsJsonObject();
                    //fall back to embed method if this fails
                    if (!args.has("url_encoded_fmt_stream_map")) {
                        videoInfo = getVideoInfoEmbed(videoId);
                    } else {
                        videoInfo = new HashMap<String, String>();
                        for (Map.Entry<String, JsonElement> entry : args.entrySet()) {
                            videoInfo.put(entry.getKey(), entry.getValue().getAsString());
                        }
                    }
                }
            }

            formats = new ArrayList<Format>();
            info.setAgeGate(ageGate);

            if (!videoInfo.containsKey("token") && videoInfo.containsKey("reason")) {
                if (videoInfo.containsKey("reason")) {
                    throw new DataNotFoundException(String.format("Token parameter not found, YouTube said: %s", videoInfo.get("reason")));
                } else {
                    throw new DataNotFoundException("Token parameter not in video info for unknown reason");
                }
            }

            info = populateDataWithVideoInfo(info, videoInfo);

            if (videoInfo.containsKey("conn") && videoInfo.get("conn").startsWith("rtmp")) {
                formats.add(new FormatBuilder()
                        .rtmp(true)
                        .url(videoInfo.get("conn"))
                        .build());
            } else if (videoInfo.containsKey("url_encoded_fmt_stream_map") || videoInfo.containsKey("adaptive_fmts")) {
                String encodedUrl = videoInfo.get("url_encoded_fmt_stream_map") + "," + videoInfo.get("adaptive_fmts");
                if (encodedUrl.contains("rtmpe%3Dyes")) {
                    throw new UnsupportedOperationException("Rtmpe downloads are currently not supported");
                }

                String[] splitEncodedUrl = encodedUrl.split(",");
                for (String urlDataString : splitEncodedUrl) {
                    FormatBuilder formatBuilder = new FormatBuilder();
                    Map<String, String> urlData = null;
                    StringBuilder urlBuilder = new StringBuilder();
                    String playerUrl = null;
                    int formatId = 0;

                    urlData = HTTPUtility.queryStringToMap(urlDataString);
                    if (!urlData.containsKey("itag") || !urlData.containsKey("url")) {
                        continue;
                    }

                    formatBuilder.formatId(Integer.parseInt(urlData.get("itag")));
                    urlBuilder.append(urlData.get("url"));

                    if (urlData.containsKey("sig")) {
                        urlBuilder.append("&signature=" + urlData.get("sig"));
                    } else if (urlData.containsKey("s")) {
                        String encryptedSig = urlData.get("s");
                        Matcher assetsPatternMatcher = assetsPattern.matcher(ageGate ? embededWebpage : videoWebpage);
                        if (!assetsPatternMatcher.find() && !ageGate) {
                            //turns out we need the embeded page after all
                            assetsPatternMatcher = assetsPattern.matcher(getVideoEmbedPageSource(videoId));
                            if (!assetsPatternMatcher.find()) {
                                throw new PlayerNotFoundException(String.format("Unable to find html5player in assets of embeded video ID: %s", videoId));
                            }
                        }

                        JsonParser parser = new JsonParser();
                        try {
                            playerUrl = parser.parse(assetsPatternMatcher.group(1)).getAsString();
                        } catch (JsonParseException ex) {
                            Matcher ytPlayerUrlMatcher = ytPlayerUrlPattern.matcher(videoWebpage);
                            if (!ytPlayerUrlMatcher.find()) {
                                throw new PlayerNotFoundException("Unable to find html5player in ytconfig");
                            }
                            try {
                                playerUrl = parser.parse(ytPlayerUrlMatcher.group(1)).getAsString();
                            } catch (JsonParseException ex2) {
                                throw new PlayerNotFoundException("Unable to parse json body of ytconfig", ex2);
                            }
                        }

                        if (playerUrl.endsWith("swf")) {
                            //we don't support swf
                            continue;
                        }

                        String signature = this.decrypter.decryptSignature(encryptedSig, playerUrl);
                        urlBuilder.append("&signature=").append(signature);
                    }

                    if (urlBuilder.indexOf("ratebypass") == -1) {
                        urlBuilder.append("&ratebypass=yes");
                    }

                    formats.add(formatBuilder
                            .url(urlBuilder.toString()).build());
                }
            }
            info.setFormats(formats);
            callback.onSuccess(info);
        } catch(Exception ex) {
            callback.onError(ex);
        }
    }

    private static String getVideoEmbedPageSource(String videoId) throws IOException {
        String stringUrl = String.format("%s://www.youtube.com/embed/%s", proto, videoId);
        return HTTPUtility.downloadPageSource(stringUrl);
    }

    private static VideoInfoData populateDataWithVideoInfo(VideoInfoData info, Map<String, String> videoInfo) throws DataNotFoundException {
        if(!videoInfo.containsKey("view_count")) {
            info.setViewCount(0);
        }

        info.setViewCount(Integer.parseInt(videoInfo.get("view_count")));

        if(videoInfo.containsKey("ypc_video_rental_bar_text") && !videoInfo.containsKey("author")) {
            throw new UnsupportedOperationException("Rental videos not supported");
        }

        if(!videoInfo.containsKey("author")) {
            throw new DataNotFoundException("Author not found in video info");
        }

        info.setAuthor(videoInfo.get("author"));

        if(!videoInfo.containsKey("title")) {
            throw new DataNotFoundException("Video title not found in video info");
        }

        info.setTitle(videoInfo.get("title"));

        if(!videoInfo.containsKey("length_seconds")) {
            info.setLength(0);
        }

        info.setLength(Integer.parseInt(videoInfo.get("length_seconds")));

        return info;
    }

    private static Map<String, String> getVideoInfoEmbed(String videoId) throws IOException {
        String stringUrl = String.format("%s://www.youtube.com/embed/%s", proto, videoId);
        String embededWebpage = getVideoEmbedPageSource(videoId);
        Matcher stsMatcher = stsPattern.matcher(embededWebpage);
        HashMap<String, String> formData = new HashMap<String, String>();

        formData.put("video_id", videoId);
        formData.put("eurl", String.format("https://youtube.googleapis.com/v/%s", videoId));
        if (stsMatcher.find()) {
            formData.put("sts", stsMatcher.group(1));
        } else {
            formData.put("sts", "");
        }

        String encodedUrlParams = HTTPUtility.queryStringFromMap(formData);
        String videoInfoWebpage = HTTPUtility.downloadPageSource(String.format("%s://www.youtube.com/get_video_info?%s", proto, encodedUrlParams));
        Map<String, String> map = HTTPUtility.queryStringToMap(videoInfoWebpage);
        return map;
    }
}
