package com.example.coursework.Controllers;

import com.example.coursework.Data.Entities.*;
import com.example.coursework.Data.Repositories.CarRepository;
import com.example.coursework.Data.Repositories.DriverRepository;
import com.example.coursework.Services.ContractService;
import com.example.coursework.Services.EmployeeService;
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
    private DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

//    @Autowired
//    private TruckService truckService;
//
//    @Autowired
//    private OrderValidator orderValidator;

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
//        List<Truck> trucks = truckService.findAllByDescription(truckDescription);
//        Truck truck = truckService.setTruckToOrder(orderForm, trucks);
//        if (orderService.validateOrderForm(orderForm, workersBuf, numberOfWorkers, truck, bindingResult, model)) {
//            orderService.pasteOrderForm(orderForm, numberOfWorkers, model);
//            model.addAttribute("user", user);
//            model.addAttribute("again", "yes");
//            return "makeOrder";
//        }
        Driver driver = new Driver(orderForm.getLicenseNumber(), orderForm.getDrivingExperience(), user);
        Car car = new Car(orderForm.getCarNumber(), orderForm.getModel(), orderForm.getYearOfManufacture(), orderForm.getPower(), orderForm.getVehicleIdentificationNumber(), user);
        Contract contract = new Contract(user, employee, driver, car, orderForm.getPrice(), false);
        driverRepository.save(driver);
        carRepository.save(car);
        contractService.save(contract);
//        List<Contract> contracts = new ArrayList<>();
//        contracts.add(contract);
//        car.setContracts(contracts);
        return "redirect:/main";
    }

    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        contractService.delete(id);
        return "redirect:/main";
    }
}