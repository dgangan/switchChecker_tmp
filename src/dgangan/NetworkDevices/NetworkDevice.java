package dgangan.NetworkDevices;

public class NetworkDevice {

    private String ipAddress;
    private String userName;
    private String password;
    private TelnetClient telnetClient;

    public NetworkDevice(String ipAddress, String userName, String password) {
        this.ipAddress = ipAddress;
        this.userName = userName;
        this.password = password;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void connect() {
        System.out.println("Connection " + userName +" : " + password);
        telnetClient = new TelnetClient(this.ipAddress, this.userName, this.password);
        System.out.println("Connection to " + this.ipAddress + " established");
        telnetClient.sendCommand("terminal len 0");
    }

    public String sendCommand(String command) {
        return telnetClient.sendCommand(command);
    }

    public void disconnect(){
        telnetClient.disconnect();
    }
}
