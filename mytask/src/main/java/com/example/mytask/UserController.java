
package com.example.mytask;


import java.util.List;
//import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.mytask.model.User;


@Controller
public class UserController{
	
	//static ArrayList<User> users=new ArrayList<>();
	@GetMapping("/")
	public String indexAction() {
		return "index";
	}
	@GetMapping("/signup")
	public String signupAction(Model model) {
		model.addAttribute("message", "Enter Your login Details");
		return "signup";
	}
	
	
	@Autowired
	private UserRepository userRepository;
	@PostMapping("/signup")
	public String signupActionProcess(
			@RequestParam String name ,
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam String address,
			@RequestParam String mobile,
			Model model) {
		
		//User n = new User(name,email,password,address,mobile);
		User n = new User();
		//users.add(n);
		n.setName(name);
		n.setEmail(email);
		n.setAddress(address);
		n.setContact(mobile);
		n.setPassword(password);
		//userRepository.saveAll(users);
		userRepository.save(n);
		
		model.addAttribute("message", "The user"+ email +" is signed up successfully");
		
		return "signup";
	}

@GetMapping("/all")
	public String getAllUsers(Model model,@RequestParam(name="page",required=false) Integer page ) {
	
	
	/*User userdetail= userRepository.findByEmail(email);
	
	if(userdetail==null) {
		
		throw new UserNotFoundException("user enter email is incorrect");
		
	}*/
	
	if(page==null) {
		
		page=1;
	}
	Integer LIMIT =3;
	Pageable pageable=PageRequest.of(page-1,LIMIT);
	Page<User> users = userRepository.findAll(pageable);	
	//Iterable<User> users = userRepository.findAll();
	 int totalPages = users.getTotalPages();
     if (totalPages > 0) {
         List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
             .boxed()
             .collect(Collectors.toList());
         model.addAttribute("pageNumbers", pageNumbers);
         System.out.println(pageNumbers);
     }    
     
	model.addAttribute("users",users);
		return "list";

	}	
@GetMapping("/update/{id}")

public String updateUser(@PathVariable Integer id,Model model) {		

	Optional<User> optionaluserdetails = userRepository.findById(id);
	User userdetails = optionaluserdetails.get();

	model.addAttribute("id",id);
	model.addAttribute("userdetails",userdetails);
	return "update";
}	

@PostMapping("update/{id}")

public String updateUser(@PathVariable Integer id,
		@RequestParam String name,@RequestParam String email,
		@RequestParam String password,
		@RequestParam String address,
		@RequestParam String mobile,Model model) {
	
	Optional<User> optionaluserdetails = userRepository.findById(id);
	User userdetails = optionaluserdetails.get();

	userdetails.setName(name);
	userdetails.setEmail(email);
	userdetails.setAddress(address);
	userdetails.setContact(mobile);
	userdetails.setPassword(password);
	
	userRepository.save(userdetails);
	
	model.addAttribute("id",id);
	model.addAttribute("userdetails",userdetails);
	
	return "update";
}
@GetMapping("/delete/{id}")

public String deleteUser(@PathVariable Integer id,Model model) {
	
	Optional<User> optuserdetails = userRepository.findById(id);
	User userdetails = optuserdetails.get();
	
	model.addAttribute("id",id);
	model.addAttribute("userdetails",userdetails);

	return "delete";

}
	
@PostMapping("/delete/{id}")

public String deleteUser(@RequestParam(name="email",required=false) String email,Model model) {
	
if(email !=null && !email.isEmpty()) {	
	
	User optuserdetails = userRepository.findByEmail(email);
	userRepository.delete(optuserdetails);
	model.addAttribute("message","Successfully deleted");
}

return "redirect:/all";
}

@Autowired
private JavaMailSender sender;
@ResponseBody
	@GetMapping("/send-mail")
	
	public String sendMail() {
		
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("arjundrkattottil088@gmail.com");
        msg.setSubject("Subject here");
        msg.setText(" This is a mail ");
        
        try{
            sender.send(msg);
            return "Successfully sent mail";
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
            return "Sending mail failed";
        }
        
		
	}

@GetMapping("/ajax-list/{id}")
@ResponseBody
public User ajaxUserList(@PathVariable Integer id) {		
	
	Optional<User> optionaluserdetails = userRepository.findById(id);
	User userdetails = optionaluserdetails.get();
	return userdetails;
}






/*@GetMapping("/ajax-message")
@ResponseBody
public String ajaxMessage() {		

	String message = "This is a message from server";
	return message;
	
}	*/

	

}
	
