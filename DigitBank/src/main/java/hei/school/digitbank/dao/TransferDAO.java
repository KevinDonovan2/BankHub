package hei.school.digitbank.dao;
import hei.school.digitbank.dbconnection.DatabaseConnector;
import hei.school.digitbank.entity.Transfer;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class TransferDAO {
    private static final String TABLE_NAME = "transfer";

    public List<Transfer> findAll() {
        List<Transfer> transfers = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                transfers.add(mapResultSetToTransfer(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transfers", e);
        }
        return transfers;
    }
    public Transfer findById(Integer idTransfer) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_transfer = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idTransfer);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToTransfer(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transfer by ID", e);
        }
        return null;
    }
    public void save(Transfer transfer) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_transfer = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transfer.getIdTransfer());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String updateQuery = "UPDATE " + TABLE_NAME + " SET amount = ?, apply_date = ?, register_date = ?, reason = ?, state = ?, account_number = ? WHERE id_transfer = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setDouble(1, transfer.getAmount());
                updateStatement.setTimestamp(2, transfer.getApplyDate());
                updateStatement.setTimestamp(3, transfer.getRegisterDate());
                updateStatement.setString(4, transfer.getReason());
                updateStatement.setString(5, transfer.getState());
                updateStatement.setInt(6, transfer.getAccountNumber());
                updateStatement.setInt(7, transfer.getDestinataireAccountNumber());
                updateStatement.setInt(8, transfer.getIdTransfer());
                updateStatement.executeUpdate();
            } else {
                String insertQuery = "INSERT INTO " + TABLE_NAME + " (id_transfer, amount, apply_date, register_date, reason, state, account_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, transfer.getIdTransfer());
                insertStatement.setDouble(2, transfer.getAmount());
                insertStatement.setTimestamp(3, transfer.getApplyDate());
                insertStatement.setTimestamp(4, transfer.getRegisterDate());
                insertStatement.setString(5, transfer.getReason());
                insertStatement.setString(6, transfer.getState());
                insertStatement.setInt(7, transfer.getAccountNumber());
                insertStatement.setInt(8, transfer.getDestinataireAccountNumber());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save transfer", e);
        }
    }
    public void delete(Integer idTransfer) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "DELETE FROM " + TABLE_NAME + " WHERE id_transfer = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idTransfer);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete transfer", e);
        }
    }
    private Transfert mapResultSetToTransfer(ResultSet resultSet) throws SQLException {
        return new Transfert(
                resultSet.getInt("id_transfer"),
                resultSet.getDouble("amount"),
                resultSet.getTimestamp("apply_date"),
                resultSet.getTimestamp("register_date"),
                resultSet.getString("reason"),
                resultSet.getString("state"),
                resultSet.getInt("account_number")
                resultSet.getInt("destinataire_account_number")
        );
    }
}

