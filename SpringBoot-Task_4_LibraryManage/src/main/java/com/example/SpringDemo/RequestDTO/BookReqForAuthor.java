package com.example.SpringDemo.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookReqForAuthor {

	private Long bookId;
	
	@NotBlank(message = "Title must be present")
	@Size(min = 2,max = 20)
	private String title;
	
	@Positive(message = "No. must be positive")
	private Integer availableCopies;

	private Integer authorId;

}
