
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:sqlserver://localhost:1433;databaseName=BankingSystem;encrypt=true;trustServerCertificate=true;";

    private static final String USERNAME = "BankingApp";

    private static final String PASSWORD = "BankApp@2026Secure!";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
