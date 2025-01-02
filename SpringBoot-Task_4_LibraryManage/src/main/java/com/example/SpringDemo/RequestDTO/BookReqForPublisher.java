package com.example.SpringDemo.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookReqForPublisher {

	private Long bookId;
	
	@NotBlank(message = "Title must be present")
	@Size(min = 2,max = 20)
	private String title;
	
	@Positive(message = "No. must be positive")
	private Integer availableCopies;

	private Integer publisherId;
}
