package hei.school.digitbank.test;
import hei.school.digitbank.dao.CategoryDAO;
import hei.school.digitbank.entity.Category;
import java.util.List;

public class CategoryDAOTest {
    public static void main(String[] args) {
        CategoryDAO categoryDAO = new CategoryDAO();
        // findAll
        System.out.println("All categories:");
        List<Category> categories = categoryDAO.findAll();
        for (Category category : categories) {
            System.out.println(category);
        }
        // findById
        Integer categoryIdToFind = 1;
        System.out.println("\nCategory found by ID:");
        Category category = categoryDAO.findById(categoryIdToFind);
        if (category != null) {
            System.out.println(category);
        } else {
            System.out.println("No category found with the ID " + categoryIdToFind);
        }
        // save
        Category newCategory = new Category(4, "Test category", "This is a test category.");
        System.out.println("\n New category registration:");
        categoryDAO.save(newCategory);
        System.out.println("New category successfully registered.");

        // delete
        Integer categoryIdToDelete = 4;
        System.out.println("\n Deleting a category:");
        categoryDAO.delete(categoryIdToDelete);
        System.out.println("Category successfully deleted.");
    }
}

