package springboot.imagedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import springboot.imagedemo.entity.User;
import springboot.imagedemo.service.UserService;

@Controller
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login(Model model) 
	{
		User theUser = new User();
		model.addAttribute("theUser", theUser);
		return "login";
	}
	
	@GetMapping("/signup")
	public String signUp(Model model)
	{
		User theUser = new User();
		model.addAttribute("theUser", theUser);
		return "signup";
	}
	
	@PostMapping("/signup")
	public String postSignUp(@ModelAttribute("theUser") User theUser)
	{
		userService.save(theUser);
		return "redirect:/login";
	}
}
