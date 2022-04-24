package mailbox.controllers.admin;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mailbox.controllers.MailboxSelectLogin;
import mailbox.models.userAccount.ResidentUsers;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.userAccount.*;
import mailbox.services.interfaceClass.FileData;

import java.io.IOException;

public class AdminLogin {
    @FXML
    private JFXTextField textField;
    @FXML
    private JFXPasswordField passwordField;

    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private AllUsersLists allLists;
    private RoomLists roomLists;
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
    public void handleAdminLoginBtn(ActionEvent event) throws IOException{
        if (adminUsers.checkUsername(textField.getText()) && adminUsers.checkPassword(passwordField.getText())) {
            Button adminLoginBtn = (Button) event.getSource();
            Stage stage = (Stage) adminLoginBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_add.fxml"));
            stage.setScene(new Scene(loader.load(), 800, 600));
            AdminAdd admin = loader.getController();
            admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
            stage.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setHeaderText("");
            alert.setTitle("Please Try Again...");
            alert.setContentText("Incorrect Username or Password.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleAdminBackBtn(ActionEvent event) throws IOException{
        Button backBtn = (Button) event.getSource();
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mailbox_controller.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        MailboxSelectLogin controller = loader.getController();
        controller.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }


}
