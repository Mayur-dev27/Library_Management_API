package com.example.SpringDemo.Utils;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.SpringDemo.Entity.Account;
import com.example.SpringDemo.Repository.AccountRepository;
import com.example.SpringDemo.SecurityDemo.JwtService;
import com.example.SpringDemo.ServieImpl.MyUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{  // class to validated using token

	@Autowired
	private JwtService jwtService;
	
	@Autowired 
	private AccountRepository accountRepository;
	
    @Autowired
    private MyUserDetailService myUserDetailService;
    
	@Autowired   
	ApplicationContext context;   // to get bean of userDetail service to fetch userName....
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");  // get authorization header
        String token = null;
        String username = null;


        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = myUserDetailService.loadUserByUsername(username);   // get user details

            // Validate token
            if (jwtService.validateToken(token, userDetails)) {

                // Fetch the Account entity to check if the user is blocked
                Account account = accountRepository.findByUserName(username);

                // Check if the user is blocked
                if (account != null && account.getIsBlocked()) {

                    response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Block access to protected endpoints
                    response.getWriter().write("Your account is blocked. Please contact support.");
                    return;
                }

                // If not blocked, authenticate the user
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Proceed to the next filter or endpoint
        filterChain.doFilter(request, response);
    }
	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		
//		String authHeader = request.getHeader("Authorization");  // set authorization 
//		String token = null;
//		String username = null;
//		
//		
//		if(authHeader != null && authHeader.startsWith("Bearer ")) {    // token and username get kiya yaha
//			token = authHeader.substring(7);
//			username = jwtService.extractUserName(token);
//		}
//		
//		
//		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			
//			UserDetails userDetails = context.getBean(MyUserDetailService.class).loadUserByUsername(username);   // to get username by our customize class
//			
//			
//			if(jwtService.validateToken(token,userDetails)) {
//				
//				
//                // Fetch the Account entity to check if the user is blocked
//                Account account = accountRepository.findByUserName(username);
//                
//                if (account != null && account.getIsBlocked()) {
//                    response.setStatus(HttpServletResponse.SC_FORBIDDEN); // User is blocked
//                    response.getWriter().write("Your account is blocked. Please contact support.");
//                    return; // Exit filter chain to prevent further processing
//                }
//                
//                
//				UsernamePasswordAuthenticationToken authToken = 
//						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//				                                      // parameters (principal obj,credentials,authorities)
//				
//				// giving into of request obj to authToken				
//				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				
//				// set authentication
//				SecurityContextHolder.getContext().setAuthentication(authToken);
//			}
//			
//		}
//		filterChain.doFilter(request, response);
//		
//	}

}
