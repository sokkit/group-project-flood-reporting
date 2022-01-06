package cf.ac.uk.wrackreport.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping({"/login"})
    public String displayLogin() {
        return "login.html";
    }

}
