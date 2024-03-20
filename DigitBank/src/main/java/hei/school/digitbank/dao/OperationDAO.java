package hei.school.digitbank.dao;

import hei.school.digitbank.dbconnection.DatabaseConnector;
import hei.school.digitbank.entity.Operation;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class OperationDAO {
    private static final String TABLE_NAME = "operation";

    public List<Operation> findAll() {
        List<Operation> operations = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                operations.add(mapResultSetToOperation(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve operations", e);
        }
        return operations;
    }
    public Operation findById(Integer idOperation) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_operation = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idOperation);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToOperation(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve operation by ID", e);
        }
        return null;
    }
    public void save(Operation operation) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_operation = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, operation.getIdOperation());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String updateQuery = "UPDATE " + TABLE_NAME + " SET operation_type = ?, amount = ?, apply_date = ?, register_date = ?, account_number = ? WHERE id_operation = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, operation.getOperationType());
                updateStatement.setDouble(2, operation.getAmount());
                updateStatement.setTimestamp(3, operation.getApplyDate());
                updateStatement.setTimestamp(4, operation.getRegisterDate());
                updateStatement.setInt(5, operation.getAccountNumber());
                updateStatement.setInt(6, operation.getIdOperation());
                updateStatement.executeUpdate();
            } else {
                String insertQuery = "INSERT INTO " + TABLE_NAME + " (id_operation, operation_type, amount, apply_date, register_date, account_number) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, operation.getIdOperation());
                insertStatement.setString(2, operation.getOperationType());
                insertStatement.setDouble(3, operation.getAmount());
                insertStatement.setTimestamp(4, operation.getApplyDate());
                insertStatement.setTimestamp(5, operation.getRegisterDate());
                insertStatement.setInt(6, operation.getAccountNumber());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save operation", e);
        }
    }
    public void delete(Integer idOperation) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "DELETE FROM " + TABLE_NAME + " WHERE id_operation = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idOperation);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete operation", e);
        }
    }
    private Operation mapResultSetToOperation(ResultSet resultSet) throws SQLException {
        return new Operation(
                resultSet.getInt("id_operation"),
                resultSet.getString("operation_type"),
                resultSet.getDouble("amount"),
                resultSet.getTimestamp("apply_date"),
                resultSet.getTimestamp("register_date"),
                resultSet.getInt("account_number")
        );
    }
    public Timestamp getLastTransactionDate(int accountNumber) {
        Timestamp lastTransactionDate = null;
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT apply_date FROM " + TABLE_NAME + " WHERE account_number = ? ORDER BY apply_date DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lastTransactionDate = resultSet.getTimestamp("apply_date");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve last transaction date", e);
        }
        return lastTransactionDate;
    }
    public long calculateDaysOverdue(int accountNumber) {
        Timestamp lastTransactionDate = getLastTransactionDate(accountNumber);
        if (lastTransactionDate == null) {
            return 0;
        }
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        long diffInMillis = currentTimestamp.getTime() - lastTransactionDate.getTime();
        long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        return diffInDays;
    }
}

