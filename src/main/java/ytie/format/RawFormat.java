package ytie.format;

/**
 * Created by Jacob on 4/8/2015.
 */
public class RawFormat {
    private final int formatId;
    private final String formatExt;
    private final FormatType formatType;
    private final int filesize;
    private final String url;

    public RawFormat(int id, String ext, int filesize, String url, FormatType type) {
        this.formatId = id;
        this.formatExt = ext;
        this.formatType = type;
        this.filesize = filesize;
        this.url = url;
    }

    public String toString() {
        return String.format("id: %d, extension: %s, { filesize: %d, url: %s }",
                this.formatId, this.formatExt, this.filesize, this.url);
    }

    public int getFormatId() {
        return this.formatId;
    }

    public String getFormatExt() {
        return this.formatExt;
    }

    public FormatType getFormatType() {
        return this.formatType;
    }

    public int getFilesize() {
        return this.filesize;
    }

    public String getURL() {
        return this.url;
    }
}
