package com.example.SpringDemo.Entity;

import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AccountDetail implements UserDetails{

	private Account account;
	
	public AccountDetail(Account account) {
		this.account=account;
	}
	
	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return Collections.singleton(new SimpleGrantedAuthority("USER"));
//	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return account.getRoles().stream()
	        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
	        .toList();
	}


	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getUserName();
	}

	
	
	@Override
	public boolean isAccountNonExpired() {
	    return true; // Or add logic to determine expiration
	}

	@Override
	public boolean isAccountNonLocked() {
	    return !account.getIsBlocked(); // Or add logic for account locking
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return true; // Or add logic for credentials expiration
	}

	@Override
	public boolean isEnabled() {
	    return !account.getIsBlocked(); // Or add logic to enable/disable account
	}

}
