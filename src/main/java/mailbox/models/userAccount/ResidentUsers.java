package mailbox.models.userAccount;

public class ResidentUsers extends Users{
    private String surname, phone, roomNumber, roomType;

    public ResidentUsers(String name, String surname, String phone, String roomNumber, String roomType, String username, String password) {
        super(username, password, name);
        this.surname = surname;
        this.phone = phone;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getRoomType() {
        return roomType;
    }
}
