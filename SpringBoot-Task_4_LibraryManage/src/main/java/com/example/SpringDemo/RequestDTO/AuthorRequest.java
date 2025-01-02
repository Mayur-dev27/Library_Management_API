package com.example.SpringDemo.RequestDTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequest {

	private Integer authorId;
	
	@NotBlank(message = "Title must be present")
	@Size(min = 2,max = 20)
	private String authorName;
	
	private List<BookReqForAuthor> books;
}

