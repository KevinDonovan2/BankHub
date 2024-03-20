package hei.school.digitbank.dao;
import hei.school.digitbank.dbconnection.DatabaseConnector;
import hei.school.digitbank.entity.Transfert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class TransfertDAO {
    private static final String TABLE_NAME = "transfert";

    public List<Transfert> findAll() {
        List<Transfert> transferts = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                transferts.add(mapResultSetToTransfert(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transfers", e);
        }
        return transferts;
    }
    public Transfert findById(Integer idTransfert) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_transfert = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idTransfert);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToTransfert(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transfer by ID", e);
        }
        return null;
    }
    public void save(Transfert transfert) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_transfert = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, transfert.getIdTransfert());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String updateQuery = "UPDATE " + TABLE_NAME + " SET amount = ?, apply_date = ?, register_date = ?, reason = ?, state = ?, account_number = ? WHERE id_transfert = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setDouble(1, transfert.getAmount());
                updateStatement.setTimestamp(2, transfert.getApplyDate());
                updateStatement.setTimestamp(3, transfert.getRegisterDate());
                updateStatement.setString(4, transfert.getReason());
                updateStatement.setString(5, transfert.getState());
                updateStatement.setInt(6, transfert.getAccountNumber());
                updateStatement.setInt(7, transfert.getIdTransfert());
                updateStatement.executeUpdate();
            } else {
                String insertQuery = "INSERT INTO " + TABLE_NAME + " (id_transfert, amount, apply_date, register_date, reason, state, account_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, transfert.getIdTransfert());
                insertStatement.setDouble(2, transfert.getAmount());
                insertStatement.setTimestamp(3, transfert.getApplyDate());
                insertStatement.setTimestamp(4, transfert.getRegisterDate());
                insertStatement.setString(5, transfert.getReason());
                insertStatement.setString(6, transfert.getState());
                insertStatement.setInt(7, transfert.getAccountNumber());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save transfer", e);
        }
    }
    public void delete(Integer idTransfert) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "DELETE FROM " + TABLE_NAME + " WHERE id_transfert = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idTransfert);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete transfer", e);
        }
    }
    private Transfert mapResultSetToTransfert(ResultSet resultSet) throws SQLException {
        return new Transfert(
                resultSet.getInt("id_transfert"),
                resultSet.getDouble("amount"),
                resultSet.getTimestamp("apply_date"),
                resultSet.getTimestamp("register_date"),
                resultSet.getString("reason"),
                resultSet.getString("state"),
                resultSet.getInt("account_number")
        );
    }
}

