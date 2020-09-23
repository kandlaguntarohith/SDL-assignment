package librarymanagement.user;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import librarymanagement.BookBorrowInfo.BookBorrowInfo;

public class User {

    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String mailId;
    private String password;
    private int vipLevel;
    private Vector<Integer> currentLendBooks;
//    private Queue<Integer> bookLendHistory;
    private Vector<Integer> waitListUpdates;

    public User(int id, String userName, String firstName, String lastName, String mailId, String password, int pri) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailId = mailId;
        this.password = password;
        this.userName = userName;
        vipLevel = pri;
        currentLendBooks = new Vector<Integer>();
//        bookLendHistory = new LinkedList<Integer>();
        waitListUpdates = new Vector<Integer>();
    }

    public void waitListUpdates(int info) {
        waitListUpdates.add(info);
    }

    public Vector<Integer> getUpdates() {
        return waitListUpdates;
    }

    public void clearUpdates() {
        waitListUpdates.clear();
    }

    public String getMailId() {
        return mailId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    private void raiseVipLevel() {
        vipLevel += 5;
    }

    public void addToBorrowedList(int id) {
        currentLendBooks.add(id);
//        bookLendHistory.add(id);
        raiseVipLevel();
    }

    public void decreaseVipPoints() {
        vipLevel -= 5;
    }

    public void removeBorrowedBook(int id) {
        currentLendBooks.remove((Object) id);
    }

    public Vector<Integer> getCurrentBorrowedList() {
        return (Vector<Integer>) currentLendBooks.clone();
    }

}
