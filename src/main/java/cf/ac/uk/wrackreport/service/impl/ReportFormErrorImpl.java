package cf.ac.uk.wrackreport.service.impl;

import cf.ac.uk.wrackreport.data.interfaces.WrackReportRepository;
import cf.ac.uk.wrackreport.service.ReportFormErrorService;
import cf.ac.uk.wrackreport.service.dto.ReportFormErrorDTO;
import org.springframework.stereotype.Service;

@Service
public class ReportFormErrorImpl implements ReportFormErrorService {

    private WrackReportRepository wrackReportRepository;

    public ReportFormErrorImpl(WrackReportRepository aRepo) {
        wrackReportRepository = aRepo;
    }

    public void saveReportFormError(ReportFormErrorDTO aReportFormErrorDTO) {
        wrackReportRepository.saveReportFormError(aReportFormErrorDTO.toReportFormError());
    }

}
