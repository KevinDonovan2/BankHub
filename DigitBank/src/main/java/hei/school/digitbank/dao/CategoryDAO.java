package hei.school.digitbank.dao;
import hei.school.digitbank.dbconnection.DatabaseConnector;
import hei.school.digitbank.entity.Category;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CategoryDAO {
    private static final String TABLE_NAME = "category";
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                categories.add(mapResultSetToCategory(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve categories", e);
        }
        return categories;
    }
    public Category findById(Integer idCategory) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_category = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idCategory);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToCategory(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve category by ID", e);
        }
        return null;
    }
    public void save(Category category) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_category = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, category.getIdCategory());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String updateQuery = "UPDATE " + TABLE_NAME + " SET category_name = ?, descriptions = ? WHERE id_category = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, category.getCategoryName());
                updateStatement.setString(2, category.getDescriptions());
                updateStatement.setInt(3, category.getIdCategory());
                updateStatement.executeUpdate();
            } else {
                String insertQuery = "INSERT INTO " + TABLE_NAME + " (id_category, category_name, descriptions) VALUES (?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, category.getIdCategory());
                insertStatement.setString(2, category.getCategoryName());
                insertStatement.setString(3, category.getDescriptions());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save category", e);
        }
    }
    public void delete(Integer idCategory) {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String query = "DELETE FROM " + TABLE_NAME + " WHERE id_category = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idCategory);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete category", e);
        }
    }
    private Category mapResultSetToCategory(ResultSet resultSet) throws SQLException {
        return new Category(
                resultSet.getInt("id_category"),
                resultSet.getString("category_name"),
                resultSet.getString("descriptions")
        );
    }
}

