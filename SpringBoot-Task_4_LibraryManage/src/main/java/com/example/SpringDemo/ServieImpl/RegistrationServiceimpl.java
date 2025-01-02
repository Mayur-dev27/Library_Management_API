package com.example.SpringDemo.ServieImpl;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SpringDemo.Entity.Account;
import com.example.SpringDemo.Entity.Role;
import com.example.SpringDemo.Repository.AccountRepository;
import com.example.SpringDemo.Repository.RoleRepository;
import com.example.SpringDemo.RequestDTO.AccountRequest;
import com.example.SpringDemo.ResponseDTO.AccountResponse;
import com.example.SpringDemo.SecurityDemo.JwtService;
import com.example.SpringDemo.Service.RegistrationService;

@Service
public class RegistrationServiceimpl implements RegistrationService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    
	public AccountResponse registerNewUser(AccountRequest accountRequest) {
		try {
	    if (accountRepository.findByUserName(accountRequest.getUsername()) != null) {
	        return new AccountResponse("Username already exists", 400);
	    }

	    Account account = new Account();
	    account.setUserName(accountRequest.getUsername());
	    account.setPassword(passwordEncoder.encode(accountRequest.getPassword())); // Encrypt password
	    account.setBlocked(false); // Ensure the user is not blocked by default



	    Set<Role> roles = new HashSet<>();
	    String roleName = accountRequest.getRoleName() != null ? accountRequest.getRoleName().toUpperCase() : "USER";
	    Role userRole = roleRepository.findByRoleName(roleName); // Get role by name
	    if (userRole == null) {
	        return new AccountResponse("Invalid role: " + roleName, 400);
	    }
	    roles.add(userRole);
	    account.setRoles(roles);

	    accountRepository.save(account);

	    return new AccountResponse("User registered successfully", 201);
		}
		catch (Exception e) {
			throw new RuntimeException("Error while register Account");
		}
    }

	
	public String verify(AccountRequest request) {
	    try {
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
	        );

	        if (authentication.isAuthenticated()) {
	            return jwtService.generateToken(request.getUsername());
	        }
	    } catch (Exception e) {
	        return "Authentication failed: " + e.getMessage();
	    }

	    return "Authentication failed";
	}

	
    // Method to block a user
    public AccountResponse blockUser(String username) {
        try {
            Account account = accountRepository.findByUserName(username);
            if (account == null) {
                return new AccountResponse("User not found", 404);
            }

            account.setBlocked(true); // Block the user
            accountRepository.save(account);

            return new AccountResponse("User blocked successfully", 200);
        } catch (Exception e) {
            return new AccountResponse("Error while blocking user: " + e.getMessage(), 500);
        }
    }

    // Method to unblock a user
    public AccountResponse unblockUser(String username) {
        try {
            Account account = accountRepository.findByUserName(username);
            if (account == null) {
                return new AccountResponse("User not found", 404);
            }

            account.setBlocked(false); // Unblock the user
            accountRepository.save(account);

            return new AccountResponse("User unblocked successfully", 200);
        } catch (Exception e) {
            return new AccountResponse("Error while unblocking user: " + e.getMessage(), 500);
        }
    }

    // Method to check if a user is blocked
    public boolean isUserBlocked(String username) {
        try {
            Account account = accountRepository.findByUserName(username);
            if (account == null) {
                throw new RuntimeException("User not found");
            }
            return account.getIsBlocked();
        } catch (Exception e) {
            throw new RuntimeException("Error while checking if user is blocked: " + e.getMessage(), e);
        }
    }
	
	
}
