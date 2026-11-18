
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please enter your username and password.");
            alert.showAndWait();

        } else {

            try {
                Parent root = FXMLLoader.load(
                    getClass().getResource("Dashboard.fxml")
                );

                Stage stage = (Stage) usernameField.getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.setTitle("Thobo's Bank - Dashboard");

            } catch (Exception e) {
                e.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Navigation Error");
                alert.setHeaderText("Could Not Open Dashboard");
                alert.setContentText("Please check Dashboard.fxml.");
                alert.showAndWait();
            }
        }
    }
}
