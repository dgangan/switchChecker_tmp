package dgangan.gui;
import dgangan.NetworkDevices.CiscoDevice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DeviceModel extends CiscoDevice {
    private String passedCheck;

    public DeviceModel(String ipAddress, String username, String password) {
        super(ipAddress, username, password);
        this.passedCheck = "?";
    }

    public String getDeviceIp() {
        return getIpAddress();
    }

    public void getPassed() {
        List<String> speedValues = Arrays.asList("a-10", "a-100", "10", "100");
            for (HashMap<String, String> value: getInterfaceStatus().values()){
                if ((value.get("duplex").endsWith("half") || speedValues.contains(value.get("speed")))) {
                    setPass("NO");
                    break;
            }else{setPass("YES");}
                }
            }

    public String getPass(){
        return this.passedCheck;
    }

    public void setPass(String passed){
        this.passedCheck = passed;
    }
}

