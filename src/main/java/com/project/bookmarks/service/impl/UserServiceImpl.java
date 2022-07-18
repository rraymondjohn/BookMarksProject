package com.project.bookmarks.service.impl;

import com.project.bookmarks.exception.ResourceNotFoundException;
import com.project.bookmarks.model.User;
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

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found!"));
    }

    public User addUser(UserRequest userRequest) {
        User newUser = new User();
        return userRepository.save(newUser);
    }

    public User updateUser(Long id, UserRequest userRequest){
        User updatedUser = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found!"));
        updatedUser.setName(userRequest.getName());
        updatedUser.setUserName(userRequest.getUserName());
        updatedUser.setPassword(userRequest.getPassword());
        updatedUser.setEmail(userRequest.getEmail());
        updatedUser.setContactNo(userRequest.getContactNo());
        return userRepository.save(updatedUser);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
