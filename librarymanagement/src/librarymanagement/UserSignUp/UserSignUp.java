package librarymanagement.UserSignUp;

import LibraryDatabase.LibraryDatabase;
import librarymanagement.library.Library;
import librarymanagement.user.User;

public class UserSignUp extends Library {
    
    private String userName;
    private String firstName;
    private String lastName;
    private String mailId;
    private String password;
    
    public UserSignUp() {
    }
    
    public void signUp() {
        System.out.print("Enter FirstName : ");
        firstName = in.nextLine();
        System.out.print("Enter LastName : ");
        lastName = in.nextLine();
        System.out.print("Enter Username : ");
        userName = in.nextLine();
        System.out.print("Enter Password : ");
        password = in.nextLine();
        System.out.print("Enter MailId : ");
        mailId = in.nextLine();
        new LibraryDatabase().insertUserIntoLibraryDatabase(userName, firstName, lastName, mailId, password);
        
    }
}
