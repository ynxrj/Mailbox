package mailbox.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import mailbox.controllers.admin.AdminLogin;
import mailbox.controllers.host.HostLogin;
import mailbox.controllers.resident.ResidentLogin;
import mailbox.models.userAccount.ResidentUsers;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.userAccount.*;
import mailbox.services.interfaceClass.FileData;
import mailbox.services.implementation.UsersFileData;

import java.io.IOException;

public class MailboxSelectLogin {

    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private FileData usersData;
    private AllUsersLists allLists;
    private RoomLists roomLists;
    private ResidentUsers residentUsers;
    private ResidentsList residentsList;

    public void setAllData(Users adminUsers, AdminLists adminLists, AllUsersLists allLists, HostUsers hostUsers, HostLists hostLists, HostUsers currentHost, RoomLists roomLists , ResidentsList residentsList){
        this.adminUsers = adminUsers;
        this.adminLists = adminLists;
        this.allLists = allLists;
        this.hostUsers = hostUsers;
        this.hostLists = hostLists;
        this.currentHost = currentHost;
        this.residentsList = residentsList;
        this.roomLists = roomLists;
    }

    @FXML
    public void initialize() throws IOException {
        allLists = new AllUsersLists();
        hostLists = new HostLists();
        adminLists = new AdminLists();
        residentsList = new ResidentsList();
        usersData = new UsersFileData("data", "users.csv");
        allLists = (AllUsersLists) usersData.getList(); //csv to arraylist


        for (Users users : allLists.getLists()) {
            if(users instanceof HostUsers) {
                hostLists.addHost((HostUsers) users);
            }else if (users instanceof ResidentUsers){
                residentsList.addResident((ResidentUsers) users);
            }else {//(users instanceof Users){
                adminLists.addAdmin(users);
                adminUsers = users; //only 1 admin users
            }
        }
    }

    @FXML
    public void handleAboutMeBtn(ActionEvent event) throws IOException {
        Button aboutme = (Button) event.getSource();
        Stage stage = (Stage) aboutme.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/aboutme.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }

    @FXML
    public void handleAdminBtn(ActionEvent event) throws IOException{
        Button admin = (Button) event.getSource();
        Stage stage = (Stage) admin.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_login.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        AdminLogin adminLogin = loader.getController();
        adminLogin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleHostBtn(ActionEvent event) throws IOException{
        Button host = (Button) event.getSource();
        Stage stage = (Stage) host.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_login.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostLogin hostLogin = loader.getController();
        hostLogin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleResidentBtn(ActionEvent event) throws IOException{
        Button host = (Button) event.getSource();
        Stage stage = (Stage) host.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_login.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        ResidentLogin residentLogin = loader.getController();
        residentLogin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleInfoBtn(ActionEvent event) throws IOException{
        Button host = (Button) event.getSource();
        Stage stage = (Stage) host.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/info/mailbox_info.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }

}
