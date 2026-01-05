package model;

import java.util.Date;

public class Transaction {

    private int transactionId;
    private TransactionType type;
    private double amount;
    private String fromAccount;
    private String toAccount;
    private Date date;

    public Transaction(int transactionId,
                       TransactionType type,
                       double amount,
                       String fromAccount,
                       String toAccount) {

        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.date = new Date();
    }

    public int getTransactionId() {
        return transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + transactionId +
                ", type=" + type +
                ", amount=" + amount +
                ", from='" + fromAccount + '\'' +
                ", to='" + toAccount + '\'' +
                ", date=" + date +
                '}';
    }
}
