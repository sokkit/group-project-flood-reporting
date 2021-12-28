package cf.ac.uk.wrackreport.jpa;

import cf.ac.uk.wrackreport.data.jpa.entities.*;
import cf.ac.uk.wrackreport.data.jpa.repositories.ReportOverviewRepository;
import cf.ac.uk.wrackreport.data.jpa.repositories.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@DirtiesContext
public class ReportOverviewJPATests {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ReportOverviewRepository reportOverviewRepository;

    @Test
    public void shouldGet12ReportOverviews() throws Exception {
        ArrayList<ReportOverviewEntity> reportOverviewEntities = reportOverviewRepository.findAll();
        System.out.println("List of categories: "+reportOverviewEntities);

        assertEquals(12, reportOverviewEntities.size());
    }

    @Test
    public void shouldFindInsertedReport() throws Exception {
        List<ReportOverviewEntity> reportOverviewEntitiesBeforeInsert = reportOverviewRepository.reportQuery("CF24 4LR", "Cardiff", "River Flooding", "2021-11-18 22:20:00", "2021-11-20 22:20:00", -1);
        assertEquals(0, reportOverviewEntitiesBeforeInsert.size());

        List<MediaEntity> media = new ArrayList<MediaEntity>();
        UserEntity user = new UserEntity(null, "ROLE_USER", "firstname", "lastname", "test@gmail.com", "07888747748", null, true);
        MediaEntity testMedia = new MediaEntity(null,null,null,"testMedia",1,"testpath");
        media.add(testMedia);
        ReportEntity reportEntity = new ReportEntity(null, user, (short)2, "test desc", (short)2, 0.2f, "51.896156,-3.933956", "2021-11-19 22:20:00", "CF24 4LR", "Cardiff", 0, media);
        reportRepository.save(reportEntity);

        List<ReportOverviewEntity> reportOverviewEntitiesAfterInsert = reportOverviewRepository.reportQuery("CF24 4LR", "Cardiff", "River Flooding", "2021-11-18 22:20:00", "2021-11-20 22:20:00", -1);
        assertEquals(1, reportOverviewEntitiesAfterInsert.size());
    }

    @ParameterizedTest
    @CsvSource({"Cardiff,1","Powys, 2", "Gwynedd, 2", "test 0, 0"})
    public void shouldGetNReportOverviewsFromLocalAuthority(String search, Integer count) throws Exception{

        List<ReportOverviewEntity> reportOverviewEntities = reportOverviewRepository.reportQuery(null, search, null, null, null, -1);
        assertEquals(count, reportOverviewEntities.size());
    }

}
