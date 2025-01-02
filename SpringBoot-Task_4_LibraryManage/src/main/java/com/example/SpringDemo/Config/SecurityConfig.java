package com.example.SpringDemo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.SpringDemo.Utils.JwtFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;  // use to load user detail from database
	
	@Autowired
	private JwtFilter jwtFilter;
	
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////        .csrf(csrf -> csrf.disable()) // Disable CSRF for testing; enable in production as needed.
////        .authorizeHttpRequests(auth -> auth
////            .requestMatchers("/api/users/**").permitAll() // Allow unauthenticated access to /api/users.
////            .anyRequest().authenticated() // Authenticate all other requests.
////        )
////        .httpBasic(); // Use basic authentication for testing.
////
////    return http.build();
//    	
////    	uppar ka contentent depereecated hai 
//    	
//    	return http.csrf(customizer -> customizer.disable())
//    			.authorizeHttpRequests(request -> request
//    					.requestMatchers("/auth/register", "/auth/login").permitAll()  // skip this and baki auth
//    					.anyRequest().authenticated())
//    			.httpBasic(Customizer.withDefaults())
//    			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//    			.build();
//    }
    
    
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.disable()) // Disable CSRF for testing; enable in production as needed.
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/auth/register","/auth/login").permitAll() // Allow registration endpoint without authentication
//                .anyRequest().authenticated() // Any other request requires authentication
//            ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
    
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/auth/register", "/auth/login").permitAll() 
	            .requestMatchers("/api/**").hasRole("ADMIN")
	            .requestMatchers("/getBookById").hasAnyRole("ADMIN","USER")
	            .requestMatchers("/getAllBooks").hasAnyRole("ADMIN","USER")
	            .anyRequest().authenticated() 
	        )
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless for JWT no data store at server 
	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add the JWT filter before authentication filter

	    return http.build();
	}

    
    @Bean
    public AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
    	provider.setUserDetailsService(userDetailsService);
    	return provider;
    }

    @Bean        // we need AuthenticationManager obj when we use jwt token
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
    	return configuration.getAuthenticationManager();    
    }
}
