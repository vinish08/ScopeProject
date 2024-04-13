 


package com.registration.RegistrationForm.service;

 
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.registration.RegistrationForm.model.User;
import com.registration.RegistrationForm.repository.OtpRepository;
import com.registration.RegistrationForm.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;

 

@Service
public class UserService {
 @Autowired
 private UserRepository repo;
 
 @Autowired
 private OtpRepository re;
 
 @Autowired
 private JavaMailSender sender;
 
 public void insert(User user,String siteUrl) throws UnsupportedEncodingException, MessagingException {
//	 System.out.println("check6");
	  String randomCode=RandomString.make(64);
	  user.setVerificationcode(randomCode);
	  user.setEnabled(false);
	  repo.save(user);
	 sendVerificationEmail(user,siteUrl);
//	 System.out.println("check7");
 }
 public void sendVerificationEmail(User user,String siteUrl) throws UnsupportedEncodingException, MessagingException {
	 String toaddr=user.getEmail();
	 String fromaddr="vinish089@gmail.com";
	 String senderName="vinish";
	 String subject="Verify Registration";
	 String message="Dear [[firstname]] please click below link to verify<h3><a href=\"[[URL]]\" target=\"_blank\">VERIFY</a></h3>";
	
	 MimeMessage msg=sender.createMimeMessage();
	 MimeMessageHelper messageHelper=new MimeMessageHelper(msg);
	 messageHelper.setFrom(fromaddr,senderName);
	 messageHelper.setTo(toaddr);
	 messageHelper.setSubject(subject);
	 message=message.replace("[[firstname]]", user.getFirstname());
	 
	 String url=siteUrl+"/verify?code="+user.getVerificationcode();
	 message=message.replace("[[URL]]", url);
	 messageHelper.setText(message,true);
	 sender.send(msg);
	 
 }
 
 public boolean verify(String verificationcode) {
	 System.out.println("checking 2");
	 User user=repo.findByVerificationcode(verificationcode);
	 System.out.println("checking 3");
	 if(user==null || user.isEnabled()) {
		 System.out.println("checking 4");
		 return false;
	 }
	 else {
		 user.setVerificationcode(null);
		 System.out.println("checking-----");
		 user.setEnabled(true);
		 repo.save(user);
		 return true;
	 }
 }
 
 
// otp send
 
 
 public void sendEmail(String toAddress,String otp) {
	 SimpleMailMessage message=new SimpleMailMessage();
	 message.setTo(toAddress);
	 message.setSubject("Otp for verified");
	 message.setText("Your Otp verification is"+otp);
	 sender.send(message);
 }
 public User getuserbyemail(String email) {
		return re.findByEmail(email);
	}
 public void updatepassword(User user) {
	 repo.save(user);
 }
 
 //edit profile
// public void updateUserdetails(User user, byte[] avatar) {
//	    if (avatar != null && avatar.length > 0) {
//	        // Set the avatar only if it's not empty
//	        user.setUploadavatar(avatar);
//	    }
//	    
//	    // Save the updated user
//	    repo.save(user);
//	}
 
 public void updateUserdetails(User updatedUser) {
	    // Retrieve the existing user from the repository
	    User existingUser = repo.findByEmail(updatedUser.getEmail());
	    
	    if (existingUser != null) {
	        // Update user details
	        existingUser.setFirstname(updatedUser.getFirstname());
	        existingUser.setLastname(updatedUser.getLastname());
	        //existingUser.setEmail(updatedUser.getEmail());
	        existingUser.setGender(updatedUser.getGender());
	        existingUser.setDob(updatedUser.getDob());
	        existingUser.setPhonenumber(updatedUser.getPhonenumber());
	        existingUser.setCountry(updatedUser.getCountry());
	        existingUser.setState(updatedUser.getState());
	        existingUser.setCity(updatedUser.getCity());
	        existingUser.setHobbies(updatedUser. getHobbies());
	        // Update avatar if provided
	        
	            existingUser.setUploadavatar(updatedUser.getUploadavatar());
	        
	        
	        
	        repo.save(existingUser);
	    }
	 
 }

 public User getCurrentUser(String email) {
	 return re.findByEmail(email);
 }
 public void saveUser(User user) {
	 re.save(user);
 }
 
 public void sendingmail(String to,String from,String subject,String text) {
	 SimpleMailMessage message=new SimpleMailMessage();
	 message.setTo(to);
	 message.setFrom(from);
	 message.setSubject(subject);
	 message.setText(text);
	 sender.send(message);
 }
}
 




