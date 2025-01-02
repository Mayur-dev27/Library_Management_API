package com.example.SpringDemo.Service;

import java.util.List;

import com.example.SpringDemo.RequestDTO.UserRequest;
import com.example.SpringDemo.ResponseDTO.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    // Get user by ID
    UserResponse getUserById(Integer userId);

    // Get all users
    List<UserResponse> getAllUsers();

    // Update a user
    UserResponse updateUser(UserRequest userRequest);

    // Delete a user
    void deleteUser(Integer userId);
}
