package cf.ac.uk.wrackreport.service;

import cf.ac.uk.wrackreport.service.dto.ReportFormErrorDTO;
import org.springframework.stereotype.Service;

public interface ReportFormErrorService {

    void saveReportFormError(ReportFormErrorDTO aReportFormErrorDTO);

}
