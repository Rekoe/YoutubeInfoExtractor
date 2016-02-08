package ytie.extractor;

import org.junit.Test;

import ytie.format.RawFormat;
import ytie.format.FormatType;

/**
 * Created by aleesa on 8/12/15.
 */
public class YoutubeVideoInfoRepositoryTest {

    private static final String VIDEO_ID = "q9xpsmDXy48";

    @Test
    public void testAsync() {
        YouTubeVideoInfoRepository extractorRepository = new YouTubeVideoInfoData();
        extractorRepository.getInfoById(VIDEO_ID, new YouTubeVideoInfoRepository.YoutubeVideoInfoCallback() {
            @Override
            public void onSuccess(RawVideoInfoData info) {
                System.out.printf("title: %s, view-count: %d, length: %d\n", info.getTitle(), info.getViewCount(), info.getLength());
                for (RawFormat format : info.getFormats()) {
                    if (format.getFormatType() != FormatType.UNKNOWN) {
                        System.out.println(format.toString());
                    }
                }

                //Write some asserts here about the type of input you are expecting
                //Do one test for each thing, make it as specific as possible for each type of failure
            }

            @Override
            public void onError(Exception ex) {
                System.err.println(ex.toString());
            }
        });
    }

    @Test
    public void testSync() {
        YouTubeVideoInfoRepository extractorRepository = new YouTubeVideoInfoData();
        final RawVideoInfoData rawVideoInfoData = extractorRepository.getInfoById(VIDEO_ID);
        for (RawFormat format : rawVideoInfoData.getFormats()) {
            if (format.getFormatType() != FormatType.UNKNOWN) {
                System.out.println(format.toString());
            }
        }
    }

}
