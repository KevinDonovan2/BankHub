package hei.school.digitbank.test;
import hei.school.digitbank.dao.TransactionDAO;
import hei.school.digitbank.entity.Transaction;

import java.util.List;

public class TransactionDAOTest {
    public static void main(String[] args) {
        TransactionDAO transactionDAO = new TransactionDAO();
        // findAll
        System.out.println("All transactions:");
        List<Transaction> transactions = transactionDAO.findAll();
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        // findById
        Integer transactionIdToFind = 1;
        System.out.println("\nTransaction found by ID:");
        Transaction transaction = transactionDAO.findById(transactionIdToFind);
        if (transaction != null) {
            System.out.println(transaction);
        } else {
            System.out.println("No transaction found with the ID " + transactionIdToFind);
        }
        // save
        Transaction newTransaction = new Transaction(4, "Test test", 2, 4);
        System.out.println("\n New transaction registration:");
        transactionDAO.save(newTransaction);
        System.out.println("New transaction successfully registered.");

        // delete
        Integer transactionIdToDelete = 4;
        System.out.println("\n Deleting a transaction:");
        transactionDAO.delete(transactionIdToDelete);
        System.out.println("Transaction successfully deleted.");
    }
}

