package com.example.coursework.Data.Repositories;

import com.example.coursework.Data.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Метод поиска сотрудников по подстроке их имени или фамилии
     * @param employeeName подстрока имени и фамилии
     * @return список сотрудников
     */
    List<Employee> findByNameContainingIgnoreCase(String employeeName);

    /**
     * Метод поиска сотрудника по имени и фамилии
     * @param name имя и фамилия
     * @return объект сотрудника
     */
    Employee findByName(String name);
}
