import java.util.Stack;

public class Bot4 extends BotFunctions {

    /* This bot is a lot smarter than previous bot, also it is unbeatable in 3*3 board
    It moves according to the following strategy:
    1) It checks if it can win anywhere
    2) Then it checks if any of the other player is winning
    3) Then it checks if it can fork somewhere (a fork is move that enables 2 or more winning moves in the next turn)
    4) Then it checks if the next player can perform a fork somewhere (only checks for next player)
    5) Then it tries moving near other pieces towards the center
    6) Otherwise it moves completely randomly */

    public static int[] makeMove(char[][] board, Stack<char[][]> st, char botSymbol, int playerNumber, int winSequence, int noOfPlayers, char[] playerList) {

        if (canBotWin(board, botSymbol, winSequence)) {
            System.out.println("Bot 4: can win");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canOtherPlayersWin(board, botSymbol, playerNumber, winSequence, noOfPlayers, playerList)) {
            System.out.println("Bot 4: other is winning");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        char nextPlayer = playerList[(playerNumber + 1) % noOfPlayers ];
        if (specialCase(board, botSymbol, nextPlayer, playerNumber)){
            System.out.println("Bot 4: Yeah opponent is doing that trick");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canFork(board, botSymbol, winSequence)) {
            System.out.println("Bot 4: can fork");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canNextPlayerFork(board, botSymbol, nextPlayer, winSequence)) {
            System.out.println("Bot 4: other can fork");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (makeInformedMove(board, botSymbol)){
            System.out.println("Bot 4: Making informed move near other pieces and towards center");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        makeTrueRandomMove(board, botSymbol);
        System.out.println("Bot 4: Moving randomly");
        st.push(Functions.makeBoardCopy(board));

        return new int[] {row,col};
    }
}
