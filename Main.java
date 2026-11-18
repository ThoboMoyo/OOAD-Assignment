public class Main {
    public static void main(String[] args) {

        Customer customer = new Customer("John", "Doe", "123 Main St");

        // TEST 1: Investment Account
        System.out.println("=== INVESTMENT ACCOUNT ===");

        InvestmentAccount investment = new InvestmentAccount(
            "IA123", 1000.0, "Main Branch"
        );

        customer.openAccount(investment);

        investment.deposit(500.0);
        investment.withdraw(200.0);

        System.out.println("Balance: BWP " + investment.getBalance());

        double investmentInterest = investment.calculateInterest();
        System.out.println("Interest: BWP " + investmentInterest);

        investment.payInterest();
        System.out.println("Balance after interest: BWP " + investment.getBalance());


        // TEST 2: Cheque Account
        System.out.println("\n=== CHEQUE ACCOUNT ===");

        ChequeAccount cheque = new ChequeAccount(
            "CA123", 2000.0, "Main Branch",
            "ABC Company", "Gaborone", "Employed"
        );

        customer.openAccount(cheque);

        cheque.deposit(500.0);
        cheque.withdraw(300.0);

        System.out.println("Balance: BWP " + cheque.getBalance());


        // TEST 3: Customer Accounts
        System.out.println("\n=== CUSTOMER ACCOUNTS ===");

        System.out.println("Number of accounts: " + customer.getAccount().size());
    }
}