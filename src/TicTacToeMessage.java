import java.io.Serializable;

public class TicTacToeMessage implements Serializable {
    public String[][] board;
    public String[] playerArr;
    public int playerArrIndex = 0;
    public int totalPlayers;
    public boolean started;
    public int turn;
    public boolean gameOver = false;
    public String winner;
    public TicTacToeMessage(){

    }
}
