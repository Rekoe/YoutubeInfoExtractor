package ytie.format;

/**
 * Created by Jacob on 4/8/2015.
 */
public class DashWebMAudioFormat extends RawFormat {
    private final String vcodec;
    private final String formatNote = "DASH audio";
    private final int abr;
    private final int preference;

    public DashWebMAudioFormat(int id, String vcodec, int abr, int preference, int filesize, String url) {
        super(id, "webm", filesize, url, FormatType.DASH_WEBM_AUDIO);
        this.vcodec = vcodec;
        this.abr = abr;
        this.preference = preference;
    }

    @Override
    public String toString() {
        return String.format("id: %d, extension: %s { format_note: %s, vcodec: %s, preference: %d, abr: %d, filesize: %d, url: %s }",
                this.getFormatId(), this.getFormatExt(), this.formatNote, this.vcodec, this.preference, this.abr, this.getFilesize(), this.getURL());
    }

    public String getVcodec() {
        return this.vcodec;
    }

    public String getFormatNote() {
        return this.formatNote;
    }

    public int getAbr() {
        return this.abr;
    }

    public int getPreference() {
        return this.preference;
    }
}
