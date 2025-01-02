package com.example.SpringDemo.ResponseDTO;




import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

	private Long bookId;
	
	private String title;
	
	private Integer availableCopies;
	
    private String authorName;
    
    private String publisherName;

    private List<BorrowResponse> borrows;

}
