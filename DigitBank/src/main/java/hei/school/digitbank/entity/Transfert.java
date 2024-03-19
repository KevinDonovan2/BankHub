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
public class Transfert {
    private Long idTransfert;
    private Double amount;
    private Date applyDate;
    private Date registerDate;
    private String reason;
    private String state;
    private Long accountNumber;
}
