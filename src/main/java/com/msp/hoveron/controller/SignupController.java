package com.msp.hoveron.controller;


import com.msp.hoveron.exception.EmailExistsException;
import com.msp.hoveron.payload.UsersDto;
import com.msp.hoveron.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignupController {

    private final UsersService usersService; // Inject the service

    public SignupController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new UsersDto()); // Initialize usersDto object
        return "signup";
    }

    @PostMapping("/processSignup")
    public String signup(@ModelAttribute("user") @Valid UsersDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return to the signup form with error messages
            return "redirect:/signup"; // Redirect to the signup form
        }

        // Validate age field
        if (userDto.getAge() == null || userDto.getAge() < 18) {
            // If age requirement is not met, redirect to the signup form with an error message
            redirectAttributes.addFlashAttribute("error", "Age must be at least 18");
            return "redirect:/signup"; // Redirect to the signup form
        }

        // Proceed with saving user data
        try {
            usersService.signup(userDto);
            redirectAttributes.addFlashAttribute("message", "Signup successful!");
            return "redirect:/hoveron/login"; // Redirect to the login page after successful signup
        } catch (EmailExistsException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/signup"; // Redirect to the signup form with error message
        }
    }

}

