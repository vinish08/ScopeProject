package com.registration.RegistrationForm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.registration.RegistrationForm.model.Country;

public interface CountryRepository extends JpaRepository<Country,Integer>{

}
