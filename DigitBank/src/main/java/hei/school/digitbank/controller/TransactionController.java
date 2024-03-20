package hei.school.digitbank.controller;
import hei.school.digitbank.dao.TransactionDAO;
import hei.school.digitbank.entity.Transaction;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionDAO transactionDAO;

    public TransactionController(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionDAO.findAll();
    }
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Integer id) {
        return transactionDAO.findById(id);
    }
    @PostMapping
    public void createTransaction(@RequestBody Transaction newTransaction) {
        transactionDAO.save(newTransaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Integer id) {
        transactionDAO.delete(id);
    }
}
