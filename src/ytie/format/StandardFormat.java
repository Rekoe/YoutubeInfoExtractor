package ytie.format;

/**
 * Created by Jacob on 4/8/2015.
 */
public class StandardFormat extends Format {
    private int width = 0;
    private int height = 0;

    public StandardFormat(int id, String ext, int filesize, String url) {
        super(id, ext, filesize, url, FormatType.STANDARD);
    }

    public StandardFormat(int id, String ext, int width, int height, int filesize, String url) {
        this(id, ext, filesize, url);
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("id: %d, extension: %s { width: %d, height: %d, url: %s }",
                this.getFormatId(), this.getFormatExt(), this.width, this.height, this.getURL());
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
