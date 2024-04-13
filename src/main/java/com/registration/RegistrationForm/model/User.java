 
package com.registration.RegistrationForm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="registration_frorm_scop")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="userid")
	private int id;
	@Column(name="firstname")
	private String firstname;
	@Column(name="lastname")
	private String lastname;
	@Column(name="gender")
	private String gender;
	@Column(name="date_of_birth")
	private String dob;
	@Column(name="email")
	private String email;
	 @Column(name="phonenumber")
	private String phonenumber;
	 @Column(name="country")
	private String country;
	 @Column(name="state")
	private String state;
	 @Column(name="city")
	private String city;
	 @Column(name="hobbies")
	private String hobbies;
	 @Column(name="userOTP")
	 private String otp;
	 @Column(name="verified")
	 private boolean verified;
	 @Lob
	 @Column(name="uploadavatar",columnDefinition = "longblob")
	private byte[] uploadavatar;
	 @Column(name="verificationcode")
	 private String verificationcode;
	 @Column(name="enabled")
	 private boolean enabled;
	 @Column(name="password")
	 private String password;
	 @Column(name="course_id")
	 private Integer joinedcourse;
	 
	   
	public Integer getJoinedcourse() {
		return joinedcourse;
	}
	public void setJoinedcourse(Integer joinedcourse) {
		this.joinedcourse = joinedcourse;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	 
	 
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVerificationcode() {
		return verificationcode;
	}
	public void setVerificationcode(String verificationcode) {
		this.verificationcode = verificationcode;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	public byte[] getUploadavatar() {
		return uploadavatar;
	}
	public void setUploadavatar(byte[] uploadavatar) {
		this.uploadavatar = uploadavatar;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", gender=" + gender
				+ ", dob=" + dob + ", email=" + email + ", phonenumber=" + phonenumber + ", country=" + country
				+ ", state=" + state + ", city=" + city + ", hobbies=" + hobbies + ",uploadavathor="+uploadavatar+"]";
	}
	 
}
