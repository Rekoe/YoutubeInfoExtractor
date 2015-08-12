package ytie;

import ytie.extractor.VideoInfoData;
import ytie.extractor.YouTubeVideoInfoData;
import ytie.extractor.YouTubeVideoInfoRepository;
import ytie.format.Format;
import ytie.format.FormatType;


public class Main {
    public static void main(String[] args) {
        YouTubeVideoInfoRepository extractorRepository = new YouTubeVideoInfoData();
        extractorRepository.getInfoByID("YOiCkhIZyzs", new YouTubeVideoInfoRepository.YoutubeVideoInfoCallback() {
            @Override
            public void onSuccess(VideoInfoData info) {
                System.out.printf("title: %s, view-count: %d, length: %d\n", info.getTitle(), info.getViewCount(), info.getLength());
                for(Format format : info.getFormats()) {
                    if(format.getFormatType() != FormatType.UNKNOWN) {
                        System.out.println(format.toString());
                    }
                }
            }

            @Override
            public void onError(Exception ex) {
                System.err.println(ex.toString());
            }
        });
    }
}
