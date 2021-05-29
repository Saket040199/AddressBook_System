package AddressBookProject;

import java.util.List;

public class AddressBookService {

    List<AddressBook> addressBookList;
    private static AddressBookConnection addressBookConnection;

    public AddressBookService() {
        addressBookConnection = AddressBookConnection.getInstance();
    }

    public List<AddressBook> readAddressBookData() throws AddressBookException {

        return this.addressBookList = addressBookConnection.readData();
    }
    public void updateAddress(String firstName, String address) throws AddressBookException{
        int result = new AddressBookConnection().updateDataUsingPreparedStatement(firstName,address);
        if (result == 0)
            return;
        AddressBook addressBook = this.getAddressBookData(firstName);
        if (addressBook != null)
            addressBook.setAddress(address);
    }

    private AddressBook getAddressBookData(String firstName) throws AddressBookException {
        addressBookList = this.readAddressBookData();
        return this.addressBookList.stream()
                .filter(addressBook -> addressBook.getFirstName().equals(firstName))
                .findFirst()
                .orElse(null);
    }

    public boolean checkEmployeePayrollInSyncWithDB(String firstName) throws AddressBookException {
        List<AddressBook> addressBooks = addressBookConnection.getRecordDataByName(firstName);

        return addressBooks.get(0).equals(getAddressBookData(firstName));
    }

}
