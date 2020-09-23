package librarymanagement.Book;

import java.util.*;

import librarymanagement.WaitObject.WaitObject;

class UserQueueComparator implements Comparator<WaitObject> {

    @Override
    public int compare(final WaitObject x, final WaitObject y) {
        if (x.priorty < y.priorty) {
            return 1;
        }
        return -1;
    }

}

public class Book {
    private int bookId;
    private String bookName;
    private String authorName;
    private int noOfPages;
    private Calendar dateOfPublish;
    private int bookCopiesCount;
    private Vector<Integer> borrowedBookinfoIds = new Vector<>();
    private Queue<Integer> bookIssueHistory = new LinkedList<>();

    private PriorityQueue<WaitObject> waitList = new PriorityQueue<WaitObject>(new UserQueueComparator());

    public Book(int bookId, String bookName, String authorName, int noOfPages, Calendar dateOfPublish,
            int bookCopiesCount) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.noOfPages = noOfPages;
        this.dateOfPublish = dateOfPublish;
        this.bookCopiesCount = bookCopiesCount;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getNoOfPages() {
        return noOfPages;
    }

    public Calendar getPublishDate() {
        return dateOfPublish;
    }

    public void addBorrowedBookinfoIds(int id) {
        borrowedBookinfoIds.add(id);
        bookIssueHistory.add(id);

    }

    public void clearBorrowedBookinfoId(int id) {
        borrowedBookinfoIds.remove((Object) id);
    }

    public void increaseCopyCount(int i) {
        bookCopiesCount += i;
    }

    public boolean iscopyAvailable() {
        return borrowedBookinfoIds.size() < bookCopiesCount;
    }

    public int copyCount() {
        return bookCopiesCount - borrowedBookinfoIds.size();
    }

    public Vector<Integer> getIssueInfoList() {
        return (Vector<Integer>) borrowedBookinfoIds.clone();
    }

    public LinkedList<Integer> getIssueHistory() {
        return new LinkedList<Integer>(bookIssueHistory);
    }

    public int addToWaitlist(int userId, int priorty) {
        waitList.add(new WaitObject(userId, priorty));
        PriorityQueue<WaitObject> waitListClone = new PriorityQueue<WaitObject>(waitList);
        int position = -1;
        for (int i = 0; i < waitList.size(); i++) {
            WaitObject w = waitListClone.poll();
            if (w.userId == userId) {
                position = i;
                break;
            }
        }

        return position + 1;
    }

    public int checkIfUserPresentInWaitList(int userId) {
        PriorityQueue<WaitObject> waitListClone = new PriorityQueue<WaitObject>(waitList);
        int position = -1;
        for (int i = 0; i < waitList.size(); i++) {
            WaitObject w = waitListClone.poll();
            if (w.userId == userId) {
                position = i;
                break;
            }
        }
        return position + 1;
    }
    public int waitListLength() {
        return waitList.size();
    }

    public int popHeadOftheWaitList() {
        return waitList.poll().userId;
    }
}