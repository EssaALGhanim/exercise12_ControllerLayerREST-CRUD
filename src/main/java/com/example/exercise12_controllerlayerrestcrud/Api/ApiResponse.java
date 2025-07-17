package com.example.exercise12_controllerlayerrestcrud.Api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {

    private String message;
    private int status;

}


