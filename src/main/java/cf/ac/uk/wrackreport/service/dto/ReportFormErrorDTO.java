package cf.ac.uk.wrackreport.service.dto;

import cf.ac.uk.wrackreport.domain.ReportFormError;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ReportFormErrorDTO {

    Long errorId;
    String field;
    String errorMessage;
    String datetime;

    public ReportFormErrorDTO(ReportFormError aReportFormError) {
        this (
                aReportFormError.getErrorId(),
                aReportFormError.getField(),
                aReportFormError.getErrorMessage(),
                aReportFormError.getDatetime()
        );
    }

    public ReportFormError toReportFormError() {
        return new ReportFormError(errorId, field, errorMessage, datetime);
    }

}
