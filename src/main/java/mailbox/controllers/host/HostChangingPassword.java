package mailbox.controllers.host;

import com.jfoenix.controls.JFXPasswordField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mailbox.controllers.host.roomAndResident.HostAdd;
import mailbox.controllers.host.roomAndResident.HostRoom;
import mailbox.controllers.host.stock.HostStock;
import mailbox.models.userAccount.ResidentUsers;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.userAccount.*;
import mailbox.services.interfaceClass.FileData;
import mailbox.services.implementation.UsersFileData;

import java.io.IOException;
import java.util.Optional;

public class HostChangingPassword {
    @FXML
    private JFXPasswordField oldPasswordField, newPasswordField, confirmPasswordField;
    @FXML
    private Label nameLabel;

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

    public void initialize(){
        Platform.runLater(() -> {
            nameLabel.setText(currentHost.getName());
        });
    }

    @FXML
    public void handleHostAddBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_add.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostAdd host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleHostRoomBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_room.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostRoom host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleHostStockBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_stock.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostStock host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleHostChangingPasswordBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_changing_password.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostChangingPassword host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleHostSoutBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_login.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostLogin host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleConfirmBtn(ActionEvent event) throws IOException{
        Button confirmBtn = (Button) event.getSource();
        if(newPasswordField.getText().contains(",") || confirmPasswordField.getText().contains(",")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setTitle("Failed...");
            alert.setHeaderText("");
            alert.setContentText("Can not use comma in information text.");
            alert.showAndWait();
        }else {
            if (hostLists.checkCurrentPassword(oldPasswordField.getText(), this.currentHost) &&
                    (newPasswordField.getText().equals(confirmPasswordField.getText())) &&
                    !(newPasswordField.getText().equals(oldPasswordField.getText())) &&
                    !oldPasswordField.getText().equals("") &&
                    !newPasswordField.getText().equals("") &&
                    !confirmPasswordField.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setGraphic(new ImageView("images/warning.png"));
                alert.setTitle("Confirmation.");
                alert.setHeaderText("");
                alert.setContentText("Are you sure?");
                Optional<ButtonType> confirmation = alert.showAndWait();
                if (confirmation.get() == ButtonType.OK) {
                    allLists.getUserByUsername(currentHost.getUsername()).setPassword(newPasswordField.getText());
                    usersData = new UsersFileData("data", "users.csv");
                    usersData.setList(allLists);
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setGraphic(new ImageView("images/confirm.png"));
                    success.setHeaderText("");
                    success.setTitle("Success.");
                    success.setContentText("Your password has been changed successfully!");
                    success.showAndWait();
                    Stage stage = (Stage) confirmBtn.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_login.fxml"));
                    stage.setScene(new Scene(loader.load(), 800, 600));
                    HostLogin host = loader.getController();
                    host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

                    stage.show();
                }
            } else if (oldPasswordField.getText().equals("") || newPasswordField.getText().equals("") || confirmPasswordField.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setGraphic(new ImageView("images/cancel.png"));
                alert.setHeaderText("");
                alert.setTitle("Please Try Again...");
                alert.setContentText("The password cannot be blank.");
                alert.showAndWait();
            } else if (!oldPasswordField.getText().equals(this.currentHost.getPassword())) { //oldPass != currentPass
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setGraphic(new ImageView("images/cancel.png"));
                alert.setHeaderText("");
                alert.setTitle("Please Try Again...");
                alert.setContentText("The old password doesn't match.");
                alert.showAndWait();
            } else if (newPasswordField.getText().equals(oldPasswordField.getText())) { //newPass = oldPass
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setGraphic(new ImageView("images/cancel.png"));
                alert.setHeaderText("");
                alert.setTitle("Please Try Again...");
                alert.setContentText("the new password must be different from the old password.");
                alert.showAndWait();
            } else if (!newPasswordField.getText().equals(confirmPasswordField.getText())) { //newPass != ConfirmPass
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setGraphic(new ImageView("images/cancel.png"));
                alert.setHeaderText("");
                alert.setTitle("Please Try Again...");
                alert.setContentText("The new password doesn't match.");
                alert.showAndWait();
            }
        }
    }
}
