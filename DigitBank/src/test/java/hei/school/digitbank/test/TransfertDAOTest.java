package hei.school.digitbank.test;
import hei.school.digitbank.dao.TransfertDAO;
import hei.school.digitbank.entity.Transfert;
import java.sql.Timestamp;
import java.util.List;

public class TransfertDAOTest {
    public static void main(String[] args) {
        TransferDAO transfertDAO = new TransferDAO();
        // findAll
        System.out.println("All transfers:");
        List<Transfer> transfers = transferDAO.findAll();
        for (Transfer transfer : transfers) {
            System.out.println(transfer);
        }
        // findById
        Integer transferIdToFind = 1;
        System.out.println("\nTransfer found by ID:");
        Transfer transfer = transferDAO.findById(transferIdToFind);
        if (transfert != null) {
            System.out.println(transfer);
        } else {
            System.out.println("No transfer found with the ID " + transferIdToFind);
        }
        // save
        Transfer newTransfer = new Transfer(4, 100.0, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "Test transfer", "Pending", 3, 2);
        System.out.println("\n New transfer registration:");
        transferDAO.save(newTransfer);
        System.out.println("New transfer successfully registered.");

        // delete
        Integer transferIdToDelete = 4;
        System.out.println("\n Deleting a transfer:");
        transferDAO.delete(transferIdToDelete);
        System.out.println("Transfer successfully deleted.");
    }
}

