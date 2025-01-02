package com.example.SpringDemo.Service;

import com.example.SpringDemo.RequestDTO.AccountRequest;
import com.example.SpringDemo.ResponseDTO.AccountResponse;

public interface RegistrationService {

	public AccountResponse registerNewUser(AccountRequest accountRequest);
	
	public String verify(AccountRequest request);
	
	public AccountResponse blockUser(String username);
	
	public AccountResponse unblockUser(String username);
	
	public boolean isUserBlocked(String username);
}
