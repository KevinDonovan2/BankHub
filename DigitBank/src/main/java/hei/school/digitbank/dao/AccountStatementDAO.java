package hei.school.digitbank.dao;
import hei.school.digitbank.dbconnection.DatabaseConnector;
import hei.school.digitbank.entity.AccountStatement;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class AccountStatementDAO {
    private static final String TABLE_NAME = "account_statement";

    public List<AccountStatement> findAll() {
        List<AccountStatement> statements = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                statements.add(mapResultSetToAccountStatement(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve account statements", e);
        }
        return statements;
    }
    public AccountStatement findById(Integer idStatement) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_statement = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idStatement);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToAccountStatement(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve account statement by ID", e);
        }
        return null;
    }
    public void save(AccountStatement statement) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_statement = ?";
            PreparedStatement statementQuery = connection.prepareStatement(query);
            statementQuery.setInt(1, statement.getIdStatement());
            ResultSet resultSet = statementQuery.executeQuery();
            if (resultSet.next()) {
                String updateQuery = "UPDATE " + TABLE_NAME + " SET date = ?, reason = ?, debit_balance = ?, credit_balance = ?, account_number = ? WHERE id_statement = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setTimestamp(1, statement.getDate());
                updateStatement.setString(2, statement.getReason());
                updateStatement.setDouble(3, statement.getDebitBalance());
                updateStatement.setDouble(4, statement.getCreditBalance());
                updateStatement.setInt(5, statement.getAccountNumber());
                updateStatement.setInt(6, statement.getIdStatement());
                updateStatement.executeUpdate();
            } else {
                String insertQuery = "INSERT INTO " + TABLE_NAME + " (id_statement, date, reason, debit_balance, credit_balance, account_number) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, statement.getIdStatement());
                insertStatement.setTimestamp(2, statement.getDate());
                insertStatement.setString(3, statement.getReason());
                insertStatement.setDouble(4, statement.getDebitBalance());
                insertStatement.setDouble(5, statement.getCreditBalance());
                insertStatement.setInt(6, statement.getAccountNumber());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save account statement", e);
        }
    }
    public void delete(Integer idStatement) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "DELETE FROM " + TABLE_NAME + " WHERE id_statement = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idStatement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete account statement", e);
        }
    }
    private AccountStatement mapResultSetToAccountStatement(ResultSet resultSet) throws SQLException {
        return new AccountStatement(
                resultSet.getInt("id_statement"),
                resultSet.getTimestamp("date"),
                resultSet.getString("reason"),
                resultSet.getDouble("debit_balance"),
                resultSet.getDouble("credit_balance"),
                resultSet.getInt("account_number")
        );
    }
}
