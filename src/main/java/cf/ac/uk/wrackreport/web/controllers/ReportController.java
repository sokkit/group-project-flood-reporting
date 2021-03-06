package cf.ac.uk.wrackreport.web.controllers;

import cf.ac.uk.wrackreport.data.jpa.repositories.ReportRepository;
import cf.ac.uk.wrackreport.domain.Media;
import cf.ac.uk.wrackreport.service.CategoryService;
import cf.ac.uk.wrackreport.api.postcode.Postcode;
import cf.ac.uk.wrackreport.service.DepthCategoryService;
import cf.ac.uk.wrackreport.service.ReportFormErrorService;
import cf.ac.uk.wrackreport.service.ReportService;
import cf.ac.uk.wrackreport.service.dto.CategoryDTO;
import cf.ac.uk.wrackreport.service.dto.ReportDTO;
import cf.ac.uk.wrackreport.service.dto.ReportFormErrorDTO;
import cf.ac.uk.wrackreport.service.dto.UserDTO;
import cf.ac.uk.wrackreport.web.controllers.forms.ReportForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.hql.internal.ast.tree.ResolvableNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.naming.SizeLimitExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@ControllerAdvice
@Slf4j
public class ReportController {

    private ReportService reportService;
    private CategoryService categoryService;
    private DepthCategoryService depthCategoryService;
    private ReportRepository reportRepository;
    private ReportFormErrorService reportFormErrorService;

    public ReportController(ReportService reportService, CategoryService categoryService, DepthCategoryService depthCategoryService1, ReportRepository reportRepository, ReportFormErrorService reportFormErrorService){
        this.reportService = reportService;
        this.categoryService = categoryService;
        this.depthCategoryService = depthCategoryService1;
        this.reportRepository = reportRepository;
        this.reportFormErrorService = reportFormErrorService;
    }


    @GetMapping("/api/report/{reportID}/")
    public ResponseEntity<?> fetchReport(@PathVariable long reportID, @RequestParam String format, HttpServletRequest request) throws IOException {
        Optional<ReportDTO> report = reportService.findByReportId(reportID);
        System.out.println("is pres");
        if(report.isPresent()){
            ObjectMapper objectMapper = new ObjectMapper();
            ReportDTO reportDTO = report.get();
            System.out.println("PRESENT: " + format);
            if(format.toLowerCase(Locale.ROOT).equals("csv")){
                String output = ReportDTO.class.getFields()[0].getName();
                for(int i=1; i < ReportDTO.class.getFields().length; i++){
                    output += ", " + ReportDTO.class.getFields()[i].getName();
                }
                return ResponseEntity.ok().body(output);
            }else{
                System.out.println("XD");
                return ResponseEntity.ok().body("xd");
                //return ResponseEntity.ok().body(objectMapper.writeValueAsString(reportDTO));
            }
        }else{
            //Invalid report ID
            return ResponseEntity.badRequest().body("invalid report ID");
        }
    }

    //API for report updates
    @PostMapping("/api/report/{reportID}/confirm")
    //Only parameter required is the reportID. User will receive an error if a bad request is submitted.
    public ResponseEntity<?> confirmReport(@PathVariable long reportID){
        //Use ReportService to fetch the relevant report by its ID
        Optional<ReportDTO> reportToConfirm = reportService.findByReportId(reportID);
        //See if the report ID exists
        if(reportToConfirm.isPresent()){
            //Valid report ID
            //Now mark report as confirmed
            reportService.confirmReport(reportToConfirm.get());
            //Let user know their request was successful (200)
            return ResponseEntity.ok().build();
        }else{
            //Invalid report ID
            //Return 400
            return ResponseEntity.badRequest().body("invalid report ID");
        }
    }

    //API for report updates
    @PostMapping("/api/report/{reportID}/remove")
    //Only parameter required is the reportID. User will receive an error if a bad request is submitted.
    public ResponseEntity<?> removeReport(@PathVariable long reportID){
        //Use ReportService to fetch the relevant report by its ID
        Optional<ReportDTO> reportToRemove = reportService.findByReportId(reportID);
        //See if the report ID exists
        if(reportToRemove.isPresent()){
            //Valid report ID
            //Now mark report as confirmed
            reportService.removeReport(reportToRemove.get());
            //Let user know their request was successful (200)
            return ResponseEntity.ok().build();
        }else{
            //Invalid report ID
            //Return 400
            return ResponseEntity.badRequest().body("invalid report ID");
        }
    }

    // Route to report form
    @GetMapping("/report-form")
    public String displayReportForm(Model model) {
        ReportForm reportForm = new ReportForm();
        LocalDateTime dateTimeNow = LocalDateTime.now();

        model.addAttribute("reportForm", reportForm);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("depthCategories", depthCategoryService.findAll());
        model.addAttribute("allReports", reportService.findAllReports());
        model.addAttribute("dateTimeNow", dateTimeNow);

        return "report-form";
    }

    // Post mapping route after form submission
    @PostMapping("/report-form")
    public String submitReport (
            @Valid ReportForm reportForm,
            BindingResult bindingResult,
            Model model) throws IOException {

        // Check form doesn't have errors before form data is retrieved
        if (bindingResult.hasErrors()) {
            log.debug("THERE ARE ERRORS" + bindingResult.getAllErrors());
            System.out.println("THERE ARE ERRORS" + bindingResult.getAllErrors());
            for (ObjectError e: bindingResult.getAllErrors()
                 ) {
                // reference get field from binding result
                // https://stackoverflow.com/a/51635372/14457259
                String errorField = ((FieldError) e).getField();
                // end of reference
                String errorMsg = e.getDefaultMessage();
                String dateTime = LocalDateTime.now().toString();
                // Save errors to report for error table
                ReportFormErrorDTO reportFormErrorDTO = new ReportFormErrorDTO(null, errorField, errorMsg, dateTime);
                reportFormErrorService.saveReportFormError(reportFormErrorDTO);
            }
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("depthCategories", depthCategoryService.findAll());
            model.addAttribute("allReports", reportRepository.findAll());
            return "/report-form";
        }


        // ----- MEDIA ----- //

        //List of media to be attached to ReportDTO
        List<Media> mediaArrayList = new ArrayList<Media>();

        try {
            //Get files from form
            ArrayList<MultipartFile> formFiles = reportForm.getFiles();

            //If there are files attached from the form...
//            if (!formFiles.get(0).isEmpty()) {
                //loop through files
                for (MultipartFile f: formFiles) {
                    //Create random string
                    //Taken from https://www.baeldung.com/java-random-string
                    String generatedString = RandomStringUtils.randomAlphanumeric(20);
                    //end of reference

                    String fileName = f.getOriginalFilename();

                    //Title is name from user without extension
                    String fileTitle = FilenameUtils.removeExtension(fileName);
                    String ext = FilenameUtils.getExtension(fileName);

                    //Get type
                    List<String> videoExtensions = Arrays.asList("mp4", "mov", "avi", "mkv");
                    List<String> imageExtensions = Arrays.asList("jpg", "png", "jpeg");
                    int type = 1;

                    if (videoExtensions.contains(ext)) {
                        type = 2;
                    } else if (imageExtensions.contains(ext)) {
                        type = 1;
                    }

                   //path is random string + file extension
                    String filePath = "./images/report-media/" + generatedString + "." + ext;
                    File file = new File("src\\main\\resources\\static\\images\\report-media\\" +generatedString + "." + ext);

                    //Write file
                    try (OutputStream os = new FileOutputStream(file)) {
                        os.write(f.getBytes());
                    }
                    //Add media to list that will be added to ReportDTO
                    mediaArrayList.add(new Media(null, null, null, fileTitle,type,filePath));
                }
//            }
        } catch (IOException e) {
            throw new IOException("could not access file: " + e);
        }

        // ----- END OF MEDIA -----//


        // Create User data transfer object from form inputs
        UserDTO userDTO = new UserDTO(reportForm.getUserId(),
                "ROLE_USER",
                reportForm.getFirstName(),
                reportForm.getSurname(),
                reportForm.getEmail(),
                reportForm.getPhoneNumber(),
                null,
                true
        );

        // save user to db
//        reportService.saveUser(userDTO);

        String dtString = reportForm.getDateTime();
        String[] datetimeSplit = dtString.split("T");
        String datetime = datetimeSplit[0].concat(" " + datetimeSplit[1] + ":00");
        log.info("datetime: " + datetime);

        // if the postcode field from the form is not empty and lat long field is empty, then ...
        if ((!reportForm.getPostcode().isEmpty()) && (reportForm.getLatLong().isEmpty())) {
            // Making the postcode into lower case and removing whitespaces
            String postcodeToSearch = reportForm.getPostcode().toLowerCase().replaceAll("\\s+", "");
            // Adapted from https://www.geeksforgeeks.org/how-to-call-or-consume-external-api-in-spring-boot/
            RestTemplate restTemplate = new RestTemplate();
            Postcode result = restTemplate.getForObject("https://api.postcodes.io/postcodes/" + postcodeToSearch, Postcode.class);
            log.info("Postcode API result: " + result);

            // Retrieving lat and long from api request and storing as one String variable
            String latitude = result.getResult().getLatitude();
            String longitude = result.getResult().getLongitude();
            String latLong = latitude.concat(", " + longitude);
            log.info("Lat, Long: " + latLong);

            String localAuthority = result.getResult().getAdmin_district();
            log.info("localAuthority: "+ localAuthority);

            ReportDTO reportDTO = new ReportDTO(
                    reportForm.getReportId(),
                    RandomStringUtils.randomAlphanumeric(20),
                    userDTO,
//                    2L,
                    reportForm.getCategoryId(),
                    reportForm.getDescription(),
                    reportForm.getDepthCategoryId(),
                    reportForm.getDepthMeters(),
                    latLong,
                    datetime,
                    reportForm.getPostcode(),
                    localAuthority,
                    0,
                    mediaArrayList);

            if (bindingResult.hasErrors()) {
                model.addAttribute("categories", categoryService.findAll());
                model.addAttribute("depthCategories", depthCategoryService.findAll());
                model.addAttribute("allReports", reportRepository.findAll());
                return "/report-form";
            }

            reportService.saveReport(reportDTO);
            return "redirect:/ReportSubmitted";

            //  if the postcode field in the form is empty, then...
        } else if (reportForm.getPostcode().isEmpty() && reportForm.getLatLong().isEmpty()) {
            String errorMsg = "Please enter a location for the report!";
            log.debug(errorMsg);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("depthCategories", depthCategoryService.findAll());
            model.addAttribute("allReports", reportRepository.findAll());
            model.addAttribute("errorMsg", errorMsg);
            return "/report-form";
        } else {
            ReportDTO reportDTO = new ReportDTO(
                    reportForm.getReportId(),
                    // Add random string for path field
                    RandomStringUtils.randomAlphanumeric(20),
                    userDTO,
                    reportForm.getCategoryId(),
                    reportForm.getDescription(),
                    reportForm.getDepthCategoryId(),
                    reportForm.getDepthMeters(),
                    reportForm.getLatLong(),
                    datetime,
                    reportForm.getPostcode(),
                    reportForm.getLocalAuthority(),
                    0,
                    mediaArrayList);


            if (bindingResult.hasErrors()) {
                model.addAttribute("categories", categoryService.findAll());
                model.addAttribute("depthCategories", depthCategoryService.findAll());
                model.addAttribute("allReports", reportRepository.findAll());
                return "/report-form";
            }

            reportService.saveReport(reportDTO);
            return "redirect:/ReportSubmitted";
        }
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class, SizeLimitExceededException.class})
    public String handleFileUploadError(RedirectAttributes ra) {
        System.out.println("caught error");
        ra.addFlashAttribute("error", "You cannot upload files larger than 150MB");
        return "redirect:/report-form";
    }


}