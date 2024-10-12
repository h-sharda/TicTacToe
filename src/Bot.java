import java.util.Random;
import java.util.Stack;

public class Bot {

    static Random random = new Random();

    private static int row, col;

    public static int[] makeMove(char[][] board, Stack<char[][]> st, char botSymbol, int playerNumber, int winCondition, int noOfPlayers, char [] playerList){

        int n = board.length;

        if (canBotWin(board, botSymbol, winCondition)) return new int[] {row, col};

        if (canOtherPlayerWin(board, botSymbol, playerNumber, winCondition, noOfPlayers, playerList)) return new int[] {row, col};

        boolean moved = false;

        while (!moved) {
            row = random.nextInt(n);
            col = random.nextInt(n);
            moved = Functions.makeMove(board, st, botSymbol, row, col);
        }

        return new int[] {row,col};
    }

    public static boolean canBotWin(char[][] board, char botSymbol, int winCondition){
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != ' ') continue;
                board[i][j] = botSymbol;
                if (Functions.checkWinner(board, winCondition, botSymbol, i, j)) {
                    row = i;
                    col = j;
                    return true;
                }
                board[i][j] = ' ';
            }
        }
        return false;
    }

    public static boolean canOtherPlayerWin(char[][] board, char botSymbol, int playerNumber, int winCondition, int noOfPlayers, char[] playerList){

        int n = board.length;
        for (int k = 0; k < noOfPlayers-1; k++) {
            char thisPlayer = playerList[ (++playerNumber) % noOfPlayers];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] != ' ') continue;
                    board[i][j] = thisPlayer;
                    if (Functions.checkWinner(board, winCondition, thisPlayer, i, j)) {
                        row = i;
                        col = j;
                        board[i][j] = botSymbol;
                        return true;
                    }
                    board[i][j] = ' ';
                }
            }
        }
        return false;
    }

}
