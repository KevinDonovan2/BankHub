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
    private Integer idTransaction;
    private String descriptions;
    private Integer idOperation;
    private Integer idCategory;
}

