package com.example.coursework.Data.Repositories;

import com.example.coursework.Data.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findEmployeeById(Long id);

    List<Employee> findByNameContainingIgnoreCase(String employeeName);

    Employee findByName(String name);
//
//    @Query(value = "select e from Employee e order by count(e.contracts)")
//    Employee findFreeEmployee();
}
