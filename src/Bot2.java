import java.util.Stack;

public class Bot2 extends BotFunctions {

    /* This bot moves according to the following strategy:
    1) It checks if it can win anywhere
    2) Then it checks if any of the other player is winning
    3) Otherwise it moves completely randomly */

    public static int[] makeMove(char[][] board, Stack<char[][]> st, char botSymbol, int playerNumber, int winSequence, int noOfPlayers, char[] playerList) {

        if (canBotWin(board, botSymbol, winSequence)) {
            System.out.println("Bot 2: can win");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canOtherPlayersWin(board, botSymbol, playerNumber, winSequence, noOfPlayers, playerList)) {
            System.out.println("Bot 2: other is winning");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        makeTrueRandomMove(board, botSymbol);
        System.out.println("Bot 2: Moving randomly");
        st.push(Functions.makeBoardCopy(board));

        return new int[] {row,col};
    }
}
