package com.example.coursework.Data.Repositories;

import com.example.coursework.Data.Entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findAllByCustomer(Long id);

    List<Contract> findAllOrderByCustomer();
}
