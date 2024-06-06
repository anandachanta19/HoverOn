package com.msp.hoveron.controller;

import com.msp.hoveron.entity.Users;
import com.msp.hoveron.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/hoveron")
public class HomeController {
    private final UsersService usersService;

    @Autowired
    public HomeController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/home/{userId}")
    public String home(@PathVariable int userId, Model model) {
        Users user = usersService.getUserById(userId);
        model.addAttribute("user", user);
        return "home";
    }

}