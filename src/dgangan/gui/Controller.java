package dgangan.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField insertIpField;
    @FXML
    public TableColumn<DeviceModel, String> deviceIp;
    @FXML
    public TableColumn<DeviceModel, String> passedCol;
    @FXML
    private TableView<DeviceModel> ListOfIps;
    @FXML
    private CheckBox connectedCheckbox;
    @FXML
    public CheckBox problematicCheckbox;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;

    private ObservableList<DeviceModel> DeviceModels = FXCollections.observableArrayList();

    public static boolean validateIP(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
    }

    public void addIpToList(ActionEvent actionEvent) {
        if (validateIP(insertIpField.getText())){
        DeviceModels.add(new DeviceModel(insertIpField.getText(), usernameField.getText(), passwordField.getText()));}
    }

    public void checkDevices(ActionEvent actionEvent) throws InterruptedException {
        for (DeviceModel device: DeviceModels){
            try{
                device.connect();
                device.gatherInterfaceStatus();
                device.getPassed();
                ListOfIps.refresh();
            }catch(Exception e){
                //System.out.println("Exception while adding devices");
            }
        }
    }

    public void deleteSelectedIps(ActionEvent actionEvent) {
        ObservableList<DeviceModel> selectedRows = ListOfIps.getSelectionModel().getSelectedItems();
        ArrayList<DeviceModel> rows = new ArrayList<>(selectedRows);
        rows.forEach(row -> ListOfIps.getItems().remove(row));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ListOfIps.setRowFactory(tv -> {
            TableRow<DeviceModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        getDetails(row.getItem());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
        deviceIp.setCellValueFactory(new PropertyValueFactory<>("DeviceIp"));
        passedCol.setCellValueFactory(new PropertyValueFactory<>("Pass"));

        ListOfIps.setItems(DeviceModels);
        ListOfIps.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void getDetails(DeviceModel device){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("DetailsWindow.fxml"));
            DetailsController controller = new DetailsController(device, connectedCheckbox.isSelected(), problematicCheckbox.isSelected());
            loader.setController(controller);

            Stage detailsStage = new Stage();
            detailsStage.setTitle(device.getDeviceIp());

            detailsStage.setScene(new Scene(loader.load()));
            detailsStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
