package hei.school.digitbank.controller;
import hei.school.digitbank.dao.TransfertDAO;
import hei.school.digitbank.entity.Transfert;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/transferts")
public class TransfertController {
    private final TransfertDAO transfertDAO;

    public TransfertController(TransfertDAO transfertDAO) {
        this.transfertDAO = transfertDAO;
    }
    @GetMapping
    public List<Transfert> getAllTransferts() {
        return transfertDAO.findAll();
    }
    @GetMapping("/{id}")
    public Transfert getTransfertById(@PathVariable Integer id) {
        return transfertDAO.findById(id);
    }
    @PostMapping
    public void createTransfert(@RequestBody Transfert newTransfert) {
        transfertDAO.save(newTransfert);
    }
    @DeleteMapping("/{id}")
    public void deleteTransfert(@PathVariable Integer id) {
        transfertDAO.delete(id);
    }
}

