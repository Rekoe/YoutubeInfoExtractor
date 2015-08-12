package ytie.format;

/**
 * Created by Jacob on 4/8/2015.
 */
public class DashMPFourVideoFormat extends Format {
    private final String formatNote = "DASH video";
    private final String acodec;
    private final int preference;

    //these are optional
    private int height;
    private int fps;
    private String vcodec = null;

    public DashMPFourVideoFormat(int id, String acodec, int preference, int filesize, String url) {
        super(id, "mp4", filesize, url, FormatType.DASH_MPFOUR_VIDEO);
        this.height = height;
        this.acodec = acodec;
        this.preference = preference;
    }

    public DashMPFourVideoFormat(int id, int height, String acodec, int preference, int fps, String vcodec, int filesize, String url) {
        this(id, acodec, preference, filesize, url);
        this.fps = fps;
        this.height = height;
        this.vcodec = vcodec;
    }

    @Override
    public String toString() {
        return String.format("id: %d, extension: %s { height: %d, format_note: %s, acodec: %s, preference: %d, fps: %d, vcodec: %s, filesize: %d, url: %s }",
                this.getFormatId(), this.getFormatExt(), this.height, this.formatNote, this.acodec, this.preference, this.fps, this.vcodec, this.getFilesize(), this.getURL());
    }

    public int getHeight() {
        return this.height;
    }

    public String getFormatNote() {
        return this.formatNote;
    }

    public String getAcodec() {
        return this.acodec;
    }

    public int getPreference() {
        return this.preference;
    }

    public int getFPS() {
        return this.fps;
    }

    public String getVcodec() {
        return this.vcodec;
    }
}
