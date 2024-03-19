package hei.school.digitbank.test;

import hei.school.digitbank.dao.AccountDAO;
import hei.school.digitbank.entity.Account;

import java.sql.Date;
import java.util.List;

public class AccountDAOTest {
    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO();
        // findAll
        System.out.println("your all accounts :");
        List<Account> accounts = accountDAO.findAll();
        for (Account account : accounts) {
            System.out.println(account);
        }
        //  findById
        Integer accountNumberToFind = 2;
        System.out.println("\n Account find by ID:");
        Account account = accountDAO.findById(accountNumberToFind);
        if (account != null) {
            System.out.println(account);
        } else {
            System.out.println("No account found with the number " + accountNumberToFind);
        }
        // save
        Account newAccount = new Account(3, "user test", new Date(System.currentTimeMillis()), 3000.0, 5000.0, 1000.0, 0.05, 0.02, true);
        System.out.println("\n New account registration :");
        accountDAO.save(newAccount);
        System.out.println("New account successfully registered.");

        // delete
        Integer accountNumberToDelete = 2;
        System.out.println("\nDeleting an account :");
        accountDAO.delete(accountNumberToDelete);
        System.out.println("Account successfully deleted.");
    }
}

