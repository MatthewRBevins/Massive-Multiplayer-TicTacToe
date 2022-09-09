import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException, ClassNotFoundException, InterruptedException {
        clientSocket = new Socket(ip, port);
        clientSocket.setTrafficClass(121);
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        while (true) {
            try {
                System.out.println("joiawe");
                oos = new ObjectOutputStream(clientSocket.getOutputStream());
                oos.write("ifwaeo".getBytes(StandardCharsets.UTF_8));
                Thread.sleep(1000);
                oos.close();
                ois = new ObjectInputStream(clientSocket.getInputStream());
                String message = (String) ois.readObject();
                System.out.println("Message: " + message);
                ois.close();
            }catch (SocketException s) {
                clientSocket = new Socket(ip, port);
            }
        }
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Client c = new Client();
        c.startConnection("127.0.0.1",8081);
    }
}