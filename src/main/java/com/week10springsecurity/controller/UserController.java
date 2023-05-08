package com.week10springsecurity.controller;


import com.week10springsecurity.auth.AuthenticationResponse;
import com.week10springsecurity.dto.UserDTO;
import com.week10springsecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public AuthenticationResponse createUser(
            @RequestBody UserDTO userDTO){
       return userService.createUser(userDTO);}
    @PostMapping("/login")
    public AuthenticationResponse loginUser(@RequestBody UserDTO userDTO){
         userService.loginUser(userDTO);
        return userService.loginUser(userDTO);
    }
    @GetMapping("/all")
    public List<UserDTO> getallUsers(){
        return userService.getALllUser();
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        session.setAttribute("userId",null);
        return   new ResponseEntity<>("You have been logged out",HttpStatus.GONE);
    }

    @PostMapping("/logout/admin")
    public ResponseEntity<String> adminLogout(HttpSession session){
        session.setAttribute("id",null);
        return   new ResponseEntity<>("You have been logged out",HttpStatus.GONE);
    }
}
