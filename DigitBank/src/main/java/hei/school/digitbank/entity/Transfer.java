package hei.school.digitbank.entity;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Transfer {
    private Integer idTransfer;
    private Double amount;
    private Timestamp applyDate;
    private Timestamp registerDate;
    private String reason;
    private String state;
    private Integer accountNumber;
    private Integer destinataireAccountNumber;
}
