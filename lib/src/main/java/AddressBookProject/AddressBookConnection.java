package AddressBookProject;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookConnection {

    List<AddressBook> addressBookList;
    private static AddressBookConnection addressBookConnection;
    private PreparedStatement recordDataStatement;
    
    public Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
        String userName = "root";
        String password = "Saket@420";
        Connection connection;
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println(connection);
        return connection;
    }
    public static AddressBookConnection getInstance() {
        if (addressBookConnection == null)
            addressBookConnection = new AddressBookConnection();
        return addressBookConnection;
    }
    public List<AddressBook> readData() throws AddressBookException {
        addressBookList = new ArrayList<AddressBook>();
        String sql = "SELECT * FROM address_book_table; ";
        return this.getDataFromDataBase(sql);
    }

    private List<AddressBook> getDataFromDataBase(String sql) throws AddressBookException {
        addressBookList = new ArrayList<AddressBook>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            addressBookList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AddressBookException(AddressBookException.AddressBookExceptionType.READ_DATA_EXCEPTION,
                    "!!Unable to read data from database!!");
        }
        return addressBookList;
    }

    private List<AddressBook> getAddressBookData(ResultSet resultSet) throws AddressBookException {
        addressBookList = new ArrayList<AddressBook>();
        try {
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                
                String email = resultSet.getString("Email");
                int zip = resultSet.getInt("zip_code");
                LocalDate date = resultSet.getDate("startDate").toLocalDate();
                addressBookList.add(new AddressBook(firstName, lastName, address, city, state, email,zip,date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AddressBookException(AddressBookException.AddressBookExceptionType.READ_DATA_EXCEPTION,
                    "!!Unable to read data from database!!");
        }
        return addressBookList;
    }
    
    public List<AddressBook> getRecordDataByName(String firstName) throws AddressBookException {
        List<AddressBook> record = null;
        if (this.recordDataStatement == null) this.preparedStatementForRecord();
        try {
            recordDataStatement.setString(1, firstName);
            ResultSet resultSet = recordDataStatement.executeQuery();
            record = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AddressBookException(AddressBookException.AddressBookExceptionType.UPDATION_DATA_EXCEPTION,
                    "!!Unable to update data from database!!");
        }
        return record;
    }

    private void preparedStatementForRecord() {
        try {
            Connection connection = this.getConnection();
            String query = "SELECT * FROM address_book_table WHERE first_name = ?";
            recordDataStatement = connection.prepareStatement(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public int updateDataUsingPreparedStatement(String firstName, String address) {
        String query = "UPDATE address_book_table SET address = ? WHERE first_name = ?";
        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatementUpdate = connection.prepareStatement(query);
            preparedStatementUpdate.setString(1, address);
            preparedStatementUpdate.setString(2, firstName);
            return preparedStatementUpdate.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    
    public List<AddressBook> getRecordsAddedInGivenDateRange(String date1, String date2) throws AddressBookException {
        String query = String.format("SELECT * FROM address_book_table WHERE startDate BETWEEN '%s' AND '%s';", date1, date2);
        return this.getDataFromDataBase(query);

    }
    
    public List<AddressBook> getRecordsByCityOrState(String city, String state) throws AddressBookException {
        List<AddressBook> addressBooks = this.readData();
        String query = String.format("SELECT * FROM address_book_table WHERE City='%s' OR State='%s';", city, state);
        return this.getDataFromDataBase(query);
    }
    
    public AddressBook addNewContact(String firstName, String lastName, String address, String city, String state,
            String phoneNo, String email) throws AddressBookException {
       int id = -1;
       AddressBook addressBookData = null;
       String query = String.format(
       "insert into addressBook(first_name, last_name, address, city, state,Email, zip_code) values ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
        firstName, lastName, address, city, state,  phoneNo, email);
       try (Connection connection = this.getConnection()) {
       Statement statement = connection.createStatement();
       int rowChanged = statement.executeUpdate(query, statement.RETURN_GENERATED_KEYS);
       if (rowChanged == 1) {
       ResultSet resultSet = statement.getGeneratedKeys();
       if (resultSet.next())
       id = resultSet.getInt(1);
       }
       addressBookData = new AddressBook(firstName, lastName, address, city, state, phoneNo, email);
       } catch (SQLException e) {
       e.printStackTrace();
       throw new AddressBookException(AddressBookException.AddressBookExceptionType.INSERTION_FAIL,"Unable to add employee!!");
       }
       return addressBookData;
    }
}
