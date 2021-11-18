package com.example.coursework.Services;

import com.example.coursework.Data.Entities.Contract;
import com.example.coursework.Data.Entities.Employee;
import com.example.coursework.Data.Repositories.EmployeeRepository;
import com.example.coursework.Validators.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeValidator employeeValidator;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee findByName(String name) {
        return employeeRepository.findByName(name);
    }

    public List<Employee> searchEmployee(String employee) {
        return employeeRepository.findByNameContainingIgnoreCase(employee);
    }

    public List<Contract> getAllEmployeesOrders() {
        List<Contract> orders = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            orders.addAll(employee.getContracts());
        }
        return orders;
    }

    public List<Employee> setWorkersToOrder(Contract orderForm, List<Employee> workers) {
        List<Employee> workersBuf = new ArrayList<>();
        boolean flag;
        for (Employee worker : workers) {
            flag = true;
            if (worker.getContracts().isEmpty())
                workersBuf.add(worker);
            else {
                for (Contract contract : worker.getContracts()) {
                    if (contract.getTargetDate().equals(orderForm.getTargetDate()))
                        flag = false;
                }
                if (flag)
                    workersBuf.add(worker);
            }
        }
        return workersBuf;
    }

    public boolean validateEmployee(Employee employee, BindingResult bindingResult, Model model) {
        employeeValidator.validate(employee, bindingResult);
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
}
