package hei.school.digitbank.service;
import hei.school.digitbank.dao.AccountDAO;
import hei.school.digitbank.dao.TransferDAO;
import hei.school.digitbank.entity.Account;
import hei.school.digitbank.entity.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class TransferService {
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private TransferDAO transferDAO;
    public Transfer performTransfer(Integer accountNumber, Integer destinataireAccountNumber, Double amount, String reason) {
        Account senderAccount = accountDAO.findById(accountNumber);
        Account receiverAccount = accountDAO.findById(destinataireAccountNumber);
        if (senderAccount.getMainBalance() < amount) {
            throw new RuntimeException("Insufficient balance for transfer");
        }

        senderAccount.setMainBalance(senderAccount.getMainBalance() - amount);
        senderAccount.setDecouvertAutorise(false);
        accountDAO.save(senderAccount);

        receiverAccount.setMainBalance(receiverAccount.getMainBalance() + amount);
        accountDAO.save(receiverAccount);

        Transfer transfer = new Transfer();
        transfer.setIdTransfer(transfer.getIdTransfer());
        transfer.setAmount(amount);
        transfer.setApplyDate(new Timestamp(new Date().getTime()));
        transfer.setRegisterDate(new Timestamp(new Date().getTime()));
        transfer.setReason(reason);
        transfer.setState("In process");
        transfer.setAccountNumber(accountNumber);
        transfer.setDestinataireAccountNumber(destinataireAccountNumber);
        transferDAO.save(transfer);

        return transfer;
    }
}
