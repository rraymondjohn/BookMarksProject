package com.project.bookmarks.service;

import com.project.bookmarks.model.User;
import com.project.bookmarks.model.request.LoginRequest;
import com.project.bookmarks.model.request.NewUserRequest;
import com.project.bookmarks.model.request.UserRequest;
import com.project.bookmarks.model.response.LoginResponse;

import java.util.List;

public interface UserService {
    LoginResponse loginUser(LoginRequest loginRequest);

    List<User> getAllUsers();

    User getUserById(Long id);

    User addUser(NewUserRequest newUserRequest);

    User updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);
}
