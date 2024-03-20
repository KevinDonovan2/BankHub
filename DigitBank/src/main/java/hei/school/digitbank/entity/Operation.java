package hei.school.digitbank.entity;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Operation {
    private Long idOperation;
    private String operationType;
    private Double amount;
    private Date applyDate;
    private Date registerDate;
    private Long accountNumber;
}
