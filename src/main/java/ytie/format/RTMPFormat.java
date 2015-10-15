package ytie.format;

/**
 * Created by Jacob on 4/8/2015.
 */
public class RTMPFormat extends RawFormat {
    public RTMPFormat(String url) {
        super(0, "rtmp", 0, url, FormatType.RTMP);
    }
}
