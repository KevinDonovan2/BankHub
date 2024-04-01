package hei.school.digitbank.controller;
import hei.school.digitbank.dao.CategoryDAO;
import hei.school.digitbank.entity.Category;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryDAO categoryDAO;
    public CategoryController(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Integer id) {
        return categoryDAO.findById(id);
    }
    @PostMapping
    public void createCategory(@RequestBody Category newCategory) {
        categoryDAO.save(newCategory);
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryDAO.delete(id);
    }
}

