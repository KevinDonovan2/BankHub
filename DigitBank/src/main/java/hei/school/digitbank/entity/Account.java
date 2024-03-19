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
public class Account {
    private Long accountNumber;
    private String customerName;
    private Date customerBirthdate;
    private Double netMonthlySalary;
    private Double mainBalance;
    private Double creditAuthorized;
    private Double interestRate7d;
    private Double interestRateAfter7d;
    private Boolean decouvertAutorise;
}

