package com.example.coursework.Controllers;

import com.example.coursework.Data.Entities.*;
import com.example.coursework.Data.Repositories.CarRepository;
import com.example.coursework.Data.Repositories.DriverRepository;
import com.example.coursework.Services.ContractService;
import com.example.coursework.Services.EmployeeService;
import com.example.coursework.Validators.CarService;
import com.example.coursework.Validators.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private CarService carService;


    @GetMapping("/makeOrder")
    public String makeOrder(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("orderForm", new ContractFormModel());
        return "makeOrder";
    }

    @PostMapping("/makeOrderAction")
    public String makeOrderAction(@ModelAttribute ContractFormModel orderForm,
                                  @AuthenticationPrincipal User user,
                                  BindingResult bindingResult, Model model) {
        List<Employee> workers = employeeService.findAll();
        Employee employee = employeeService.setWorkersToOrder(workers);
        if (contractService.validateOrderForm(orderForm, bindingResult, model)) {
            contractService.pasteOrderForm(orderForm, model);
            model.addAttribute("user", user);
            model.addAttribute("again", "yes");
            return "makeOrder";
        }
        Driver driver = driverService.findByLicenseNumber(orderForm.getLicenseNumber());
        if (driver == null)
            driver = new Driver(orderForm.getLicenseNumber(), orderForm.getDrivingExperience(), user);
        Car car = carService.findByCarNumberOrVehicleIdentificationNumber(orderForm.getCarNumber(), orderForm.getVehicleIdentificationNumber());
        if (car == null)
            car = new Car(orderForm.getCarNumber(), orderForm.getModel(), orderForm.getYearOfManufacture(), orderForm.getPower(), orderForm.getVehicleIdentificationNumber(), user);
        Contract contract = new Contract(user, employee, driver, car, orderForm.getPrice(), false);
        driverService.saveDriver(driver);
        carService.saveCar(car);
        contractService.save(contract);
        return "redirect:/main";
    }

    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        contractService.delete(id);
        return "redirect:/main";
    }
}