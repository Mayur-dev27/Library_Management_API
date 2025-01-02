package com.example.SpringDemo.ResponseDTO;

import java.util.List;


import com.example.SpringDemo.RequestDTO.BookReqForPublisher;

import lombok.Data;

@Data
public class PublisherResponse {

	private Integer publisherId;
	
	private String pName;
	
	private List<BookReqForPublisher> books;
}
