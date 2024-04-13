package com.registration.RegistrationForm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registration.RegistrationForm.model.Country;
import com.registration.RegistrationForm.model.State;
import com.registration.RegistrationForm.repository.StateRepository;

@Service
public class StateService {
	@Autowired
	private StateRepository stateRepository;
	public List<State>getstate(){
		return stateRepository.findAll();
	}
	public List<State>getStateBy(Country countryid){
		return stateRepository.findByCountry(countryid);
	}

}
