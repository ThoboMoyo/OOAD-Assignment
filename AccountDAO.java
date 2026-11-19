
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public void insertAccount(Account account, int customerID)
            throws SQLException {

        String accountType;

        if (account instanceof SavingsAccount) {
            accountType = "Savings";
        } else if (account instanceof InvestmentAccount) {
            accountType = "Investment";
        } else if (account instanceof ChequeAccount) {
            accountType = "Cheque";
        } else {
            throw new IllegalArgumentException("Unknown account type");
        }

        String sql = """
            INSERT INTO Accounts
            (AccountNumber, CustomerID, AccountType, Balance, Branch)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql)) {

            statement.setString(1, account.getAccountNumber());
            statement.setInt(2, customerID);
            statement.setString(3, accountType);
            statement.setDouble(4, account.getBalance());
            statement.setString(5, account.getBranch());

            statement.executeUpdate();

            System.out.println(accountType +
                " account saved successfully!");
        }
    }

    public void displayAccounts() throws SQLException {

        String sql = "SELECT * FROM Accounts";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                System.out.println(
                    "Account Number: " +
                    result.getString("AccountNumber")
                );

                System.out.println(
                    "Account Type: " +
                    result.getString("AccountType")
                );

                System.out.println(
                    "Balance: BWP " +
                    result.getBigDecimal("Balance")
                );

                System.out.println("-------------------------");
            }
        }
    }

    public List<Account> getAccounts() throws SQLException {
    List<Account> accounts = new ArrayList<>();

    String sql = "SELECT * FROM Accounts";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet result = statement.executeQuery()) {

        while (result.next()) {
            String accountNumber = result.getString("AccountNumber");
            String accountType = result.getString("AccountType");
            double balance = result.getDouble("Balance");
            String branch = result.getString("Branch");

            Account account = null;

            switch (accountType) {
                case "Savings":
                    account = new SavingsAccount(accountNumber, balance, branch);
                    break;

                case "Investment":
                    account = new InvestmentAccount(accountNumber, balance, branch);
                    break;

                case "Cheque":
                    account = new ChequeAccount(
                        accountNumber, balance, branch,
                        "ABC Company", "Gaborone", "Employed"
                    );
                    break;
            }

            if (account != null) {
                accounts.add(account);
            }
        }
    }

    return accounts;
}

public void depositAndRecord(String accountNumber, double amount)
        throws SQLException {

    if (amount <= 0 || !Double.isFinite(amount)) {
        throw new IllegalArgumentException("Invalid deposit amount.");
    }

    String updateSQL =
        "UPDATE Accounts SET Balance = Balance + ? WHERE AccountNumber = ?";

    String insertSQL = """
        INSERT INTO Transactions (AccountNumber, TransactionType, Amount)
        VALUES (?, 'Deposit', ?)
        """;

    try (Connection connection = DBConnection.getConnection()) {

        connection.setAutoCommit(false);

        try {
            // Update account balance
            try (PreparedStatement update =
                     connection.prepareStatement(updateSQL)) {

                update.setDouble(1, amount);
                update.setString(2, accountNumber);

                int rowsUpdated = update.executeUpdate();

                if (rowsUpdated == 0) {
                    throw new SQLException("Account not found.");
                }
            }

            // Record the transaction
            try (PreparedStatement insert =
                     connection.prepareStatement(insertSQL)) {

                insert.setString(1, accountNumber);
                insert.setDouble(2, amount);

                insert.executeUpdate();
            }

            connection.commit();

            System.out.println("Deposit and transaction saved successfully!");

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
}
}
