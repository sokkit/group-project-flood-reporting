package cf.ac.uk.wrackreport.fullcontainer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class ReportOverviews {

    @Autowired
    MockMvc mvc;

    @Test
    public void shouldGet12ReportOverviewsAsJSON() throws Exception {
        mvc.perform(get("/api/report-overviews").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(12)));
    }

    @ParameterizedTest
    @CsvSource({"SY18 6RG,1","CG23 9JK, 0", "CF47 8EU, 1"})
    public void shouldGetNReportOverviewsFromPostcodeAsJSON(String search, String countAsString) throws Exception{
        Integer count = Integer.valueOf(countAsString);
        mvc.perform(get("/api/report/exportQuery?postcode="+search+"&localAuthority=&categoryName=&dateFrom=&dateTo=&showRemoved=false").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(count)));

    }

    @ParameterizedTest
    @CsvSource({"Flash Flooding,2","Coastal Flooding, 2", "test 0, 0"})
    public void shouldGetNReportOverviewsFromCategoryNameAsJSON(String search, String countAsString) throws Exception{
        Integer count = Integer.valueOf(countAsString);
        mvc.perform(get("/api/report/exportQuery?postcode=&localAuthority=&categoryName="+search+"&dateFrom=&dateTo=&showRemoved=false").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(count)));

    }

}
