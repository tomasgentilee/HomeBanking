package com.MindHub.HomeBanking.services;

import com.MindHub.HomeBanking.Datos.TransactionDTO;
import com.MindHub.HomeBanking.Models.Transaction;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> getTransactionsDTO();

    void saveTransaction(Transaction transaction);
}
