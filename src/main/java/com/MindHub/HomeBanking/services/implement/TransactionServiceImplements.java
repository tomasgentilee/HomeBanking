package com.MindHub.HomeBanking.services.implement;

import com.MindHub.HomeBanking.Datos.TransactionDTO;
import com.MindHub.HomeBanking.Models.Transaction;
import com.MindHub.HomeBanking.Repository.TransactionRepository;
import com.MindHub.HomeBanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TransactionServiceImplements implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<TransactionDTO> getTransactionsDTO() {
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(toList());
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
