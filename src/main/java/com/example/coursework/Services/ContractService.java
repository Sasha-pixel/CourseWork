package com.example.coursework.Services;

import com.example.coursework.Data.Entities.Car;
import com.example.coursework.Data.Entities.Contract;
import com.example.coursework.Data.Entities.Employee;
import com.example.coursework.Data.Entities.User;
import com.example.coursework.Data.Repositories.ContractRepository;
import com.example.coursework.Mail.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private OrderValidator orderValidator;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private MailSender mailSender;

    public void save(Contract contract) {
        User user = authorizationService.findByUsername(contract.getCustomerUsername());
        String message = "Здравствуйте, " + contract.getCustomerUsername() + ". Вами был оформлен заказ." + '\n' +
                "Информация о заказе: " + '\n' +
                "Пункт отправки: " + contract.getAddressFrom() + '\n' +
                "Пункт назначения: " + contract.getAddressTo() + '\n' +
                "Дата оформления заказа: " + contract.getCreationDate() + '\n' +
                "Дата выполнения заказа: " + contract.getTargetDate() + '\n' +
                "Примерная стоимость: " + contract.getPrice() + " руб." + '\n' + '\n' +
                "Спасибо, что выбрали нас!";
        mailSender.send(user.getEmail(), "Новый заказ", message);
        contractRepository.save(contract);
    }

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    public List<Contract> findAllByCustomerUsername(String customerUsername) {
        return contractRepository.findAllByCustomerUsername(customerUsername);
    }

    public void delete(Long id) {
        contractRepository.deleteById(id);
    }

    public List<Contract> findAllByOrderByCustomerUsername() {
        return contractRepository.findAllByOrderByCustomerUsername();
    }

    public void pasteOrderForm(Contract orderForm, int numberOfWorkers, Model model) {
        model.addAttribute("addressFrom_paste", orderForm.getAddressFrom());
        model.addAttribute("addressTo_paste", orderForm.getAddressTo());
        model.addAttribute("distance_paste", orderForm.getDistance());
        model.addAttribute("duration_paste", orderForm.getDuration());
        model.addAttribute("targetDate_paste", orderForm.getTargetDate());
        model.addAttribute("hours_paste", orderForm.getTargetTime().split(":")[0]);
        model.addAttribute("minutes_paste", orderForm.getTargetTime().split(":")[1]);
        model.addAttribute("targetTime_paste", orderForm.getTargetTime());
        model.addAttribute("numberOfWorkers_paste", numberOfWorkers);
        model.addAttribute("price_paste", orderForm.getPrice());
    }

    public boolean validateOrderForm(Contract orderForm, List<Employee> workersBuf, int numberOfWorkers, Car car, BindingResult bindingResult, Model model) {
        orderValidator.customValidate(orderForm, numberOfWorkers, workersBuf, car, bindingResult);
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

    public List<Contract> searchOrdersByUsername(String username) {
        return contractRepository.findAllByCustomerUsernameContainingIgnoreCase(username);
    }
}
