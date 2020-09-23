package librarymanagement.library;

import LibraryDatabase.LibraryDatabase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import librarymanagement.user.*;
import librarymanagement.Book.Book;
import librarymanagement.BookBorrowInfo.BookBorrowInfo;
import librarymanagement.admin.*;
import java.util.Vector;

public class Library {

    protected static Scanner in = new Scanner(System.in);

    static protected <T> void print(T printableData) {
        System.out.println(printableData);
    }

    protected static void showAllLibraryBooks() {
        Vector<Book> books = (new LibraryDatabase()).getBookList();
        if (books.size() == 0) {
            print("Empty !");
            return;
        }
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
        books.forEach((book) -> {
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
        Book book = new LibraryDatabase().getBookById(bookBorrowInfo.getBookId());
        User user = new LibraryDatabase().getUserById(bookBorrowInfo.getUserId());
        System.out.println("-------------------------------------------------");
        System.out.println("Book issue details : ");
        System.out.println("Book issue Id : " + bookBorrowInfo.getId());
        System.out.println("Book Id : " + book.getBookId());
        System.out.println("Book Name : " + book.getBookName());
        System.out.println("User Id : " + user.getId());
        System.out.println("User Name : " + user.getUserName());
        System.out.println("Book issue Date : " + getDateFormatString(bookBorrowInfo.getIssueDate()));
        System.out.println("Book return Deadline : " + getDateFormatString(bookBorrowInfo.getReturnDate()));
        System.out.println("-------------------------------------------------\n");
    }

    protected String getDateFormatString(Calendar calender) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calender.getTime());
    }

    protected Calendar getCalenderFromDateFormatString(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cal.setTime(sdf.parse(date));// all done
        } catch (ParseException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cal;
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
            Book book = new LibraryDatabase().getBookById((Integer) searchQueryChar);
            if (book == null) {
                print("No Book Found with that Id!");
                return;
            }
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

        } else if (searchQueryChar.getClass() == String.class) {

            Vector<Book> books = new LibraryDatabase().getBookList();
            if (books.size() == 0) {
                print("No result Found !");
                return;
            }
            books.forEach((book) -> {
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

        } else {
            print("Enter Valid Data");
        }
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
