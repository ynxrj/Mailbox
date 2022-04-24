package mailbox.controllers.host;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mailbox.controllers.MailboxSelectLogin;
import mailbox.controllers.host.roomAndResident.HostAdd;
import mailbox.models.userAccount.ResidentUsers;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.userAccount.*;
import mailbox.services.interfaceClass.FileData;
import mailbox.services.implementation.UsersFileData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HostLogin {
    @FXML
    private JFXTextField textField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private Label name;

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
    public void handleHostLoginBtn(ActionEvent event) throws IOException {
        if (hostLists.checkUsername(textField.getText()) && hostLists.checkPassword(passwordField.getText()) &&
                !hostLists.checkBlock(hostLists.getHost(textField.getText()))){
            currentHost = hostLists.getCurrentHost(textField.getText()); //username ไม่ซ้ำ
            String loginTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
            currentHost.setDate(loginTime);

            usersData = new UsersFileData("data", "users.csv");
            usersData.setList(allLists);

            Button hostLoginBtn = (Button) event.getSource();
            Stage stage = (Stage) hostLoginBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_add.fxml"));
            stage.setScene(new Scene(loader.load(), 800, 600));
            HostAdd host = loader.getController();
            host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

            stage.show();
        }else if (!hostLists.checkUsername(textField.getText()) || !hostLists.checkPassword(passwordField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setHeaderText("");
            alert.setTitle("Please Try Again...");
            alert.setContentText("Incorrect Username or Password.");
            alert.showAndWait();
        }else if(hostLists.checkBlock(hostLists.getHost(textField.getText()))){
            hostLists.addCountBlock(hostLists.getHost(textField.getText()));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setHeaderText("");
            alert.setTitle("Sorry...");
            alert.setContentText("User " + hostLists.getHost(textField.getText()).getUsername() + " has been blocked\n"
                    + "Login attempts : " + hostLists.getHost(textField.getText()).getBlockNum());
            usersData = new UsersFileData("data", "users.csv");
            usersData.setList(allLists);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleHostBackBtn(ActionEvent event) throws IOException{
        Button backBtn = (Button) event.getSource();
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mailbox_controller.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        MailboxSelectLogin controller = loader.getController();
        controller.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

}
