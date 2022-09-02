package pl.coderslab.charity.user;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Log4j2
@Controller
public class UserAuthorizationController {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    private final String USER = "user";

    public UserAuthorizationController(UserService userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/login")
    public String initLogin() {
        return "authorization/login";
    }

    @GetMapping("/register")
    public String initRegistration(Model model) {
        model.addAttribute(USER, new User());
        return "authorization/register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.warn(bindingResult.getAllErrors());
            return "authorization/register";
        }
        userService.saveUser(user);
        return "redirect:/donate";
    }
}
