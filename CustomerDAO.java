import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public void insertCustomer(Customer customer, String employmentStatus,
                               String companyName, String companyAddress)
                               throws SQLException {

        String sql = """
            INSERT INTO Customers
            (FirstName, Surname, Address, EmploymentStatus,
             CompanyName, CompanyAddress)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getFirstname());
            statement.setString(2, customer.getSurname());
            statement.setString(3, customer.getAddress());
            statement.setString(4, employmentStatus);
            statement.setString(5, companyName);
            statement.setString(6, companyAddress);

            statement.executeUpdate();

            System.out.println("Customer saved successfully!");
        }
    }

    public void displayCustomers() throws SQLException {

        String sql = "SELECT * FROM Customers";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                System.out.println(
                    "Customer ID: " + result.getInt("CustomerID")
                );

                System.out.println(
                    "Name: " + result.getString("FirstName") + " " +
                    result.getString("Surname")
                );

                System.out.println(
                    "Address: " + result.getString("Address")
                );

                System.out.println("-------------------------");
            }
        }
    }

    public List<Customer> getCustomers() throws SQLException {
    List<Customer> customers = new ArrayList<>();

    String sql = "SELECT FirstName, Surname, Address FROM Customers";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet result = statement.executeQuery()) {

        while (result.next()) {
            Customer customer = new Customer(
                result.getString("FirstName"),
                result.getString("Surname"),
                result.getString("Address")
            );

            customers.add(customer);
        }
    }

    return customers;
}


public void updateCustomer(int customerID, Customer customer,
                           String employmentStatus,
                           String companyName,
                           String companyAddress) throws SQLException {

    String sql = """
        UPDATE Customers
        SET FirstName = ?, Surname = ?, Address = ?,
            EmploymentStatus = ?, CompanyName = ?, CompanyAddress = ?
        WHERE CustomerID = ?
        """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, customer.getFirstname());
        statement.setString(2, customer.getSurname());
        statement.setString(3, customer.getAddress());
        statement.setString(4, employmentStatus);
        statement.setString(5, companyName);
        statement.setString(6, companyAddress);
        statement.setInt(7, customerID);

        int rowsUpdated = statement.executeUpdate();

        if (rowsUpdated == 0) {
            throw new SQLException("Customer not found.");
        }

        System.out.println("Customer updated successfully!");
    }
}


public void deleteCustomer(int customerID) throws SQLException {

    String sql = "DELETE FROM Customers WHERE CustomerID = ?";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, customerID);

        int rowsDeleted = statement.executeUpdate();

        if (rowsDeleted == 0) {
            throw new SQLException("Customer not found.");
        }

        System.out.println("Customer deleted successfully!");
    }
}

}
