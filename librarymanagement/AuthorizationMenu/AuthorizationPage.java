package librarymanagement.AuthorizationMenu;

import librarymanagement.AdminLogin.AdminLogin;
import librarymanagement.AdminMenu.AdminMenu;
import librarymanagement.UserLogin.UserLogin;
import librarymanagement.UserMenu.UserMenu;
import librarymanagement.UserSignUp.UserSignUp;
import librarymanagement.admin.Admin;
import librarymanagement.library.Library;
import librarymanagement.user.User;

public class AuthorizationPage extends Library {
    public AuthorizationPage() {
        boolean exit = false;
        while (!exit) {
            System.out.println("_____________________________________________\n");
            System.out.println("1. User Login");
            System.out.println("2. User SignUp");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.println("_____________________________________________");
            Integer choice = Integer.parseInt(in.nextLine());
            switch (choice) {

                case 1:
                    boolean isLoggedIn = false;
                    boolean tryLogin = true;
                    User user;
                    while (tryLogin) {
                        UserLogin userLogin = new UserLogin();
                        userLogin.getLoginCredentials();
                        isLoggedIn = userLogin.tryLogin();
                        if (isLoggedIn) {
                            user = userLogin.getUser();
                            userLogin = null;
                            tryLogin = false;
                            System.out.println("\nWelcome, " + user.getFirstName() + "\n");
                            UserMenu userMenu = new UserMenu(user.getId());
                            userMenu.showMenu();
                        } else {
                            System.out.println("Invalid Credentials !\n");
                            System.out.println("1. Try Again ");
                            System.out.println("2. Exit");
                            choice = Integer.parseInt(in.nextLine());
                            if (choice != 1)
                                tryLogin = false;
                        }
                    }
                    break;
                case 2:
                    try {
                        final UserSignUp userSignUp = new UserSignUp();
                        userSignUp.signUp();
                        System.out.println("\nAccount successfully created !\n");
                    } catch (final Exception e) {
                        // library.idCount--;
                        System.out.println(" \nError Occured !  Try again\n");
                    }
                    break;
                case 3:
                    isLoggedIn = false;
                    tryLogin = true;
                    Admin admin;
                    while (tryLogin) {
                        final AdminLogin adminLogin = new AdminLogin();
                        adminLogin.getLoginCredentials();
                        isLoggedIn = adminLogin.tryLogin();
                        if (isLoggedIn) {
                            tryLogin = false;
                            admin = adminLogin.getAdmin();
                            System.out.println("\nWelcome, " + admin.getFirstName() + "\n");
                            final AdminMenu adminMenu = new AdminMenu(admin);
                            adminMenu.showMenu();
                        } else {
                            System.out.println("Invalid Credentials !\n");
                            System.out.println("1. Try Again ");
                            System.out.println("2. Exit");
                            choice = Integer.parseInt(in.nextLine());
                            if (choice != 1)
                                tryLogin = false;
                        }
                    }
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid Choice !");
                    break;
            }
        }
    }

}