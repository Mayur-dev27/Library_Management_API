package com.example.SpringDemo.Service;

import java.util.List;

import com.example.SpringDemo.RequestDTO.PublisherRequest;
import com.example.SpringDemo.ResponseDTO.PublisherResponse;

public interface PublisherService {

    PublisherResponse createPublisher(PublisherRequest publisherRequest);

    // Get publisher by ID
    PublisherResponse getPublisherById(Integer publisherId);

    // Get all publishers
    List<PublisherResponse> getAllPublishers();

    // Update a publisher
    PublisherResponse updatePublisher(PublisherRequest publisherRequest);

    // Delete a publisher
    void deletePublisher(Integer publisherId);
    
}
