package hei.school.digitbank.controller;
import hei.school.digitbank.dao.AccountDAO;
import hei.school.digitbank.entity.Account;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountDAO accountDAO;
    public AccountController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
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
}

