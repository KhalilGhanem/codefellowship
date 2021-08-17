package com.auth.CodeFellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class ApplicationUserController {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    ApplicationUserRepository applicationUserRepository;


    @GetMapping("/")
    public String getHome(Principal p, Model m){
        m.addAttribute("userName",p.getName());
        return "home.html";
    }

    @GetMapping("/signup")
    public String getSignUpPage(){

        return "signup.html";
    }
    @GetMapping("/login")
    public String getSignInPage(){
        return "signin.html";
    }
    @PostMapping("/signup")
    public RedirectView SignUp(@RequestParam(value ="username")String username, @RequestParam(value = "password")String password,
                               @RequestParam(value = "firstName")String firstName, @RequestParam(value = "lastName")String lastName,
                               @RequestParam(value = "bio")String bio, @RequestParam(value = "dateOfBirth")String dateOfBirth){

       ApplicationUser newUser=new ApplicationUser(username,bCryptPasswordEncoder.encode(password),firstName,lastName,bio,dateOfBirth);
       applicationUserRepository.save(newUser);
       return new RedirectView("/login");
    }


}
