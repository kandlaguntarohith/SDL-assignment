package librarymanagement.AdminLogin;

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
        boolean valid = false;
        final ArrayList<Admin> admins = new ArrayList<Admin>(allRegisteredAdmins.values());
        for (int i = 0; i < admins.size() && !valid; i++) {
            Admin admin = admins.get(i);
            valid = (admin.getUserName().equals(adminName) && admin.getPassword().equals(password));
            if (valid)
                currentAdmin = admin;
        }
        return valid;
    }

    public Admin getAdmin() {
        return currentAdmin;
    }

}