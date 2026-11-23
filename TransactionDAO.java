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

            Timestamp timestamp = result.getTimestamp("TransactionDate");

            Transaction transaction = new Transaction(
                result.getInt("TransactionID"),
                result.getString("TransactionType"),
                result.getDouble("Amount"),
                result.getString("AccountNumber"),
                timestamp.toLocalDateTime()
            );

            transactions.add(transaction);
        }
    }

    return transactions;
}


public void updateTransaction(int transactionID, String transactionType,
                              double amount) throws SQLException {

    if (amount <= 0 || !Double.isFinite(amount)) {
        throw new IllegalArgumentException("Invalid transaction amount.");
    }

    String sql = """
        UPDATE Transactions
        SET TransactionType = ?, Amount = ?
        WHERE TransactionID = ?
        """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, transactionType);
        statement.setDouble(2, amount);
        statement.setInt(3, transactionID);

        int rowsUpdated = statement.executeUpdate();

        if (rowsUpdated == 0) {
            throw new SQLException("Transaction not found.");
        }

        System.out.println("Transaction updated successfully!");
    }
}


public void deleteTransaction(int transactionID) throws SQLException {

    String sql = "DELETE FROM Transactions WHERE TransactionID = ?";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, transactionID);

        int rowsDeleted = statement.executeUpdate();

        if (rowsDeleted == 0) {
            throw new SQLException("Transaction not found.");
        }

        System.out.println("Transaction deleted successfully!");
    }
}

}
