package com.example.coursework.Data.Repositories;

import com.example.coursework.Data.Entities.Contract;
import com.example.coursework.Data.Entities.Employee;
import com.example.coursework.Data.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findAllByCustomer(User user);

    List<Contract> findAllByOrderByCustomer();
}
