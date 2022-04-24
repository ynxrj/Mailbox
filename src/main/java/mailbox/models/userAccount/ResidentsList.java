package mailbox.models.userAccount;

import java.util.ArrayList;

public class ResidentsList {
    private ArrayList<ResidentUsers> residentslist;

    public ResidentsList() {
        residentslist = new ArrayList<>();
    }

    public ArrayList<ResidentUsers> getResidentslist() {
        return residentslist;
    }

    public void addResident(ResidentUsers residentUsers){
        residentslist.add(residentUsers);
    }

    public ResidentUsers getResidents(String name, String surname) {
        for(ResidentUsers residentUsers : residentslist){
            if(name.equals(residentUsers.getName()) && surname.equals(residentUsers.getSurname())){
                return residentUsers;
            }
        }
        return null;
    }

    public String checkResidentByName(String name, String surname){
        for(ResidentUsers residentUsers : residentslist){
            if(name.equals(residentUsers.getName()) && surname.equals(residentUsers.getSurname())){
                return residentUsers.getRoomNumber();
            }
        }
        return null;
    }

    public ResidentUsers getCurrentByUserName(String username){
        for(ResidentUsers residentUsers : residentslist){
            if(username.equals(residentUsers.getUsername())){
                return residentUsers;
            }
        }
        return null;
    }

    public boolean checkRegisterAgain(String username){
        for(ResidentUsers residentUsers : residentslist){
            if(username.equals(residentUsers.getUsername())){
                if(residentUsers.getPassword().equals("null")){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkLoginResident(String username, String password){
        for(ResidentUsers residentUsers : residentslist){
            if(username.equals(residentUsers.getUsername()) && password.equals(residentUsers.getPassword())){
                return true;
            }
        }
        return false;
    }

    public boolean checkRegister(String name, String surname, String username){
        for(ResidentUsers residentUsers : residentslist){
            if(username.equals(residentUsers.getUsername()) && name.equals(residentUsers.getName()) && surname.equals(residentUsers.getSurname())){
                return true;
            }
        }
        return false;
    }

    public boolean checkCurrentPassword(String password, ResidentUsers currentResident) {
        return currentResident.getPassword().equals(password);
    }

    public void removeResident(ResidentUsers residentUsers){
        residentslist.remove(residentUsers);
    }

    @Override
    public String toString() {
        return "ResidentLists{" +
                "residentslist=" + residentslist +
                '}';
    }

}
