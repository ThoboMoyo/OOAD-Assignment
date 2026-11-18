
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
}
