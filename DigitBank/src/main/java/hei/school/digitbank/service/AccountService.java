package hei.school.digitbank.service;
import hei.school.digitbank.dao.AccountDAO;
import hei.school.digitbank.dao.TransactionDAO;
import hei.school.digitbank.entity.Account;
import hei.school.digitbank.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AccountService {
    private static AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;
    @Autowired
    public AccountService(AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }
    // Answer F2
    public void enableCreditForAccount(Integer accountNumber) {
        Account account = accountDAO.findById(accountNumber);
        if (account != null) {
            account.setCreditAuthorized(calculateCreditAuthorized(account.getNetMonthlySalary()));
            accountDAO.save(account);
        } else {
            throw new RuntimeException("Account not found");
        }
    }
    // Answer F2
    public void disableCreditForAccount(Integer accountNumber) {
        Account account = accountDAO.findById(accountNumber);
        if (account != null) {
            account.setCreditAuthorized(0.0);
            accountDAO.save(account);
        } else {
            throw new RuntimeException("Account not found");
        }
    }
    // The authorized overdraft amount is equal to 1/3 of net monthly salary
    private Double calculateCreditAuthorized(Double netMonthlySalary) {
        return netMonthlySalary / 3;
    }
    // Answer F2
    // Scheduled method for calculating daily interest on overdrafts
    @Scheduled(cron = "0 0 0 * * *")
    public void calculateDailyInterest() {
        List<Account> accounts = accountDAO.findAll();
        for (Account account : accounts) {
            if (account.getMainBalance() < 0) {
                double interestRate = calculateInterestRate(account);
                double overdraftAmount = Math.abs(account.getMainBalance());
                double dailyInterest = overdraftAmount * interestRate;
                account.setMainBalance(account.getMainBalance() - dailyInterest);
                accountDAO.updateMainBalance(account.getAccountNumber(), account.getMainBalance());
            }
        }
    }
    // Answer F2
    // Method for calculating the interest rate based on the number of days overdrawn
    private double calculateInterestRate(Account account) {
        long daysInOverdraft = calculateDaysInOverdraft(account);
        if (daysInOverdraft <= 7) {
            return 0.01;
        } else {
            return 0.02;
        }
    }
    // Answer F2
    // Method for calculating the number of overdraft days
    private long calculateDaysInOverdraft(Account account) {
        return TimeUnit.DAYS.convert((long) Math.abs(account.getMainBalance()), TimeUnit.MILLISECONDS);
    }
    // Answer F2: Retrait d'argent
    public static boolean withdrawMoney(Integer accountNumber, Double amount) {
        Account account = accountDAO.findById(accountNumber);
        if (account != null) {
            Double totalBalance = account.getMainBalance() + account.getCreditAuthorized();
            if (totalBalance >= amount) {
                Double newBalance = account.getMainBalance() - amount;
                account.setMainBalance(newBalance);
                accountDAO.save(account);
                return true;
            } else {
                System.out.println("Insufficient balance for withdrawal.");
                return false;
            }
        } else {
            System.out.println("Account not found.");
            return false;
        }
    }
    // Answer F3: Consultation solde
    public Account getAccountDetails(Integer accountNumber) {
        return accountDAO.findById(accountNumber);
    }
    public double getMainBalance(Integer accountNumber) {
        Account account = accountDAO.findById(accountNumber);
        return account.getMainBalance();
    }
    public double getLoans(Integer accountNumber) {
        Account account = accountDAO.findById(accountNumber);
        return account.getLoans();
    }
    public double getInterestOnLoans(Integer accountNumber) {
        Account account = accountDAO.findById(accountNumber);
        return account.getInterestOnLoans();
    }
}

