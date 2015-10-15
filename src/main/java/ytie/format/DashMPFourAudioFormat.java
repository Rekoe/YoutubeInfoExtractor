package ytie.format;

/**
 * Created by Jacob on 4/8/2015.
 */
public class DashMPFourAudioFormat extends RawFormat {
    private final String acodec;
    private final String vcodec;
    private final String formatNote = "DASH audio";
    private final int abr;
    private final int preference;
    private final String container = "m4a_dash";

    public DashMPFourAudioFormat(int id, String acodec, String vcodec, int abr, int preference, int filesize, String url) {
        super(id, "m4a", filesize, url, FormatType.DASH_MPFOUR_AUDIO);
        this.acodec = acodec;
        this.vcodec = vcodec;
        this.abr = abr;
        this.preference = preference;
    }

    @Override
    public String toString() {
        return String.format("id: %d, extension: %s { format_note: %s, acodec: %s, preference: %d, abr: %d, container: %s filesize: %d, url: %s }",
                this.getFormatId(), this.getFormatExt(), this.formatNote, this.acodec, this.preference, this.abr, this.container, this.getFilesize(), this.getURL());
    }

    public String getAcodec() {
        return this.acodec;
    }

    public String getVcodec() {
        return this.vcodec;
    }

    public int getABR() {
        return this.abr;
    }

    public String getFormatNote() {
        return this.formatNote;
    }

    public int getPreference() {
        return this.preference;
    }

    public String getContainer() {
        return container;
    }
}
