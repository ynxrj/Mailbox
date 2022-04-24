package mailbox.controllers.resident.stockList;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mailbox.controllers.resident.ResidentChangePassword;
import mailbox.controllers.resident.ResidentLogin;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.storage.*;
import mailbox.models.userAccount.*;
import mailbox.services.implementation.StockFileData;
import mailbox.services.interfaceClass.FileData;

import java.io.File;
import java.io.IOException;

public class ResidentReceivedPackage {

    @FXML
    private TableView<Letter> packagesTable;
    private ObservableList<Letter> packagesObservableList;
    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private AllUsersLists allLists;
    private RoomLists roomLists;
    private ResidentsList residentsList;
    private AllStockList stockList, packagesList, packagesOutList;
    private FileData stockData;
    private Packages selectedPackage;
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
            stockList = new AllStockList();
            packagesList = new AllStockList();
            stockData = new StockFileData("data", "stock.csv");
            stockList = (AllStockList) stockData.getList();
            for (Letter item : stockList.getAllStockList()) {
                if (item instanceof Packages) {
                    packagesList.addStock(item);
                }
            }
            showPackagesData();
            showSearch();
        });
        packagesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItem((Packages) newValue);
            }
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
    public void handleResidentDocumentListsBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_documents_list.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        ResidentDocumentsList residentDocumentsList = loader.getController();
        residentDocumentsList.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList, currentResident);

        stage.show();
    }
    @FXML
    public void handleResidentPackageListsBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_packages_list.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        ResidentPackagesList residentPackagesList = loader.getController();
        residentPackagesList.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList, currentResident);
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
    public void handleResidentReceivedPackagesBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_received_package.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        ResidentReceivedPackage residentReceivedPackage = loader.getController();
        residentReceivedPackage.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList, currentResident);
        stage.show();
    }

    @FXML
    public void handleResidentReceivedDocumentBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_received_document.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        ResidentReceivedDocument residentReceivedDocument = loader.getController();
        residentReceivedDocument.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList, currentResident);
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
    private ImageView sampleImage;
    @FXML
    private Label senderText, receiverText, addByText, sizeText, nameLabel;
    private String[] receiver;

    private void showSelectedItem(Packages packages) {
        selectedPackage = packages;
        receiver = packages.getReceiver().split(" - ");
        receiverText.setText(receiver[1]);
        senderText.setText(packages.getSender());
        addByText.setText(packages.getAddBy());
        sizeText.setText(packages.getSize());
        File picture = new File(packages.getPath());
        Image pic = new Image(picture.toURI().toString());
        sampleImage.setImage(pic);
    }

    public void showPackagesData(){
        packagesOutList = packagesList.getOutStockListByRoom(currentResident.getRoomNumber());
        packagesObservableList = FXCollections.observableArrayList(packagesOutList.getAllStockList());

        packagesTable.setItems(packagesObservableList);
        TableColumn sender = new TableColumn("Sender");
        TableColumn receiver = new TableColumn("Receiver");
        TableColumn size = new TableColumn("Size");
        TableColumn addBy = new TableColumn("Host");
        TableColumn time = new TableColumn("Time");
        TableColumn transporter = new TableColumn("Transporter");
        TableColumn tracking = new TableColumn("Tracking Number");
        sender.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("sender")
        );
        receiver.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("receiver")
        );
        size.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("size")
        );
        addBy.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("addBy")
        );
        time.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("time")
        );
        transporter.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("transporter")
        );
        tracking.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("trackingNumber")
        );

        time.setSortType(TableColumn.SortType.DESCENDING);
        packagesTable.getColumns().addAll(sender, receiver, size, transporter, tracking, addBy, time);
        packagesTable.getSortOrder().add(time);
    }

    @FXML
    private TextField searchText;
    public void showSearch(){
        ObservableList observableList =  packagesTable.getItems();
        searchText.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                packagesTable.setItems(observableList);
            }
            String search = newValue.toLowerCase();
            ObservableList<Letter> showSearchList = FXCollections.observableArrayList();
            long column = packagesTable.getColumns().size();
            for (int i = 0; i < packagesTable.getItems().size(); i++) {
                for (int j = 0; j < column; j++) {
                    String residentResult = "" + packagesTable.getColumns().get(j).getCellData(i);
                    if (residentResult.toLowerCase().contains(search)) {
                        showSearchList.add(packagesTable.getItems().get(i));
                        break;
                    }
                }
            }
            packagesTable.setItems(showSearchList);
        });
    }
}
