package dgangan.NetworkDevices;

import java.io.InputStream;
import java.io.PrintStream;

public class TelnetClient {
    private org.apache.commons.net.telnet.TelnetClient telnet = new org.apache.commons.net.telnet.TelnetClient();
    private InputStream in;
    private PrintStream out;
    private String prompt;

    public TelnetClient(String server, String user, String password, String prompt) {
        this.prompt = prompt;
        try {
            telnet.setConnectTimeout(5000);
            telnet.connect(server, 23);
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());

            readUntil("Username: ");
            write(user);
            readUntil("Password: ");
            write(password);
            readUntil(prompt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public TelnetClient(String server, String user, String password){
        this(server, user, password, "#");
    }

    public String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuilder sb = new StringBuilder();
            boolean found = false;
            char ch = (char) in.read();
            while (true) {
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(String value) {
        try {
            out.println(value);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendCommand(String command) {
        try {
            write(command);
            return readUntil(this.prompt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disconnect() {
        try {
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


