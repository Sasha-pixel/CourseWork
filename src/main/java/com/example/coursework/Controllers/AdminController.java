package com.example.coursework.Controllers;

import com.example.coursework.Data.Entities.Employee;
import com.example.coursework.Data.Entities.User;
import com.example.coursework.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/main")
    public String adminPage(@AuthenticationPrincipal User user, Model model) {
        return adminService.getAdminPage(user, model);
    }

    @GetMapping("/searchByUsername")
    public String searchForUsername(@AuthenticationPrincipal User user, @RequestParam("username") String username, Model model) {
        adminService.checkUserActivationCode(user, model);
        return adminService.searchUsers(username, model);
    }

    @GetMapping("/searchEmployee")
    public String searchEmployee(@AuthenticationPrincipal User user, @RequestParam("employee") String employee, Model model) {
        adminService.checkUserActivationCode(user, model);
        return adminService.checkEmployees(employee, model);
    }

    @GetMapping("addNewEmployeeOrCar")
    public String getPageAddNewEmployeeOrCar(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("employee-form", new Employee());
        return "newEmployee";
    }

    @PostMapping("/addEmployee")
    public String addNewEmployee(@ModelAttribute("employee-form") Employee employee,
                                 @AuthenticationPrincipal User user,
                                 BindingResult bindingResult,
                                 Model model) {
        return adminService.addingEmployee(employee, user, bindingResult, model);
    }
}
