package cf.ac.uk.wrackreport.web.controllers;

import cf.ac.uk.wrackreport.service.ReportService;
import cf.ac.uk.wrackreport.service.dto.ReportDTO;
import cf.ac.uk.wrackreport.service.dto.UserDTO;
import cf.ac.uk.wrackreport.web.controllers.forms.ReportForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/report-form")
    public String displayReportForm(Model model) {
        ReportForm reportForm = new ReportForm();
        LocalDateTime dateTimeNow = LocalDateTime.now();

        model.addAttribute("reportForm", reportForm);
        model.addAttribute("dateTimeNow", dateTimeNow);

        return "report-form";
    }

    @PostMapping("/report-form")
    public String submitReport (
            @Valid ReportForm reportForm,
            BindingResult bindingResult,
            Model model) {

//        Create data transfer object from form inputs
        UserDTO userDTO = new UserDTO(reportForm.getUserId(),
                1,
                reportForm.getFirstName(),
                reportForm.getSurname(),
                reportForm.getEmail(),
                reportForm.getPhoneNumber()
        );
//        check for errors
        if (bindingResult.hasErrors()) {
            System.out.println("THERE ARE ERRORS" + bindingResult.getAllErrors());
            return "/report-form";
        }
//        save user to db
        reportService.saveUser(userDTO);


        String datetime = reportForm.getDate().concat(" "+reportForm.getTime()+":00");
        System.out.println(datetime);

                ReportDTO reportDTO = new ReportDTO(
                        reportForm.getReportId(),
//                        1L,
//                        reportForm.getUserId(),
                        2L,
//                        reportForm.getCategoryId(),
                        3L,
                        reportForm.getDescription(),
//                        reportForm.getLatLong(),
                        "123,123",
                        datetime,
                        reportForm.getPostcode());

            if (bindingResult.hasErrors()) {
                return "/report-form";
            }

            reportService.saveReport(reportDTO);
            return "redirect:/";
    }

        reportService.saveReport(reportDTO);
        return "redirect:/";
    }
}


