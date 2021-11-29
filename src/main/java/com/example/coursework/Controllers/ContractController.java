package com.example.coursework.Controllers;

import com.example.coursework.Data.Entities.ContractFormModel;
import com.example.coursework.Data.Entities.User;
import com.example.coursework.Services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping("/makeOrder")
    public String makeOrder(@AuthenticationPrincipal User user, Model model) {
        if (!user.getActivationCode().isEmpty())
            return "redirect:/main";
        model.addAttribute("user", user);
        model.addAttribute("orderForm", new ContractFormModel());
        return "makeOrder";
    }

    @PostMapping("/makeOrderAction")
    public String makeOrderAction(@ModelAttribute ContractFormModel orderForm,
                                  @AuthenticationPrincipal User user,
                                  BindingResult bindingResult, Model model) {
        return contractService.createOrder(orderForm, user, bindingResult, model);
    }

    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        contractService.delete(id);
        return "redirect:/main";
    }

    @GetMapping("/createNewOsago/{id}")
    public String createNewOsagoForCar(@PathVariable Long id, Model model) {
        return contractService.createNewOsagoForCar(id, model);
    }
}