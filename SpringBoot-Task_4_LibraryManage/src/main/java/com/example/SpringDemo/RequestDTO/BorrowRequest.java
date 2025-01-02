package com.example.SpringDemo.RequestDTO;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class BorrowRequest {
	
	private Integer borrowId;
	
	@NotNull(message = "Borrow date is required")
    private Date borrowDate;

    @NotNull(message = "Due date is required")
    private Date dueDate;

//    private LocalDate returnDate; //not be available initially

    private Integer userId;


    private Integer bookId;

    private Boolean isReturned;

}
