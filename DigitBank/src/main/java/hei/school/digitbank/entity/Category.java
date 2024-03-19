package hei.school.digitbank.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Category {
    private Long idCategory;
    private String categoryName;
    private String descriptions;
}

