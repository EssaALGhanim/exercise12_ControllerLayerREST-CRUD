package com.example.exercise12_bank.Controller;

import com.example.exercise12_bank.Api.ApiResponse;
import com.example.exercise12_bank.Model.bankAccount;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/bank")
public class BankController {

    ArrayList<bankAccount> accounts = new ArrayList<>();

    @PostMapping("manage/add")
    public ApiResponse addAccount(@RequestBody bankAccount account){
        for (bankAccount acc : accounts){
            if (acc.getID().equals(account.getID())){
                return new ApiResponse("Account already exists", HttpStatus.CONFLICT.value());
            }
        }
        accounts.add(account);
        return new ApiResponse("Account added successfully", HttpStatus.CREATED.value());
    }

    @DeleteMapping("manage/remove/{ID}")
    public ApiResponse removeAccount(@PathVariable String ID){
        for (bankAccount account : accounts){
            if (account.getID().equals(ID)){
                accounts.remove(account);
                return new ApiResponse("Account removed successfully", HttpStatus.OK.value());
            }
        }
        return new ApiResponse("Account not found", HttpStatus.NOT_FOUND.value());
    }


    @PutMapping("manage/update/{ID}")
    public ApiResponse updateAccount(@RequestBody bankAccount account, @PathVariable String ID){

        for (int i = 0; i < accounts.size(); i++){
            if (accounts.get(i).getID().equals(ID)){
                accounts.set(i, account);
                return new ApiResponse("Account updated successfully", HttpStatus.OK.value());
            }
        }
        return new ApiResponse("Account not found", HttpStatus.NOT_FOUND.value());
    }

    @GetMapping("/get/all-customers")
    public ArrayList<bankAccount> getAccounts(){
        return accounts;
    }

    @GetMapping("/get/customer-by-id/{ID}")
    public Object getAccount(@PathVariable String ID){
        for (bankAccount account : accounts){
            if (account.getID().equals(ID)){
                return account;
            }
        }
        return new ApiResponse("Account not found", 404);
    }

    @PutMapping("/transaction/deposit/{ID}/{amount}")
    public ApiResponse deposit(@PathVariable String ID, @PathVariable double amount){
        if (amount <= 0){
            return new ApiResponse("Invalid amount, amount must be positive", HttpStatus.BAD_REQUEST.value());
        }
        for (bankAccount account : accounts){
            if (account.getID().equals(ID)){
                account.setBalance(account.getBalance() + amount);
                return new ApiResponse("Account balance updated successfully", HttpStatus.OK.value());
            }
        }
        return new ApiResponse("Account not found", HttpStatus.NOT_FOUND.value());
    }

    @PutMapping("/transaction/withdraw/{ID}/{amount}")
    public ApiResponse withdraw(@PathVariable String ID, @PathVariable double amount){
        if (amount <= 0){
            return new ApiResponse("Invalid amount, amount must be positive", HttpStatus.BAD_REQUEST.value());
        }
        for (bankAccount account : accounts){
            if (account.getID().equals(ID)){
                if (account.getBalance() < amount){
                    return new ApiResponse("Insufficient balance", HttpStatus.BAD_REQUEST.value());
                }
                account.setBalance(account.getBalance() - amount);
                return new ApiResponse("Account balance updated successfully", HttpStatus.OK.value());
            }
        }
        return new ApiResponse("Account not found", HttpStatus.NOT_FOUND.value());
    }

    @PutMapping("/transaction/transfer/{ID}/{amount}/{targetID}")
    public ApiResponse transfer(@PathVariable String ID, @PathVariable double amount, @PathVariable String targetID){
        if (amount <= 0){
            return new ApiResponse("Invalid amount, amount must be positive", HttpStatus.BAD_REQUEST.value());
        }
        for (bankAccount account : accounts){
            if (account.getID().equals(ID)){
                if (account.getBalance() < amount){
                    return new ApiResponse("Insufficient balance", HttpStatus.BAD_REQUEST.value());
                }
                for (bankAccount targetAccount : accounts){
                    if (targetAccount.getID().equals(targetID)){
                        account.setBalance(account.getBalance() - amount);
                        targetAccount.setBalance(targetAccount.getBalance() + amount);
                        return new ApiResponse("Account balance updated successfully", HttpStatus.OK.value());
                    }
                }
                return new ApiResponse("Target account not found", HttpStatus.NOT_FOUND.value());
            }
        }
        return new ApiResponse("Account not found", HttpStatus.NOT_FOUND.value());
    }




}
