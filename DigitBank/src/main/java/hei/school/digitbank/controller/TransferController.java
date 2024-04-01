package hei.school.digitbank.controller;
import hei.school.digitbank.dao.TransferDAO;
import hei.school.digitbank.entity.Transfer;
import hei.school.digitbank.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferController {
    private final TransferDAO transferDAO;
    private final TransferService transferService;
    @Autowired
    public TransferController(TransferDAO transferDAO, TransferService transferService) {
        this.transferDAO = transferDAO;
        this.transferService = transferService;
    }
    @GetMapping
    public List<Transfer> getAllTransfers() {
        return transferDAO.findAll();
    }
    @GetMapping("/{id}")
    public Transfer getTransferById(@PathVariable Integer id) {
        return transferDAO.findById(id);
    }
    @PostMapping
    public Transfer createTransfer(@RequestBody Transfer newTransfer) {
        return transferService.performTransfer(
                newTransfer.getAccountNumber(),
                newTransfer.getDestinataireAccountNumber(),
                newTransfer.getAmount(),
                newTransfer.getReason()
        );
    }
    @DeleteMapping("/{id}")
    public void deleteTransfer(@PathVariable Integer id) {
        transferDAO.delete(id);
    }
}

