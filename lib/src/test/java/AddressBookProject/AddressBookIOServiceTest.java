package AddressBookProject;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class AddressBookIOServiceTest {

    @Test
    public void given3ContactDetailsWhenWrittenToFileShouldReturnCount() {
        AddressBook[] arrayOfEmps = {
                new AddressBook("Saket", "Jain", "Purana panna naka", "Chhatarpur", "MP", 798746 , "saketj420@gmail.com",471001),
                new AddressBook("Abhishek", "Jain", "Purana panna naka", "Chhatarpur", "MP", 798746 , "saketj420@gmail.com",471001),
                new AddressBook("Samkit", "Jain", "Purana panna naka", "Chhatarpur", "MP", 798746 , "saketj420@gmail.com",471001)
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
        		   new AddressBook("Saket", "Jain", "Purana panna naka", "Chhatarpur", "MP", 798746 , "saketj420@gmail.com",471001),
                   new AddressBook("Abhishek", "Jain", "Purana panna naka", "Chhatarpur", "MP", 798746 , "saketj420@gmail.com",471001),
                   new AddressBook("Samkit", "Jain", "Purana panna naka", "Chhatarpur", "MP", 798746 , "saketj420@gmail.com",471001)
           };
        AddressBookCSVFile addressBookCsvReader = new AddressBookCSVFile();
        addressBookCsvReader.writeDataInCSVFile(Arrays.asList(arrayOfEmps));
        int count = addressBookCsvReader.readData();
        Assert.assertEquals(4,count);
    }

}
