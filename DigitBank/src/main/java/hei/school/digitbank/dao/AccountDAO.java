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
                String updateQuery = "UPDATE " + TABLE_NAME + " SET customer_name = ?, customer_birthdate = ?, net_monthly_salary = ?, main_balance = ?, credit_authorized = ?, interest_rate_7d = ?, interest_rate_after_7d = ?, decouvert_autorize = ? WHERE account_number = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, account.getCustomerName());
                updateStatement.setDate(2, new java.sql.Date(account.getCustomerBirthdate().getTime()));
                updateStatement.setDouble(3, account.getNetMonthlySalary());
                updateStatement.setDouble(4, account.getMainBalance());
                updateStatement.setDouble(5, account.getCreditAuthorized());
                updateStatement.setDouble(6, account.getInterestRate7d());
                updateStatement.setDouble(7, account.getInterestRateAfter7d());
                updateStatement.setBoolean(8, account.getDecouvertAutorise());
                updateStatement.setInt(9, account.getAccountNumber());
                updateStatement.executeUpdate();
            } else {
                String insertQuery = "INSERT INTO " + TABLE_NAME + " (account_number, customer_name, customer_birthdate, net_monthly_salary, main_balance, credit_authorized, interest_rate_7d, interest_rate_after_7d, decouvert_autorize) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, account.getAccountNumber());
                insertStatement.setString(2, account.getCustomerName());
                insertStatement.setDate(3, new java.sql.Date(account.getCustomerBirthdate().getTime()));
                insertStatement.setDouble(4, account.getNetMonthlySalary());
                insertStatement.setDouble(5, account.getMainBalance());
                insertStatement.setDouble(6, account.getCreditAuthorized());
                insertStatement.setDouble(7, account.getInterestRate7d());
                insertStatement.setDouble(8, account.getInterestRateAfter7d());
                insertStatement.setBoolean(9, account.getDecouvertAutorise());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save account", e);
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
                resultSet.getDouble("credit_authorized"),
                resultSet.getDouble("interest_rate_7d"),
                resultSet.getDouble("interest_rate_after_7d"),
                resultSet.getBoolean("decouvert_autorize")
        );
    }
    // F2 : Gestion de retrait d’argent
    public boolean withdraw(Integer accountNumber, Double amount) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM account WHERE account_number = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Double mainBalance = resultSet.getDouble("main_balance");
                Double creditAuthorized = resultSet.getDouble("credit_authorized");
                Boolean decouvertAutorise = resultSet.getBoolean("decouvert_autorize");

                if (mainBalance + creditAuthorized >= amount || decouvertAutorise) {
                    mainBalance -= amount;
                    String updateQuery = "UPDATE account SET main_balance = ? WHERE account_number = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setDouble(1, mainBalance);
                    updateStatement.setInt(2, accountNumber);
                    updateStatement.executeUpdate();
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to process withdrawal", e);
        }
        return false;
    }
}

