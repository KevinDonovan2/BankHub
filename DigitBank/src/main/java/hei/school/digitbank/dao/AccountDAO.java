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

    public void save(Account account) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            if (account.getAccountNumber() != null) {
                throw new IllegalArgumentException("Account number should not be provided for new accounts.");
            }
            account.setAccountNumber(null);

            String insertQuery = "INSERT INTO " + TABLE_NAME + " (customer_name, customer_birthdate, net_monthly_salary, main_balance, decouvert_autorise) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, account.getCustomerName());
            insertStatement.setDate(2, new java.sql.Date(account.getCustomerBirthdate().getTime()));
            insertStatement.setDouble(3, account.getNetMonthlySalary());
            insertStatement.setDouble(4, account.getMainBalance());
            insertStatement.setBoolean(5, account.getDecouvertAutorise());
            insertStatement.executeUpdate();

            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                account.setAccountNumber(generatedKeys.getInt(1));
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

    private Integer generateAccountNumber() throws SQLException {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT MAX(account_number) FROM " + TABLE_NAME;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Integer maxAccountNumber = resultSet.getInt(1);
                return maxAccountNumber + 1;
            } else {
                return 1;
            }
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
                resultSet.getBoolean("decouvert_autorise"),
                resultSet.getDouble("credit_authorized")
        );
    }
}
