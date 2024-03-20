package hei.school.digitbank.dao;
import hei.school.digitbank.dbconnection.DatabaseConnector;
import hei.school.digitbank.entity.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class TransactionDAO {
    private static final String TABLE_NAME = "transactions";

    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                transactions.add(mapResultSetToTransaction(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transactions", e);
        }
        return transactions;
    }
    public Transaction findById(Integer idTransaction) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_transaction = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idTransaction);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToTransaction(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transaction by ID", e);
        }
        return null;
    }
    public void save(Transaction transaction) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_transaction = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transaction.getIdTransaction());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String updateQuery = "UPDATE " + TABLE_NAME + " SET descriptions = ?, id_operation = ?, id_category = ? WHERE id_transaction = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, transaction.getDescriptions());
                updateStatement.setInt(2, transaction.getIdOperation());
                updateStatement.setInt(3, transaction.getIdCategory());
                updateStatement.setInt(4, transaction.getIdTransaction());
                updateStatement.executeUpdate();
            } else {
                String insertQuery = "INSERT INTO " + TABLE_NAME + " (id_transaction, descriptions, id_operation, id_category) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, transaction.getIdTransaction());
                insertStatement.setString(2, transaction.getDescriptions());
                insertStatement.setInt(3, transaction.getIdOperation());
                insertStatement.setInt(4, transaction.getIdCategory());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save transaction", e);
        }
    }
    public void delete(Integer idTransaction) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "DELETE FROM " + TABLE_NAME + " WHERE id_transaction = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idTransaction);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete transaction", e);
        }
    }
    private Transaction mapResultSetToTransaction(ResultSet resultSet) throws SQLException {
        return new Transaction(
                resultSet.getInt("id_transaction"),
                resultSet.getString("descriptions"),
                resultSet.getInt("id_operation"),
                resultSet.getInt("id_category")
        );
    }
}

