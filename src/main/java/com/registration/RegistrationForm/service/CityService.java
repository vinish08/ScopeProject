package com.registration.RegistrationForm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registration.RegistrationForm.model.City;
import com.registration.RegistrationForm.model.State;
import com.registration.RegistrationForm.repository.CityRepository;

@Service
public class CityService {
	@Autowired
	private CityRepository cityRepository;
	public List<City>getcity(){
		return cityRepository.findAll();
	}
	public List<City>getcityBy(State stateid){
		return cityRepository.findByState(stateid);
	}

}
