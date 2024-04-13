package com.registration.RegistrationForm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.registration.RegistrationForm.model.User;

public interface OtpRepository extends JpaRepository<User,Integer>{
public User findByEmail(String email);
public User findByEmailAndPassword(String email,String password);
}
