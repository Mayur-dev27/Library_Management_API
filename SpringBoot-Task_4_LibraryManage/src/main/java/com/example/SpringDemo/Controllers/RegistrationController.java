package com.example.SpringDemo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringDemo.RequestDTO.AccountRequest;
import com.example.SpringDemo.ResponseDTO.AccountResponse;
import com.example.SpringDemo.Service.RegistrationService;

@RestController
@RequestMapping("/auth")
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;
	
	
	@PostMapping("/register")
	public ResponseEntity<AccountResponse> registerUser(@RequestBody AccountRequest accountRequest) {
	    AccountResponse response = registrationService.registerNewUser(accountRequest);

	    return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}

//	@PostMapping("/login")
//	public ResponseEntity<String> verify(@RequestBody AccountRequest request){
//		
//		if(registrationService.verify(request) != null) {
//			return new ResponseEntity<>("Successfully Login",HttpStatus.valueOf(201));
//		}
//		return new ResponseEntity<>("Failed Login",HttpStatus.valueOf(401));
//		
//		
//	}
	
	@PostMapping("/login")
	public String veify(@RequestBody AccountRequest request) {
		return registrationService.verify(request);
	}
	
	
    // Endpoint to block a user
    @PostMapping("/block-user")
    public ResponseEntity<AccountResponse> blockUser(@RequestBody AccountRequest request) {
        AccountResponse response = registrationService.blockUser(request.getUsername());
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // Endpoint to unblock a user
    @PostMapping("/unblock-user")
    public ResponseEntity<AccountResponse> unblockUser(@RequestBody AccountRequest request) {
        AccountResponse response = registrationService.unblockUser(request.getUsername());
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // Endpoint to check if a user is blocked
    @PostMapping("/is-user-blocked")
    public ResponseEntity<String> isUserBlocked(@RequestBody AccountRequest request) {
        boolean isBlocked = registrationService.isUserBlocked(request.getUsername());
        String message = isBlocked ? "User is blocked" : "User is not blocked";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
	
	
}
