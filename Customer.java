import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String firstname;
    private String surname;
    private String address;

    private List<Account> accounts;

    public Customer(String firstname, String surname, String address) {
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;

        this.accounts = new ArrayList<>();
    }

    public void openAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccount() {
        return accounts;
    }

    public String getFirstname() {
    return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }
}