package com.parovsky.traver.controller;

import com.parovsky.traver.Utils.Utils;
import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.service.EmailService;
import com.parovsky.traver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws UserNotFoundException {
        UserDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getCurrentUser() throws UserNotFoundException {
        UserDTO userDTO = userService.getCurrentUser();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) throws UserIsAlreadyExistException {
        UserDTO user = userService.saveUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/send-verification-code", consumes = "application/json")
    public ResponseEntity<Void> verifyEmail(@RequestBody UserDTO userDTO) throws UserNotFoundException {
        int code = Utils.generateVerificationCode();
        emailService.sendEmail(userDTO.getMail(), "Verification code", "Your verification code is: " + code);
        UserDTO user = userService.getUserByEmail(userDTO.getMail());
        user.setVerifyCode(String.valueOf(code));
        userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value = "/user", consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) throws UserNotFoundException {
        UserDTO user = userService.updateUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
