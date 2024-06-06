package com.msp.hoveron.controller;


import com.msp.hoveron.entity.Gender;
import com.msp.hoveron.entity.Users;
import com.msp.hoveron.payload.LoginDto;
import com.msp.hoveron.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/hoveron")
public class LoginController {

    private final UsersService usersService;
    private final HttpServletRequest request;

    @Autowired
    public LoginController(UsersService usersService, HttpServletRequest request) {
        this.usersService = usersService;
        this.request = request;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginDto") LoginDto loginDto, BindingResult bindingResult, Model model) {
        // Find the user by username or email
        Users user = usersService.findByUserNameAndPassword(loginDto.getUserName(), loginDto.getPassword());

        // Verify the password
        if (user == null || !user.getPassword().equals(loginDto.getPassword())) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

        // Create a new session or retrieve the existing session
        HttpSession session = request.getSession();
        // Set user ID in the session attribute
        session.setAttribute("userId", user.getUserId());
        System.out.print("THis is user id who is logged in "+user.getUserId());

        // Inside the login method after successful authentication
        session.setAttribute("username", user.getUserName());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("gender", user.getGender());
        session.setAttribute("age", user.getAge());


        // Redirect to the dashboard or home page
        return "redirect:/hoveron/home/"+user.getUserId();
    }

    @GetMapping("/user/details")
    @ResponseBody
    public ResponseEntity<?> getUserDetails(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
        }
        Users user = usersService.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", user.getUserName());
        userDetails.put("email", user.getEmail());
        userDetails.put("gender", user.getGender());
        userDetails.put("age", user.getAge());
        return ResponseEntity.ok(userDetails);
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate the current session to clear any session attributes
        session.invalidate();

        // Redirect the user to the login page
        return "redirect:/hoveron/login";
    }

    // Mapping for updating user profile information
    @PostMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> updates, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"User is not logged in\"}");
        }
        Users user = usersService.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"User not found\"}");
        }

        try {
            // Update user profile with the provided information
            if (updates.containsKey("username")) user.setUserName((String) updates.get("username"));
            if (updates.containsKey("email")) user.setEmail((String) updates.get("email"));
            if (updates.containsKey("gender")) {
                String genderStr = ((String) updates.get("gender")).toLowerCase(); // Convert to lowercase
                try {
                    Gender gender = Gender.valueOf(genderStr);
                    user.setGender(gender);
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Invalid gender value\"}");
                }
            }

            if (updates.containsKey("age")) {
                String ageStr = (String) updates.get("age");
                try {
                    Integer age = Integer.parseInt(ageStr);
                    user.setAge(age);
                } catch (NumberFormatException e) {
                    // Handle the case where age is not a valid integer
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Invalid age value\"}");
                }
            }


            usersService.save(user); // Save the updated user object

            return ResponseEntity.ok("{\"message\": \"Profile updated successfully\"}");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception stack trace
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An unexpected error occurred\"}");
        }
    }


    // Mapping for updating user password
    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> payload, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"User is not logged in\"}");
        }
        Users user = usersService.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"User not found\"}");
        }

        String oldPassword = payload.get("oldPassword");
        String newPassword = payload.get("newPassword");

        // Check if the old password matches
        if (!user.getPassword().equals(oldPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Incorrect old password\"}");
        }

        // Update the password
        user.setPassword(newPassword); // Make sure to hash the new password before saving
        usersService.save(user);

        return ResponseEntity.ok("{\"message\": \"Password updated successfully\"}");
    }

}
