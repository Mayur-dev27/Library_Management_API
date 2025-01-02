package com.example.SpringDemo.RequestDTO;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowReqForUser {

	private Long borrowId;
	
	@NotNull(message = "Borrow date is required")
    private Date borrowDate;

    @NotNull(message = "Due date is required")
    private Date dueDate;


    private Long bookId;

    private Boolean isReturned;
}
