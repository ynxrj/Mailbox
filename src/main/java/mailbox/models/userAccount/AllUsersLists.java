package mailbox.models.userAccount;

import java.util.ArrayList;

public class AllUsersLists {
    private ArrayList<Users> users;

    public AllUsersLists() {
        users = new ArrayList<>();
    }

    public void addUsers(Users user) {
        users.add(user);
    }

    public Users getUserByUsername(String username){
        for(Users user : users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public boolean checkUsername(String username){
        for(Users user : users){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public void removeUser(Users user){
        users.remove(user);
    }

    public int getSize(){
        return users.size();
    }

    public ArrayList<Users> getLists() {
        return users;
    }

    @Override
    public String toString() {
        return "AllUsersLists{" +
                "users=" + users +
                '}';
    }
}
