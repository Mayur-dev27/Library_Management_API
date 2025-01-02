package com.example.SpringDemo.ServieImpl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.SpringDemo.Entity.Account;
import com.example.SpringDemo.Repository.AccountRepository;

@Service
public class MyUserDetailService implements UserDetailsService{

	@Autowired
	private AccountRepository accountRepository;
	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		System.out.println("Username received: " + username);
//
//		Account acc = accountRepository.findByUserName(username);
//		
//		if(acc==null) {
//			throw new UsernameNotFoundException("Account not found with userName: "+username);
//		}
//		return new AccountDetail(acc);
//	}
	
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserName(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Don't throw an error here if the account is blocked, just allow login
        return new org.springframework.security.core.userdetails.User(account.getUserName(),
                account.getPassword(), getAuthorities(account));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Account account) {
        // Your logic to fetch authorities (roles/permissions)
        return account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getRoleName()))  // Assuming Role has a method getRoleName()
                .toList(); 
    }

}
