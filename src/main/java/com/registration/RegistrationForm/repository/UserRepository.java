 
package com.registration.RegistrationForm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.registration.RegistrationForm.model.Course;
import com.registration.RegistrationForm.model.User;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	public User findByVerificationcode(String verificationcode);
	public User findByEmail(String email);
	public User findByEmailAndPassword(String email,String password);
	 
	
	

}

