
public class AddSampleRecords {

    public static void main(String[] args) {

        try {
            AccountDAO accountDAO = new AccountDAO();

            accountDAO.depositAndRecord("SA123", 25.00);
            accountDAO.depositAndRecord("SA123", 75.00);
            accountDAO.depositAndRecord("IA123", 100.00);
            accountDAO.depositAndRecord("CA123", 150.00);
            accountDAO.depositAndRecord("CA123", 200.00);

            System.out.println("Five sample transactions added successfully!");

            TransactionDAO transactionDAO = new TransactionDAO();
            transactionDAO.displayTransactions();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
