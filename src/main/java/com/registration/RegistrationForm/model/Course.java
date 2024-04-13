package com.registration.RegistrationForm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="courseid")
private int courseid;
	@Column(name="coursename")
private String coursename;
	@Column(name="duration")
private String courseduration;
	@Column(name="fee")
	private int coursefee;
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public String getCourseduration() {
		return courseduration;
	}
	public void setCourseduration(String courseduration) {
		this.courseduration = courseduration;
	}
	public int getCoursefee() {
		return coursefee;
	}
	public void setCoursefee(int coursefee) {
		this.coursefee = coursefee;
	}
	
	 
	 
}
