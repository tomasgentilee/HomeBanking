package com.MindHub.HomeBanking.services;

import com.MindHub.HomeBanking.Datos.ClientLoanDTO;
import com.MindHub.HomeBanking.Datos.LoanDTO;
import com.MindHub.HomeBanking.Models.ClientLoan;
import com.MindHub.HomeBanking.Models.Loan;

import java.util.List;

public interface LoanService {

    List<LoanDTO> getLoansDTO();

    Loan getLoan(long id);

    void saveClientLoan(ClientLoan ClientLoan);

    List<ClientLoanDTO> getClientLoans();

    ClientLoanDTO getClientLoan(long id);

    void saveLoan(Loan loan);

}
