package com.registration.RegistrationForm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.registration.RegistrationForm.model.Course;
 
 

public interface CourseRepository extends JpaRepository<Course,Integer>{
	public Course findById(int courseid);
}
