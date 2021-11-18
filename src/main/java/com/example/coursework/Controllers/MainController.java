package com.example.coursework.Controllers;

import com.example.coursework.Data.Entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private OrderService orderService;

    /**
     * Возврат домашней страницы
     *
     * @return a {@link java.lang.String} object.
     */
    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        if (user != null) {
            if (user.getRoles().contains(Role.USER))
                model.addAttribute("userRole", "user");
        }
        return "home";
    }

    @GetMapping("/main")
    public String mainPage(HttpServletResponse httpServletResponse,
                           @AuthenticationPrincipal User user,
                           Model model) {
        return authorizationService.getMainPage(user, model);
    }

    @GetMapping("/changePassword")
    public String changePassword(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "changePage";
    }

    @PostMapping("/changePasswordAction")
    public String changePasswordAction(@ModelAttribute("old_password") String oldPassword,
                                       @ModelAttribute("password") String newPassword,
                                       Model model) {
        return authorizationService.changingPassword(oldPassword, newPassword, model);
    }
}
