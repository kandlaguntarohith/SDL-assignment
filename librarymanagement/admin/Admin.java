package librarymanagement.admin;

public class Admin {
    private int adminId;
    private String adminName;
    private String firstName;
    private String lastName;
    private String mailId;
    private String password;
    private String phoneNo;

    public Admin(int adminId, String adminName, String firstName, String lastName, String mailId, String password) {
        this.adminId = adminId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailId = mailId;
        this.password = password;
        this.adminName = adminName;
    }

    public String getUserName() {
        return adminName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return adminId;
    }
}