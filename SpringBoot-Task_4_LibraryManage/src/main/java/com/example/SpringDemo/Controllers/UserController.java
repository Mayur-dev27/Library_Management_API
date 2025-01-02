package com.example.SpringDemo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringDemo.ExceptionClasses.UserNotFoundException;
import com.example.SpringDemo.RequestDTO.UserRequest;
import com.example.SpringDemo.ResponseDTO.UserResponse;
import com.example.SpringDemo.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	
	@PostMapping("/createUser")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }


    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer userId) {
        UserResponse userResponse = userService.getUserById(userId);
        if (userResponse == null) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
	  
    @PutMapping("/updateUser")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequest request){
    	UserResponse response=userService.updateUser( request);
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteByUserId/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId){
    	userService.deleteUser(userId);
    	return new ResponseEntity<>("Deleted User with id: "+userId,HttpStatus.OK);
    }
	    
}
