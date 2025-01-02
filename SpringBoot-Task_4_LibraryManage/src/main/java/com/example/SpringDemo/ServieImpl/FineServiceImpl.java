package com.example.SpringDemo.ServieImpl;

import java.util.Date;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringDemo.Entity.Borrow;
import com.example.SpringDemo.Entity.Fine;
import com.example.SpringDemo.ExceptionClasses.FineNotFoundException;
import com.example.SpringDemo.Repository.BorrowRepository;
import com.example.SpringDemo.Repository.FineRepository;
import com.example.SpringDemo.RequestDTO.FineRequest;
import com.example.SpringDemo.ResponseDTO.FineResponse;
import com.example.SpringDemo.Service.FineService;
import com.example.SpringDemo.Utils.MappingUtils;

@Service
public class FineServiceImpl implements FineService {

    @Autowired
    private FineRepository fineRepository;
    
    @Autowired
    private BorrowRepository borrowRepository;



    @Autowired
    private MappingUtils mappingUtils;
    
    @Override
	public FineResponse createFine(FineRequest fineRequest) {
		Fine fine = mappingUtils.mapToObject(fineRequest, Fine.class);
		
		try {

			Fine savedFine = fineRepository.save(fine); 

			return mappingUtils.mapToObject(savedFine, FineResponse.class);
			
		} catch (Exception e) {
			throw new RuntimeException("Error while creating fine: " + e.getMessage(), e);
		}
	}

	@Override
	public FineResponse getFineById(Integer fineId) {
		try {
			// Retrieve fine by ID
			Fine fine = fineRepository.findById(fineId)
				.orElseThrow(() -> new FineNotFoundException("Fine not found with ID: " + fineId));
			// Map Fine entity to FineResponse DTO
			return mappingUtils.mapToObject(fine, FineResponse.class);
		} catch (FineNotFoundException e) {
			throw e; // Re-throw FineNotFoundException for clarity
		} catch (Exception e) {
			throw new RuntimeException("Error fetching fine by ID: " + e.getMessage(), e);
		}
	}

	@Override
	public List<FineResponse> getAllFines() {
		try {
			// Retrieve all fines
			List<Fine> fines = fineRepository.findAll();
			// Map list of Fine entities to FineResponse DTOs
			return mappingUtils.mapToList(fines, FineResponse.class);
		} catch (Exception e) {
			throw new RuntimeException("Error fetching all fines: " + e.getMessage(), e);
		}
	}

	@Override
	public FineResponse updateFine(Integer fineId, FineRequest fineRequest) {
		try {
			// Retrieve the existing fine by ID
			Fine existingFine = fineRepository.findById(fineId)
				.orElseThrow(() -> new FineNotFoundException("Fine not found with ID: " + fineId));
			
			// Update fine fields from the FineRequest
			existingFine.setAmount(fineRequest.getAmount());
			existingFine.setFineDate(fineRequest.getFineDate());
			
			// Save the updated fine
			Fine updatedFine = fineRepository.save(existingFine);
			
			// Map updated Fine entity to FineResponse DTO
			return mappingUtils.mapToObject(updatedFine, FineResponse.class);
		} catch (FineNotFoundException e) {
			throw e; // Re-throw FineNotFoundException
		} catch (Exception e) {
			throw new RuntimeException("Error updating fine: " + e.getMessage(), e);
		}
	}

	@Override
	public void deleteFine(Integer fineId) {
		try {
			// Retrieve the fine by ID
			Fine fine = fineRepository.findById(fineId)
				.orElseThrow(() -> new FineNotFoundException("Fine not found with ID: " + fineId));
			
			// Delete the fine
			fineRepository.delete(fine);
		} catch (FineNotFoundException e) {
			throw e; // Re-throw FineNotFoundException
		} catch (Exception e) {
			throw new RuntimeException("Error deleting fine: " + e.getMessage(), e);
		}
	}

//    @Override
//    public void applyLateFeesToOverdueAccounts() {
//        // Get all borrow records where the book is overdue and not returned
//        List<Borrow> overdueBorrows = borrowRepository.findOverdueBorrows();
//
//        overdueBorrows.forEach(borrow -> {
//            // Get the current date and the due date
//            Date currentDate = new Date(); // current date
//            Date dueDate = borrow.getDueDate();
//
//            // Check if the book is overdue and not returned
//            if (dueDate != null && borrow.getIsReturned() == false) {
//                long overdueMillis = currentDate.getTime() - dueDate.getTime(); // in milliseconds
//                long overdueDays = TimeUnit.MILLISECONDS.toDays(overdueMillis);
//
//                if (overdueDays > 0) {
//                    // Calculate fine (₹5 per day)
//                    Double fineAmount = overdueDays * 5.0;
//
//                    // Create and save the fine record
//                    Fine fine = new Fine();
//                    fine.setAmount(fineAmount);
//                    fine.setFineDate(new java.sql.Date(currentDate.getTime())); // Set fine date
//                    fine.setUser(borrow.getUser());
//                    fineRepository.save(fine); // Save the fine in the database
//                }
//            }
//        });
//    }
	
	@Override
	public void applyLateFeesToOverdueAccounts() {
	    try {
	        // Get all borrow records where the book is overdue and not returned
	        List<Borrow> overdueBorrows = borrowRepository.findOverdueBorrows();  // created method in repo

	        overdueBorrows.forEach(borrow -> {
	            Date currentDate = new Date(); // current date
	            Date dueDate = borrow.getDueDate();

	            // Check if the book is overdue and not returned
	            if (dueDate != null && Boolean.FALSE.equals(borrow.getIsReturned())) {
	                long overdueMillis = currentDate.getTime() - dueDate.getTime(); // in milliseconds
	                long overdueDays = TimeUnit.MILLISECONDS.toDays(overdueMillis);

	                if (overdueDays > 0) {

	                    Double fineAmount = overdueDays * 5.0;


	                    Fine fine = new Fine();
	                    fine.setAmount(fineAmount);
	                    fine.setFineDate(new java.sql.Date(currentDate.getTime())); // Set fine date
	                    fine.setUser(borrow.getUser());


	                    fineRepository.save(fine);
	                    
	                    // Optional: Add logging to verify that the fine is being applied
	                    System.out.println("Fine applied: ₹" + fineAmount + " for user ID " + borrow.getUser().getUserId());
	                }
	            }
	        });
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}



}
