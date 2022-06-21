package com.MindHub.HomeBanking;

import com.MindHub.HomeBanking.Models.*;
import com.MindHub.HomeBanking.Repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;




@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    TransactionRepository transactionRepository;


//Loans

    @Test
    public void existLoans(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans,is(not(empty())));

    }

    @Test
    public void existPersonalLoan(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));

    }

//Cards

    @Test
    public void existCard(){

        List<Card> cards = cardRepository.findAll();

        assertThat(cards,is(not(empty())));

    }

    @Test
    public void existPersonalCard(){

        List<Card> cards = cardRepository.findAll();

        assertThat(cards, hasItem(hasProperty("cardNumber")));

    }

//Accounts

    @Test
    public void existAccount(){

        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts,is(not(empty())));

    }

    @Test
    public void existPersonalAccount(){

        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts, hasItem(hasProperty("number", is("VIN001") )));

    }

//ClientLoans

    @Test
    public void existClientLoans(){

        List<ClientLoan> clientLoans = clientLoanRepository.findAll();

        assertThat(clientLoans,is(not(empty())));

    }

    @Test
    public void existPersonalClientLoan(){

        List<ClientLoan> clientLoans = clientLoanRepository.findAll();

        assertThat(clientLoans, hasItem(hasProperty("id")));

    }

//Transaction

    @Test
    public void existTransaction(){

        List<Transaction> transactions = transactionRepository.findAll();

        assertThat(transactions,is(not(empty())));

    }

    @Test
    public void existPersonalTransaction(){

        List<Transaction> transactions = transactionRepository.findAll();

        assertThat(transactions, hasItem(hasProperty("type")));

    }




}