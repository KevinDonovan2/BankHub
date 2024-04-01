package hei.school.digitbank.controller;
import hei.school.digitbank.dao.TransferDAO;
import hei.school.digitbank.entity.Transfer;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/transferts")
public class TransfertController {
    private final TransferDAO transferDAO;

    public TransfertController(TransferDAO transfertDAO) {
        this.transferDAO = transfertDAO;
    }
    @GetMapping
    public List<Transfer> getAllTransferts() {
        return transferDAO.findAll();
    }
    @GetMapping("/{id}")
    public Transfer getTransfertById(@PathVariable Integer id) {
        return transferDAO.findById(id);
    }
    @PostMapping
    public void createTransfert(@RequestBody Transfer newTransfert) {
        transferDAO.save(newTransfert);
    }
    @DeleteMapping("/{id}")
    public void deleteTransfert(@PathVariable Integer id) {
        transferDAO.delete(id);
    }
}

