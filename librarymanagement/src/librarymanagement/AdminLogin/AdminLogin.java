package librarymanagement.AdminLogin;

import LibraryDatabase.LibraryDatabase;
import java.util.ArrayList;

import librarymanagement.admin.Admin;
import librarymanagement.library.Library;

public class AdminLogin extends Library {

    private String adminName;
    private String password;
    Admin currentAdmin;

    public AdminLogin() {
    }

    public void getLoginCredentials() {
        System.out.print("Enter Username : ");
        adminName = in.nextLine();
        System.out.print("Enter Password : ");
        password = in.nextLine();
    }

    public boolean tryLogin() {
        currentAdmin = new LibraryDatabase().getAdminCredentialsFromLibraryDatabase(adminName, password);
        if (currentAdmin == null) {
            return false;
        }
        return true;
    }

    public Admin getAdmin() {
        return currentAdmin;
    }

}
