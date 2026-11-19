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

        // Load the latest accounts directly from SQL Server
        loadAccounts();
    }

    private void loadAccounts() {

        try {
            AccountDAO accountDAO = new AccountDAO();

            accountsTable.setItems(
                FXCollections.observableArrayList(
                    accountDAO.getAccounts()
                )
            );

        } catch (SQLException e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Could not load accounts");
            alert.setContentText(
                "Please check your database connection and try again."
            );
            alert.showAndWait();
        }
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
