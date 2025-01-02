package com.example.SpringDemo.RequestDTO;

import java.util.Date;


import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class FineRequest {

    private Long fineId;


    @Positive(message = "Fine amount must be a positive number")
    private Double amount;


    private Date fineDate;


    private Integer userId;
}
