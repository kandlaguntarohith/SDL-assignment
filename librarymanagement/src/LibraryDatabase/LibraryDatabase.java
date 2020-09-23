package LibraryDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.Calendar;
import java.util.Vector;
import librarymanagement.Book.Book;
import librarymanagement.BookBorrowInfo.BookBorrowInfo;
import librarymanagement.admin.Admin;
import librarymanagement.library.Library;
import librarymanagement.user.User;

public class LibraryDatabase extends Library {

    String url = "jdbc:mysql://localhost:3306/Library";
    String user = "root";
    String password = "Redi@123";
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    public LibraryDatabase() {

        url = "jdbc:mysql://localhost:3306/Library";
        user = "root";
        password = "Redi@123";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void insertUserIntoLibraryDatabase(String userName, String firstName, String lastName, String mailId, String password) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO User VALUES(?,?,?,?,?,?,?)");
            ps.setNull(1, Types.NULL);
            ps.setString(2, userName);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setString(5, mailId);
            ps.setString(6, password);
            ps.setInt(7, 10);
            int ans = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public User getUserCredentialsFromLibraryDatabase(String userName, String password) {

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM User WHERE userName = ? and password = ?");
            ps.setString(1, userName);
            ps.setString(2, password);
            resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7));

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public User getUserById(int userId) {

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM User WHERE userId = ?");
            ps.setInt(1, userId);
            resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7));

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public Admin getAdminCredentialsFromLibraryDatabase(String adminName, String password) {

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Admin WHERE adminName = ? and password = ?");
            ps.setString(1, adminName);
            ps.setString(2, password);
            resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new Admin(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public Vector<Book> getBookList() {
        Vector<Book> books = new Vector<Book>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Book");

            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                books.add(new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), getCalenderFromDateFormatString(resultSet.getString(5)), resultSet.getInt(6)));
            }
            return books;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public Vector<User> getUserList() {
        Vector<User> users = new Vector<User>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM User");

            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                users.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7)));
            }
            return users;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean isBookListcontainsId(int bookId) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT count(*) FROM Book WHERE bookId = ?");
            ps.setInt(1, bookId);
            resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                return false;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    public void updateBookCountOfId(int bookId, int count) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT bookCopiesCount FROM Book WHERE bookId = ?");
            ps.setInt(1, bookId);
            resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Error !");
                return;
            }
            int bookCount = resultSet.getInt(1);
            bookCount += count;
            ps = connection.prepareStatement("UPDATE Book SET bookCopiesCountbookId = ? WHERE bookId = ?");
            ps.setInt(1, bookCount);
            ps.setInt(2, bookId);
            ps.executeUpdate();
            System.out.println("\nCopies successfully added !\n");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Book getBookById(int bookId) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Book WHERE bookId = ?");
            ps.setInt(1, bookId);
            resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            return new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), getCalenderFromDateFormatString(resultSet.getString(5)), resultSet.getInt(6));
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public int addNewBookToDatabase(String bookName, String authorName, int noOfPages, Calendar dateOfPublish, int copiesCount) {
        int id = -1;
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Book VALUES(?,?,?,?,?,?)");
            ps.setNull(1, Types.NULL);
            ps.setString(2, bookName);
            ps.setString(3, authorName);
            ps.setInt(4, noOfPages);
            ps.setString(5, getDateFormatString(dateOfPublish));
            ps.setInt(6, copiesCount);
            ps.executeUpdate();
            ps = connection.prepareStatement("SELECT bookId FROM Book WHERE bookName = ? AND authorName = ?");
            ps.setString(1, bookName);
            ps.setString(2, authorName);
            resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                return -1;
            }
            id = resultSet.getInt(1);

        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }

    public boolean isBoolAvailableToBorrow(int bookId) {

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT bookCopiesCount FROM Book WHERE bookId = ?");
            ps.setInt(1, bookId);
            resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                print("error ! occured ");
                return false;
            }
            int count = resultSet.getInt(1);
            ps = connection.prepareStatement("SELECT COUNT(*) FROM BookBorrowInfo WHERE bookId = ?");
            ps.setInt(1, bookId);
            resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                print("error ! occured ");
                return false;
            }
            int borrowedCount = resultSet.getInt(1);
            if (count - borrowedCount < 1) {
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }

    public int addToBookBorrowInfoTable(int userId, int bookId, String issueCalendarDate, String returnDeadlineDate) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO BookBorrowInfo VALUES(?,?,?,?,?,?)");
            ps.setNull(1, Types.NULL);
            ps.setInt(2, userId);
            ps.setInt(3, bookId);
            ps.setString(4, issueCalendarDate);
            ps.setString(5, returnDeadlineDate);
            ps.setNull(6, Types.NULL);
            ps.executeUpdate();
            ps = connection.prepareStatement("SELECT BookBorrowInfoid FROM BookBorrowInfo WHERE userId = ? AND bookId = ? AND issueCalendarDate = ?");
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.setString(3, issueCalendarDate);
            resultSet = ps.executeQuery();
            int id = resultSet.getInt(1);
            return id;
        } catch (Exception e) {
        }
        return bookId - 1;

    }

    public Vector<BookBorrowInfo> getBorrowedBooksList(int userId) {
        Vector<BookBorrowInfo> bookBorrowInfos = new Vector<BookBorrowInfo>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM BookBorrowInfo WHERE userId = ?");
            ps.setInt(1, userId);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String returnDate = resultSet.getString(6);
                Calendar calendar = returnDate == null ? null : getCalenderFromDateFormatString(returnDate);
                bookBorrowInfos.add(new BookBorrowInfo(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), getCalenderFromDateFormatString(resultSet.getString(4)), getCalenderFromDateFormatString(resultSet.getString(5)), calendar));
            }
        } catch (Exception e) {
            print(e);
        }
        return bookBorrowInfos;
    }

    public int getPriorityLevelFromWaitList(int userId, int bookId) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT priority FROM waitList WHERE userId = ? AND bookId = ?");
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            print(e);
        }
        return -1;
    }

    public int getPriorityLevelFromUserTable(int userId) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT vipLevel FROM User WHERE userId = ?");
            ps.setInt(1, userId);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            print(e);
        }
        return -1;
    }

    public int getWaitListposition(int userId, int bookId) {
        int priority = getPriorityLevelFromWaitList(userId, bookId);
        if (priority == -1) {
            return -1;
        }
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM waitList WHERE priority >=? ORDER BY priority DESC");
            ps.setInt(1, priority);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            print(e);
        }
        return -1;
    }

    public void addToWaitList(int userId, int bookId, int priority) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO waitList VALUES(?,?,?)");
            ps.setInt(1, bookId);
            ps.setInt(2, userId);
            ps.setInt(3, priority);
            ps.executeUpdate();
        } catch (Exception e) {
            print(e);
        }
    }

    public boolean isWaitListEmpty(int bookId) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM waitList");

            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) == 0 ? false : true;
            }
        } catch (Exception e) {
            print(e);
        }
        return false;
    }

    public boolean isBookBorrowListcontainsId(int bookBorrowList) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM BookBorrowInfo WHERE BookBorrowInfoid = ?");
            ps.setInt(1, bookBorrowList);
            resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                return false;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    public BookBorrowInfo getFromBookBorrowListBycontainsId(int bookBorrowList) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM BookBorrowInfo WHERE BookBorrowInfoid = ?");
            ps.setInt(1, bookBorrowList);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String returnDate = resultSet.getString(6);
                Calendar calendar = returnDate == null ? null : getCalenderFromDateFormatString(returnDate);
                return new BookBorrowInfo(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), getCalenderFromDateFormatString(resultSet.getString(4)), getCalenderFromDateFormatString(resultSet.getString(5)), calendar);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void clearBookBorrowInfoById(int bookBorrowInfo) {

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM BookBorrowInfo WHERE BookBorrowInfoid = ?");
            ps.setInt(1, bookBorrowInfo);
            ps.executeUpdate();
        } catch (Exception e) {
            print(e);
        }
    }

    public void IncreaseVipPointsOfUser(int userId) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE User SET vipLevel = vipLevel + ? WHERE userId = ?");
            ps.setInt(1, 5);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            print(e);
        }
    }

    public void decreaseVipPointsOfUser(int userId) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE User SET vipLevel = vipLevel - ? WHERE userId = ?");
            ps.setInt(1, 10);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            print(e);
        }
    }

    public int fetchAndDeleteTheMaxPriorityUserFromWaitList(int bookId) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT userId FROM waitList WHERE priority = (SELECT MAX(priority) FROM waitList WHERE bookId = ?)");
            ps.setInt(1, bookId);
            resultSet = ps.executeQuery();
            int id = -1;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            if (id == -1) {
                print("ERROR OCCURED !");
                return -1;
            }
            DeleteUserFromWaitList(bookId, id);
        } catch (Exception e) {
            print(e);
        }
        return -1;
    }

    public void DeleteUserFromWaitList(int bookId, int userId) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM waitList WHERE userId = ? AND bookId = ?");
            ps.setInt(1, userId);
            ps.setInt(1, bookId);
            ps.executeUpdate();

        } catch (Exception e) {
            print(e);
        }

    }

    public Vector<Integer> getAndDeleteWaitListUpdates(int userId) {
        Vector<Integer> list = new Vector<Integer>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT BookBorrowInfoid FROM waitListUpdates WHERE userId = ?");
            ps.setInt(1, userId);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getInt(1));
            }
            ps = connection.prepareStatement("DELETE FROM waitListUpdates WHERE userId = ?");
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            print(e);
        }
        return list;
    }

    public void addToUserUpdates(int userId, int bookBorrowInfoId) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO waitListUpdates VALUES(?,?)");
            ps.setInt(1, userId);
            ps.setInt(2, bookBorrowInfoId);
            ps.executeUpdate();

        } catch (Exception e) {
            print(e);
        }
    }

}
