package ytie.extractor;

/**
 * Created by Jacob on 4/11/2015.
 */
public interface YouTubeVideoInfoRepository {
    public interface YoutubeVideoInfoCallback {
        public void onSuccess(VideoInfoData info);
        public void onError(Exception ex);
    }

    public void getInfoByID(final String id, YoutubeVideoInfoCallback callback);
}
