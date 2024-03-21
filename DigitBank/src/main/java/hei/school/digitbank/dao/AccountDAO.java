package hei.school.digitbank.dao;
import hei.school.digitbank.dbconnection.DatabaseConnector;
import hei.school.digitbank.entity.Account;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class AccountDAO {
    private static final String TABLE_NAME = "account";

    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                accounts.add(mapResultSetToAccount(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve accounts", e);
        }
        return accounts;
    }

    public Account findById(Integer accountNumber) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE account_number = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToAccount(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve account by ID", e);
        }
        return null;
    }
   //  Answer: F1 : Création et modification des informations d’un compte
    public void save(Account account) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE account_number = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, account.getAccountNumber());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String updateQuery = "UPDATE " + TABLE_NAME + " SET customer_name = ?, customer_birthdate = ?, net_monthly_salary = ?, main_balance = ?, loans = ?, interest_on_loans = ?, decouvert_autorize = ?,  credit_authorized = ? WHERE account_number = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, account.getCustomerName());
                updateStatement.setDate(2, new java.sql.Date(account.getCustomerBirthdate().getTime()));
                updateStatement.setDouble(3, account.getNetMonthlySalary());
                updateStatement.setDouble(4, account.getMainBalance());
                updateStatement.setDouble(5, account.getLoans());
                updateStatement.setDouble(6, account.getInterestOnLoans());
                updateStatement.setBoolean(7, account.getDecouvertAutorise());
                updateStatement.setDouble(8, account.getCreditAuthorized());
                updateStatement.setInt(9, account.getAccountNumber());
                updateStatement.executeUpdate();
            } else {
                String insertQuery = "INSERT INTO " + TABLE_NAME + " (account_number, customer_name, customer_birthdate, net_monthly_salary, main_balance, loans, interest_on_loans, decouvert_autorize, credit_authorized) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, account.getAccountNumber());
                insertStatement.setString(2, account.getCustomerName());
                insertStatement.setDate(3, new java.sql.Date(account.getCustomerBirthdate().getTime()));
                insertStatement.setDouble(4, account.getNetMonthlySalary());
                insertStatement.setDouble(5, account.getMainBalance());
                insertStatement.setDouble(6, account.getLoans());
                insertStatement.setDouble(7, account.getInterestOnLoans());
                insertStatement.setBoolean(8, account.getDecouvertAutorise());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save account", e);
        }
    }
    public void updateMainBalance(Integer accountNumber, Double mainBalance) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String updateQuery = "UPDATE " + TABLE_NAME + " SET main_balance = ? WHERE account_number = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setDouble(1, mainBalance);
            updateStatement.setInt(2, accountNumber);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update main balance of account", e);
        }
    }

    public void delete(Integer accountNumber) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "DELETE FROM " + TABLE_NAME + " WHERE account_number = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete account", e);
        }
    }
    private Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        return new Account(
                resultSet.getInt("account_number"),
                resultSet.getString("customer_name"),
                resultSet.getDate("customer_birthdate"),
                resultSet.getDouble("net_monthly_salary"),
                resultSet.getDouble("main_balance"),
                resultSet.getDouble("loans"),
                resultSet.getDouble("interest_on_loans"),
                resultSet.getBoolean("decouvert_autorize"),
                resultSet.getDouble("credit_authorized")
        );
    }
}

