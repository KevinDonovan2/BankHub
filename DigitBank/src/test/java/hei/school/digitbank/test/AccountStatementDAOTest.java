package hei.school.digitbank.test;
import hei.school.digitbank.dao.AccountStatementDAO;
import hei.school.digitbank.entity.AccountStatement;
import java.sql.Timestamp;
import java.util.List;

public class AccountStatementDAOTest {
    public static void main(String[] args) {
        AccountStatementDAO accountStatementDAO = new AccountStatementDAO();
        // findAll
        System.out.println("All account statements:");
        List<AccountStatement> statements = accountStatementDAO.findAll();
        for (AccountStatement statement : statements) {
            System.out.println(statement);
        }
        // findById
        Integer statementIdToFind = 1;
        System.out.println("\n Account statement found by ID:");
        AccountStatement statement = accountStatementDAO.findById(statementIdToFind);
        if (statement != null) {
            System.out.println(statement);
        } else {
            System.out.println("No account statement found with the ID " + statementIdToFind);
        }
        // save
        AccountStatement newStatement = new AccountStatement(4, new Timestamp(System.currentTimeMillis()), "Test statement", 100.0, 200.0, 3);
        System.out.println("\n New account statement registration:");
        accountStatementDAO.save(newStatement);
        System.out.println("New account statement successfully registered.");

        // delete
        Integer statementIdToDelete = 4;
        System.out.println("\n Deleting an account statement:");
        accountStatementDAO.delete(statementIdToDelete);
        System.out.println("Account statement successfully deleted.");
    }
}

