package hei.school.digitbank.controller;
import hei.school.digitbank.dao.OperationDAO;
import hei.school.digitbank.entity.Operation;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationController {
    private final OperationDAO operationDAO;

    public OperationController(OperationDAO operationDAO) {
        this.operationDAO = operationDAO;
    }
    @GetMapping
    public List<Operation> getAllOperations() {
        return operationDAO.findAll();
    }
    @GetMapping("/{id}")
    public Operation getOperationById(@PathVariable Integer id) {
        return operationDAO.findById(id);
    }
    @PostMapping
    public void createOperation(@RequestBody Operation newOperation) {
        operationDAO.save(newOperation);
    }
    @DeleteMapping("/{id}")
    public void deleteOperation(@PathVariable Integer id) {
        operationDAO.delete(id);
    }
}

