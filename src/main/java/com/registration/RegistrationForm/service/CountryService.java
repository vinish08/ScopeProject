package com.registration.RegistrationForm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registration.RegistrationForm.model.Country;
import com.registration.RegistrationForm.repository.CountryRepository;

@Service
public class CountryService {
	@Autowired
	private CountryRepository countryRepository;
	
	public List<Country>countrylist(){
		return countryRepository.findAll();
	}

}
