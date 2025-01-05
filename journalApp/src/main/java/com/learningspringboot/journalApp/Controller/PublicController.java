package com.learningspringboot.journalApp.Controller;

import com.learningspringboot.journalApp.Entity.User;
import com.learningspringboot.journalApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class PublicController {

    @GetMapping("/Health-check")
    public String healthCheck()
    {
        return "OK";
    }

    @Autowired
    private UserService userService;

    @PostMapping("Create-user")
    ResponseEntity<?> CreateNewUser(@RequestBody User user)
    {
        userService.saveNewUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
