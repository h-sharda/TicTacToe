import java.util.Stack;

public class Bot1 extends BotFunctions {

    /* This bot moves according to the following strategy:
    1) It checks if it can win anywhere
    2) Otherwise it moves completely randomly */

    public static int[] makeMove(char[][] board, Stack<char[][]> st, char botSymbol, int winSequence) {

        if (canBotWin(board, botSymbol, winSequence)) {
            System.out.println("Bot 1: can win");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        makeTrueRandomMove(board, botSymbol);
        System.out.println("Bot 1: Moving randomly");
        st.push(Functions.makeBoardCopy(board));

        return new int[] {row,col};
    }

}
