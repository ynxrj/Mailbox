package mailbox.models.userAccount;

import java.util.ArrayList;

public class HostUsers extends Users{
    private int blockNum;
    private String surname;
    private String phone;
    private int age;
    private String status;
    private String date;

    public HostUsers(String name, String surname, String phone, int age, String username, String password, String status, int blockNum, String date){
        super(username, password, name);
        this.surname = surname;
        this.phone = phone;
        this.age = age;
        this.status = status;
        this.blockNum = blockNum;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public int getBlockNum() {
        return blockNum;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBlockNum(int blockNum) {
        this.blockNum = blockNum;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }
}
