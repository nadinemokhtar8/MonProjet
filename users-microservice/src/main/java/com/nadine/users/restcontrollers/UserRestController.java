package com.nadine.users.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nadine.users.entities.User;
import com.nadine.users.register.RegistationRequest;
import com.nadine.users.service.UserService;

@RestController
@RequestMapping
@CrossOrigin("*")
public class UserRestController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody RegistationRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/verifyEmail")
    public User verifyEmail(@RequestParam("code") String code) {
        return userService.verifyEmail(code);
    }
}
