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
    private Integer accountNumber;
    private String customerName;
    private Date customerBirthdate;
    private Double netMonthlySalary;
    private Double mainBalance;
    private Double loans;
    private Double interestOnLoans;
    private Boolean decouvertAutorise;
    private Double creditAuthorized;

    public Account(int accountNumber, String userTest, java.sql.Date customerBirthdate, double netMonthlySalary, double mainBalance, double loans, double interestOnLoans, boolean decouvertAutorise) {
    }
}

