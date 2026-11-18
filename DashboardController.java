
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextInputDialog;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

import java.util.Optional;

public class DashboardController {

    @FXML
    private Label totalBalanceLabel;

    @FXML
    private Label savingsCountLabel;

    @FXML
    private Label investmentCountLabel;

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    public void initialize() {
        setupTransactionTable();
        refreshDashboard();
        refreshTransactions();
    }

    private void setupTransactionTable() {

        TableColumn<Transaction, String> idColumn =
            new TableColumn<>("Transaction ID");

        idColumn.setCellValueFactory(data ->
            new SimpleStringProperty(
                String.valueOf(data.getValue().getTransactionID())
            )
        );

        TableColumn<Transaction, String> accountColumn =
            new TableColumn<>("Account");

        accountColumn.setCellValueFactory(data ->
            new SimpleStringProperty(
                data.getValue().getAccountNumber()
            )
        );

        TableColumn<Transaction, String> typeColumn =
            new TableColumn<>("Type");

        typeColumn.setCellValueFactory(data ->
            new SimpleStringProperty(
                data.getValue().getTransactionType()
            )
        );

        TableColumn<Transaction, String> amountColumn =
            new TableColumn<>("Amount");

        amountColumn.setCellValueFactory(data ->
            new SimpleStringProperty(
                String.format("BWP %.2f", data.getValue().getAmount())
            )
        );

        idColumn.setPrefWidth(120);
        accountColumn.setPrefWidth(120);
        typeColumn.setPrefWidth(120);
        amountColumn.setPrefWidth(140);

        transactionTable.getColumns().setAll(
            idColumn, accountColumn, typeColumn, amountColumn
        );
    }

    private void refreshDashboard() {

        double totalBalance = 0;
        int savingsCount = 0;
        int investmentCount = 0;

        for (Account account : BankData.customer.getAccount()) {

            totalBalance += account.getBalance();

            if (account instanceof SavingsAccount) {
                savingsCount++;
            }

            if (account instanceof InvestmentAccount) {
                investmentCount++;
            }
        }

        totalBalanceLabel.setText(
            String.format("BWP %.2f", totalBalance)
        );

        savingsCountLabel.setText(String.valueOf(savingsCount));
        investmentCountLabel.setText(String.valueOf(investmentCount));
    }

    private void refreshTransactions() {

        transactionTable.setItems(
            FXCollections.observableArrayList(BankData.transactions)
        );
    }

    @FXML
    private void handleDeposit() {

        ChoiceDialog<String> accountDialog = new ChoiceDialog<>(
            "Savings - SA123",
            "Savings - SA123",
            "Investment - IA123",
            "Cheque - CA123"
        );

        accountDialog.setTitle("Make a Deposit");
        accountDialog.setHeaderText("Select an Account");
        accountDialog.setContentText("Choose account:");

        Optional<String> selectedAccount = accountDialog.showAndWait();

        if (selectedAccount.isEmpty()) {
            return;
        }

        TextInputDialog amountDialog = new TextInputDialog();

        amountDialog.setTitle("Make a Deposit");
        amountDialog.setHeaderText("Enter Deposit Amount");
        amountDialog.setContentText("Amount (BWP):");

        Optional<String> enteredAmount = amountDialog.showAndWait();

        if (enteredAmount.isEmpty()) {
            return;
        }

        try {

            double amount = Double.parseDouble(enteredAmount.get());

            if (!Double.isFinite(amount) || amount <= 0) {
                showAlert("Invalid Amount",
                    "Please enter a valid amount greater than zero.");
                return;
            }

            Account account = null;

            switch (selectedAccount.get()) {
                case "Savings - SA123":
                    account = BankData.savings;
                    break;

                case "Investment - IA123":
                    account = BankData.investment;
                    break;

                case "Cheque - CA123":
                    account = BankData.cheque;
                    break;
            }

            if (account != null) {

                account.deposit(amount);

                Transaction transaction = new Transaction(
                    BankData.nextTransactionID++,
                    "Deposit",
                    amount,
                    account.getAccountNumber()
                );

                BankData.transactions.add(transaction);

                refreshDashboard();
                refreshTransactions();

                showAlert("Deposit Successful",
                    String.format(
                        "BWP %.2f deposited successfully into %s.",
                        amount, selectedAccount.get()
                    ));
            }

        } catch (NumberFormatException e) {

            showAlert("Invalid Input",
                "Please enter a valid numerical amount.");
        }
    }


    @FXML
    private void handleWithdrawal() {

        ChoiceDialog<String> accountDialog = new ChoiceDialog<>(
            "Investment - IA123",
            "Savings - SA123",
            "Investment - IA123",
            "Cheque - CA123"
        );

        accountDialog.setTitle("Make a Withdrawal");
        accountDialog.setHeaderText("Select an Account");
        accountDialog.setContentText("Choose account:");

        Optional<String> selectedAccount = accountDialog.showAndWait();

        if (selectedAccount.isEmpty()) {
            return;
        }

        // Savings accounts do not allow withdrawals.
        if (selectedAccount.get().equals("Savings - SA123")) {
            showAlert("Withdrawal Not Allowed",
                "Withdrawals are not allowed on savings accounts.");
            return;
        }

        TextInputDialog amountDialog = new TextInputDialog();

        amountDialog.setTitle("Make a Withdrawal");
        amountDialog.setHeaderText("Enter Withdrawal Amount");
        amountDialog.setContentText("Amount (BWP):");

        Optional<String> enteredAmount = amountDialog.showAndWait();

        if (enteredAmount.isEmpty()) {
            return;
        }

        try {

            double amount = Double.parseDouble(enteredAmount.get());

            if (!Double.isFinite(amount) || amount <= 0) {
                showAlert("Invalid Amount",
                    "Please enter a valid amount greater than zero.");
                return;
            }

            Account account = null;

            switch (selectedAccount.get()) {
                case "Investment - IA123":
                    account = BankData.investment;
                    break;

                case "Cheque - CA123":
                    account = BankData.cheque;
                    break;
            }

            if (account == null) {
                return;
            }

            if (amount > account.getBalance()) {
                showAlert("Insufficient Funds",
                    "You do not have enough money in this account.");
                return;
            }

            account.withdraw(amount);

            Transaction transaction = new Transaction(
                BankData.nextTransactionID++,
                "Withdrawal",
                amount,
                account.getAccountNumber()
            );

            BankData.transactions.add(transaction);

            refreshDashboard();
            refreshTransactions();

            showAlert("Withdrawal Successful",
                String.format(
                    "BWP %.2f withdrawn successfully from %s.",
                    amount, selectedAccount.get()
                ));

        } catch (NumberFormatException e) {

            showAlert("Invalid Input",
                "Please enter a valid numerical amount.");
        }
    }


    @FXML
    private void handlePayInterest() {

        // Calculate and pay savings account interest
        double savingsInterest = BankData.savings.calculateInterest();
        BankData.savings.payInterest();

        Transaction savingsTransaction = new Transaction(
            BankData.nextTransactionID++,
            "Interest",
            savingsInterest,
            BankData.savings.getAccountNumber()
        );

        BankData.transactions.add(savingsTransaction);


        // Calculate and pay investment account interest
        double investmentInterest = BankData.investment.calculateInterest();
        BankData.investment.payInterest();

        Transaction investmentTransaction = new Transaction(
            BankData.nextTransactionID++,
            "Interest",
            investmentInterest,
            BankData.investment.getAccountNumber()
        );

        BankData.transactions.add(investmentTransaction);


        // Refresh dashboard information
        refreshDashboard();
        refreshTransactions();

        showAlert(
            "Interest Paid",
            String.format(
                "Savings interest: BWP %.2f\nInvestment interest: BWP %.2f",
                savingsInterest,
                investmentInterest
            )
        );
    }


    @FXML
    private void handleAccounts() {
        navigateTo("Accounts.fxml", "Thobo's Bank - My Accounts");
    }

    @FXML
    private void handleTransactions() {
        navigateTo("Transactions.fxml", "Thobo's Bank - Transactions");
    }

    @FXML
    private void handleLogout() {
        navigateTo("Login.fxml", "Thobo's Bank - Login");
    }

    private void navigateTo(String fxmlFile, String title) {

        try {
            Parent root = FXMLLoader.load(
                getClass().getResource(fxmlFile)
            );

            Stage stage = (Stage) transactionTable.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle(title);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Navigation Error",
                "Could not open " + fxmlFile);
        }
    }

    private void showAlert(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
