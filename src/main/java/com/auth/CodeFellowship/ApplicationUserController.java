package com.auth.CodeFellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        try {
            m.addAttribute("userName", p.getName());
        }catch (NullPointerException e){
            m.addAttribute("userName","Visitor");
        }
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
    @GetMapping("/users/{id}")
    public String getUserPage(Principal p,Model m, @PathVariable int id){
        ApplicationUser requiredUser=applicationUserRepository.findById(id).get();
        if (requiredUser !=null) {
            System.out.println("id="+id);
            System.out.println("user="+requiredUser.getUsername());
            try {
                m.addAttribute("userName", p.getName());
            }catch (NullPointerException e){
                m.addAttribute("msg","user not exist");
                return "error.html";
            }
            m.addAttribute("requireduser", requiredUser);
        }else {
            m.addAttribute("msg","user not exist");
            return "error.html";
        }
        return "user.html";
    }
    @GetMapping("/profile")
    public String getProfile(Model m,Principal p){
        ApplicationUser targetUser=applicationUserRepository.findByUsername(p.getName());
        if(targetUser !=null){
            try {
                m.addAttribute("userName", p.getName());
            }catch (NullPointerException e){
                m.addAttribute("userName","Visitor");
            }
            m.addAttribute("targetUser", targetUser);
        }else {
            m.addAttribute("msg","Please signup or login to view your profile");
            return "error.html";
            /// error
        }
        return "profile.html";
    }


}
