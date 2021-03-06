package cf.ac.uk.wrackreport.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private Long reportId;
    private String reportPath;
    private User user;
    private short categoryId;
    private String description;
    private short depthCategoryId;
    private Float depthMeters;
    private String latLong;
    private String datetime;
    private String postcode;
    private String localAuthority;
    private List<Media> media;
    private int status;

    public Report(Long reportId, String reportPath, User user, short categoryId, String description, short depthCategoryId, Float depthMeters, String latLong, String datetime, String postcode, String localAuthority, int status) {
        this.reportId = reportId;
        this.reportPath = reportPath;
        this.user = user;
        this.categoryId = categoryId;
        this.description = description;
        this.depthCategoryId = depthCategoryId;
        this.depthMeters = depthMeters;
        this.latLong = latLong;
        this.datetime = datetime;
        this.postcode = postcode;
        this.localAuthority = localAuthority;
        this.media = new ArrayList<Media>();
        this.status = status;
    }

    public void addMedia(Media aMedia) {
        media.add(aMedia);
    }


}
