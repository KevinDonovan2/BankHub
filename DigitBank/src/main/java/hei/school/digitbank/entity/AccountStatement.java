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
public class AccountStatement {
    private Long idStatement;
    private Date date;
    private String reason;
    private Double debitBalance;
    private Double creditBalance;
    private Long accountNumber;
}

