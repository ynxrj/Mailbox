package mailbox.models.userAccount;

public class Users {
    private String name;
    private String username;
    private String password;

    public Users(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean checkUsername(String username){
        if(username.equals("")){
            return false;
        }
        return getUsername().equals(username);
    }
    public boolean checkPassword(String password){
        if(password.equals("")){
            return false;
        }
        return getPassword().equals(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{" + getClass() +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
