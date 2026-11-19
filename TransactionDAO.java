import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class TransactionDAO {

    public void insertTransaction(Transaction transaction)
            throws SQLException {

        String sql = """
            INSERT INTO Transactions
            (AccountNumber, TransactionType, Amount)
            VALUES (?, ?, ?)
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql)) {

            statement.setString(1, transaction.getAccountNumber());
            statement.setString(2, transaction.getTransactionType());
            statement.setDouble(3, transaction.getAmount());

            statement.executeUpdate();

            System.out.println("Transaction saved successfully!");
        }
    }

    public void displayTransactions() throws SQLException {

        String sql = "SELECT * FROM Transactions ORDER BY TransactionID";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                System.out.println(
                    "Transaction ID: " +
                    result.getInt("TransactionID")
                );

                System.out.println(
                    "Account Number: " +
                    result.getString("AccountNumber")
                );

                System.out.println(
                    "Transaction Type: " +
                    result.getString("TransactionType")
                );

                System.out.println(
                    "Amount: BWP " +
                    result.getBigDecimal("Amount")
                );

                System.out.println("-------------------------");
            }
        }
    }

    public List<Transaction> getTransactions() throws SQLException {
    List<Transaction> transactions = new ArrayList<>();

    String sql = "SELECT * FROM Transactions ORDER BY TransactionID";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet result = statement.executeQuery()) {

        while (result.next()) {
            Transaction transaction = new Transaction(
                result.getInt("TransactionID"),
                result.getString("TransactionType"),
                result.getDouble("Amount"),
                result.getString("AccountNumber")
            );

            transactions.add(transaction);
        }
    }

    return transactions;
}
}
