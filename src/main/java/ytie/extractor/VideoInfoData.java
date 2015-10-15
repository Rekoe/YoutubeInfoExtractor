package ytie.extractor;

import ytie.format.RawFormat;

import java.util.List;
import java.util.Map;

/**
 * Created by Jacob on 4/7/2015.
 */
public class VideoInfoData {
    private String title;
    private String videoId;
    private String author;
    private String description;
    private Boolean ageGate;
    private int length;
    private int viewCount;
    private double loudness;
    private int timestamp;
    private Map<String, String> thumbnails;
    private List<String> keywords;
    private List<RawFormat> formats;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoID() {
        return videoId;
    }

    public void setVideoID(String videoId) {
        this.videoId = videoId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAgeGate() {
        return ageGate;
    }

    public void setAgeGate(Boolean ageGate) {
        this.ageGate = ageGate;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public double getLoudness() {
        return loudness;
    }

    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Map<String, String> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<RawFormat> getFormats() {
        return formats;
    }

    public void setFormats(List<RawFormat> formats) {
        this.formats = formats;
    }
}
