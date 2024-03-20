package hei.school.digitbank.controller;
import hei.school.digitbank.dao.AccountDAO;
import hei.school.digitbank.entity.Account;
import hei.school.digitbank.entity.WithdrawalRequest;
import hei.school.digitbank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountDAO accountDAO;
    private final AccountService accountService;
    @Autowired
    public AccountController(AccountDAO accountDAO, AccountService accountService) {
        this.accountDAO = accountDAO;
        this.accountService = accountService;
    }
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountDAO.findAll();
    }
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Integer id) {
        return accountDAO.findById(id);
    }
    @PostMapping
    public void createAccount(@RequestBody Account newAccount) {
        accountDAO.save(newAccount);
    }
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Integer id) {
        accountDAO.delete(id);
    }
    @PostMapping("/withdraw")
    public boolean withdrawMoney(@RequestBody WithdrawalRequest withdrawalRequest) {
        return accountService.withdrawMoney(withdrawalRequest.getAccountNumber(), withdrawalRequest.getAmount());
    }
    @GetMapping("/{accountNumber}/main-balance")
    public double getMainBalance(@PathVariable Integer accountNumber) {
        return accountService.getMainBalance(accountNumber);
    }
    @GetMapping("/{accountNumber}/loans")
    public double getLoans(@PathVariable Integer accountNumber) {
        return accountService.getLoans(accountNumber);
    }
    @GetMapping("/{accountNumber}/interest-on-loans")
    public double getInterestOnLoans(@PathVariable Integer accountNumber) {
        return accountService.getInterestOnLoans(accountNumber);
    }
}

