import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import java.sql.SQLException;

public class TransactionsController {

    @FXML
    private TableView<Transaction> transactionsTable;

    @FXML
    public void initialize() {

        TableColumn<Transaction, String> idColumn =
            new TableColumn<>("ID");

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
            new TableColumn<>("Amount (BWP)");

        amountColumn.setCellValueFactory(data ->
            new SimpleStringProperty(
                String.format("%.2f", data.getValue().getAmount())
            )
        );

        TableColumn<Transaction, String> dateColumn =
            new TableColumn<>("Date");

        dateColumn.setCellValueFactory(data ->
            new SimpleStringProperty(
                data.getValue().getTransactionDate().toString()
            )
        );

        idColumn.setPrefWidth(60);
        accountColumn.setPrefWidth(120);
        typeColumn.setPrefWidth(110);
        amountColumn.setPrefWidth(120);
        dateColumn.setPrefWidth(130);

        transactionsTable.getColumns().setAll(
            idColumn, accountColumn, typeColumn,
            amountColumn, dateColumn
        );

        // Load transactions directly from SQL Server
        loadTransactions();
    }

    private void loadTransactions() {

        try {
            TransactionDAO transactionDAO = new TransactionDAO();

            transactionsTable.setItems(
                FXCollections.observableArrayList(
                    transactionDAO.getTransactions()
                )
            );

        } catch (SQLException e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Could not load transactions");
            alert.setContentText(
                "Please check your database connection and try again."
            );
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDashboard() {
        navigateTo("Dashboard.fxml", "Thobo's Bank - Dashboard");
    }

    @FXML
    private void handleAccounts() {
        navigateTo("Accounts.fxml", "Thobo's Bank - My Accounts");
    }

    private void navigateTo(String fxmlFile, String title) {

        try {
            Parent root = FXMLLoader.load(
                getClass().getResource(fxmlFile)
            );

            Stage stage = (Stage) transactionsTable.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle(title);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
