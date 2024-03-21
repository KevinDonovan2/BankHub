package hei.school.digitbank.controller;
import hei.school.digitbank.dao.TransferDAO;
import hei.school.digitbank.entity.Transfer;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/transferts")
public class TransfertController {
    private final TransferDAO transfertDAO;

    public TransfertController(TransferDAO transfertDAO) {
        this.transfertDAO = transfertDAO;
    }
    @GetMapping
    public List<Transfer> getAllTransferts() {
        return transfertDAO.findAll();
    }
    @GetMapping("/{id}")
    public Transfer getTransfertById(@PathVariable Integer id) {
        return transfertDAO.findById(id);
    }
    @PostMapping
    public void createTransfert(@RequestBody Transfer newTransfert) {
        transfertDAO.save(newTransfert);
    }
    @DeleteMapping("/{id}")
    public void deleteTransfert(@PathVariable Integer id) {
        transfertDAO.delete(id);
    }
}

