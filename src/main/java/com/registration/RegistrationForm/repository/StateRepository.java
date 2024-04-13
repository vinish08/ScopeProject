package com.registration.RegistrationForm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.registration.RegistrationForm.model.Country;
import com.registration.RegistrationForm.model.State;

@Repository
public interface StateRepository extends JpaRepository<State,Integer>{
	List<State>findByCountry(Country countryid);

}
