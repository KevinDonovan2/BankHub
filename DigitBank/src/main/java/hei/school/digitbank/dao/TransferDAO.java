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

    public static List<Transfer> findAll() {
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
    public static Transfer findById(Integer idTransfer) {
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
    public static void save(Transfer transfer) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            if (transfer.getIdTransfer() != null) {
                throw new IllegalArgumentException("ID transfer should not be provided for new transfer.");
            }

            String insertQuery = "INSERT INTO " + TABLE_NAME + " ( amount, reason, state, account_number, destinataire_account_number ) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setDouble(1, transfer.getAmount());
            insertStatement.setString(2, transfer.getReason());
            insertStatement.setString(3, transfer.getState());
            insertStatement.setInt(4, transfer.getAccountNumber());
            insertStatement.setInt(5, transfer.getDestinataireAccountNumber());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save transfer", e);
        }
    }


    public static void delete(Integer idTransfer) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "DELETE FROM " + TABLE_NAME + " WHERE id_transfer = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idTransfer);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete transfer", e);
        }
    }
    private static Transfer mapResultSetToTransfer(ResultSet resultSet) throws SQLException {
        return new Transfer(
                resultSet.getInt("id_transfer"),
                resultSet.getDouble("amount"),
                resultSet.getTimestamp("apply_date"),
                resultSet.getTimestamp("register_date"),
                resultSet.getString("reason"),
                resultSet.getString("state"),
                resultSet.getInt("account_number"),
                resultSet.getInt("destinataire_account_number")
        );
    }
}

