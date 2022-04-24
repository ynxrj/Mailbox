package mailbox.controllers.admin;

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
import javafx.stage.Stage;
import mailbox.models.userAccount.ResidentUsers;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.userAccount.*;
import mailbox.services.interfaceClass.FileData;

import java.io.IOException;

public class AdminHostsList {
    @FXML
    private Label nameLabel;
    @FXML
    private TableView<HostUsers> hostTable;
    private ObservableList<HostUsers> hostUsersObservableList;
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

    public void initialize(){
        Platform.runLater(() -> {
            nameLabel.setText(adminUsers.getName());
            showHostData();
            showSearch();
        });
    }
    @FXML
    public void handleAdminAddBtn(ActionEvent event) throws IOException {
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

    public void showHostData(){
        hostUsersObservableList = FXCollections.observableArrayList(hostLists.getList());

        hostTable.setItems(hostUsersObservableList);
        TableColumn name = new TableColumn("Firstname");
        TableColumn surname = new TableColumn("Surname");
        TableColumn phone = new TableColumn("Phone");
        TableColumn username = new TableColumn("Username");
        TableColumn password = new TableColumn("Password");
        TableColumn status = new TableColumn("User status");
        TableColumn login = new TableColumn("Attempts");
        TableColumn date = new TableColumn("Latest access");

        name.setCellValueFactory(
                new PropertyValueFactory<HostUsers,String>("name")
        );
        surname.setCellValueFactory(
                new PropertyValueFactory<HostUsers,String>("surname")
        );
        password.setCellValueFactory(
                new PropertyValueFactory<HostUsers,String>("password")
        );
        phone.setCellValueFactory(
                new PropertyValueFactory<HostUsers,String>("phone")
        );
        username.setCellValueFactory(
                new PropertyValueFactory<HostUsers,String>("username")
        );
        status.setCellValueFactory(
                new PropertyValueFactory<HostUsers,String>("status")
        );
        login.setCellValueFactory(
                new PropertyValueFactory<HostUsers,String>("blockNum")
        );
        date.setCellValueFactory(
                new PropertyValueFactory<HostUsers,String>("date")
        );


        hostTable.getColumns().addAll(name, surname, phone, username, password,status, login, date);
        date.setSortType(TableColumn.SortType.DESCENDING);
        hostTable.getSortOrder().add(date);
//        name.setSortable(false);
//        surname.setSortable(false);
//        age.setSortable(false);
//        phone.setSortable(false);
//        username.setSortable(false);
//        status.setSortable(false);
//        login.setSortable(false);
//        date.setSortable(false);
    }

    @FXML
    private TextField searchText;
    public void showSearch(){
        ObservableList observableList =  hostTable.getItems();
        searchText.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                hostTable.setItems(observableList);
            }
            String search = newValue.toLowerCase();
            ObservableList<HostUsers> showSearchList = FXCollections.observableArrayList();
            long column = hostTable.getColumns().size();
            for (int i = 0; i < hostTable.getItems().size(); i++) {
                for (int j = 0; j < column; j++) {
                    String residentResult = "" + hostTable.getColumns().get(j).getCellData(i);
                    if (residentResult.toLowerCase().contains(search)) {
                        showSearchList.add(hostTable.getItems().get(i));
                        break;
                    }
                }
            }
            hostTable.setItems(showSearchList);
        });
    }
}
