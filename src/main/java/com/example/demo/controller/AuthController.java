package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class AuthController {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("register")
    public String registerForm(Model model){
        return "auth/register";
    }

    @PostMapping("register")
    public String register(@ModelAttribute User user){
        user.setRole("GUEST");
        String hashPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        userRepository.save(user);
        return "redirect:/register";
    }

    @GetMapping("login")
    public String login(){
        return "auth/login";
    }

    @PostMapping("login")
    public String postLogin(@ModelAttribute User user){
        List<User> list = userRepository.findByEmail(user.getEmail());
        if(list.size() > 0){
            User existUser = list.get(0);
            // so sánh password
            if(bCryptPasswordEncoder.matches(user.getPassword(), existUser.getPassword())){
                return "redirect:/";
            }
        }
        return "redirect:/login";
    }
}
