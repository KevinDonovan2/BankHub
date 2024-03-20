package hei.school.digitbank.controller;
import hei.school.digitbank.dao.AccountStatementDAO;
import hei.school.digitbank.entity.AccountStatement;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/account-statements")
public class AccountStatementController {
    private final AccountStatementDAO accountStatementDAO;

    public AccountStatementController(AccountStatementDAO accountStatementDAO) {
        this.accountStatementDAO = accountStatementDAO;
    }
    @GetMapping
    public List<AccountStatement> getAllAccountStatements() {
        return accountStatementDAO.findAll();
    }
    @GetMapping("/{id}")
    public AccountStatement getAccountStatementById(@PathVariable Integer id) {
        return accountStatementDAO.findById(id);
    }
    @PostMapping
    public void createAccountStatement(@RequestBody AccountStatement newStatement) {
        accountStatementDAO.save(newStatement);
    }
    @DeleteMapping("/{id}")
    public void deleteAccountStatement(@PathVariable Integer id) {
        accountStatementDAO.delete(id);
    }
}

