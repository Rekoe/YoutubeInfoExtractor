package ytie.extractor;

import junit.framework.Assert;

import org.junit.Test;

import ytie.format.Format;
import ytie.format.FormatType;

/**
 * Created by aleesa on 8/12/15.
 */
public class YoutubeVideoInfoRepositoryTest {

    @Test
    public void testMain() {
        YouTubeVideoInfoRepository extractorRepository = new YouTubeVideoInfoData();
        extractorRepository.getInfoByID("YOiCkhIZyzs", new YouTubeVideoInfoRepository.YoutubeVideoInfoCallback() {
            @Override
            public void onSuccess(VideoInfoData info) {
                System.out.printf("title: %s, view-count: %d, length: %d\n", info.getTitle(), info.getViewCount(), info.getLength());
                for (Format format : info.getFormats()) {
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

}
