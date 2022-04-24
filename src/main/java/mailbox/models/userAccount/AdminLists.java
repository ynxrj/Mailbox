package mailbox.models.userAccount;

import java.util.ArrayList;

public class AdminLists {
    private ArrayList<Users> adminList;

    public AdminLists(){
        adminList = new ArrayList<>();
    }


    public void addAdmin(Users adminUsers){
        adminList.add(adminUsers);
    }

    @Override
    public String toString() {
        return "AdminLists{" +
                "adminList=" + adminList +
                '}';
    }

    public ArrayList<Users> getAdminList() {
        return adminList;
    }
}
