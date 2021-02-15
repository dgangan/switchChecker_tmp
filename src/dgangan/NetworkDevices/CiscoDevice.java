package dgangan.NetworkDevices;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CiscoDevice extends NetworkDevice {

    private LinkedHashMap<String, HashMap<String,String>> interfaceStatus;

    public CiscoDevice(String ipAddress, String userName, String password) {
        super(ipAddress, userName, password);
    }

    public void gatherInterfaceStatus() {
        String[] namedGroups = {"interface", "description", "status", "duplex", "speed"};

        String patternString = String.format(".*(?<%s>Gi\\S*)\\s+(?<%s>.*)\\s+(?<%s>notconnect|connected)\\s+.*" +
                "\\s+(?<%s>a-\\S*|full|half|auto)\\s+" +
                "(?<%s>a-\\S*|10|100|1000|auto)\\s+.*", (Object[]) namedGroups);
        String rawOutput = this.sendCommand("show int status");

        interfaceStatus = OutputParser.parse(rawOutput, patternString, namedGroups);
    }

    public LinkedHashMap<String, HashMap<String,String>> getInterfaceStatus(){
        return interfaceStatus;
    }

    public LinkedHashMap<String, HashMap<String,String>> getInterfaceStatusFromText(String rawOutput){
        String[] namedGroups = {"interface","description","status","duplex","speed"};

        String patternString = String.format(".*(?<%s>Gi\\S*)\\s+(?<%s>.*)\\s+(?<%s>notconnect|connected)\\s+.*"+
                                             "\\s+(?<%s>a-\\S*|full|half|auto)\\s+"+
                                             "(?<%s>a-\\S*|10|100|1000|auto)\\s+.*", (Object[]) namedGroups);

        interfaceStatus =  OutputParser.parse(rawOutput, patternString, namedGroups);
        return interfaceStatus;
    }
}
