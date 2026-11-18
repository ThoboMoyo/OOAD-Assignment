
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

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

        transactionsTable.getItems().setAll(
            BankData.transactions
        );
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
