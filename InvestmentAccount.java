public class InvestmentAccount extends Account implements InterestBearing {

    private double InterestRate = 0.05;
    private double interest;

    public InvestmentAccount(String accountNumber, double initialDeposit, String branch) {
        super(accountNumber, initialDeposit, branch);

        if (initialDeposit < 500) {
            throw new IllegalArgumentException(
                "Investment account requires a minimum opening deposit of BWP500."
            );
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Invalid amount or insufficient funds.");
        }
    }

    @Override
    public double calculateInterest() {
        interest = balance * InterestRate;
        return interest;
    }

    public void payInterest() {
        balance += calculateInterest();
    }
}