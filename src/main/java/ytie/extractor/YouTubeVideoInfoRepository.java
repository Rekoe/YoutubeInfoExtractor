package ytie.extractor;

/**
 * Created by Jacob on 4/11/2015.
 */
public interface YouTubeVideoInfoRepository {
    interface YoutubeVideoInfoCallback {
        void onSuccess(VideoInfoData info);
        void onError(Exception ex);
    }

    void getInfoById(final String id, YoutubeVideoInfoCallback callback);

    public VideoInfoData getInfoByid( String videoId );
}
