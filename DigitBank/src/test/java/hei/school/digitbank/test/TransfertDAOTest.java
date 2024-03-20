package hei.school.digitbank.test;
import hei.school.digitbank.dao.TransfertDAO;
import hei.school.digitbank.entity.Transfert;
import java.sql.Timestamp;
import java.util.List;

public class TransfertDAOTest {
    public static void main(String[] args) {
        TransfertDAO transfertDAO = new TransfertDAO();
        // findAll
        System.out.println("All transfers:");
        List<Transfert> transferts = transfertDAO.findAll();
        for (Transfert transfert : transferts) {
            System.out.println(transfert);
        }
        // findById
        Integer transfertIdToFind = 1;
        System.out.println("\nTransfer found by ID:");
        Transfert transfert = transfertDAO.findById(transfertIdToFind);
        if (transfert != null) {
            System.out.println(transfert);
        } else {
            System.out.println("No transfer found with the ID " + transfertIdToFind);
        }
        // save
        Transfert newTransfert = new Transfert(4, 100.0, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "Test transfer", "Pending", 3);
        System.out.println("\n New transfer registration:");
        transfertDAO.save(newTransfert);
        System.out.println("New transfer successfully registered.");

        // delete
        Integer transfertIdToDelete = 4;
        System.out.println("\n Deleting a transfer:");
        transfertDAO.delete(transfertIdToDelete);
        System.out.println("Transfer successfully deleted.");
    }
}

