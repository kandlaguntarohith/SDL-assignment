package librarymanagement.UserMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import librarymanagement.Book.Book;
import librarymanagement.BookBorrowInfo.BookBorrowInfo;
import librarymanagement.BookMenu.BookMenu;
import librarymanagement.library.Library;
import librarymanagement.user.User;

public class UserMenu extends Library {
    User user;
    Socket socket = null;

    public UserMenu(int userId) {
        this.user = allRegisteredUsers.get((Object) userId);
    }

    public void showMenu() {
        showUpdates();
        boolean cont = true;
        while (cont) {
            print("======================================================");
            print("1. Display all Library books ");
            print("2. Show Borrowed books ");
            print("3. Return a book ");
            print("4. Search Query");
            print("5. Connect Admin");
            print("0. LogOut");
            print("======================================================");
            final int choice = Integer.parseInt(in.nextLine());
            switch (choice) {
                case 1:
                    showBookLibrary();
                    break;
                case 2:
                    showAllBorrowedBooks();
                    break;
                case 3:
                    returnAbook();
                    break;
                case 4:
                    searchQuery();
                    break;
                case 5:
                    try {
                        ConnectServer("localhost", 3000);
                    } catch (Exception e) {
                        print(e);
                    }
                    break;
                case 0:
                    allRegisteredUsers.put(user.getId(), user);
                    cont = false;
                    break;

                default:
                    print("Please enter a valid choice !");
                    break;
            }

        }
    }

    void showUpdates() {
        Vector<Integer> updates = user.getUpdates();
        if (updates.size() == 0)
            return;
        print("Following are the updates while you were away : ");
        updates.forEach((bookinfoId) -> {
            printBookIssueDetails(bookBorrowInfoData.get((Object) bookinfoId));
            print("");

        });
        user.clearUpdates();
    }

    void searchQuery() {
        print("1. By Book Id : ");
        print("2. By Book Name : ");
        print("3. By Book Author Name : ");
        switch (Integer.parseInt(in.nextLine())) {
            case 1:
                print("Enter Book Id");
                getBookSearchResults(Integer.parseInt(in.nextLine().trim()));
                break;
            case 2:
                print("Enter Book Name");
                getBookSearchResults(in.nextLine().trim());
                break;
            case 3:
                print("Enter Book Author Name");
                getBookSearchResults(in.nextLine().trim());
                break;
            default:
                print("Enter Valid Choice !");
                break;
        }

        // String queryString;
        // queryString = in.nextLine().trim();
        // if (tryParseInt(queryString))
        // getBookSearchResults(Integer.parseInt(queryString));
        // else if (queryString.length() > 0)
        // getBookSearchResults(queryString);
        // else {
        // print("Enter Valid data");
        // print("\n");
        // return;
        // }

    }

    void returnAbook() {
        showAllBorrowedBooks();
        while (true) {
            print("\n\n1. Select book");
            print("2. Exit");
            final int choice = Integer.parseInt(in.nextLine());
            if (choice == 1) {
                print("\nEnter the issueId of the Book : ");
                final int issueId = Integer.parseInt(in.nextLine());
                if (bookBorrowInfoData.containsKey(issueId)) {
                    BookMenu bookMenu = new BookMenu(
                            bookCollection.get((Object) bookBorrowInfoData.get((Object) issueId).getBookId()));
                    bookMenu.returnBook(issueId);
                } else
                    print("\n Issue id not found !");

            } else
                break;
        }
    }

    void showBookLibrary() {
        showAllLibraryBooks();

        while (true) {
            print("\n\n1. Select book");
            print("2. Exit");
            final int choice = Integer.parseInt(in.nextLine());
            if (choice == 1) {
                print("\nEnter the id of the Book : ");
                final int bookId = Integer.parseInt(in.nextLine());
                if (bookCollection.containsKey(bookId)) {
                    final BookMenu bookMenu = new BookMenu(bookCollection.get(bookId), user);
                    bookMenu.showUserMenu();
                } else
                    print("\n Book id not found !");

            } else
                break;
        }
    }

    void showAllBorrowedBooks() {
        Vector<Integer> allBorrowedBooks;
        allBorrowedBooks = user.getCurrentBorrowedList();
        if (allBorrowedBooks.size() == 0)
            print("No Books Borrowed!");
        else {
            System.out.printf("%-10s%-10s%-30s%-15s%-15s", "Issue Id", "Book Id", "Book Name", "Issue Date",
                    "Deadline");
            print("\n");
            allBorrowedBooks.forEach((borrowId) -> {
                BookBorrowInfo bookBorrowInfo = bookBorrowInfoData.get((Object) borrowId);
                Book book = bookCollection.get((Object) bookBorrowInfo.getBookId());
                System.out.printf("%-10d%-10d%-30s%-15s%-15s", borrowId, book.getBookId(), book.getBookName(),
                        getDateFormatString(bookBorrowInfo.getIssueDate()),
                        getDateFormatString(bookBorrowInfo.getReturnDate()));
            });
            print("\n");
        }

    }

    public void ConnectServer(String address, int port) throws UnknownHostException, IOException {
        socket = new Socket(address, port);
        print("Connection to the Server Successfull !");
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        String inputData = "";
        while (true) {
            print(inputStream.readUTF());
            inputData = in.nextLine();
            if (inputData.trim().toLowerCase().contains("end"))
                break;
            outputStream.writeUTF(inputData);
            print(inputStream.readUTF());
        }

        outputStream.close();
        inputStream.close();
        socket.close();
    }
}
