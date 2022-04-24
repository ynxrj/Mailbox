package mailbox.controllers.host.roomAndResident;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

import java.io.IOException;
import java.util.Optional;

public class HostResidentsList {
    @FXML
    private Button removeBtn;
    @FXML
    private Label nameLabel, resName, resSurname, resRoomNum;
    @FXML
    private TableView<ResidentUsers> residentTable;
    private ObservableList<ResidentUsers> residentUsersObservableList;
    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private FileData roomData, usersData;
    private AllUsersLists allLists;
    private RoomLists roomLists;
    private ResidentUsers selectedResidentUsers;
    private ResidentsList residentsList;

    public void setAllData(Users adminUsers, AdminLists adminLists, AllUsersLists allLists, HostUsers hostUsers, HostLists hostLists, HostUsers currentHost, RoomLists roomLists, ResidentsList residentsList) {
        this.adminUsers = adminUsers;
        this.adminLists = adminLists;
        this.allLists = allLists;
        this.hostUsers = hostUsers;
        this.hostLists = hostLists;
        this.currentHost = currentHost;
        this.residentsList = residentsList;
        this.roomLists = roomLists;
    }

    public void initialize() {
        removeBtn.setDisable(true);
        Platform.runLater(() -> {
            nameLabel.setText(currentHost.getName());
            showResidentsLists();
            showSearch();
        });

        residentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedResident(newValue);
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
    public void handleHostRoomBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_room.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostRoom host = loader.getController();
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
    public void handleHostStockBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_stock.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostStock host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleHostChangingPasswordBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_changing_password.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostChangingPassword host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleHostSoutBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_login.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostLogin host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleBackBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_add.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostAdd host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    public void showResidentsLists() {
        residentUsersObservableList = FXCollections.observableArrayList(residentsList.getResidentslist());
        residentTable.setItems(residentUsersObservableList);
        TableColumn roomNumber = new TableColumn("Room");
        TableColumn name = new TableColumn("Name");
        TableColumn surname = new TableColumn("Surname");
        TableColumn phone = new TableColumn("Phone");
        TableColumn roomType = new TableColumn("Room Type");

        roomNumber.setCellValueFactory(
                new PropertyValueFactory<ResidentUsers, String>("roomNumber")
        );
        name.setCellValueFactory(
                new PropertyValueFactory<Room, String>("name")
        );
        surname.setCellValueFactory(
                new PropertyValueFactory<Room, String>("surname")
        );
        phone.setCellValueFactory(
                new PropertyValueFactory<Room, String>("phone")
        );
        roomType.setCellValueFactory(
                new PropertyValueFactory<Room, String>("roomType")
        );

        name.setSortType(TableColumn.SortType.ASCENDING);
        residentTable.getColumns().addAll(name, surname, phone, roomNumber, roomType);
        residentTable.getSortOrder().add(name);
    }

    private void showSelectedResident(ResidentUsers residentUsers) {
        selectedResidentUsers = residentUsers;
        resName.setText(residentUsers.getName());
        resSurname.setText(residentUsers.getSurname());
        resRoomNum.setText(residentUsers.getRoomNumber());
        removeBtn.setDisable(false);
    }

    private void clearSelectedResident() {
        selectedResidentUsers = null;
        resName.setText("-");
        resSurname.setText("-");
        resRoomNum.setText("-");
        removeBtn.setDisable(true);
    }

    @FXML
    private TextField searchText;
    public void showSearch(){
        ObservableList observableList = residentTable.getItems();
        searchText.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                residentTable.setItems(observableList);
            }
            String search = newValue.toLowerCase();
            ObservableList<ResidentUsers> showSearchList = FXCollections.observableArrayList();
            long column = residentTable.getColumns().size();
            for (int i = 0; i < residentTable.getItems().size(); i++) {
                for (int j = 0; j < column; j++) {
                    String residentResult = "" + residentTable.getColumns().get(j).getCellData(i);
                    if (residentResult.toLowerCase().contains(search)) {
                        showSearchList.add(residentTable.getItems().get(i));
                        break;
                    }
                }
            }
            residentTable.setItems(showSearchList);
        });
    }

    @FXML
    public void handleRemoveBtn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setGraphic(new ImageView("images/warning.png"));
        alert.setTitle("Confirmation.");
        alert.setHeaderText("");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.get() == ButtonType.OK) {
            String text = resName.getText() + " has been remove from room " + residentsList.getResidents(resName.getText(),
                    resSurname.getText()).getRoomNumber();
            roomLists.setRemoveMaxByRoomNum(residentsList.getResidents(resName.getText(),
                    resSurname.getText()).getRoomNumber());
            residentsList.removeResident(selectedResidentUsers);
            allLists.removeUser(selectedResidentUsers);
            usersData = new UsersFileData("data", "users.csv");
            usersData.setList(allLists);
            roomData = new RoomFileData("data", "rooms.csv");
            roomData.setList(roomLists);
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setGraphic(new ImageView("images/confirm.png"));
            success.setHeaderText("");
            success.setTitle("Success");
            success.setContentText(text);
            success.showAndWait();
            clearSelectedResident();
            Button hostBtn = (Button) event.getSource();
            Stage stage = (Stage) hostBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_residents_list.fxml"));
            stage.setScene(new Scene(loader.load(), 800, 600));
            HostResidentsList host = loader.getController();
            host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
            stage.show();
        }
    }
}
