package cf.ac.uk.wrackreport.data.interfaces;

import cf.ac.uk.wrackreport.domain.*;
import cf.ac.uk.wrackreport.service.dto.ReportOverviewDTO;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface WrackReportRepository {

    void saveReport(Report aReport);
    
    ArrayList<Category> findAllCategories();

    ArrayList<DepthCategory> findAllDepthCategories();

    List<Report> findAllReports();

    Optional<Report> findByReportId(Long reportId);

    boolean checkValidCategoryID(short id);
    void saveUser(User aUser);

    void saveMedia(Media aMedia);

    List<ReportOverview> findAllReportOverview();

    Optional<StaffUser> findByEmail(String userName);

    @Procedure("ReportQuery")
    List<ReportOverview> reportQuery(String postcode, String localAuthority, String categoryName, String dateFrom, String dateTo);
}
