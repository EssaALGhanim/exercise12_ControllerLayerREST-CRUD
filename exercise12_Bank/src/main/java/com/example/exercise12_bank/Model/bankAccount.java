package com.example.exercise12_bank.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class bankAccount {
    private String ID;
    private String Username;
    private double Balance;
}
