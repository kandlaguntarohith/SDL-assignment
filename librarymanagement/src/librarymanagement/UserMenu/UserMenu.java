package librarymanagement.UserMenu;


import LibraryDatabase.LibraryDatabase;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import librarymanagement.Book.Book;
import librarymanagement.BookBorrowInfo.BookBorrowInfo;
import librarymanagement.BookMenu.BookMenu;
import librarymanagement.library.Library;
import librarymanagement.user.User;

public class UserMenu extends Library {
    User user;
    Socket socket = null;

    public UserMenu(User user) {
        this.user = user;
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
                    cont = false;
                    break;

                default:
                    print("Please enter a valid choice !");
                    break;
            }

        }
    }

    void showUpdates() {
        Vector<Integer> updates = new LibraryDatabase().getAndDeleteWaitListUpdates(user.getId());
        if (updates.size() == 0)
            return;
        print("Following are the updates while you were away : ");
        updates.forEach((bookinfoId) -> {
            printBookIssueDetails(new LibraryDatabase().getFromBookBorrowListBycontainsId(bookinfoId));
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
        LibraryDatabase database = new LibraryDatabase();
        while (true) {
            print("\n\n1. Select book");
            print("2. Exit");
            final int choice = Integer.parseInt(in.nextLine());
            if (choice == 1) {
                print("\nEnter the issueId of the Book : ");
                final int issueId = Integer.parseInt(in.nextLine());
                
                if (database.isBookBorrowListcontainsId(issueId)) {
                    BookBorrowInfo bookBorrowInfo = database.getFromBookBorrowListBycontainsId(issueId);
                    BookMenu bookMenu = new BookMenu(
                          database.getBookById(bookBorrowInfo.getBookId()) );
                    bookMenu.returnBook(bookBorrowInfo);
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
                LibraryDatabase libraryDatabase = new LibraryDatabase();
                if (libraryDatabase.isBookListcontainsId(bookId)) {
                    final BookMenu bookMenu = new BookMenu(libraryDatabase.getBookById(bookId), user);
                    bookMenu.showUserMenu();
                } else
                    print("\n Book id not found !");

            } else
                break;
        }
    }

    void showAllBorrowedBooks() {
        LibraryDatabase database = new LibraryDatabase();
        Vector<BookBorrowInfo> allBorrowedBooks;
        allBorrowedBooks = database.getBorrowedBooksList(user.getId());
        if (allBorrowedBooks.size() == 0)
            print("No Books Borrowed!");
        else {
            System.out.printf("%-10s%-10s%-30s%-15s%-15s", "Issue Id", "Book Id", "Book Name", "Issue Date",
                    "Deadline");
            print("\n");
            allBorrowedBooks.forEach((bookBorrowInfo) -> {                
                Book book  = new LibraryDatabase().getBookById(bookBorrowInfo.getBookId());
                System.out.printf("%-10d%-10d%-30s%-15s%-15s\n", bookBorrowInfo.getId(), book.getBookId(),book.getBookName(),
                        getDateFormatString(bookBorrowInfo.getIssueDate()),
                        getDateFormatString(bookBorrowInfo.getReturnDate()));
            });
            print("\n");
        }

    }

    public void ConnectServer(String address, int port) throws IOException  {
//        socket = new Socket(address, port);
//        print("Connection to the Server Successfull !");
//        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
//        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
//        String inputData = "";
//        while (true) {
//            print(inputStream.readUTF());
//            inputData = in.nextLine();
//            if (inputData.trim().toLowerCase().contains("end"))
//                break;
//            outputStream.writeUTF(inputData);
//            print(inputStream.readUTF());
//        }
//
//        outputStream.close();
//        inputStream.close();
//        socket.close();
        ClientChatWindow var = new ClientChatWindow();
        var.main(null);
    }
}
