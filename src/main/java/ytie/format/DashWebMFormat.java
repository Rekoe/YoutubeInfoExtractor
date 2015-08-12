package ytie.format;

/**
 * Created by Jacob on 4/8/2015.
 */
public class DashWebMFormat extends Format {
    private final int height;
    private final int preference;
    private final String formatNote = "DASH video";
    private final String acodec;

    private String container;
    private int width;
    private String vcodec;

    public DashWebMFormat(int id, int height, int preference, String acodec, int filesize, String url) {
        super(id, "webm", filesize, url, FormatType.DASH_WEBM);
        this.height = height;
        this.preference = preference;
        this.acodec = acodec;
    }

    public DashWebMFormat(int id, int height, int width, int preference, String acodec, String container, String vcodec, int filesize, String url) {
        this(id, height, preference, acodec, filesize, url);
        this.width = width;
        this.container = container;
        this.vcodec = vcodec;
    }

    @Override
    public String toString() {
        return String.format("id: %d, extension: %s { height: %d, width: %d, format_note: %s, acodec: %s, preference: %d, vcodec: %s, container: %s filesize: %d, url: %s }",
                this.getFormatId(), this.getFormatExt(), this.height, this.width, this.formatNote, this.acodec, this.preference, this.vcodec, this.container, this.getFilesize(), this.getURL());
    }

    public int getHeight() {
        return this.height;
    }

    public int getPreference() {
        return this.preference;
    }

    public String getFormatNote() {
        return this.formatNote;
    }

    public String getAcodec() {
        return this.acodec;
    }

    public String getContainer() {
        return this.container;
    }

    public String getVcodec() {
        return this.vcodec;
    }
}
