package com.example.SpringDemo.RequestDTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class PublisherRequest {

	private Integer publisherId;

	@NotBlank(message = "Publisher name must me filled")
	@Size(min = 2,max = 20)
	@JsonProperty("pName")
	private String pName;

	private List<BookReqForPublisher> books;
}
