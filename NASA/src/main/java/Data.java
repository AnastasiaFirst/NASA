import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Data {
    private String copyright;
    private Date date;
    private String explanation;
    private String hdurl;
    private String media_type;
    private String service_version;
    private String title;
    private String url;

    public Data(
            @JsonProperty("copyright") String copyright,
            @JsonProperty("date") Date date,
            @JsonProperty("explanation") String explanation,
            @JsonProperty("hdurl") String hdurl,
            @JsonProperty("media_type") String media_type,
            @JsonProperty("service_version") String service_version,
            @JsonProperty("title") String title,
            @JsonProperty("url") String url
    ) {

        this.url = url;
        this.title = title;
        this.service_version = service_version;
        this.media_type = media_type;
        this.hdurl = hdurl;
        this.explanation = explanation;
        this.date = date;
        this.copyright = copyright;
    }

    public String getCopyright() {
        return copyright;
    }

    public Date getDate() { return date; }

    public String getExplanation() {
        return explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public String getMediaType() {
        return media_type;
    }

    public String getServiceVersion() {
        return service_version;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Data" +
                "\n copyright=" + copyright +
                "\n date=" + date +
                "\n explanation=" + explanation +
                "\n hdurl=" + hdurl +
                "\n media_type=" + media_type +
                "\n service_version=" + service_version +
                "\n title=" + title +
                "\n url=" + url;
    }
}
