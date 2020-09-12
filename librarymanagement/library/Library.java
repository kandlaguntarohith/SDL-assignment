package librarymanagement.library;

import java.text.SimpleDateFormat;
import java.util.*;
import librarymanagement.user.*;
import librarymanagement.Book.Book;
import librarymanagement.BookBorrowInfo.BookBorrowInfo;
import librarymanagement.admin.*;

public class Library {
    static boolean call = true;
    protected static int bookInfoIdCount = 1;
    protected static int bookIdCount = 1;
    protected static Hashtable<Integer, BookBorrowInfo> bookBorrowInfoData = new Hashtable<Integer, BookBorrowInfo>();
    protected static Scanner in = new Scanner(System.in);
    protected static Hashtable<Integer, User> allRegisteredUsers = new Hashtable<Integer, User>();
    protected static Hashtable<Integer, Admin> allRegisteredAdmins = new Hashtable<Integer, Admin>();
    protected static int idCount = 1;
    static int adminIdCount = 1;
    protected static Hashtable<Integer, Book> bookCollection = new Hashtable<Integer, Book>();

    static protected <T> void print(T printableData) {
        System.out.println(printableData);
    }

    public Library() {
        if (call) {
            Calendar doj = Calendar.getInstance();
            idCount = 1;
            adminIdCount = 1;
            bookIdCount = 1;
            allRegisteredUsers.put(idCount,
                    new User(idCount++, "User", "Rohith", "Reddy", "Rohithreddyr1@gmail.com", "User", 5));
            allRegisteredUsers.put(idCount, new User(idCount++, "User1", "Raj", "rani", "rr@gmail.com", "User1", 10));
            allRegisteredUsers.put(idCount,
                    new User(idCount++, "User2", "john", "wick", "johnwick@gmail.com", "User2", 15));
            allRegisteredAdmins.put(adminIdCount,
                    new Admin(adminIdCount++, "Admin", "Admin", "Admin", "Admin", "Admin"));
            bookCollection.put(bookIdCount, new Book(bookIdCount, "The Fault in our Stars", "John Green", 399, doj, 1));
            bookIdCount++;
            bookCollection.put(bookIdCount, new Book(bookIdCount, "Dongri to Dubai", "Hussain Zaidi", 408, doj, 1));
            bookIdCount++;
            call = false;
        }
    }

    protected static void showAllLibraryBooks() {
        System.out.println(
                "__________________________________________________________________________________________\n");
        System.out.println("All the Books in the Libary are :");
        System.out.print(
                "+---------------------------------------------------------------------------------------------------+\n");
        System.out.print("|  ");
        System.out.printf("%-10s", "Id");
        System.out.print("|  ");
        System.out.printf("%-30s", "Name");
        System.out.print("|  ");
        System.out.printf("%-15s", "Author");
        System.out.print("|  ");
        System.out.printf("%-15s", "Copies");
        System.out.print("|  ");
        System.out.printf("%-15s", "Date");
        System.out.print("|\n");
        System.out.print(
                "+---------------------------------------------------------------------------------------------------+\n");
        bookCollection.values().forEach((book) -> {
            System.out.print("|  ");
            System.out.printf("%-10d", book.getBookId());
            System.out.print("|  ");
            System.out.printf("%-30s", book.getBookName());
            System.out.print("|  ");
            System.out.printf("%-15s", book.getAuthorName());
            System.out.print("|  ");
            System.out.printf("%-15s", book.copyCount());
            System.out.print("|  ");
            Date date = book.getPublishDate().getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            System.out.printf("%-15s", format1.format(date));
            System.out.print("|\n");
        });
        System.out.print(
                "+---------------------------------------------------------------------------------------------------+\n");
    }

    protected void printBookIssueDetails(BookBorrowInfo bookBorrowInfo) {
        Book book = bookCollection.get(bookBorrowInfo.getBookId());
        User user = allRegisteredUsers.get(bookBorrowInfo.getUserId());
        Date issueDate = bookBorrowInfo.getIssueDate().getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date returnDeadlineDate = bookBorrowInfo.getReturnDate().getTime();
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("-------------------------------------------------");
        System.out.println("Book issue details : ");
        System.out.println("Book issue Id : " + bookBorrowInfo.getId());
        System.out.println("Book Id : " + book.getBookId());
        System.out.println("Book Name : " + book.getBookName());
        System.out.println("User Id : " + user.getId());
        System.out.println("User Name : " + user.getUserName());
        System.out.println("Book issue Date : " + format1.format(issueDate));
        System.out.println("Book return Deadline : " + format2.format(returnDeadlineDate));
        System.out.println("-------------------------------------------------\n");
    }

    protected String getDateFormatString(Calendar calender) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calender.getTime());
    }

    protected <T> void getBookSearchResults(T searchQueryChar) {
        System.out.println(
                "__________________________________________________________________________________________\n");
        System.out.println("Search Results :");
        System.out.print(
                "+---------------------------------------------------------------------------------------------------+\n");
        System.out.print("|  ");
        System.out.printf("%-10s", "Id");
        System.out.print("|  ");
        System.out.printf("%-30s", "Name");
        System.out.print("|  ");
        System.out.printf("%-15s", "Author");
        System.out.print("|  ");
        System.out.printf("%-15s", "Copies");
        System.out.print("|  ");
        System.out.printf("%-15s", "Date");
        System.out.print("|\n");
        System.out.print(
                "+---------------------------------------------------------------------------------------------------+\n");
        if (searchQueryChar.getClass() == Integer.class) {
            bookCollection.values().forEach((book) -> {
                if ((Integer) searchQueryChar == book.getBookId()) {
                    System.out.print("|  ");
                    System.out.printf("%-10d", book.getBookId());
                    System.out.print("|  ");
                    System.out.printf("%-30s", book.getBookName());
                    System.out.print("|  ");
                    System.out.printf("%-15s", book.getAuthorName());
                    System.out.print("|  ");
                    System.out.printf("%-15s", book.copyCount());
                    System.out.print("|  ");
                    Date date = book.getPublishDate().getTime();
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    System.out.printf("%-15s", format1.format(date));
                    System.out.print("|\n");
                }
            });

        } else if (searchQueryChar.getClass() == String.class) {
            bookCollection.values().forEach((book) -> {
                if (book.getBookName().toLowerCase().contains(((String) searchQueryChar).toLowerCase())
                        || book.getAuthorName().toLowerCase().contains(((String) searchQueryChar).toLowerCase())) {
                    System.out.print("|  ");
                    System.out.printf("%-10d", book.getBookId());
                    System.out.print("|  ");
                    System.out.printf("%-30s", book.getBookName());
                    System.out.print("|  ");
                    System.out.printf("%-15s", book.getAuthorName());
                    System.out.print("|  ");
                    System.out.printf("%-15s", book.copyCount());
                    System.out.print("|  ");
                    Date date = book.getPublishDate().getTime();
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    System.out.printf("%-15s", format1.format(date));
                    System.out.print("|\n");
                }
            });

        } else
            print("Enter Valid Data");
        System.out.print(
                "+---------------------------------------------------------------------------------------------------+\n");

    }

    protected boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
