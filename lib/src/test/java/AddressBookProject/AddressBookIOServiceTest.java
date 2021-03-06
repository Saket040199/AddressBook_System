package AddressBookProject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class AddressBookIOServiceTest {
	
    AddressBookService addressBookService = new AddressBookService();
    List<AddressBook> addressBookList;


    @Test
    public void given3ContactDetailsWhenWrittenToFileShouldReturnCount() {
        AddressBook[] arrayOfEmps = {
                new AddressBook("Saket", "Jain", "Purana panna naka", "Chhatarpur", "MP", "798746" , "saketj420@gmail.com",471001),
                new AddressBook("Abhishek", "Jain", "Purana panna naka", "Chhatarpur", "MP", "798746" , "saketj420@gmail.com",471001),
                new AddressBook("Samkit", "Jain", "Purana panna naka", "Chhatarpur", "MP", "798746" , "saketj420@gmail.com",471001)
        };
        AddressBookIOService addressBookService;
        addressBookService = new AddressBookIOService();
        addressBookService.writeData(Arrays.asList(arrayOfEmps));
        addressBookService.readData();
        long entries = addressBookService.countDataEntries();
        Assert.assertEquals(3,entries);

    }
    
    @Test
    public void CSV_Test() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        AddressBook[] arrayOfEmps = {
        		   new AddressBook("Saket", "Jain", "Purana panna naka", "Chhatarpur", "MP", "798746" , "saketj420@gmail.com",471001),
                   new AddressBook("Abhishek", "Jain", "Purana panna naka", "Chhatarpur", "MP", "798746" , "saketj420@gmail.com",471001),
                   new AddressBook("Samkit", "Jain", "Purana panna naka", "Chhatarpur", "MP", "798746" , "saketj420@gmail.com",471001)
           };
        AddressBookCSVFile addressBookCsvReader = new AddressBookCSVFile();
        addressBookCsvReader.writeDataInCSVFile(Arrays.asList(arrayOfEmps));
        int count = addressBookCsvReader.readData();
        Assert.assertEquals(4,count);
    }
    
    @Test
    public void Json_Test() throws IOException {
        AddressBook[] arrayOfEmps = {
        		   new AddressBook("Saket", "Jain", "Purana panna naka", "Chhatarpur", "MP", "798746" , "saketj420@gmail.com",471001),
                   new AddressBook("Abhishek", "Jain", "Purana panna naka", "Chhatarpur", "MP", "798746" , "saketj420@gmail.com",471001),
                   new AddressBook("Samkit", "Jain", "Purana panna naka", "Chhatarpur", "MP", "798746" , "saketj420@gmail.com",471001)
              };
        AddressBookJSONFile addJsonFile = new AddressBookJSONFile();
        addJsonFile.writeDataInJSONFile(Arrays.asList(arrayOfEmps));
        addJsonFile.readDataFromJSONFile();
        int m = addJsonFile.count();
        Assert.assertEquals(3,m);
    }
    
    @Test
    public void givenAddressBook_WhenRetrived_ShouldReturnAddressBookSize() throws AddressBookException {
        addressBookList = addressBookService.readAddressBookData();
        System.out.println(addressBookList);
        Assert.assertEquals(4, addressBookList.size());     
    }

    @Test
    public void givenNewAddress_WhenUpdated_ShouldSyncWithDatabase() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBook>addressBooks = addressBookService.readAddressBookData();
        addressBookService.updateAddress("Anikesh","jammuland");
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Anikesh");
        Assert.assertTrue(result);
    }
    
//    @Test
//    public void givenDateRangeForRecord_WhenRetrieved_ShouldReturnProperData() throws AddressBookException {
//        AddressBookService addressBookService = new AddressBookService();
//        List<AddressBook> recordDataInGivenDateRange = addressBookService.getRecordAddedInDateRange("2020-01-01","2015-05-20");
//        Assert.assertEquals(3,recordDataInGivenDateRange.size());
//    }
//    
    @Test
    public void givenNameofCityOrState_WhenRetrieved_ShouldReturnProperData() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBook> addressBooks = addressBookService.getRecordsAddedByCityOrStateName("chhatarpur","MP");
        System.out.println(addressBooks);
        Assert.assertEquals(2,addressBooks.size());
    }
    
    @Test
    public void givenNewContact_WhenAdded_ShouldSyncWithDB() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.addNewContact("Abhishek","Jain","naya panna naka","chhatarpur","MP","7458964401","abhishek04@gmail.com");
        Assert.assertTrue(addressBookService.checkAddressBookInSyncWithDB("Farzan"));

    }

    @Test
    public void givenMultipleContact_WhenAdded_ShouldSyncWithDB() throws AddressBookException {
        AddressBook[] addressBooks= {
                new AddressBook("Samkit", "jain", "purana panna naka", "chhatarpur",
                        "MP", "789456244", "Sam30@gmail.com"),
                new AddressBook("Sachi", "jain", "mahaveer colony", "chhatarpur",
                        "MP", "785625444", "sachi21@gmail.com")

        };
        List<AddressBook> addressBookList = Arrays.asList(addressBooks);
        addressBookService.addMultipleContactsToRecord(addressBookList);
        Assert.assertTrue(addressBookService.checkAddressBookInSyncWithDB("Samkit"));
        Assert.assertTrue(addressBookService.checkAddressBookInSyncWithDB("Sachi"));

    }
}
