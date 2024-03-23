package hei.school.digitbank.entity;
import java.sql.Timestamp;
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
    private Integer idOperation;
    private String operationType;
    private Double amount;
    private Timestamp applyDate;
    private Timestamp registerDate;
    private Integer accountNumber;
}
