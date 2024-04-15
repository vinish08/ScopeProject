 
package com.registration.RegistrationForm.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.registration.RegistrationForm.model.City;
import com.registration.RegistrationForm.model.Contact;
import com.registration.RegistrationForm.model.Country;
import com.registration.RegistrationForm.model.Course;
import com.registration.RegistrationForm.model.State;
import com.registration.RegistrationForm.model.User;
import com.registration.RegistrationForm.repository.CourseRepository;
import com.registration.RegistrationForm.repository.OtpRepository;
import com.registration.RegistrationForm.repository.UserRepository;
import com.registration.RegistrationForm.service.CityService;
import com.registration.RegistrationForm.service.CountryService;
import com.registration.RegistrationForm.service.StateService;
import com.registration.RegistrationForm.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

 

@Controller
public class UserController {
	@Autowired
	private UserService service;
	@Autowired
	private CountryService countryservice;
	@Autowired
	private StateService stateservice;
	@Autowired
	private CityService cityservice;
	@Autowired
	private OtpRepository repository;
	@Autowired
	private UserRepository reposit;
	@Autowired
	private CourseRepository coro;
	
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("usercontact", new Contact());
		return "home";
	}
@GetMapping("/registerform")
public String form(Model model) {
	List<Country>countryList=countryservice.countrylist();
	model.addAttribute("countryList",countryList);
	 model.addAttribute("user",new User());
	return "index";
}
 

@PostMapping("/register")
public String register(@Valid @ModelAttribute("user")User user,BindingResult bindingResult,@RequestParam("uploadavatar") MultipartFile file,HttpServletRequest request) throws IOException, MessagingException {
	System.out.println("check1");
 
	if(!file.isEmpty()) {
		System.out.println("check3");
 
			user.setUploadavatar(file.getBytes());
			System.out.println("check4");
			service.insert(user,getSiteURL(request));
			System.out.println("check5");
		    return "check";
	 
	}
		else {
			System.out.println("check8");
			return "redirect:/";
			
		}
}
private String getSiteURL(HttpServletRequest request) {
	String siteurl=request.getRequestURL().toString();
	return siteurl.replace(request.getServletPath(),"");
}
@GetMapping("/verify")
public String verify(@Param("code")String code,Model model) {
	System.out.println(code);
	if(service.verify(code)) {
		model.addAttribute("Data",new User());
		return "send-otp";
	}else {
		return "error";
	}
}



@GetMapping("/dashboard")
public String dash(Model model,HttpSession session,HttpServletResponse response) {
	String email=(String)session.getAttribute("loggedEmail");
	User user=repository.findByEmail(email);
	if(user!=null) {
		String firstname=user.getFirstname();
		String lastname=user.getLastname();
		String Email=user.getEmail();
		String phonenumber=user.getPhonenumber();
		String gender=user.getGender();
		byte[] image=user.getUploadavatar();
		String base64Uploadavatar=Base64.getEncoder().encodeToString(image);
		
		 model.addAttribute("email",Email);
		 model.addAttribute("lastname",lastname);
		 model.addAttribute("firstname",firstname);
		 model.addAttribute("phonenumber",phonenumber);
		 model.addAttribute("gender",gender);
		 model.addAttribute("uploadavatar",base64Uploadavatar);  
		//model.addAttribute("email",email);

		 

		
		return "dashBoard";
		
	}
	else {
		return "redirect:/login";
	}
	
}


 
@GetMapping("/states/{id}")
public @ResponseBody Iterable<State> getStateByCountry(@PathVariable Country id){
	
	return stateservice.getStateBy(id);
}
@GetMapping("/cities/{stateid}")
public @ResponseBody Iterable<City>getcityBYState(@PathVariable State stateid){
	return cityservice.getcityBy(stateid);
}

//send otp....
@PostMapping("/send-otp")
public String sendOtp(Model model,@RequestParam("email")String email,User user) {
	User existingStudent=repository.findByEmail(email); 
	if(existingStudent!=null) {
		 
		String newOtp=generateRandomOtp();
		existingStudent.setVerified(false);
		existingStudent.setOtp(newOtp);
		repository.save(existingStudent);
		service.sendEmail(email,newOtp);
		//model.addAttribute("email",email);
		model.addAttribute("Data",new User());
		return "checkOtp";
		
		
	}
	else {
		model.addAttribute("error","Email not found please register first");
		return "error";
		
	}
}

 

@PostMapping("/confirm-otp")
public String verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String enteredOTP, Model model) {
    User user = repository.findByEmail(email);
    if (user != null && user.getOtp() != null && user.getOtp().equals(enteredOTP)) {
    	user.setVerified(true);
        repository.save(user);
        model.addAttribute("email", email);
        model.addAttribute("pass",new User());
        return "set-new-password";
    } else {
        model.addAttribute("error", "Invalid OTP. Please try again.");
        return "error";
    }
}

private String generateRandomOtp() { 
	String otp=String.valueOf(new Random().nextInt(900000)+100000);
	return otp;
}
//set password


 


@PostMapping("/setpassword")
public String password(@RequestParam("email") String email,@RequestParam("password")String password ,Model model) {
	User  studentpass=reposit.findByEmail(email);
	studentpass.setPassword(password);
	repository.save(studentpass);
	model.addAttribute("email",email);
	model.addAttribute("user",new User());
	return "login";
}
@GetMapping("/login")
public String log(Model model) {
	model.addAttribute("user", new User());
	return "login";
}
@PostMapping("/login")
public String login(@RequestParam("email")String email,@RequestParam("password")String password,Model model,HttpSession session,HttpServletResponse response) {
	User user=repository.findByEmail(email);
	if(user!=null && user.getPassword().equals(password) && user.isVerified()) {
		//model.addAttribute("email",email);
		//model.addAttribute("user",new User());
		session.setAttribute("loggedEmail", user.getEmail());
		String firstname=user.getFirstname();
		String lastname=user.getLastname();
		String Email=user.getEmail();
		String DOB=user.getDob();
		String phonenumber=user.getPhonenumber();
		String gender=user.getGender();
		String Hobie=user.getHobbies();
		byte[] image=user.getUploadavatar();
		String base64Uploadavatar=Base64.getEncoder().encodeToString(image);
		
		 model.addAttribute("email",Email);
		 model.addAttribute("lastname",lastname);
		 model.addAttribute("firstname",firstname);
		 model.addAttribute("phonenumber",phonenumber);
		 model.addAttribute("gender",gender);
		 model.addAttribute("dob",DOB);
		 model.addAttribute("hobbies",Hobie);
		 model.addAttribute("uploadavatar",base64Uploadavatar);  
		//model.addAttribute("email",email);

		 

		
		return "dashBoard";
	}
	else {
        model.addAttribute("error", "Invalid email or password.");

		return "redirect:/login";
	}
	 
}
 @GetMapping("/forgo-pass")
 public String changepassword(Model model) {
	 model.addAttribute("user",new User());
	 return "forgotpassword";
 }
 
 @GetMapping("/forgotpass") 
 public String resetpassword(@ModelAttribute("user") User user) {
	 User use=service.getuserbyemail(user.getEmail());
	 use.setPassword(user.getPassword());
	 service.updatepassword(use);
	 return "login";
 }
 
//edit details 
 
@RequestMapping(value = "/editpro", method = {RequestMethod.GET, RequestMethod.POST})
 public String editDetails(Model model, HttpSession session) {
     // Retrieve user from session
   String loggedUser =(String) session.getAttribute("loggedEmail");
    
     if (loggedUser == null) {
         // If the user is not logged in, redirect to the login page
         return "error";
     }
     
    // Add the user to the model
     User user =service.getuserbyemail(loggedUser);
     model.addAttribute("user", user);
     byte[] image=user.getUploadavatar();
 	String base64Uploadavatar=Base64.getEncoder().encodeToString(image);
 	 model.addAttribute("uploadavatar",base64Uploadavatar);  


    return "editprofile";  
 }

 
 //@PostMapping("/editprofiles")
 @RequestMapping(value ="/editprofiles", method = {RequestMethod.GET, RequestMethod.POST})
 public String updateDetails( @Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam("uploadavatar") MultipartFile file,Model model, HttpSession session) throws IOException {
//     if (result.hasErrors()) {
//         // Handle validation errors
//     	model.addAttribute("errorMessage","validation error");
//         return "error"; // Or whatever your error handling logic is
//     }

    // Retrieve the logged-in user from session
   //  String loggedInUser = (String) session.getAttribute("loggedEmail");

     // Update user details
	 user.setUploadavatar(file.getBytes());
    service.updateUserdetails(user);
    model.addAttribute("user", user);
    model.addAttribute("message", "User details updated successfully");
    
    session.setAttribute("user",user);
	String firstname=user.getFirstname();
	String lastname=user.getLastname();
	String Email=user.getEmail();
	String DOB=user.getDob();
	String phonenumber=user.getPhonenumber();
	String gender=user.getGender();
	String Hobie=user.getHobbies();
	byte[] image=user.getUploadavatar();
	System.out.println(image);
	String base64Uploadavatar=Base64.getEncoder().encodeToString(image);
 
	
	 model.addAttribute("email",Email);
	 model.addAttribute("lastname",lastname);
	 model.addAttribute("firstname",firstname);
	 model.addAttribute("phonenumber",phonenumber);
	 model.addAttribute("gender",gender);
	 model.addAttribute("dob",DOB);
	 model.addAttribute("hobbies",Hobie);
	 model.addAttribute("uploadavatar",base64Uploadavatar);  

      
     
     return "dashBoard"; 
 }
 
 
 
 @GetMapping("/course")
 public String course(Model model) {
     List<Course> courses = coro.findAll();
     model.addAttribute("courses", courses);
     model.addAttribute("usercontact", new Contact());
	 
     return "courselist";
 }

 @GetMapping("/coursedetails/{courseid}")
 public String courseDetailsPage(@PathVariable String courseid, Model model, HttpSession session) {
     try {
         int courseId = Integer.parseInt(courseid);
         Course course = coro.findById(courseId);
         if (course != null) {
             model.addAttribute("course", course);

             // Retrieve email from session
             String userEmail = (String) session.getAttribute("loggedEmail");
              
             // Get the currently logged-in user using the email
             User currentUser = service.getCurrentUser(userEmail);
             if (currentUser != null) {
                 model.addAttribute("currentUser", currentUser);
             }

             return "coursedetails";
         } else {
             model.addAttribute("message", "The course with ID " + courseId + " does not exist");
             return "error"; 
         }
     } catch (NumberFormatException e) {
         model.addAttribute("message", "Course ID must be a valid integer");
         return "error"; 
     }
     

 }
 


 @GetMapping("/saveid/{courseid}")
 public String saveCourseId(@PathVariable(name = "courseid", required = false) String courseIdString,@RequestParam("email") String email,Model model, HttpSession session) 
// public String saveCourseId(@PathVariable(name = "courseid", required = false) String courseIdString,
//         @RequestParam(name = "email", required = true) String email,
//         Model model, HttpSession session)
 {
	 System.out.println("heloo1 "+email);

     // Initialize courseId to null
     Integer courseId = null;
     System.out.println("heloo1");

     // Parse courseIdString to an integer if it's not null
     if (courseIdString != null) {
    	 System.out.println("heloo1");
         try {
        	 System.out.println("heloo2");
             courseId = Integer.parseInt(courseIdString);
             
         } catch (NumberFormatException e) {
             // Handle the case when courseIdString cannot be parsed to an integer
             model.addAttribute("message", "Course ID must be a valid integer");
             return "errrrr";
         }
     }
     System.out.println("heloo8");

     // Check if courseId is not null
     if (courseId != null) {
    	 
         // Get the user by email
         User use = service.getCurrentUser(email);
         System.out.println("heloo55 ");
         if (use!=null) {
        	 System.out.println("heloo44");
             // Set the joined course for the user
            // //if (use.getJoinedcourse()== null) {
                 use.setJoinedcourse(courseId);

                 // Save the updated user
                 service.saveUser(use);

                 // Add success message to the model
                 model.addAttribute("message", "You have been successfully enrolled");
                 List<Course> courses = coro.findAll();
                 model.addAttribute("courses", courses);
                 model.addAttribute("usercontact", new Contact());
                 // Redirect to a success page
                 return "courselist";
                 
             } 
             
//             
         } else {
             // If user is not found, handle the error appropriately
             model.addAttribute("message", "User with provided email does not exist");
             System.out.println("heloo48");
             return "error"; // or redirect to an error page
         }
      
     return "error";
 }

 
 
 @GetMapping("/logout")
 public String logout(HttpSession session,HttpServletResponse response) {
	 session.invalidate();
	 return "redirect:/login";
 }
 

 
 
 
 @GetMapping("/contact")
	public String contact(Model model) {
 	 model.addAttribute("usercontact", new Contact());
		 
		return "contact";
	}
 
 
 @PostMapping("/sendmail")  
 public String sendMail(Contact contact, Model model, User user) {
     String toAddress = "vinish089@gmail.com";
     String fromAddress = user.getEmail();
     String name = contact.getName();
     String number = contact.getNumber();
     String message = contact.getMessage();
     
     
     String emailContent = "Name: " + name + "\nNumber: " + number + "\nMessage: " + message;
     
     // Send email
     service.sendingmail(toAddress, fromAddress, "New Contact Form Submission", emailContent);

   
    
    
     model.addAttribute("usercontact", new Contact());
    // model.addAttribute("sucess", "Your message has been sent successfully!");
     return "contact";
 }
}


