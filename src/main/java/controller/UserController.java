package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired(required = false)
    private UserService userService;

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        if (userService == null) return null;
        return userService.getById(id);
    }
}
