package com.example.SpringDemo.ServieImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringDemo.Entity.Fine;
import com.example.SpringDemo.Entity.User;
import com.example.SpringDemo.ExceptionClasses.UserNotFoundException;
import com.example.SpringDemo.Repository.UserRepository;
import com.example.SpringDemo.RequestDTO.UserRequest;
import com.example.SpringDemo.ResponseDTO.UserResponse;
import com.example.SpringDemo.Service.UserService;
import com.example.SpringDemo.Utils.MappingUtils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MappingUtils mappingUtils;

	@Override
	public UserResponse createUser(UserRequest userRequest) {
		User user = mappingUtils.mapToObject(userRequest, User.class);
		
		try {
			User savedUser = userRepository.save(user); 
			return mappingUtils.mapToObject(savedUser, UserResponse.class);
			
		} catch (Exception e) {
			throw new RuntimeException("Error while creating user.."+e);
		}
	}

	@Override
	public UserResponse getUserById(Integer userId) {
		try {
			User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found with id: "+userId));
			return mappingUtils.mapToObject(user, UserResponse.class);
		}
		catch (UserNotFoundException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException("Error retrieving user: " + e.getMessage(), e);
		}
		
	}

	@Override
	public List<UserResponse> getAllUsers() {
        try {
            // Retrieve All Users
            List<User> users = userRepository.findAll();

            // Map Entities to Response DTO List
            return mappingUtils.mapToList(users, UserResponse.class);

        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all users: " + e.getMessage(), e);
        }
	}

	@Override
	public UserResponse updateUser(UserRequest userRequest) {
        try {
            // Retrieve Existing User
//            User existingUser = userRepository.findById(userId)
//                    .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        	User existingUser = mappingUtils.mapToObject(userRequest, User.class);
            // Update fields manually or map selectively
//            mappingUtils.getModelMapper().map(userRequest, existingUser);
//        	List<FineRequest> fines=userRequest.getFines().stream().map(null)
        	List<Fine> fines = existingUser.getFines();
            if (fines != null && !fines.isEmpty()) {
                for (Fine fine : fines) {
                    fine.setUser(existingUser); // Set the User reference in each Fine
                }
            }

        	
            // Save updated user
            existingUser = userRepository.save(existingUser);

            // Map Updated Entity to Response DTO
            return mappingUtils.mapToObject(existingUser, UserResponse.class);

        } catch (UserNotFoundException e) {
            throw e; // Custom Exception for User Not Found
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user: " + e.getMessage(), e);
        }
	}

	@Override
	public void deleteUser(Integer userId) {
        try {
            // Retrieve Existing User
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

            // Delete User
            userRepository.delete(user);

        } catch (UserNotFoundException e) {
            throw e; // Custom Exception for User Not Found
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage(), e);
        }

	}

}
