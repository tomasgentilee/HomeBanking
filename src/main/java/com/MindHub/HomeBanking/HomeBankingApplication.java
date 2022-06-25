package com.MindHub.HomeBanking;

import com.MindHub.HomeBanking.Models.*;
import com.MindHub.HomeBanking.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomeBankingApplication {

	public static void
	main(String[] args) {
		SpringApplication.run(HomeBankingApplication.class, args);

	}

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository, CreditCardLimitRepository creditCardLimitRepository) {
		return (args) -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba123"));
			clientRepository.save(client1);

			Client admin = new Client("Tomas", "Gentile", "tomigentile@gmail.com", passwordEncoder.encode("melba123"));
			clientRepository.save(admin);

			Account account1 = new Account("VIN001", 5000, LocalDateTime.now(), client1, AccountType.Savings);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002", 7500, LocalDateTime.now().plusDays(1), client1, AccountType.Current);
			accountRepository.save(account2);

			Transaction transaction1 = new Transaction("Te transfiero unos pesos", -3000, LocalDateTime.now(), TransactionType.DEBITO, account1);
			transactionRepository.save(transaction1);
			Transaction transaction2 = new Transaction("Toma amigo", 2000, LocalDateTime.now(), TransactionType.CREDITO, account1);
			transactionRepository.save(transaction2);
			Transaction transaction3 = new Transaction("Te lo devuelvo", 4000, LocalDateTime.now(), TransactionType.CREDITO, account2);
			transactionRepository.save(transaction3);

			Loan Hipotecario = new Loan("Hipotecario", 500000, List.of(12,24,36,48,60), 1.25);
			loanRepository.save(Hipotecario);
			Loan Personal = new Loan("Personal", 100000, List.of(12,24,36,48,60),1.40);
			loanRepository.save(Personal);
			Loan Automotriz = new Loan("Automotriz", 300000, List.of(6,12,24,36),1.30);
			loanRepository.save(Automotriz);

			ClientLoan clientLoan1 = new ClientLoan(400000, 60, client1, Hipotecario);
			clientLoanRepository.save(clientLoan1);
			ClientLoan clientLoan2 = new ClientLoan(30000, 12, client1, Personal);
			clientLoanRepository.save(clientLoan2);

			CreditCardLimit limitSilver = new CreditCardLimit(CardColor.SILVER, 50000.00, 50000.00);
			creditCardLimitRepository.save(limitSilver);

			CreditCardLimit limitGold = new CreditCardLimit(CardColor.GOLD, 100000.00, 100000.00);
			creditCardLimitRepository.save(limitGold);

			CreditCardLimit limitBlack = new CreditCardLimit(CardColor.BLACK, 200000.00, 200000.00);
			creditCardLimitRepository.save(limitBlack);

			CreditCardLimit limitTitanium = new CreditCardLimit(CardColor.TITANIUM, 400000.00, 400000.00);
			creditCardLimitRepository.save(limitTitanium);

			Card card1 = new Card(CardColor.GOLD, CardType.CREDIT, "Melba Morel", "1111-1111-1111-1111", 666, LocalDateTime.now(), LocalDateTime.now().plusYears(5),client1, limitGold);
			cardRepository.save(card1);

			Card card2 = new Card(CardColor.TITANIUM, CardType.DEBIT, "Melba Morel", "2222-2222-2222-2222", 777, LocalDateTime.now(), LocalDateTime.now().plusYears(5),client1, account1);
			cardRepository.save(card2);


		};
	}
}
