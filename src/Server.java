import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
    private ServerSocket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Socket s;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = in.readLine();
            System.out.println(message);
            System.out.println(socket.getTrafficClass());
            socket.getOutputStream().write("hello there".getBytes(StandardCharsets.UTF_8));
            in.close();
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        serverSocket.close();
    }
    public static void main(String[] args) throws IOException {
        Server server=new Server();
        server.start(8081);
    }
}