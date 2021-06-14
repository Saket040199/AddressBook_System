package AddressBookProject;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class AddressBookRestAssuredTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }

    public AddressBook[] getAddressBook() {
        Response response =RestAssured.get("C:\\Users\\Saket Jain\\eclipse-workspace\\AddressBookProject\\lib\\src\\main\\java\\AddressBookProject\\AddressBook");
        System.out.println("AddressBook entries in json server :\n" + response.asString());
        AddressBook[] arrayOfAddressBook = new Gson().fromJson(response.asString(), AddressBook[].class);
        return arrayOfAddressBook;

    }
    private Response addContactToJsonServer(AddressBook addressBookData) {
        String addressBookJson = new Gson().toJson(addressBookData);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(addressBookJson);
        return request.post("\\Addressbook\\");
    }

    @Test
    public void givenContactDataInJsonServer_WhenRetrieved_ShouldMatchContactCount() {
        AddressBook[] arrayOfContacts = getAddressBook();
        AddressBookService serviceObject = new AddressBookService(Arrays.asList(arrayOfContacts));
        long entries = serviceObject.sizeOfContactList();
        Assert.assertEquals(3, entries);
    }

    @Test
    public void givenMultipleContact_WhenAdded_ShouldMatch201ResponseAndCount() {
        AddressBook[] arrayOfAddressBook = getAddressBook();
        AddressBookService addressBookService;
        addressBookService = new AddressBookService(Arrays.asList(arrayOfAddressBook));
        AddressBook[] arrayOfPersonPayroll = {
                new AddressBook("Saket", "Jain", "Purana panna naka", "Chhatarpur", "MP", "7987469952","saketj420@gmail.com", 471001),
                new AddressBook("Abhishek", "jain", "Naya panna naka", "Chhatarpur", "MP", "7987458695", "abhi@gmail.com", 471001)
        };
        for (AddressBook addressBookData : arrayOfPersonPayroll) {

            Response response = addContactToJsonServer(addressBookData);
            int statusCode = response.getStatusCode();
            Assert.assertEquals(201, statusCode);

            addressBookData = new Gson().fromJson(response.asString(), AddressBook.class);
            addressBookService.addContactToAddressBook(addressBookData);
        }
        long entries = addressBookService.sizeOfContactList();
        Assert.assertEquals(5, entries);
    }
    @Test
    public void givenCity_WhenUpdated_ShouldMatch200response() {
        AddressBook[] arrayOfAddressBook = getAddressBook();
        AddressBookService addressBookService;
        addressBookService = new AddressBookService(Arrays.asList(arrayOfAddressBook));
        addressBookService.updateContactCity("Saket","Chhatarpur");
        AddressBook addressBookData = addressBookService.getAddressBookContent("Abhishek");

        String addJson = new Gson().toJson(addressBookData);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(addJson);
        Response response = request.put("\\Addressbook\\" + addressBookData.getName());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
    }
    @Test
    public void givenContactDetails_WhenDeleted_ShouldMatch200ResponseAndCount() {
        AddressBook[] arrayOfAddressBook = getAddressBook();
        AddressBookService addressBookService;
        addressBookService = new AddressBookService(Arrays.asList(arrayOfAddressBook));
        AddressBook addressBookData = addressBookService.getAddressBookContent("Saket");
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        Response response = request.delete("\\Addressbook\\" + addressBookData.getName());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);

        addressBookService.deleteContactPayroll(addressBookData.getFirstName());
        long entries = addressBookService.sizeOfContactList();
        Assert.assertEquals(4, entries);

    }
}
