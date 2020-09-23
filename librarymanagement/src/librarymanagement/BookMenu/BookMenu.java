package librarymanagement.BookMenu;

import LibraryDatabase.LibraryDatabase;
import java.util.Calendar;
import librarymanagement.Book.Book;
import librarymanagement.BookBorrowInfo.BookBorrowInfo;
import librarymanagement.library.Library;
import librarymanagement.user.User;

public class BookMenu extends Library {
    final Book book;
    User user;

    public BookMenu(Book book, User user) {
        this.book = book;
        this.user = user;
    }

    public BookMenu(Book book) {
        this.book = book;
    }

    public void showUserMenu() {
        boolean continueMenuLoop = true;
        while (continueMenuLoop) {
            System.out.println("#################################################");
            System.out.println("1. Lend Book");
            System.out.println("2. Check Availability");
//            System.out.println("3. Show currently borrowed List");
            System.out.println("0. Exit");
            System.out.println("#################################################");
            int choice = Integer.parseInt(in.nextLine());
            switch (choice) {
                case 1:
                    lendBookForUser();
                    break;
                case 2:
                    System.out.println("Book Avaliablity : " + new LibraryDatabase().isBoolAvailableToBorrow(book.getBookId()));
                    break;
//                case 3:
//                    showCurrentBorrowedList();
//                    break;
                case 0:
                    continueMenuLoop = false;
                    break;

                default:
                    System.out.println("Invalid Choice !");
                    break;
            }

        }
    }

    public void showCurrentBorrowedList() {
        System.out.println("\n\nborrowed List: ");
        System.out.println(book.getIssueInfoList() + "\n\n");
    }

    public void updateWaitList() {
        LibraryDatabase database = new LibraryDatabase();

        if (database.isWaitListEmpty(book.getBookId()))
            return;
        Calendar calendar = Calendar.getInstance();
        String issueDate = getDateFormatString(calendar);
        calendar.add(Calendar.DATE, 7);
        String deadlineDate = getDateFormatString(calendar);
        int user1 = database.fetchAndDeleteTheMaxPriorityUserFromWaitList(book.getBookId());
        database.addToBookBorrowInfoTable(user1, book.getBookId(), issueDate , deadlineDate);
        database.addToUserUpdates(user1, book.getBookId());
            }

    public void returnBook(BookBorrowInfo borrowInfo) {
        borrowInfo.setActualBookReturnDate();
//        book.clearBorrowedBookinfoId(id);
//        User user = allRegisteredUsers.get(borrowInfo.getUserId());
//        user.removeBorrowedBook(id);
        LibraryDatabase database = new LibraryDatabase();
        database.clearBookBorrowInfoById(borrowInfo.getId());
        System.out.println("Book successfully returned !\n");
        updateWaitList();
        if (borrowInfo.getActualReturnDate().after(borrowInfo.getReturnDate())) {
            database.decreaseVipPointsOfUser(borrowInfo.getUserId());
            System.out.println("Vip points decreased due to late submission !\n\n");
        }
        else
        {
            database.IncreaseVipPointsOfUser(borrowInfo.getUserId());
            System.out.println("Vip points Awarded due to timely submission !\n\n");
        }                
    }

    private void lendBookForUser() {
         LibraryDatabase database =new LibraryDatabase();
        if (!database.isBoolAvailableToBorrow(book.getBookId())) {
            System.out.println("Sorry, The Book is Not available ! ");

            boolean continueLoop = true;
            while (continueLoop) {
                System.out.println("Would you like to be added to waitlist of the Book ? ");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int choice = Integer.parseInt(in.nextLine());
                switch (choice) {
                    case 1:
                        addToWaitList();
                    case 2:
                        continueLoop = false;
                        break;
                    default:
                        System.out.println("Invalid Choice !");
                        break;
                }
            }
            return;
        }
        Calendar calendar = Calendar.getInstance();
        String issueDate = getDateFormatString(calendar);
        calendar.add(Calendar.DATE, 7);
        String deadlineDate = getDateFormatString(calendar);
        int id = database.addToBookBorrowInfoTable(user.getId(), book.getBookId(), issueDate, deadlineDate);
        System.out.println("Book successfully issued to you !\n");
        System.out.println("Issue Details are as follows :");
        printBookIssueDetails(new BookBorrowInfo(id, user.getId(), book.getBookId()));

    }

    private void addToWaitList() {
        LibraryDatabase database = new LibraryDatabase();
        int checkPriorWaitListindex = database.getWaitListposition(user.getId(), book.getBookId());
        if (checkPriorWaitListindex != -1) {
            System.out.println("You are already in the waitlist at : " + checkPriorWaitListindex);
            return;
        }
        database.addToWaitList(user.getId(), book.getBookId(), database.getPriorityLevelFromUserTable(user.getId()));
        int position = database.getWaitListposition(user.getId(), book.getBookId());
        System.out.println("successfully added to book waitlist !");
        System.out.println("Your waitlist Number is : " + position);                
    }

}