package librarymanagement.UserLogin;

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
        boolean valid = false;
        // Library.showAllLibraryBooks();
        ArrayList<User> users = new ArrayList<User>(allRegisteredUsers.values());
        // Library.showAllLibraryBooks();
        for (int i = 0; i < users.size() && !valid; i++) {
            User user = users.get(i);
            valid = (user.getUserName().equals(userName) && user.getPassword().equals(password));
            if (valid)
                currentUser = user;
        }
        return valid;
    }

    public User getUser() {
        return currentUser;
    }
}