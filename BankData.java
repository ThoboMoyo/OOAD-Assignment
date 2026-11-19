
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankData {

    public static Customer customer;

    public static InvestmentAccount investment;
    public static ChequeAccount cheque;
    public static SavingsAccount savings;

    public static List<Transaction> transactions = new ArrayList<>();

    public static int nextTransactionID = 1;

    public static void initialise() {

        customer = new Customer(
            "John", "Doe", "123 Main St"
        );

        investment = new InvestmentAccount(
            "IA123", 1365.0, "Main Branch"
        );

        cheque = new ChequeAccount(
            "CA123", 2200.0, "Main Branch",
            "ABC Company", "Gaborone", "Employed"
        );

        savings = new SavingsAccount(
            "SA123", 1000.0, "Main Branch"
        );

        customer.openAccount(investment);
        customer.openAccount(cheque);
        customer.openAccount(savings);

        transactions.clear();
        nextTransactionID = 1;
    }

    public static void initialiseFromDatabase() throws SQLException {

    CustomerDAO customerDAO = new CustomerDAO();
    AccountDAO accountDAO = new AccountDAO();
    TransactionDAO transactionDAO = new TransactionDAO();

    // Load customers
    List<Customer> customers = customerDAO.getCustomers();

    if (customers.isEmpty()) {
        throw new SQLException("No customers found in database.");
    }

    customer = customers.get(0);

    // Load accounts
    List<Account> accounts = accountDAO.getAccounts();

    for (Account account : accounts) {
        customer.openAccount(account);

        if (account instanceof SavingsAccount) {
            savings = (SavingsAccount) account;
        } else if (account instanceof InvestmentAccount) {
            investment = (InvestmentAccount) account;
        } else if (account instanceof ChequeAccount) {
            cheque = (ChequeAccount) account;
        }
    }

    // Load transactions
    transactions = transactionDAO.getTransactions();

    // Set the next transaction ID
    nextTransactionID = transactions.stream()
        .mapToInt(Transaction::getTransactionID)
        .max()
        .orElse(0) + 1;
}
}
