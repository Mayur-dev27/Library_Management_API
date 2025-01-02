package com.example.SpringDemo.ResponseDTO;

import java.util.Date;

import com.example.SpringDemo.Entity.User;

import lombok.Data;

@Data
public class FineResponse {

    private Long fineId;

    private Double amount;

    private Date fineDate;

    private User user;
}
