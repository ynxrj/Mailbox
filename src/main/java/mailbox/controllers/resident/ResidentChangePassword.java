package mailbox.controllers.resident;

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
import mailbox.controllers.resident.stockList.ResidentReceivedLetter;
import mailbox.controllers.resident.stockList.ResidentStockList;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.storage.*;
import mailbox.models.userAccount.*;
import mailbox.services.implementation.UsersFileData;
import mailbox.services.interfaceClass.FileData;

import java.io.IOException;
import java.util.Optional;

public class ResidentChangePassword {

    @FXML
    private Label nameLabel;
    @FXML
    private JFXPasswordField oldPasswordField, newPasswordField, confirmPasswordField;
    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private AllUsersLists allLists;
    private RoomLists roomLists;
    private ResidentsList residentsList;
    private AllStockList stockList;
    private FileData usersData;
    private Letter selectedLetter;
    private ResidentUsers currentResident;

    public void setAllData(Users adminUsers, AdminLists adminLists, AllUsersLists allLists, HostUsers hostUsers, HostLists hostLists, HostUsers currentHost, RoomLists roomLists , ResidentsList residentsList, ResidentUsers currentResident){
        this.adminUsers = adminUsers;
        this.adminLists = adminLists;
        this.allLists = allLists;
        this.hostUsers = hostUsers;
        this.hostLists = hostLists;
        this.currentHost = currentHost;
        this.residentsList = residentsList;
        this.roomLists = roomLists;
        this.currentResident = currentResident;
    }

    public void initialize(){
        Platform.runLater(() -> {
            nameLabel.setText(currentResident.getName());
        });
    }

    @FXML
    public void handleResidentStockListsBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_stock_list.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        ResidentStockList residentStockList = loader.getController();
        residentStockList.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList, currentResident);
        stage.show();
    }


    @FXML
    public void handleResidentLogOutBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_login.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        ResidentLogin residentLogin = loader.getController();
        residentLogin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleResidentReceivedListsBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_received_letter.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        ResidentReceivedLetter residentReceivedLetter = loader.getController();
        residentReceivedLetter.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList, currentResident);
        stage.show();
    }

    @FXML
    public void handleChangingPasswordBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_change_password.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        ResidentChangePassword residentChangePassword = loader.getController();
        residentChangePassword.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList, currentResident);
        stage.show();
    }

    @FXML
    public void handleConfirmBtn(ActionEvent event) throws IOException {
        if(newPasswordField.getText().contains(",") || confirmPasswordField.getText().contains(",")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setTitle("Failed...");
            alert.setHeaderText("");
            alert.setContentText("Can not use comma in information text.");
            alert.showAndWait();
        }else {
            if (!newPasswordField.getText().equals("null")) {
                if (residentsList.checkCurrentPassword(oldPasswordField.getText(), this.currentResident) &&
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
                        allLists.getUserByUsername(currentResident.getUsername()).setPassword(newPasswordField.getText());
                        usersData = new UsersFileData("data", "users.csv");
                        usersData.setList(allLists);
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setGraphic(new ImageView("images/confirm.png"));
                        success.setHeaderText("");
                        success.setTitle("Success.");
                        success.setContentText("Your password has been changed successfully!");
                        success.showAndWait();
                        Button btn = (Button) event.getSource();
                        Stage stage = (Stage) btn.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_login.fxml"));
                        stage.setScene(new Scene(loader.load(), 800, 600));
                        ResidentLogin residentLogin = loader.getController();
                        residentLogin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
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
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setGraphic(new ImageView("images/cancel.png"));
                alert.setHeaderText("");
                alert.setTitle("Please Try Again...");
                alert.setContentText("Password can't be \"null\"");
                alert.showAndWait();
            }
        }
    }
}
