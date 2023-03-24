package com.BankAccounts.Bank.Accounts.Controller;

import com.BankAccounts.Bank.Accounts.Service.TransactionService;
import com.BankAccounts.Bank.Accounts.dto.RequestDTO;
import com.BankAccounts.Bank.Accounts.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    //API for multiple users doing transactions(credit and debit) on a single account at the same time
    @PostMapping("/concurrent_transaction")
    public List<ResponseDTO> multipleTransactions(@RequestBody List<RequestDTO> requestDTOList){

        return transactionService.transaction(requestDTOList);
    }

    //API for credit amount
    @PostMapping("/deposit")
    public ResponseEntity credit(@RequestBody RequestDTO requestDTO){

       ResponseDTO responseDTO = transactionService.credit(requestDTO);

        return new ResponseEntity(responseDTO,HttpStatus.ACCEPTED);
    }

    //API for debit amount
    @PostMapping("/withdraw")
    public ResponseEntity debit(@RequestBody RequestDTO requestDTO){

        ResponseDTO responseDTO = transactionService.debit(requestDTO);

        return new ResponseEntity(responseDTO,HttpStatus.ACCEPTED);
    }
}
