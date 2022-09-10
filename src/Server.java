import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements java Socket server
 * @author pankaj
 *
 */
public class Server {

    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;
    private static TicTacToeMessage game = new TicTacToeMessage();

    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        game.playerArr = new String[10];
        game.started = false;
        game.turn = 1;
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            Socket socket = server.accept();

            //WRITE
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(game);

            //READ
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            game = (TicTacToeMessage) ois.readObject();
            System.out.println(game);
            ois.close();
            oos.close();
            socket.close();
            //terminate the server if client sends exit request
        }
    }

}