package com.project.bookmarks.controller;

import com.project.bookmarks.model.Book;
import com.project.bookmarks.model.User;
import com.project.bookmarks.model.request.BookRequest;
import com.project.bookmarks.model.request.LoginRequest;
import com.project.bookmarks.model.request.NewUserRequest;
import com.project.bookmarks.model.request.UserRequest;
import com.project.bookmarks.model.response.LoginResponse;
import com.project.bookmarks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path="/bookmarks/users")
public class UserController {
    @Autowired
    private UserService userService;

    //Login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.loginUser(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    //Get all users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    //Get 1 user
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    //Add User
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@Valid @RequestBody NewUserRequest newUserRequest){
        User newUser = userService.addUser(newUserRequest);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
    //Update User
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id,@Valid @RequestBody UserRequest userRequest) {
        User updatedUser = userService.updateUser(id, userRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    //Delete User
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
