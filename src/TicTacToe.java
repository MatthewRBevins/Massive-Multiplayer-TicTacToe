import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TicTacToe {
    // 79 chars
    private static String chars = "abcdefghijklmnopqrstuvwxyz!#^&*()-_=+{}[]|:;<>.?/ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String[] playerChars = {"X","O","%","§","╬","©","¶","¾","@","$"};
    private static Integer playerCount = 5;
    private static Integer boardLength = 9;
    private static Integer boardHeight = 9;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        int connections = 0;
        while (true) {
            try (Socket socket = server.accept()) {
                System.out.println("*********************");
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String line = reader.readLine();
                System.out.println(line);
                Date today = new Date();
                connections++;
                File crunchifyFile = new File("index.html");
                FileInputStream fileInputStream;
                fileInputStream = new FileInputStream(crunchifyFile);
                byte[] crunchifyValue = new byte[(int) crunchifyFile.length()];
                fileInputStream.read(crunchifyValue);
                fileInputStream.close();
                String fileContent = new String(crunchifyValue, "UTF-8");
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + fileContent;
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
            }
        }
        /*if(boardLength > chars.length()) {
            System.out.println("Your board is too wide");
            System.exit(69);
        }
        if(boardLength == 0 || boardHeight == 0) {
            System.out.println("The board cannot be smaller than 1x1");
            System.exit(69);
        }
        if(playerCount > playerChars.length) {
            System.out.println("You have too many players specified");
            System.exit(69);
        }

        String[][] board = new String[boardHeight][boardLength];
        for(int i = 0; i < boardHeight; i++) {
            for(int j = 0; j < boardLength; j++) {
                board[i][j] = "#";
            }
        }
        Hashtable<Character, Integer> letterToNumber = new Hashtable<Character, Integer>();

        for (int i = 0; i < chars.length(); i++) {
            letterToNumber.put(chars.charAt((i)), i);
        }

        for(int i = 0; i < boardLength * boardHeight; i++) {
            boolean validMove = false;
            while(!validMove) {
                printBoard(board);

                Scanner scanner = new Scanner(System.in);
                System.out.print("Where would player " + (i % playerCount + 1) + " like to play? ");
                String input = scanner.next();
                try {
                    int row = Integer.parseInt(String.valueOf(input.charAt(1))) - 1;
                    int column = letterToNumber.get(input.charAt(0));
                    for(int k = 0; k < playerCount; k++) {
                        if (board[row][column] == "#") {
                            String currentPlayerChar = playerChars[i % playerCount];

                            board[row][column] = currentPlayerChar;
                            validMove = true;
                        }
                    }

                    String didWin = checkWin(board);
                    if (didWin != "#") {
                        System.out.println(didWin + " won");
                        System.exit(0);
                    }
                } catch (Exception e) {
                    System.out.println("You broke the game, redo your turn");
                }
            }
        }
        System.out.println("The game is a draw");
        System.exit(0);
    }
    static void printBoard(String[][] board) {
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

    static String checkWin(String[][] board) {
        // check horizontal
        for(int i = 0; i < boardHeight; i++) {
            String value = board[i][0];
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] != value) {
                    value = "#";
                    break;
                }
            }
            if(value != "#"){
                return value;
            }
        }
        //check vertical
        for(int i = 0; i < boardLength; i++) {
            String value = board[0][i];
            for (int j = 0; j < board.length; j++) {
                if(board[j][i] != value) {
                    value = "#";
                    break;
                }
            }
            if(value != "#"){
                return value;
            }
        }
        // check upper left tot bottom right
        if(boardHeight == boardLength) {
            String value = board[0][0];
            for(int i = 0; i < boardHeight; i++) {
                if(board[i][i] != value) {
                    value = "#";
                    break;
                }
            }
            if(value != "#"){
                return value;
            }
        }
        // check bottom left to upper right
        if(boardHeight == boardLength) {
            String value = board[boardHeight - 1][0];
            for(int i = 0; i < boardHeight; i++) {
                if(board[boardHeight - i - 1][i] != value) {
                    value = "#";
                    break;
                }
            }
            if(value != "#"){
                return value;
            }
        }
        return "#";*/
    }
}
