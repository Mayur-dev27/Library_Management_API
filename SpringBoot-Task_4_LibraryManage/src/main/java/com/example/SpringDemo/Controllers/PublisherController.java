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

import com.example.SpringDemo.RequestDTO.PublisherRequest;
import com.example.SpringDemo.ResponseDTO.PublisherResponse;
import com.example.SpringDemo.Service.PublisherService;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;


    @PostMapping("/createPublisher")
    public ResponseEntity<PublisherResponse> createPublisher(@RequestBody PublisherRequest publisherRequest) {
        PublisherResponse publisherResponse = publisherService.createPublisher(publisherRequest);
        return new ResponseEntity<>(publisherResponse, HttpStatus.CREATED);
    }


    @GetMapping("/getPublisherById/{publisherId}")
    public ResponseEntity<PublisherResponse> getPublisherById(@PathVariable Integer publisherId) {
        PublisherResponse publisherResponse = publisherService.getPublisherById(publisherId);
        return new ResponseEntity<>(publisherResponse, HttpStatus.OK);
    }


    @GetMapping("/getAllPublishers")
    public ResponseEntity<List<PublisherResponse>> getAllPublishers() {
        List<PublisherResponse> publisherResponses = publisherService.getAllPublishers();
        return new ResponseEntity<>(publisherResponses, HttpStatus.OK);
    }

    @PutMapping("/updatePublisher")
    public ResponseEntity<PublisherResponse> updatePublisher(@RequestBody PublisherRequest publisherRequest) {
        PublisherResponse publisherResponse = publisherService.updatePublisher(publisherRequest);
        return new ResponseEntity<>(publisherResponse, HttpStatus.OK);
    }


    @DeleteMapping("/deletePublisherById/{publisherId}")
    public ResponseEntity<String> deletePublisher(@PathVariable Integer publisherId) {
        publisherService.deletePublisher(publisherId);
        return new ResponseEntity<>("Publisher deleted successfully", HttpStatus.NO_CONTENT);
    }
}
