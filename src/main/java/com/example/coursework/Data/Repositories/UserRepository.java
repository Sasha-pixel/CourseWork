package com.example.coursework.Data.Repositories;

import com.example.coursework.Data.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    User findByPhoneNumber(String phoneNumber);

    User findByActivationCode(String code);

    User findByResetPasswordToken(String token);
}
