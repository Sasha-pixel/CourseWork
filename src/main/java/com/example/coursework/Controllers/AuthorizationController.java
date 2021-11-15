package com.example.coursework.Controllers;

import com.example.coursework.Data.Entities.User;
import com.example.coursework.Services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping("/login")
    public String authorizationPage(@RequestParam(required = false) String error, @AuthenticationPrincipal User user, Model model, HttpServletRequest request) {
        return authorizationService.checkAuthority(error, user, model, request);
    }

    @GetMapping("/registration")
    public String registration(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registrationAction")
    public String registrationAction(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        return authorizationService.save(userForm, bindingResult, model);
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = authorizationService.activateUser(code);
        if (isActivated)
            model.addAttribute("activate_success", "Учётная запись успешно активирована");
        else
            model.addAttribute("activate_fail", "Активация учётной записи не удалась");
        return "login";
    }

    @GetMapping("/forgetPassword")
    public String getForgetPasswordPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "forgetPassword";
    }

    @PostMapping("/forgetPasswordAction")
    public String forgetPasswordAction(@AuthenticationPrincipal User user,
                                       @RequestParam("email") String email,
                                       Model model) {
        return authorizationService.forgetPasswordAction(user, email, model);
    }

    @GetMapping("/resetPassword/{resetPasswordToken}")
    public String getResetPasswordPage(@AuthenticationPrincipal User user,
                                       @PathVariable String resetPasswordToken,
                                       Model model) {
        model.addAttribute("user", user);
        model.addAttribute("token", resetPasswordToken);
        return "resetPassword";
    }

    @PostMapping("/resetPasswordAction")
    public String resetPasswordAction(@AuthenticationPrincipal User user,
                                      @ModelAttribute("token") String token,
                                      @ModelAttribute("password") String password,
                                      Model model) {
        return authorizationService.resetPasswordAction(user, token, password, model);
    }
}
