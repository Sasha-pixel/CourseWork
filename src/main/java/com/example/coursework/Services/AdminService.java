package com.example.coursework.Services;

import com.example.coursework.Data.Entities.Contract;
import com.example.coursework.Data.Entities.Employee;
import com.example.coursework.Data.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ContractService contractService;

    public String getAdminPage(User user, Model model) {
        if (user.getActivationCode() != null)
            model.addAttribute("notActivated", "Вы не активировали учётную запись," +
                    " в связи с этим, некоторые функции личного кабинета недоступны");
        model.addAttribute("user", user);
        model.addAttribute("isChecking", "users");
        model.addAttribute("allUsersOrders", contractService.findAllOrderByCustomer());
        model.addAttribute("allEmployees", employeeService.findAll());
        return "admin";
    }

    public void checkUserActivationCode(User user, Model model) {
        if (user.getActivationCode() != null)
            model.addAttribute("notActivated", "Вы не активировали учётную запись," +
                    " в связи с этим, некоторые функции личного кабинета недоступны");
        model.addAttribute("user", user);
    }

    public String searchUsers(String username, Model model) {
        if (username.isEmpty()) {
            return "redirect:/admin/main";
        }
        else {
            List<Contract> contracts = new ArrayList<>();
            for (Contract contract : contractService.findAll()) {
                if (contract.getCustomer().getUsername().toUpperCase().contains(username.toUpperCase()))
                    contracts.add(contract);
            }
            model.addAttribute("username", username);
            model.addAttribute("isChecking", "users");
            model.addAttribute("allUsersOrders", contracts);
            model.addAttribute("allEmployees", employeeService.findAll());
            return "admin";
        }
    }

    public String checkEmployees(String employee, Model model) {
        if (employee.isEmpty()) {
            return "redirect:/admin/main";
        }
        else {
            List<Employee> employeeList = new ArrayList<>();
            for (Employee employee1 : employeeService.findAll()) {
                if (employee1.getName().toUpperCase().contains(employee.toUpperCase()))
                    employeeList.add(employee1);
            }
            model.addAttribute("employee", employee);
            model.addAttribute("isChecking", "employees");
            model.addAttribute("allEmployees", employeeList);
            model.addAttribute("allUsersOrders", contractService.findAllOrderByCustomer());
            return "admin";
        }
    }

    public String addingEmployee(Employee employee, User user, BindingResult bindingResult, Model model) {
        if (!employeeService.validateEmployee(employee, bindingResult, model)) {
            employeeService.save(employee);
            return "redirect:/admin/main";
        }
        else {
            model.addAttribute("name_paste", employee.getName());
            model.addAttribute("user", user);
            return "newEmployee";
        }
    }
}
