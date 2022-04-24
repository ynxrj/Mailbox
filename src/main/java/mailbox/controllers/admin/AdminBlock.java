package mailbox.controllers.admin;


import com.jfoenix.controls.JFXTextField;
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
import mailbox.models.userAccount.ResidentUsers;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.userAccount.*;
import mailbox.services.interfaceClass.FileData;
import mailbox.services.implementation.UsersFileData;
import java.io.IOException;
import java.util.Optional;

public class AdminBlock {
    @FXML
    private Label nameLabel;
    @FXML
    JFXTextField blockField, unblockField;

    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private FileData usersData;
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

    public void initialize(){
        Platform.runLater(() -> {
            nameLabel.setText(adminUsers.getName());
        });
    }

    @FXML
    public void handleAdminAddBtn(ActionEvent event) throws IOException{
        Button adminAddBtn = (Button) event.getSource();
        Stage stage = (Stage) adminAddBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_add.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        AdminAdd admin = loader.getController();
        admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleAdminBlockBtn(ActionEvent event) throws IOException{
        Button adminBlockBtn = (Button) event.getSource();
        Stage stage = (Stage) adminBlockBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_block.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        AdminBlock admin = loader.getController();
        admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleAdminHostlistsBtn(ActionEvent event) throws IOException{
        Button adminHostListsBtn = (Button) event.getSource();
        Stage stage = (Stage) adminHostListsBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_hosts_list.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        AdminHostsList admin = loader.getController();
        admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleAdminChangePasswordBtn(ActionEvent event) throws IOException{
        Button adminChangPassBtn = (Button) event.getSource();
        Stage stage = (Stage) adminChangPassBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_changing_password.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        AdminChangingPassword admin = loader.getController();
        admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleAdminSoutBtn(ActionEvent event) throws IOException{
        Button adminSoutBtn = (Button) event.getSource();
        Stage stage = (Stage) adminSoutBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_login.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        AdminLogin admin = loader.getController();
        admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleBlockBtnOnAction(ActionEvent event) throws IOException{
        if(hostLists.checkUsername(blockField.getText()) &&
                !hostLists.checkBlock(hostLists.getHost(blockField.getText()))) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setGraphic(new ImageView("images/warning.png"));
            alert.setTitle("Confirmation.");
            alert.setHeaderText("");
            alert.setContentText("Are you sure?");
            Optional<ButtonType> confirmation = alert.showAndWait();
            if (confirmation.get() == ButtonType.OK) {
                hostLists.setBlock(blockField.getText());
                usersData = new UsersFileData("data", "users.csv");
                usersData.setList(allLists);
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setGraphic(new ImageView("images/confirm.png"));
                success.setHeaderText("");
                success.setTitle("Success.");
                success.setContentText("User " +hostLists.getHost(blockField.getText()).getUsername()+ " has been blocked.");
                success.showAndWait();
                Button adminBlockBtn = (Button) event.getSource();
                Stage stage = (Stage) adminBlockBtn.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_block.fxml"));
                stage.setScene(new Scene(loader.load(), 800, 600));
                AdminBlock admin = loader.getController();
                admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
                stage.show();
            }
        }else if (hostLists.checkBlock(hostLists.getHost(blockField.getText()))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setHeaderText("");
            alert.setTitle("Please Try Again...");
            alert.setContentText("This user already blocked");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setHeaderText("");
            alert.setTitle("Please Try Again...");
            alert.setContentText("Username not found.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleUnBlockBtnOnAction(ActionEvent event) throws IOException{
        if(hostLists.checkUsername(unblockField.getText()) &&
                hostLists.checkBlock(hostLists.getHost(unblockField.getText()))) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setGraphic(new ImageView("images/warning.png"));
            alert.setTitle("Confirmation.");
            alert.setHeaderText("");
            alert.setContentText("Are you sure?");
            Optional<ButtonType> confirmation = alert.showAndWait();
            if (confirmation.get() == ButtonType.OK) {
                hostLists.setUnBlock(unblockField.getText());
                hostLists.getHost(unblockField.getText()).setBlockNum(0);
                usersData = new UsersFileData("data", "users.csv");
                usersData.setList(allLists);
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setGraphic(new ImageView("images/confirm.png"));
                success.setHeaderText("");
                success.setTitle("Success.");
                success.setContentText("User " + hostLists.getHost(unblockField.getText()).getUsername() + " has been unblocked.");
                success.showAndWait();
                Button adminBlockBtn = (Button) event.getSource();
                Stage stage = (Stage) adminBlockBtn.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_block.fxml"));
                stage.setScene(new Scene(loader.load(), 800, 600));
                AdminBlock admin = loader.getController();
                admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
                stage.show();
            }
        }else if (!hostLists.checkBlock(hostLists.getHost(unblockField.getText())) && hostLists.checkUsername(unblockField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setHeaderText("");
            alert.setTitle("Please Try Again...");
            alert.setContentText("This user already unblocked");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setHeaderText("");
            alert.setTitle("Please Try Again...");
            alert.setContentText("Username not found.");
            alert.showAndWait();
        }
    }

}
