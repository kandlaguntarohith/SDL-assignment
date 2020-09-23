package librarymanagement.UserLogin;

import LibraryDatabase.LibraryDatabase;
import java.util.ArrayList;

import librarymanagement.library.Library;
import librarymanagement.user.User;

public class UserLogin extends Library {

    private String userName;
    private String password;
    protected User currentUser;

    public UserLogin() {
    }

    public void getLoginCredentials() {
        System.out.print("Enter Username : ");
        userName = in.nextLine();
        System.out.print("Enter Password : ");
        password = in.nextLine();
    }

    public boolean tryLogin() {
        currentUser = (new LibraryDatabase()).getUserCredentialsFromLibraryDatabase(userName, password);
        if (currentUser == null) {
            return false;
        }
        return true;
    }

    public User getUser() {
        return currentUser;
    }
}
