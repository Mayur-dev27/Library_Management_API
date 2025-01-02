package com.example.SpringDemo.Service;

import java.util.List;

import com.example.SpringDemo.RequestDTO.FineRequest;
import com.example.SpringDemo.ResponseDTO.FineResponse;

public interface FineService {

    FineResponse createFine(FineRequest fineRequest);

    // Get fine by ID
    FineResponse getFineById(Integer fineId);

    // Get all fines
    List<FineResponse> getAllFines();

    // Update a fine
    FineResponse updateFine(Integer fineId, FineRequest fineRequest);

    // Delete a fine
    void deleteFine(Integer fineId);
    
    
    public void applyLateFeesToOverdueAccounts();
}
