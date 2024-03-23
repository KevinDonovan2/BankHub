package hei.school.digitbank.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor
@ToString
public class WithdrawalRequest {
    private Integer accountNumber;
    private Double amount;
}

