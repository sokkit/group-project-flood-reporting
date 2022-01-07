package cf.ac.uk.wrackreport.data.jpa.repositories;

import cf.ac.uk.wrackreport.data.jpa.entities.ReportFormErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportFormErrorRepository extends JpaRepository<ReportFormErrorEntity, Long> {

    ReportFormErrorEntity save(ReportFormErrorEntity aReportFormErrorEntity);

}
