package com.example.SpringDemo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringDemo.RequestDTO.FineRequest;
import com.example.SpringDemo.ResponseDTO.FineResponse;
import com.example.SpringDemo.Service.FineService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fines")
public class FineController {
	
	
	@Autowired
    private FineService fineService;

    // Create a new fine
    @PostMapping("/createfine")
    public ResponseEntity<FineResponse> createFine(@RequestBody @Valid FineRequest fineRequest) {
        FineResponse fineResponse = fineService.createFine(fineRequest);
        return new ResponseEntity<>(fineResponse, HttpStatus.CREATED);
    }

    // Get fine by ID
    @GetMapping("/getfinebyid/{fineId}")
    public ResponseEntity<FineResponse> getFineById(@PathVariable Integer fineId) {
        FineResponse fineResponse = fineService.getFineById(fineId);
        return new ResponseEntity<>(fineResponse, HttpStatus.OK);
    }

    // Get all fines
    @GetMapping("/getallfines")
    public ResponseEntity<List<FineResponse>> getAllFines() {
        List<FineResponse> fines = fineService.getAllFines();
        return new ResponseEntity<>(fines, HttpStatus.OK);
    }

    // Update fine by ID
    @PutMapping("/updatefine/{fineId}")
    public ResponseEntity<FineResponse> updateFine(@PathVariable Integer fineId, @RequestBody @Valid FineRequest fineRequest) {
        FineResponse fineResponse = fineService.updateFine(fineId, fineRequest);
        return new ResponseEntity<>(fineResponse, HttpStatus.OK);
    }

    // Delete fine by ID
    @DeleteMapping("/deletefinebyid/{fineId}")
    public ResponseEntity<Void> deleteFine(@PathVariable Integer fineId) {
        fineService.deleteFine(fineId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    // Endpoint to trigger fine calculation manually
    @PostMapping("/apply-late-fees")
    public String applyLateFees() {
        fineService.applyLateFeesToOverdueAccounts();
        return "Late fees applied successfully!";
    }
}
