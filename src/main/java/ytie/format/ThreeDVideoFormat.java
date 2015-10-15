package ytie.format;

/**
 * Created by Jacob on 4/8/2015.
 */
public class ThreeDVideoFormat extends RawFormat {
    private final int height;
    private final String formatNote = "3D";
    private final int preference;

    public ThreeDVideoFormat(int id, String ext, int height, int preference, int filesize, String url) {
        super(id, ext, filesize, url, FormatType.THREED_VIDEO);
        this.height = height;
        this.preference = preference;
    }

    @Override
    public String toString() {
        return String.format("id: %d, extension: %s { height: %d, format_note: %s, preference: %d, filesize: %d, url: %s }",
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
