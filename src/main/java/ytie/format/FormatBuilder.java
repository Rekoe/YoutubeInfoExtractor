package ytie.format;

/**
 * Created by Jacob on 4/9/2015.
 */
public class FormatBuilder {
    private int formatId;
    private String formatExt;
    private int filesize;
    private String url;

    private int height;
    private int width;
    private int fps;

    private String acodec;
    private String vcodec;
    private int preference;

    private int abr;
    private Boolean rtmp = false;

    private String container;

    public FormatBuilder() {

    }

    public FormatBuilder rtmp(Boolean rtmp) {
        this.rtmp = rtmp;
        return this;
    }

    public FormatBuilder formatId(int id) {
        this.formatId = id;
        return this;
    }

    public FormatBuilder formatExt(String ext) {
        this.formatExt = ext;
        return this;
    }

    public FormatBuilder filesize(int filesize) {
        this.filesize = filesize;
        return this;
    }

    public FormatBuilder url(String url) {
        this.url = url;
        return this;
    }

    public FormatBuilder height(int height) {
        this.height = height;
        return this;
    }

    public FormatBuilder width(int width) {
        this.width = width;
        return this;
    }

    public FormatBuilder abr(int abr) {
        this.abr = abr;
        return this;
    }

    public FormatBuilder fps(int fps) {
        this.fps = fps;
        return this;
    }

    public FormatBuilder acodec(String acodec) {
        this.acodec = acodec;
        return this;
    }

    public FormatBuilder vcodec(String vcodec) {
        this.vcodec = vcodec;
        return this;
    }

    public FormatBuilder container(String container) {
        this.container = container;
        return this;
    }

    public FormatBuilder preference(int preference) {
        this.preference = preference;
        return this;
    }

    public RawFormat build() {
        RawFormat format = null;
        switch(this.formatId) {
            case 5:
                return new StandardFormat(this.formatId, "flv", 400, 240, this.filesize, this.url);
            case 6:
                return new StandardFormat(this.formatId, "flv", 450, 270, this.filesize, this.url);
            case 13:
                return new StandardFormat(this.formatId, "3gp", this.filesize, this.url);
            case 17:
                return new StandardFormat(this.formatId, "3gp", 176, 144, this.filesize, this.url);
            case 18:
                return new StandardFormat(this.formatId, "mp4", 640, 360, this.filesize, this.url);
            case 22:
                return new StandardFormat(this.formatId, "mp4", 1280, 720, this.filesize, this.url);
            case 34:
                return new StandardFormat(this.formatId, "flv", 640, 360, this.filesize, this.url);
            case 35:
                return new StandardFormat(this.formatId, "flv", 854, 480, this.filesize, this.url);
            case 36:
                return new StandardFormat(this.formatId, "3gp", 320, 240, this.filesize, this.url);
            case 37:
                return new StandardFormat(this.formatId, "mp4", 1920, 1080, this.filesize, this.url);
            case 38:
                return new StandardFormat(this.formatId, "mp4", 4096, 3072, this.filesize, this.url);
            case 43:
                return new StandardFormat(this.formatId, "webm", 640, 360, this.filesize, this.url);
            case 44:
                return new StandardFormat(this.formatId, "webm", 854, 480, this.filesize, this.url);
            case 45:
                return new StandardFormat(this.formatId, "webm", 1280, 720, this.filesize, this.url);
            case 46:
                return new StandardFormat(this.formatId, "webm", 1920, 1080, this.filesize, this.url);
        }

        if ((this.formatId >= 82 && this.formatId <= 85) ||
                (this.formatId >= 100 && this.formatId <= 102)) {
            format = new ThreeDVideoFormat(this.formatId, this.formatExt, this.height, this.preference, this.filesize, this.url);
        } else if ((this.formatId >= 92 && this.formatId <= 96) ||
                this.formatId == 132 ||
                this.formatId == 151) {
            format = new AppleLiveStreamingFormat(this.formatId, this.height, this.preference, this.filesize, this.url);
        } else if ((this.formatId >= 133 && this.formatId <= 138) ||
                this.formatId == 160 ||
                this.formatId == 264 ||
                this.formatId == 298 ||
                this.formatId == 299 ||
                this.formatId == 266) {
            format = new DashMPFourVideoFormat(
                    this.formatId, this.height, this.acodec, this.preference, this.fps, this.vcodec, this.filesize, this.url);
        } else if (this.formatId >= 139 || this.formatId <= 141) {
            format = new DashMPFourAudioFormat(this.formatId, this.vcodec, this.acodec, this.abr, this.preference, this.filesize, this.url);
        } else if ((this.formatId >= 167 && this.formatId <= 170) ||
                this.formatId == 218 ||
                this.formatId == 219 ||
                this.formatId == 278 ||
                (this.formatId >= 242 && this.formatId <= 248) ||
                this.formatId == 271 ||
                this.formatId == 272 ||
                this.formatId == 302 ||
                this.formatId == 303 ||
                this.formatId == 308 ||
                this.formatId == 313 ||
                this.formatId == 315) {
            format = new DashWebMFormat(
                    this.formatId, this.height, this.width, this.preference, this.acodec, this.container, this.vcodec,
                    this.filesize, this.url);
        } else if (this.formatId == 171 || this.formatId == 172) {
            format = new DashWebMAudioFormat(this.formatId, this.vcodec, this.abr, this.preference, this.filesize, this.url);
        } else if (this.formatId >= 249 && this.formatId <= 251) {
            format = new DashWebMAudioOpusFormat(this.formatId, this.acodec, this.abr, this.preference, this.filesize, this.url);
        } else if (this.rtmp) {
            format = new RTMPFormat(this.url);
        }
        return format;
    }
}
