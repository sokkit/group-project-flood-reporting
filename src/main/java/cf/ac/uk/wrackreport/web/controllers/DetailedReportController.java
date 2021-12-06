package cf.ac.uk.wrackreport.web.controllers;

import cf.ac.uk.wrackreport.service.DetailedReportService;
import cf.ac.uk.wrackreport.service.ReportService;
import cf.ac.uk.wrackreport.service.dto.DetailedReportDTO;
import cf.ac.uk.wrackreport.service.dto.ReportDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Optional;

@Controller
public class DetailedReportController {

    private DetailedReportService detailedReportService;

    public DetailedReportController(DetailedReportService aDetailedReportService) {
        this.detailedReportService = aDetailedReportService;
    }

    @GetMapping("/detailed-report/{furl}")
    public String showDetailedReportByID(@PathVariable(value = "furl", required = true) Long reportId, Model model) {
        Optional<DetailedReportDTO> detailedReportDTO;
        detailedReportDTO = detailedReportService.findAllByReportId(reportId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities());

        SimpleGrantedAuthority adminAuth = new SimpleGrantedAuthority("ROLE_ADMIN");
        SimpleGrantedAuthority staffAuth = new SimpleGrantedAuthority("ROLE_STAFF");

        Boolean auth = authentication.getAuthorities().contains(adminAuth) || authentication.getAuthorities().contains(staffAuth);

        if (detailedReportDTO.isPresent()) {
            model.addAttribute("report", detailedReportDTO.get());
            model.addAttribute("authenticated", auth);
            return "detailed-report";
        } else {
            return "redirect:/";
        }
    }
}
