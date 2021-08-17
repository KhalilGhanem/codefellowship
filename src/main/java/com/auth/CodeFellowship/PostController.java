package com.auth.CodeFellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class PostController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postRepository;

    @PostMapping("/profile")
    public RedirectView addpost(@RequestParam(value ="postbody")String body, Principal p){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String s=dtf.format(now);
        Post post=new Post(body,s,applicationUserRepository.findByUsername(p.getName()));
        postRepository.save(post);
        return new RedirectView("/profile");
    }

}
