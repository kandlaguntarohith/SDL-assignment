package librarymanagement.AdminMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import librarymanagement.Book.Book;
import librarymanagement.admin.Admin;
import librarymanagement.library.Library;

public class AdminMenu extends Library {
    static int bookId = 1;

    private Admin admin;
    ServerSocket serverSocket = null;
    Socket socket = null;

    public AdminMenu(Admin admin) {
        bookId = 1;
        this.admin = admin;
    }

    public void showMenu() {
        boolean cont = true;
        while (cont) {
            System.out.println("___________________________________________________________\n");
            System.out.println("1. Show All Registered Users");
            System.out.println("2. Show All Books in Library");
            System.out.println("3. Add Book to Library");
            System.out.println("4. Increase book copy count");
            System.out.println("5. Connect to User");
            System.out.println("0. Log Out");
            System.out.println("___________________________________________________________\n");
            int choice = Integer.parseInt(in.nextLine());
            ;
            switch (choice) {
                case 1:
                    showAllRegisteredUsers();
                    break;
                case 2:
                    showAllLibraryBooks();
                    break;
                case 3:
                    addBookToLibrary();

                    break;
                case 4:
                    increaseCopyCountOfaBook();
                    break;
                case 5:
                    try {
                        connectUser(3000);
                    } catch (Exception e) {
                        print(e);
                    }

                    break;
                case 0:
                    allRegisteredAdmins.put(admin.getId(), admin);
                    cont = false;
                    break;

                default:
                    break;
            }
        }
    }

    private void addBookToLibrary() {
        int id = bookId++;
        String bookName = "";
        String authorName = "";
        int noOfPages = 0;
        Calendar dateOfPublish = Calendar.getInstance();
        System.out.println("___________________________________________________________\n");
        while ((bookName.trim()).length() == 0) {
            System.out.print("Enter name of the book : ");
            bookName = in.nextLine();
            if ((bookName.trim()).length() == 0)
                System.out.print("Book name cant be empty !");
        }
        while (authorName.trim().length() == 0) {
            System.out.print("Enter Author of the book : ");
            authorName = in.nextLine();
            if ((authorName.trim()).length() == 0)
                System.out.println("Book Author cant be empty !");
        }
        while (noOfPages == 0) {
            System.out.print("Enter Number of pages in the book : ");
            noOfPages = Integer.parseInt(in.nextLine());
            if (noOfPages == 0)
                System.out.println("Book Author cant be empty !");
        }
        System.out.println("Enter Date of the book : ");
        System.out.print("Year : ");
        dateOfPublish.set(Calendar.YEAR, Integer.parseInt(in.nextLine()));
        System.out.print("Month : ");
        dateOfPublish.set(Calendar.MONTH, Integer.parseInt(in.nextLine()));
        System.out.print("Day : ");
        dateOfPublish.set(Calendar.DAY_OF_MONTH, Integer.parseInt(in.nextLine()));
        System.out.println("No of Copies :");
        int copiesCount = Integer.parseInt(in.nextLine());
        bookCollection.put(id, new Book(id, bookName, authorName, noOfPages, dateOfPublish, copiesCount));
        System.out.println("___________________________________________________________\n");
        System.out.println("\nBook Entry of \nId :" + id + "\nName : " + authorName + "\nAuthor : " + authorName
                + "\n\nSuccessfully Created in Library !");
    }

    private void increaseCopyCountOfaBook() {
        showAllLibraryBooks();
        while (true) {
            System.out.println("\n\n1. Enter bookId");
            System.out.println("2. Exit");
            int choice = Integer.parseInt(in.nextLine());
            if (choice == 1) {
                System.out.println("\nEnter the id of the Book you wish to Increase Copy Count : ");
                int bookId = Integer.parseInt(in.nextLine());
                if (bookCollection.containsKey(bookId)) {
                    System.out.println("\nEnter extra copies count : ");
                    int count = Integer.parseInt(in.nextLine());
                    bookCollection.get(bookId).increaseCopyCount(count);
                    System.out.println("\nCopies successfully added !\n");

                } else {
                    System.out.println("\nBook id not found !\n");
                }
            } else
                break;
        }
    }

    private void showAllRegisteredUsers() {
        System.out.println("__________________________________________________________________\n");
        System.out.println("All Registered Users are as Follows :");
        System.out.print("+------------------------------------------------------------------+\n");
        System.out.print("|  ");
        System.out.printf("%-10s", "Id");
        System.out.print("|  ");
        System.out.printf("%-15s", "UserName");
        System.out.print("|  ");
        System.out.printf("%-15s", "FirstName");
        System.out.print("|  ");
        System.out.printf("%-15s", "LastName");
        System.out.print("|\n");
        System.out.print("+------------------------------------------------------------------+\n");
        allRegisteredUsers.values().forEach((user) -> {
            System.out.print("|  ");
            System.out.printf("%-10d", user.getId());
            System.out.print("|  ");
            System.out.printf("%-15s", user.getUserName());
            System.out.print("|  ");
            System.out.printf("%-15s", user.getFirstName());
            System.out.print("|  ");
            System.out.printf("%-15s", user.getLastName());
            System.out.print("|\n");
        });
        System.out.print("+------------------------------------------------------------------+\n");
    }

    private void connectUser(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Awaiting Connection");
        socket = serverSocket.accept();
        System.out.println("Connection From " + socket + " Established");
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        String inputReceivedString = "";
        String sendData = "";
        while (true) {
            sendData = in.nextLine();
            outputStream.writeUTF(sendData);
            inputReceivedString = inputStream.readUTF();
            if (inputReceivedString.trim().toLowerCase().contains("end"))
                break;
            print(inputReceivedString);
        }
        // System.out.println(inputReceivedString);
        // inFromClient.close();
        // outToClient.close();
        inputStream.close();
        outputStream.close();
        socket.close();
    }

}