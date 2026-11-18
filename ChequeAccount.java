public class ChequeAccount extends Account {

    private String companyName;
    private String companyAddress;

    public ChequeAccount(String accountNumber, double balance, String branch,
                         String companyName, String companyAddress,
                         String employmentStatus) {

        super(accountNumber, balance, branch);

        if (!employmentStatus.equalsIgnoreCase("Employed")) {
            throw new IllegalArgumentException(
                "Only employed customers can open a cheque account."
            );
        }

        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }
}