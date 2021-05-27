package AddressBookProject;

import java.util.Scanner;

public class AddressBookMain {

    static ContactDirectory contactDirectory = new ContactDirectory();
    public void addressBookMain(){
        Scanner sc = new Scanner(System.in);
        addressBookOperations();
        System.out.println("Do u wqant to proceed , say Yes/No");
        String ch = sc.next();
        while(ch.equals("Yes")) {
            addressBookOperations();
            System.out.println("Do u wqant to proceed , say Yes/No");
            ch = sc.next();
        }
        
    }
    public static void addressBookOperations(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 : Add contact");
        System.out.println("Enter 2 : Edit contact ");
        System.out.println("Enter 3 : Delete Contact");
        System.out.println("Enter 4 : Show Contact");
        System.out.println("Enter 5: Search Contact by city name or by state name ");
        System.out.println("Enter 6: Count Contact by city name or by state name ");
        System.out.println("Enter 7: Sorted Entries");
        
        int choose = sc.nextInt();
        switch(choose) {
            case 1 :
                contactDirectory.addContact();
                break;

            case 2:
                contactDirectory.editContact();
                break;
                
            case 3:
                contactDirectory.deleteContact();
                break;

            case 4:
                contactDirectory.show();
                break;
                
            case 5:
                contactDirectory.searchContact();
                break;
                
            case 6:
                contactDirectory.countPerson();
                break;

            case 7:
            	contactDirectory.sortedEntries();
            	break;
                
            default:
                System.out.println("Invalid Choice");
                break;

        }
     
    }
    }
