package librarymanagement.BookBorrowInfo;

import java.util.Calendar;
// import librarymanagement.library.Library;

public class BookBorrowInfo {

    final int id;
    final int userId;
    final int bookId;
    final Calendar issueCalendarDate;
    final Calendar returnDeadlineDate;
    Calendar actualReturnDate;

    public BookBorrowInfo(int id, int userId, int bookId) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        issueCalendarDate = Calendar.getInstance();
        returnDeadlineDate = Calendar.getInstance();
        returnDeadlineDate.add(Calendar.DATE, 7);
    }

    public BookBorrowInfo(int id, int userId, int bookId, Calendar issue, Calendar deadline, Calendar returnDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        issueCalendarDate = issue;
        returnDeadlineDate = deadline;
        actualReturnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public Calendar getIssueDate() {
        return issueCalendarDate;
    }

    public Calendar getReturnDate() {
        return returnDeadlineDate;
    }

    public void setActualBookReturnDate() {
        actualReturnDate = Calendar.getInstance();
    }

    public Calendar getActualReturnDate() {
        return actualReturnDate;
    }
}
