package com.example.coursework.Services;

import com.example.coursework.Data.Entities.Contract;
import com.example.coursework.Data.Entities.Employee;
import com.example.coursework.Data.Repositories.ContractRepository;
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

    @Autowired
    private ContractRepository contractRepository;

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

    public Employee setWorkersToOrder(List<Employee> workers) {
        Employee employee = employeeRepository.findEmployeeById(1L);
        for (Employee worker : workers) {
            if (worker.getContracts().isEmpty())
                return worker;
            else {
                if (employee.getContracts().size() > worker.getContracts().size())
                    employee = worker;
            }
        }
        return employee;
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
