package dgangan.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {

    private ObservableList<PortEntry> portInfo = FXCollections.observableArrayList();

    DeviceModel device;

    @FXML
    public TableColumn<PortEntry, String> portName;
    @FXML
    public TableColumn<PortEntry, String> portStatus;
    @FXML
    public TableColumn<PortEntry, String> portSpeed;
    @FXML
    public TableColumn<PortEntry, String> portDuplex;
    @FXML
    public TableColumn<PortEntry, String> portDescription;
    @FXML
    private TableView<PortEntry> devicesIntStatus;

    public DetailsController(DeviceModel device, boolean connectedSelected, boolean problematicSelected){
        this.device = device;
        for (String key: device.getInterfaceStatus().keySet()){
            HashMap<String, String > intDetails = device.getInterfaceStatus().get(key);
            PortEntry portEntry = new PortEntry(key,intDetails.get("description"),intDetails.get("status"),intDetails.get("duplex"),intDetails.get("speed"));

            if(connectedSelected && problematicSelected) {
                if (intDetails.get("status").equals("connected") && !portEntry.getPassed(Arrays.asList("a-10", "a-100", "10", "100"))){
                    portInfo.add(portEntry);
                }
            }else if(connectedSelected) {
                if (intDetails.get("status").equals("connected")){
                    portInfo.add(portEntry);
                }
            }else if(problematicSelected) {
                if (!portEntry.getPassed(Arrays.asList("a-10", "a-100", "10", "100"))){
                    portInfo.add(portEntry);
                }
            } else {
                portInfo.add(new PortEntry(key,intDetails.get("description"),intDetails.get("status"),intDetails.get("duplex"),intDetails.get("speed")));
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        portName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        portStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        portSpeed.setCellValueFactory(new PropertyValueFactory<>("Speed"));
        portDuplex.setCellValueFactory(new PropertyValueFactory<>("Duplex"));
        portDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        devicesIntStatus.setItems(portInfo);
    }
}
