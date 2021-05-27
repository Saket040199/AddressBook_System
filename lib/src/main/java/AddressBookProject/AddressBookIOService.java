package AddressBookProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AddressBookIOService {

    public static String AddressBookPath = "C:\\Users\\Saket Jain\\eclipse-workspace\\AddressBookProject\\lib\\src\\main\\java\\AddressBookProject\\AddressBookData.txt";
    public void writeData(List<AddressBook> addressBooks) {
        StringBuffer contactBufferString = new StringBuffer();
        addressBooks.forEach(contact -> {
            String contactDataString = contact.toString().concat("\n");
            contactBufferString.append(contactDataString);
        });

        try {
            Files.write(Paths.get(AddressBookPath), contactBufferString.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readData(){
        try {
            Files.lines(new File("C:\\Users\\Saket Jain\\eclipse-workspace\\AddressBookProject\\lib\\src\\main\\java\\AddressBookProject\\AddressBookData.txt").toPath()).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public long countDataEntries() {
        long entries = 0;
        try {
            entries = Files.lines(new File("C:\\Users\\Saket Jain\\eclipse-workspace\\AddressBookProject\\lib\\src\\main\\java\\AddressBookProject\\AddressBookData.txt").toPath()).count();
            System.out.println(entries);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }
}
