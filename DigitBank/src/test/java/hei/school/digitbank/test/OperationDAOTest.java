package hei.school.digitbank.test;
import hei.school.digitbank.dao.OperationDAO;
import hei.school.digitbank.entity.Operation;
import java.sql.Timestamp;
import java.util.List;

public class OperationDAOTest {
    public static void main(String[] args) {
        OperationDAO operationDAO = new OperationDAO();
        // findAll
        System.out.println("All operations:");
        List<Operation> operations = operationDAO.findAll();
        for (Operation operation : operations) {
            System.out.println(operation);
        }
        // findById
        Integer operationIdToFind = 1;
        System.out.println("\nOperation found by ID:");
        Operation operation = operationDAO.findById(operationIdToFind);
        if (operation != null) {
            System.out.println(operation);
        } else {
            System.out.println("No operation found with the ID " + operationIdToFind);
        }
        // save
        Operation newOperation = new Operation(4, "Test operation", 100.0, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), 3);
        System.out.println("\n New operation registration:");
        operationDAO.save(newOperation);
        System.out.println("New operation successfully registered.");

        // delete
        Integer operationIdToDelete = 4;
        System.out.println("\n Deleting an operation:");
        operationDAO.delete(operationIdToDelete);
        System.out.println("Operation successfully deleted.");
    }
}

