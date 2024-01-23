import net.SSLClient;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.ServerSocket;

public class Main {

    private static final int PORT_MONEY = 6666;
    private static final int PORT_AUTH = 7777;
    private static final int PORT_HTTPS_ACQ = 8888;

    public static void main(String[] args) {
        /*try {
          //  System.setProperty("javax.net.ssl.trustStore","mySrvKeystore");
          //  System.setProperty("javax.net.ssl.trustStorePassword","123456");

            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", 9999);

            InputStream inputstream = System.in;
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            OutputStream outputstream = sslsocket.getOutputStream();
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
            BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

            String string = null;
            while ((string = bufferedreader.readLine()) != null) {
                bufferedwriter.write(string + '\n');
                bufferedwriter.flush();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }*/
        askTokenACS("lol");
    }

    private static void askTokenACS(String token) {
        SSLClient client = new SSLClient("127.0.0.1", PORT_MONEY, "src/main/resources/acq.jks");
        client.write(token);
    }
}
