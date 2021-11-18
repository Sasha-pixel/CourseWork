package com.example.coursework.Data.Repositories;

import com.example.coursework.Data.Entities.Osago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OsagoRepository extends JpaRepository<Osago, Long> {
}
