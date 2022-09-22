/**
 * Created by mona on 5/27/16.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Date;

public class ClientTest {
    public static String readInStream(InputStream is) throws IOException {
        String f = "";
        try( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                f += line;
            }
            return f;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new MalformedURLException("URL is malformed!!");
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }
    public static void readFromWeb(String webURL) throws IOException {
        URL url = new URL(webURL);
        InputStream is =  url.openStream();
        System.out.println(readInStream(is));
    }
    public static void main(String[] args) throws IOException {

        InetAddress host = InetAddress.getByName("TicTacToeServer.matthewbevins.repl.co");
        Socket socket = new Socket(host.getHostName(), 443);
        System.out.println(socket.isConnected());
        Date today = new Date();
        String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "faewiopjawef";
        socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
    }

}