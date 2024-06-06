package com.msp.hoveron.controller;


import com.msp.hoveron.payload.UsersDto;
import com.msp.hoveron.service.SearchService;
import com.msp.hoveron.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/auth")

public class UserController {

    @Autowired
    private UsersService usersService;

    //USING POST STORING THE USER IN DB
    @PostMapping(path = "/signup")
    public ResponseEntity<UsersDto> signup(@RequestBody @Valid UsersDto usersDto) {
        return new ResponseEntity<>(usersService.signup(usersDto), HttpStatus.CREATED);
    }

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

}
