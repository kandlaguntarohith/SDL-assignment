package librarymanagement.BookMenu;

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
            System.out.println("3. Show currently borrowed List");
            System.out.println("4. Exit");
            System.out.println("#################################################");
            int choice = Integer.parseInt(in.nextLine());
            switch (choice) {
                case 1:
                    lendBookForUser();
                    break;
                case 2:
                    System.out.println("Book Avaliablity : " + book.iscopyAvailable());
                    break;
                case 3:
                    showCurrentBorrowedList();
                    break;
                case 4:
                    bookCollection.put(book.getBookId(), book);
                    if (user != null)
                        allRegisteredUsers.put(user.getId(), user);
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

        if (book.waitListLength() < 1)
            return;
        BookBorrowInfo borrowInfo = new BookBorrowInfo(bookInfoIdCount++, book.popHeadOftheWaitList(),
                book.getBookId());
        book.addBorrowedBookinfoIds(borrowInfo.getId());
        User u = allRegisteredUsers.get(borrowInfo.getUserId());
        u.addToBorrowedList(borrowInfo.getId());
        u.waitListUpdates(borrowInfo.getId());
        allRegisteredUsers.put(u.getId(), u);
        bookBorrowInfoData.put(borrowInfo.getId(), borrowInfo);
    }

    public void returnBook(int id) {

        BookBorrowInfo borrowInfo = bookBorrowInfoData.get(id);
        borrowInfo.setActualBookReturnDate();
        book.clearBorrowedBookinfoId(id);
        User user = allRegisteredUsers.get(borrowInfo.getUserId());
        user.removeBorrowedBook(id);
        System.out.println("Book successfully returned !\n");
        updateWaitList();
        if (borrowInfo.getActualReturnDate().after(borrowInfo.getReturnDate())) {
            user.decreaseVipPoints();
            System.out.println("Vip points decreased due to late submission !\n\n");
        }
        bookBorrowInfoData.put(borrowInfo.getId(), borrowInfo);
        bookCollection.put(book.getBookId(), book);
        allRegisteredUsers.put(user.getId(), user);

    }

    private void lendBookForUser() {
        if (!book.iscopyAvailable()) {
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
        int lendId = bookInfoIdCount++;
        final BookBorrowInfo lendInfo = new BookBorrowInfo(lendId, user.getId(), book.getBookId());
        bookBorrowInfoData.put(lendId, lendInfo);
        book.addBorrowedBookinfoIds(lendId);
        user.addToBorrowedList(lendId);
        bookCollection.put(book.getBookId(), book);
        if (user != null)
            allRegisteredUsers.put(user.getId(), user);
        System.out.println("Book successfully issued to you !\n");
        System.out.println("Issue Details are as follows :");
        printBookIssueDetails(lendInfo);

    }

    private void addToWaitList() {
        int checkPriorWaitListindex = book.checkIfUserPresentInWaitList(user.getId());
        if (checkPriorWaitListindex > 0) {
            System.out.println("You are already in the waitlist at : " + checkPriorWaitListindex);
            return;
        }
        int position = book.addToWaitlist(user.getId(), user.getVipLevel());
        System.out.println("successfully added to book waitlist !");
        System.out.println("Your waitlist Number is : " + position);
        allRegisteredUsers.put(user.getId(), user);
        bookCollection.put(book.getBookId(), book);

    }

}