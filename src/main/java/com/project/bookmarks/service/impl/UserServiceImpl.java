package com.project.bookmarks.service.impl;

import com.project.bookmarks.exception.CustomException;
import com.project.bookmarks.exception.ResourceNotFoundException;
import com.project.bookmarks.model.Role;
import com.project.bookmarks.model.User;
import com.project.bookmarks.model.request.LoginRequest;
import com.project.bookmarks.model.request.NewUserRequest;
import com.project.bookmarks.model.request.UserRequest;
import com.project.bookmarks.model.response.LoginResponse;
import com.project.bookmarks.repository.UserRepository;
import com.project.bookmarks.security.JwtTokenProvider;
import com.project.bookmarks.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponse loginUser(LoginRequest loginRequest){
        User user = userRepository.findByUsername(loginRequest.getUsername());
        log.info("Logging in user : {}", user.getUsername());
        if(user == null) {
            throw new ResourceNotFoundException("Username not found");
        } else if (!loginRequest.getPassword().equals(user.getPassword())){
            throw new ResourceNotFoundException("Password does not match");
        }
        try {
            //perform UsernamePasswordAuthenticationToken authentication
            log.info("Performing Authentication");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            log.info("Authentication performed");
            //return generated JWT token
            String token = jwtTokenProvider.createToken(loginRequest.getUsername(), userRepository.findByUsername(loginRequest.getUsername()).getRole());
            log.info("Token created : {}", token);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setId(user.getId());
            loginResponse.setRole(user.getRole());
            loginResponse.setToken(token);
            return loginResponse;
        } catch (AuthenticationException e) {
            //return exception when authentication failed
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


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
        System.out.println(newUserRequest.getContactNo());
        newUser.setContactNo(Integer.parseInt(newUserRequest.getContactNo()));
        System.out.println(newUser.getContactNo());
        newUser.setRole(Role.ROLE_CLIENT.toString());
        return userRepository.save(newUser);
    }

    public User updateUser(Long id, UserRequest userRequest){
        User updatedUser = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found!"));
        updatedUser.setName(userRequest.getName());
        updatedUser.setUsername(userRequest.getUsername());
        updatedUser.setPassword(userRequest.getPassword());
        updatedUser.setEmail(userRequest.getEmail());
        updatedUser.setContactNo(userRequest.getContactNo());
        updatedUser.setRole(userRequest.getRole());
        return userRepository.save(updatedUser);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
