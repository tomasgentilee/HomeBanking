package com.MindHub.HomeBanking.services.implement;

import com.MindHub.HomeBanking.Datos.ClientLoanDTO;
import com.MindHub.HomeBanking.Datos.LoanDTO;
import com.MindHub.HomeBanking.Models.ClientLoan;
import com.MindHub.HomeBanking.Models.Loan;
import com.MindHub.HomeBanking.Repository.ClientLoanRepository;
import com.MindHub.HomeBanking.Repository.LoanRepository;
import com.MindHub.HomeBanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LoanServiceImplements implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Override
    public List<LoanDTO> getLoansDTO() {
        return  loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
    }

    @Override
    public Loan getLoan(long id) {
        return loanRepository.getOne(id);
    }

    @Override
    public void saveClientLoan(ClientLoan ClientLoan) {
        clientLoanRepository.save(ClientLoan);
    }

    @Override
    public List<ClientLoanDTO> getClientLoans() {
        return clientLoanRepository.findAll().stream().map(ClientLoanDTO::new).collect(toList());
    }

    @Override
    public ClientLoanDTO getClientLoan(long id) {
        return clientLoanRepository.findById(id).map(ClientLoanDTO::new).orElse(null);
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }
}
