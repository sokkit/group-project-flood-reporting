package cf.ac.uk.wrackreport.api.controllers;

import cf.ac.uk.wrackreport.domain.Report;
import cf.ac.uk.wrackreport.service.ReportOverviewService;
import cf.ac.uk.wrackreport.service.ReportService;
import cf.ac.uk.wrackreport.service.dto.ReportDTO;
import cf.ac.uk.wrackreport.service.dto.ReportOverviewDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class ReportOverviewAPIController {

    private ReportOverviewService reportOverviewService;

    public ReportOverviewAPIController(ReportOverviewService aReportOverviewService) {
        reportOverviewService = aReportOverviewService;
    }

    @GetMapping("report-overviews")
    public ResponseEntity<List<ReportOverviewDTO>> findAll() {
        List<ReportOverviewDTO> reportOverviewDTOS;
        reportOverviewDTOS = reportOverviewService.findAllReportOverview();

        return ResponseEntity.ok(reportOverviewDTOS);
    }

    @GetMapping("/report/reportQuery")
    public ResponseEntity<?> exportQuery(@RequestParam(required = false) String postcode, @RequestParam(required = false) String localAuthority, @RequestParam(required = false) String categoryName, @RequestParam(required = false) String dateFrom, @RequestParam(required = false) String dateTo, @RequestParam(required = false) Integer status, @RequestParam(required=false) String showRemoved){

        if(status == null){
            status = 0;
        }
        if(showRemoved.equals("true")){
            status = -1;
        }
        if(postcode.equals("")){
            postcode = null;
        }
        if(localAuthority.equals("")){
            localAuthority = null;
        }
        if(categoryName.equals("")){
            categoryName = null;
        }

        if(dateFrom.equals("")){
            dateFrom = null;
        }
        if(dateTo.equals("")){
            dateTo = null;
        }

        System.out.println(postcode);

        List<ReportOverviewDTO> reportOverviewDTOListList = reportOverviewService.reportQuery(postcode, localAuthority, categoryName, dateFrom, dateTo, status);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok().body(reportOverviewDTOListList);
    }

}
