package mailbox.models.userAccount;

import java.util.ArrayList;

public class HostLists {
    private ArrayList<HostUsers> list;

    public HostLists (){
        list = new ArrayList<>();
    }

    public void setBlock(String username){
        for (HostUsers hostUsers : list) {
            if(username.equals(hostUsers.getUsername())){
                hostUsers.setStatus("Blocked");
            }
        }
    }
    public void setUnBlock(String username){
        for (HostUsers hostUsers : list) {
            if(username.equals(hostUsers.getUsername())){
                hostUsers.setStatus("Authorize");
            }
        }
    }

    public boolean checkBlock(HostUsers hostUsers){
        if(hostUsers == null){
            return false;
        }
        return hostUsers.getStatus().equals("Blocked");
    }

    public boolean checkAdd(HostUsers hostUsers, Users adminUsers){
        if(hostUsers.getUsername().equals(adminUsers.getUsername())){
            return false;
        }
        for(HostUsers host : list){
            if(host.getUsername().equals(hostUsers.getUsername())){
                return false;
            }
        }
        return true;
    }

    public void addHost(HostUsers hostUsers){
        list.add(hostUsers);
    }

    public boolean checkUsername(String username){
        for(HostUsers host : list){
            if(host.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean checkPassword(String password){
        for(HostUsers host : list){
            if(host.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<HostUsers> getList() {
        return list;
    }

    public HostUsers getHost(String username){
        for(HostUsers host : list){
            if(host.getUsername().equals(username)){
                return host;
            }
        }
        return null;
    }

    public boolean checkCurrentPassword(String password, HostUsers currentHost){
        return currentHost.getPassword().equals(password);
    }

    public HostUsers getCurrentHost(String username) {
        for (HostUsers hostUsers : list) {
            if(username.equals(hostUsers.getUsername())){
                return hostUsers;
            }
        }
        return null;
    }

    public void addCountBlock(HostUsers hostUsers){
        hostUsers.setBlockNum(hostUsers.getBlockNum()+1);
    }
}
