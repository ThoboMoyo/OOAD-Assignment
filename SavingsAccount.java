public class SavingsAccount extends Account implements InterestBearing {

    private double InterestRate = 0.0005;
    private double interest;

    public SavingsAccount(String accountNumber, double balance, String branch) {
        super(accountNumber, balance, branch);
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("Withdrawals are not allowed on savings accounts.");
    }

    @Override
    public double calculateInterest() {
        interest = balance * InterestRate;
        return interest;
    }

    public void payInterest() {
        balance += calculateInterest();
    }

    public double getInterest() {
        return interest;
    }

    public String getAccounttype() {
        return "Savings Account";
    }
}