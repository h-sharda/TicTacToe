import java.util.Stack;

public class Bot5 extends BotFunctions {

    /* This bot is very strong, it is unbeatable in various small board size
    It moves according to the following strategy:
    1) It checks if it can win anywhere
    2) Then it checks if any of the other player is winning
    3) Then it checks if it can fork somewhere (a fork is move that enables 2 or more winning moves in the next turn)
    4) Then it checks if the next player can perform a fork somewhere (only checks for next player)
    5) Then it checks for a double fork, a move that creates 2 or more forks, or makes a winning move and a fork for the next move
    6) Then it tries moving near other pieces towards the center
    7) Then it checks if the next player can perform a double fork somewhere (only checks for next player)
    8) Otherwise it moves completely randomly */

    public static int[] makeMove(char[][] board, Stack<char[][]> st, char botSymbol, int playerNumber, int winSequence, int noOfPlayers, char[] playerList) {

        if (canBotWin(board, botSymbol, winSequence)) {
            System.out.println("Bot 5: can win");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canOtherPlayersWin(board, botSymbol, playerNumber, winSequence, noOfPlayers, playerList)) {
            System.out.println("Bot 5: other is winning");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        char nextPlayer = playerList[(playerNumber + 1) % noOfPlayers ];
        if (specialCase(board, botSymbol, nextPlayer, playerNumber)){
            System.out.println("Bot 5: Yeah opponent is doing that trick");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canFork(board, botSymbol, winSequence)) {
            System.out.println("Bot 5: can fork");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canNextPlayerFork(board, botSymbol, nextPlayer, winSequence)) {
            System.out.println("Bot 5: other can fork");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canDoubleFork(board, botSymbol, winSequence)) {
            System.out.println("Bot 5: can double fork");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (makeInformedMove(board, botSymbol)){
            System.out.println("Bot 5: Making informed move near other pieces and towards center");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canNextPlayerDoubleFork(board, botSymbol, nextPlayer, winSequence)) {
            System.out.println("Bot 5: other can double fork");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        makeTrueRandomMove(board, botSymbol);
        System.out.println("Bot 5: Moving randomly");
        st.push(Functions.makeBoardCopy(board));

        return new int[] {row,col};
    }
}
