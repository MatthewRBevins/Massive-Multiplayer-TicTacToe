import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    static Scanner s = new Scanner(System.in);
    static String chars = "abcdefghijklmnopqrstuvwxyz!#^&*()-_=+{}[]|:;<>.?/ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String[] playerSymbols = new String[]{"X","O","%","§","╬","©","¶","¾","@","$"};
    public static void printBoard(String[][] board) {
        // amog
        System.out.print("   ");
        for(int i = 0; i < board[0].length; i++) {
            System.out.print(" " + chars.charAt(i));
        }
        System.out.println("");

        for(int i = 0; i < board.length; i++) {
            if(i + 1 < 10) {
                System.out.print(" ");
            }
            if(i + 1 < 100) {
                System.out.print(" ");
            }
            System.out.print(i + 1 + " ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("");
        }
    }
    public static String checkWin(String[][] board) {
        // check horizontal
        for(int i = 0; i < board.length; i++) {
            String value = board[i][0];
            for (int j = 0; j < board[i].length; j++) {
                if(!Objects.equals(board[i][j], value)) {
                    value = "#";
                    break;
                }
            }
            if(!Objects.equals(value, "#")){
                return value;
            }
        }
        //check vertical
        for(int i = 0; i < board[0].length; i++) {
            String value = board[0][i];
            for (int j = 0; j < board.length; j++) {
                if(!Objects.equals(board[j][i], value)) {
                    value = "#";
                    break;
                }
            }
            if(!Objects.equals(value, "#")){
                return value;
            }
        }
        // check upper left tot bottom right
        if(board.length == board[0].length) {
            String value = board[0][0];
            for(int i = 0; i < board.length; i++) {
                if(!Objects.equals(board[i][i], value)) {
                    value = "#";
                    break;
                }
            }
            if(!Objects.equals(value, "#")){
                return value;
            }
        }
        // check bottom left to upper right
        if(board.length == board[0].length) {
            String value = board[board.length - 1][0];
            for(int i = 0; i < board.length; i++) {
                if(!Objects.equals(board[board.length - i - 1][i], value)) {
                    value = "#";
                    break;
                }
            }
            if(!Objects.equals(value, "#")){
                return value;
            }
        }
        return "#";
    }
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //CONNECTION VARS
        InetAddress host = InetAddress.getLocalHost();
        byte[] ipAddr = new byte[] {(byte) 172,18,0,54};
        InetAddress host = InetAddress.getByAddress(ipAddr);
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        TicTacToeMessage message = null;

        //GAME VARS
        int playerNum = 1;
        boolean joinedGame = false;
        System.out.print("Enter name: ");
        String name = s.nextLine();
        while (true) {
            socket = new Socket(host.getHostName(), 8080);
            System.out.println("Socket connecting to port " + socket.getPort() + " at " + socket.getInetAddress().getHostName());
            //READ
            ois = new ObjectInputStream(socket.getInputStream());
            message = (TicTacToeMessage) ois.readObject();

            //JOIN GAME IF NEEDED
            if (! joinedGame) {
                message.playerArr[message.playerArrIndex] = name;
                message.playerArrIndex++;
                playerNum = message.playerArrIndex;
                joinedGame = true;
                if (playerNum == 1) {
                    System.out.print("Enter total players: ");
                    message.totalPlayers = s.nextInt();
                    System.out.print("Enter board width: ");
                    int wid = s.nextInt();
                    System.out.print("Enter board height: ");
                    int hei = s.nextInt();
                    message.board = new String[wid][hei];
                    for(int i = 0; i < hei; i++) {
                        for(int j = 0; j < wid; j++) {
                            message.board[i][j] = "#";
                        }
                    }
                }
                System.out.println("Waiting for more players...");
            }

            //START GAME
            if (message.playerArrIndex == message.totalPlayers) {
                message.started = true;
            }

            //END GAME
            if (message.gameOver) {
                System.out.println("YOU LOSE. THE WINNER WAS " + message.winner);
                break;
            }

            //GAME
            if (message.started && message.turn == playerNum) {
                printBoard(message.board);
                boolean validMove = false;
                int row = 0;
                int col = 0;
                while (! validMove) {
                    System.out.print("Enter position choice: ");
                    String choice = s.next();
                    col = chars.indexOf(choice.charAt(0));
                    row = Integer.parseInt(choice.substring(1))-1;
                    if (Objects.equals(message.board[row][col], "#")) {
                        validMove = true;
                    }
                }
                message.board[row][col] = playerSymbols[playerNum-1];
                message.turn++;
                if (message.turn > message.totalPlayers) {
                    message.turn = 1;
                }
                System.out.println("Waiting for next player to move...");
                if (!Objects.equals(checkWin(message.board), "#")) {
                    message.gameOver = true;
                    message.winner = checkWin(message.board);
                    System.out.println("YOU WON!");
                }
            }

            //WRITE
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);

            ois.close();
            oos.close();
            Thread.sleep(1000);
        }
    }
}