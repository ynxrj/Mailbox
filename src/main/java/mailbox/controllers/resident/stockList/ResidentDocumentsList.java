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

public class ResidentDocumentsList {

    @FXML
    private TableView<Letter> documentTable;
    private ObservableList<Letter> documentObservableList;
    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private AllUsersLists allLists;
    private RoomLists roomLists;
    private ResidentsList residentsList;
    private AllStockList stockList, documentsList, documentInList;
    private FileData stockData;
    private Document selectedDocument ;
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
            stockData = new StockFileData("data", "stock.csv");
            stockList = (AllStockList) stockData.getList();
            documentsList = new AllStockList();
            for (Letter item : stockList.getAllStockList()) {
                if (item instanceof Document) {
                    documentsList.addStock(item);
                }
            }
            showDocumentData();
            showSearch();
        });
        documentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            showSelectedItem((Document) newValue);
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

    private void showSelectedItem(Document document) {
        selectedDocument = document;
        receiver = document.getReceiver().split(" - ");
        receiverText.setText(receiver[1]);
        senderText.setText(document.getSender());
        addByText.setText(document.getAddBy());
        sizeText.setText(document.getSize());
        File picture = new File(document.getPath());
        Image pic = new Image(picture.toURI().toString());
        sampleImage.setImage(pic);
    }

    public void showDocumentData(){
        documentInList = documentsList.getInStockListByRoom(currentResident.getRoomNumber());
        documentObservableList = FXCollections.observableArrayList(documentInList.getAllStockList());

        documentTable.setItems(documentObservableList);
        TableColumn sender = new TableColumn("Sender");
        TableColumn receiver = new TableColumn("Receiver");
        TableColumn size = new TableColumn("Size");
        TableColumn addBy = new TableColumn("Host");
        TableColumn time = new TableColumn("Time");
        TableColumn priority = new TableColumn("Priority");
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
        priority.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("priority")
        );

        time.setSortType(TableColumn.SortType.DESCENDING);
        documentTable.getColumns().addAll(sender, receiver, size, priority, addBy, time);
        documentTable.getSortOrder().add(time);
    }

    @FXML
    private TextField searchText;
    public void showSearch(){
        ObservableList observableList =  documentTable.getItems();
        searchText.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                documentTable.setItems(observableList);
            }
            String search = newValue.toLowerCase();
            ObservableList<Letter> showSearchList = FXCollections.observableArrayList();
            long column = documentTable.getColumns().size();
            for (int i = 0; i < documentTable.getItems().size(); i++) {
                for (int j = 0; j < column; j++) {
                    String residentResult = "" + documentTable.getColumns().get(j).getCellData(i);
                    if (residentResult.toLowerCase().contains(search)) {
                        showSearchList.add(documentTable.getItems().get(i));
                        break;
                    }
                }
            }
            documentTable.setItems(showSearchList);
        });
    }
}
