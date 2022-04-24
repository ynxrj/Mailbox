package mailbox.controllers.host.roomAndResident;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mailbox.controllers.host.HostChangingPassword;
import mailbox.controllers.host.HostLogin;
import mailbox.controllers.host.stock.HostStock;
import mailbox.models.userAccount.ResidentUsers;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.Room;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.userAccount.*;
import mailbox.services.interfaceClass.FileData;
import mailbox.services.implementation.RoomFileData;
import mailbox.services.implementation.UsersFileData;

import java.io.*;
import java.util.Optional;

public class HostAdd {

    @FXML
    private Label nameLabel;
    @FXML
    private JFXTextField name, surname, phone, username;
    @FXML
    private JFXComboBox<String> roomNum;
    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private FileData roomData, usersData;
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
            nameLabel.setText(currentHost.getName());
            roomData = new RoomFileData("data", "rooms.csv");
            roomLists = (RoomLists) roomData.getList();
            for (Room room: roomLists.getLists()) {
                if(room.getRemaining() > 0) {
                    roomNum.getItems().add(room.getRoomType() + " - " + room.getRoomNumber() + " - Remaining : " + room.getRemaining());
                }
            }
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
    public void handleHostResidentsListsBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_residents_list.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostResidentsList host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleAddBtn(ActionEvent event) throws IOException {
        if (name.getText().contains(",") || surname.getText().contains(",") || phone.getText().contains(",") || username.getText().contains(",")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setTitle("Failed...");
            alert.setHeaderText("");
            alert.setContentText("Can not use comma in information text.");
            alert.showAndWait();
        } else {
            if (name.getText().equals("") || surname.getText().equals("")
                    || phone.getText().equals("") || roomNum.getValue() == null || username.getText().equals("")) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setGraphic(new ImageView("images/cancel.png"));
                error.setHeaderText("");
                error.setTitle("Failed");
                error.setContentText("Text cannot be blank.");
                error.showAndWait();
            } else if (allLists.checkUsername(username.getText())) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setGraphic(new ImageView("images/cancel.png"));
                error.setHeaderText("");
                error.setTitle("Failed");
                error.setContentText("Username already exists.");
                username.clear();
                error.showAndWait();
            } else if (roomLists.getRoomInAddByRoomNumInComboBox(roomNum.getValue()) == null) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setGraphic(new ImageView("images/cancel.png"));
                error.setHeaderText("");
                error.setTitle("Failed");
                error.setContentText("Room not found.");
                roomNum.getSelectionModel().clearSelection();
                error.showAndWait();
            } else if (roomLists.getRoomInAddByRoomNumInComboBox(roomNum.getValue()) != null && roomLists.getRoomInAddByRoomNumInComboBox(roomNum.getValue()).getRemaining() <= 0) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setGraphic(new ImageView("images/cancel.png"));
                error.setHeaderText("");
                error.setTitle("Sorry...");
                error.setContentText("This room is not available.");
                roomNum.getSelectionModel().clearSelection();
                error.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setGraphic(new ImageView("images/warning.png"));
                alert.setTitle("Confirmation.");
                alert.setHeaderText("");
                alert.setContentText("Are you sure?");
                Optional<ButtonType> confirmation = alert.showAndWait();
                if (confirmation.get() == ButtonType.OK) {
                    String[] roomNumber = roomNum.getValue().split(" - ");
                    ResidentUsers newResidentUsers = new ResidentUsers(name.getText(), surname.getText(), phone.getText(), roomNumber[1].trim(), roomNumber[0].trim(), username.getText(), "null");
                    residentsList.addResident(newResidentUsers);
                    allLists.addUsers(newResidentUsers);
                    usersData = new UsersFileData("data", "users.csv");
                    usersData.setList(allLists);
                    roomLists.setAddMaxByRoomNum(roomNumber[1]);
                    roomData = new RoomFileData("data", "rooms.csv");
                    roomData.setList(roomLists);
                    ;
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setGraphic(new ImageView("images/confirm.png"));
                    success.setHeaderText("");
                    success.setTitle("Success");
                    success.setContentText(name.getText() + " has been added to room " + roomNumber[1]);
                    success.showAndWait();

                    Button hostBtn = (Button) event.getSource();
                    Stage stage = (Stage) hostBtn.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_add.fxml"));
                    stage.setScene(new Scene(loader.load(), 800, 600));
                    HostAdd host = loader.getController();
                    host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
                    stage.show();
                }
            }
        }
    }
}
