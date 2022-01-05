package cf.ac.uk.wrackreport.service.dto;

import cf.ac.uk.wrackreport.domain.ReportOverview;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor

public class ReportOverviewDTO {
    Long reportId;
    String reportPath;
    String datetime;
    String categoryName;
    Float depthMeters;
    String postcode;
    String localAuthority;
    int status;

    public ReportOverviewDTO(ReportOverview reportOverview){
        this(
                reportOverview.getReportId(),
                reportOverview.getReportPath(),
                reportOverview.getDatetime(),
                reportOverview.getCategoryName(),
                reportOverview.getDepthMeters(),
                reportOverview.getPostcode(),
                reportOverview.getLocalAuthority(),
                reportOverview.getStatus()
        );

    }
    public ReportOverview toReportOverview(){
        return new ReportOverview(reportId, reportPath, datetime,categoryName,depthMeters,postcode,localAuthority,status);
    }

}
