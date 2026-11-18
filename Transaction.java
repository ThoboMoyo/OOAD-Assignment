
import java.time.LocalDateTime;

public class Transaction {

    private int transactionID;
    private String transactionType;
    private double amount;
    private LocalDateTime transactionDate;
    private String accountNumber;

    public Transaction(int transactionID, String transactionType,
                       double amount, String accountNumber) {

        this.transactionID = transactionID;
        this.transactionType = transactionType;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.transactionDate = LocalDateTime.now();
    }

    public int getTransactionID() {
        return transactionID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getTransactionDetails() {
        return "Transaction ID: " + transactionID +
               "\nTransaction Type: " + transactionType +
               "\nAccount Number: " + accountNumber +
               "\nAmount: BWP " + amount +
               "\nTransaction Date: " + transactionDate;
    }
}
