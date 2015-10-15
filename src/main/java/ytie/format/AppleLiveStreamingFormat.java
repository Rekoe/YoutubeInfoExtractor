package ytie.format;

/**
 * Created by Jacob on 4/8/2015.
 */
public class AppleLiveStreamingFormat extends RawFormat {
    private final int height;
    private final String formatNote = "HLS";
    private final int preference;

    public AppleLiveStreamingFormat(int id, int height, int preference, int filesize, String url) {
        super(id, "mp4", filesize, url, FormatType.APPLE_LIVE_STREAMING);
        this.height = height;
        this.preference = preference;
    }

    @Override
    public String toString() {
        return String.format("id: %d, extension: %s { height: %d, format_note: %s, preference: %d, filesize: %d, url: %d }",
                this.getFormatId(), this.getFormatExt(), this.height, this.formatNote, this.preference, this.getFilesize(), this.getURL());
    }

    public int getHeight() {
        return this.height;
    }

    public String getFormatNote() {
        return this.formatNote;
    }

    public int getPreference() {
        return this.preference;
    }
}
