package com.project.bookmarks.service.impl;

import com.project.bookmarks.exception.ResourceNotFoundException;
import com.project.bookmarks.model.User;
import com.project.bookmarks.model.request.LoginRequest;
import com.project.bookmarks.model.request.NewUserRequest;
import com.project.bookmarks.model.request.UserRequest;
import com.project.bookmarks.repository.UserRepository;
import com.project.bookmarks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

//    public User loginUser(LoginRequest loginRequest){
//
//    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found!"));
    }

    public User addUser(NewUserRequest newUserRequest) {
        User newUser = new User();
        newUser.setName(newUserRequest.getName());
        newUser.setUsername(newUserRequest.getUsername());
        newUser.setPassword(newUserRequest.getPassword());
        newUser.setEmail(newUserRequest.getEmail());
        newUser.setContactNo(Integer.parseInt(newUserRequest.getContactNo()));
        newUser.setIsAdmin(false);
        return userRepository.save(newUser);
    }

    public User updateUser(Long id, UserRequest userRequest){
        User updatedUser = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found!"));
        updatedUser.setName(userRequest.getName());
        updatedUser.setUsername(userRequest.getUsername());
        updatedUser.setPassword(userRequest.getPassword());
        updatedUser.setEmail(userRequest.getEmail());
        updatedUser.setContactNo(userRequest.getContactNo());
        updatedUser.setIsAdmin(userRequest.getIsAdmin());
        return userRepository.save(updatedUser);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
