package com.example.coursework.Services;

import com.example.coursework.Data.Entities.*;
import com.example.coursework.Data.Repositories.CarRepository;
import com.example.coursework.Data.Repositories.ContractRepository;
import com.example.coursework.Mail.MailSender;
import com.example.coursework.Validators.ContractFormModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.List;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractFormModelValidator contractFormModelValidator;

    @Autowired
    private CarService carService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private MailSender mailSender;

    public Contract findById(Long id) {
        return contractRepository.findContractById(id);
    }

    public void save(Contract contract) {
        User user = authorizationService.findByUsername(contract.getCustomer().getUsername());
        String message = "Здравствуйте, " + contract.getCustomer().getUsername() + ". Вами была оформлена заявка на получение страховки ОСАГО." + '\n' +
                "Информация о заявке: " + '\n' +
                "Номер автомобиля: " + contract.getCar().getCarNumber() + '\n' +
                "Модель автомобиля: " + contract.getCar().getModel() + '\n' +
                "Дата оформления заявки: " + contract.getCreationDate() + '\n' +
                "Примерная стоимость: " + contract.getPrice() + " руб." + '\n' + '\n' +
                "Спасибо, что выбрали нас!";
        mailSender.send(user.getEmail(), "Новый заказ", message);
        contractRepository.save(contract);
    }

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    public List<Contract> findAllByCustomer(User user) {
        return contractRepository.findAllByCustomer(user);
    }

    public void delete(Long id) {
        contractRepository.deleteById(id);
    }

    public List<Contract> findAllOrderByCustomer() {
        return contractRepository.findAllByOrderByCustomer();
    }

    public void pasteOrderForm(ContractFormModel orderForm, Model model) {
        model.addAttribute("carNumber_paste", orderForm.getCarNumber());
        model.addAttribute("model_paste", orderForm.getModel());
        model.addAttribute("yearOfManufacture_paste", orderForm.getYearOfManufacture());
        model.addAttribute("power_paste", orderForm.getPower());
        model.addAttribute("vehicleIdentificationNumber_paste", orderForm.getVehicleIdentificationNumber());
        model.addAttribute("licenseNumber_paste", orderForm.getLicenseNumber());
        model.addAttribute("drivingExperience_paste", orderForm.getDrivingExperience());
        model.addAttribute("price_paste", orderForm.getPrice());
    }

    public boolean validateOrderForm(ContractFormModel orderForm, BindingResult bindingResult, Model model) {
        contractFormModelValidator.validate(orderForm, bindingResult);
        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError)object;
                    model.addAttribute(fieldError.getField(), fieldError.getCode());
                }
            }

            return true;
        }
        else
            return false;
    }

    public void approveContract(Contract contract) {
        contract.setApproved(true);
        contract.setEndDate(new Date());
        String message = "Здравствуйте, " + contract.getCustomer().getUsername() + ". Вами была оформлена заявка на получение страховки ОСАГО." + '\n' +
                "Она одобрена, через 2 рабочих дня можете забрать полис ОСАГО в нашем офисе по адресу г.Москва, ул.Проспект Вернадского, д.78";
        mailSender.send(contract.getCustomer().getEmail(), "Новый заказ", message);
        contractRepository.save(contract);
    }

    public String createNewOsagoForCar(Long id, Model model) {
        Car car = carService.findCarById(id);
        ContractFormModel contractFormModel = new ContractFormModel(
                car.getCarNumber(),
                car.getModel(),
                car.getYearOfManufacture(),
                car.getPower(),
                car.getVehicleIdentificationNumber(),
                car.getOwner().getDriver().getLicenseNumber(),
                car.getOwner().getDriver().getDrivingExperience(),
                0
        );
        pasteOrderForm(contractFormModel, model);
        return "makeOrder";
    }

    public List<Contract> findAllByCustomerAndApproved(User customer, Boolean approved) {
        return contractRepository.findAllByCustomerAndApproved(customer, approved);
    }

    public String createOrder(ContractFormModel orderForm, User user, BindingResult bindingResult, Model model) {
        List<Employee> workers = employeeService.findAll();
        Employee employee = employeeService.setWorkersToOrder(workers);
        if (validateOrderForm(orderForm, bindingResult, model)) {
            pasteOrderForm(orderForm, model);
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
        Contract contract = new Contract(user, new Date(), employee, driver, car, orderForm.getPrice(), false);
        authorizationService.updateUserCarAndDriver(user, car, driver);
        driverService.saveDriver(driver);
        carService.saveCar(car);
        save(contract);
        return "redirect:/main";
    }
}
