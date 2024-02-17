package springsecutiry.customlogin.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/dashboard")
    String dashboard(){
        return "dashboard";
    }
    @GetMapping("/login")
    String login(){
            return "login";
        }
}
