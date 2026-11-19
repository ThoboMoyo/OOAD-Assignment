
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        BankData.initialiseFromDatabase();

        Parent root = FXMLLoader.load(
            getClass().getResource("Login.fxml")
        );

        Scene scene = new Scene(root);

        stage.setTitle("Banking System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}
