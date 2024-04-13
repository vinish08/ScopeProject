package com.registration.RegistrationForm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.registration.RegistrationForm.model.City;
import com.registration.RegistrationForm.model.State;

@Repository
public interface CityRepository extends JpaRepository<City,Integer> {
List<City>findByState(State stateid);
}
