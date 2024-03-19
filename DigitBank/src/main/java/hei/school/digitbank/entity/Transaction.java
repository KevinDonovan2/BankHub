package hei.school.digitbank.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Transaction {
    private Long idTransaction;
    private String descriptions;
    private Long idOperation;
    private Long idCategory;
}

