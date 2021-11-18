package com.example.coursework.Services;

import com.example.coursework.Data.Entities.Contract;
import com.example.coursework.Data.Entities.Role;
import com.example.coursework.Data.Entities.User;
import com.example.coursework.Data.Repositories.UserRepository;
import com.example.coursework.Mail.MailSender;
import com.example.coursework.Validators.AuthorizationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorizationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthorizationValidator authorizationValidator;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private ContractService contractService;

    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean matches(String rawPassword, String oldPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, oldPassword);
    }

    public String checkAuthority(String error, User user, Model model, HttpServletRequest request) {
        model.addAttribute("user", user);
        if (user != null)
            return "redirect:/main";
        if(error != null){
            model.addAttribute("IncorrectData", "Неправильный логин или пароль");
        }
        else{
            Cookie[] cookies = request.getCookies();
            if(cookies!=null)
                for (Cookie c: cookies){
                    if(c.getName().equals("password"))
                        model.addAttribute("password", c.getValue());
                    if(c.getName().equals("login"))
                        model.addAttribute("username", c.getValue());
                }
        }
        return "login";
    }

    public String getMainPage(User user, Model model) {
        if (user.getRoles().contains(Role.ADMIN)) {
            return "redirect:/admin/main";
        }
        else {
            if (user.getActivationCode() != null)
                model.addAttribute("notActivated", "Вы не активировали учётную запись," +
                        " в связи с этим, некоторые функции личного кабинета недоступны");
            List<Contract> userOrders = contractService.findAllByCustomer(user.getId());
            model.addAttribute("orders", null);
            model.addAttribute("user", user);
            return "mainUser";
        }
    }

    public String save(User user, BindingResult bindingResult, Model model) {
        if (validateUserForm(user, bindingResult, model)) {
            pasteUserForm(user, model);
            return "registration";
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        String message = String.format(
                "Здравствуйте, %s \n" +
                        "Пожалуйста, перейдите по следующей ссылке, чтобы активировать свою учётную запись: " +
                        "http://localhost:8080/activate/%s",
                user.getUsername(),
                user.getActivationCode()
        );
        mailSender.send(user.getEmail(), "Активация учётной записи", message);
        return "redirect:/main";
    }

    public String updatePassword(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/main";
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public void createCookie(HttpServletResponse httpServletResponse) {
        User user = findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Cookie usernameCookie = new Cookie("username", user.getUsername());
        Cookie passwordCookie = new Cookie("password", user.getPassword());
        httpServletResponse.addCookie(usernameCookie);
        httpServletResponse.addCookie(passwordCookie);
    }

    public void pasteUserForm(User userForm, Model model) {
        model.addAttribute("username_paste", userForm.getUsername());
        model.addAttribute("email_paste", userForm.getEmail());
        model.addAttribute("phoneNumber_paste", userForm.getPhoneNumber());
    }

    public boolean validateUserForm(User userForm, BindingResult bindingResult, Model model) {
        authorizationValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    model.addAttribute(fieldError.getField(), fieldError.getCode());
                }
            }
            return true;
        }
        else
            return false;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null)
            return false;
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }

    public String changingPassword(String oldPassword, String newPassword, Model model) {
        User user = findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (matches(oldPassword, user.getPassword())) {
            if (!newPassword.contains(" ")) {
                if (newPassword.length() > 5) {
                    if (oldPassword.equals(newPassword)) {
                        model.addAttribute("errorMessage", "старый и новый пароли должны отличаться");
                        model.addAttribute("user", user);
                        return "changePage";
                    } else {
                        user.setPassword(newPassword);
                        updatePassword(user);
                        return "redirect:/main";
                    }
                } else {
                    model.addAttribute("errorMessage", "пароль не должен быть короче 6 символов");
                    model.addAttribute("user", user);
                    return "changePage";
                }
            }
            else {
                model.addAttribute("errorMessage", "пароль не должен содержать пробелы");
                model.addAttribute("user", user);
                return "changePage";
            }
        }
        else {
            model.addAttribute("errorMessage", "неверный старый пароль");
            model.addAttribute("user", user);
            return "changePage";
        }
    }

    public String forgetPasswordAction(User user, String email, Model model) {
        if (email.isEmpty()) {
            model.addAttribute("user", user);
            model.addAttribute("IncorrectData", "обязательно к заполнению");
            return "forgetPassword";
        }
        if (!authorizationValidator.validateEmail(email)) {
            model.addAttribute("user", user);
            model.addAttribute("IncorrectData", "неправильный формат эл.почты");
            return "forgetPassword";
        }
        User userFromDB = userRepository.findByEmail(email);
        if (userFromDB == null) {
            model.addAttribute("user", user);
            model.addAttribute("IncorrectData", "пользователь с такой эл.почтой не зарегистрирован");
            return "forgetPassword";
        }
        userFromDB.setResetPasswordToken(UUID.randomUUID().toString());
        userRepository.save(userFromDB);
        String message = "Здравствуйте, " + userFromDB.getUsername() + '\n' +
                "Для смены пароля перейдите по ссылке: http://localhost:8080/resetPassword/" +
                userFromDB.getResetPasswordToken();
        mailSender.send(userFromDB.getEmail(), "Восстановление пароля", message);
        return "redirect:/login";
    }

    public String resetPasswordAction(User user, String token, String password, Model model) {
        if (password.isEmpty()) {
            model.addAttribute("user", user);
            model.addAttribute("IncorrectData", "обязательно к заполнению");
            return "resetPassword";
        }
        if (password.length() < 6) {
            model.addAttribute("user", user);
            model.addAttribute("IncorrectData", "пароль не должен быть короче 6 символов");
            return "resetPassword";
        }
        User userFromDB = userRepository.findByResetPasswordToken(token);
        if (userFromDB == null) {
            model.addAttribute("user", user);
            model.addAttribute("IncorrectData", "не удалось сменить пароль");
            return "resetPassword";
        }
        userFromDB.setResetPasswordToken(null);
        userFromDB.setPassword(password);
        updatePassword(userFromDB);
        model.addAttribute("reset_password_success", "пароль успешно обновлён");
        return "redirect:/login";
    }
}
