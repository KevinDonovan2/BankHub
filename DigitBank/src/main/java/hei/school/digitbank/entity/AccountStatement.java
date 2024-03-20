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
public class AccountStatement {
    private Integer idStatement;
    private Timestamp date;
    private String reason;
    private Double debitBalance;
    private Double creditBalance;
    private Integer accountNumber;
}

