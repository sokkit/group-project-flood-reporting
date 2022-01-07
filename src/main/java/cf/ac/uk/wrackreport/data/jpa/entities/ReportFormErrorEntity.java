package cf.ac.uk.wrackreport.data.jpa.entities;

import cf.ac.uk.wrackreport.domain.ReportFormError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report_form_errors")
public class ReportFormErrorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long errorId;

    @Column(name = "field")
    private String field;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "date_time")
    private String datetime;


    public ReportFormErrorEntity(ReportFormError aReportFormError) {
        this.errorId = aReportFormError.getErrorId();
        this.field = aReportFormError.getField();
        this.errorMessage = aReportFormError.getErrorMessage();
        this.datetime = aReportFormError.getDatetime();
    }

    public ReportFormError toDomain() {
        ReportFormError domainReportFormError = new ReportFormError(
                this.errorId,
                this.field,
                this.errorMessage,
                this.datetime
        );
        return domainReportFormError;
    }

}
