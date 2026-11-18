
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

public class AccountsController {

    @FXML
    private TableView<Account> accountsTable;

    @FXML
    public void initialize() {

        TableColumn<Account, String> numberColumn =
            new TableColumn<>("Account Number");

        numberColumn.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getAccountNumber())
        );

        TableColumn<Account, String> typeColumn =
            new TableColumn<>("Account Type");

        typeColumn.setCellValueFactory(data -> {

            Account account = data.getValue();
            String type;

            if (account instanceof SavingsAccount) {
                type = "Savings Account";
            } else if (account instanceof InvestmentAccount) {
                type = "Investment Account";
            } else {
                type = "Cheque Account";
            }

            return new SimpleStringProperty(type);
        });

        TableColumn<Account, String> branchColumn =
            new TableColumn<>("Branch");

        branchColumn.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getBranch())
        );

        TableColumn<Account, String> balanceColumn =
            new TableColumn<>("Balance (BWP)");

        balanceColumn.setCellValueFactory(data ->
            new SimpleStringProperty(
                String.format("%.2f", data.getValue().getBalance())
            )
        );

        numberColumn.setPrefWidth(130);
        typeColumn.setPrefWidth(140);
        branchColumn.setPrefWidth(120);
        balanceColumn.setPrefWidth(150);

        accountsTable.getColumns().setAll(
            numberColumn, typeColumn, branchColumn, balanceColumn
        );

        accountsTable.getItems().setAll(
            BankData.customer.getAccount()
        );
    }

    @FXML
    private void handleDashboard() {

        try {
            Parent root = FXMLLoader.load(
                getClass().getResource("Dashboard.fxml")
            );

            Stage stage = (Stage) accountsTable.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Thobo's Bank - Dashboard");

        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not open the dashboard.");
            alert.showAndWait();
        }
    }
}
