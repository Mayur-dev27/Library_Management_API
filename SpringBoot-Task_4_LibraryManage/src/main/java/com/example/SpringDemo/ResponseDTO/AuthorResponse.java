package com.example.SpringDemo.ResponseDTO;

import java.util.List;

import com.example.SpringDemo.RequestDTO.BookReqForAuthor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {


	private Integer authorId;
	
	private String authorName;
	
	private List<BookReqForAuthor> books;
}
