import java.util.Random;
import java.util.Stack;

public class Bot {

    static Random random = new Random();

    public static int[] makeMove(char[][] board, Stack<char[][]> st, char botSymbol){

        int row = -1 , col = -1;
        int n = board.length;
        boolean moved = false;

        while (!moved) {
            row = random.nextInt(n);
            col = random.nextInt(n);
            moved = Functions.makeMove(board, st, botSymbol, row, col);
        }

        return new int[] {row,col};
    }
}