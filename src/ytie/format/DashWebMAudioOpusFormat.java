package ytie.format;

/**
 * Created by Jacob on 4/8/2015.
 */
public class DashWebMAudioOpusFormat extends Format {
    private final String acodec;
    private final String formatNote = "DASH audio";
    private final int abr;
    private final int preference;

    public DashWebMAudioOpusFormat(int id, String acodec, int abr, int preference, int filesize, String url) {
        super(id, "webm", filesize, url, FormatType.DASH_WEBM_AUDIO_OPUS);
        this.acodec = acodec;
        this.abr = abr;
        this.preference = preference;
    }

    @Override
    public String toString() {
        return String.format("id: %d, extension: %s { format_note: %s, acodec: %s, preference: %d, abr: %d, filesize: %d, url: %d }",
                this.getFormatId(), this.getFormatExt(), this.formatNote, this.acodec, this.preference, this.abr, this.getFilesize(), this.getURL());
    }

    public String getAcodec() {
        return this.acodec;
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
