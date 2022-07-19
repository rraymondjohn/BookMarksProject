package com.project.bookmarks.service;

import com.project.bookmarks.model.User;
import com.project.bookmarks.model.request.NewUserRequest;
import com.project.bookmarks.model.request.UserRequest;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User addUser(NewUserRequest newUserRequest);

    User updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);
}
