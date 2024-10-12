import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Bot {

    static Random random = new Random();

    private static int row, col;

    public static int[] makeMove(char[][] board, Stack<char[][]> st, char botSymbol, int playerNumber, int winCondition, int noOfPlayers, char [] playerList){

        char nextPlayer = playerList[(playerNumber + 1) % noOfPlayers ];

        if (canBotWin(board, botSymbol, winCondition)) {
            System.out.println("can win");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canOtherPlayersWin(board, botSymbol, playerNumber, winCondition, noOfPlayers, playerList)) {
            System.out.println("other is winning");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canFork(board, botSymbol, winCondition)) {
            System.out.println("can fork");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canNextPlayerFork(board, botSymbol, nextPlayer, winCondition)) {
            System.out.println("other can fork");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canDoubleFork(board, botSymbol, winCondition)) {
            System.out.println("can double fork");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        if (canNextPlayerDoubleFork(board, botSymbol, nextPlayer, winCondition)) {
            System.out.println("other can double fork");
            st.push(Functions.makeBoardCopy(board));
            return new int[] {row, col};
        }

        makeInformedRandomMove(board, botSymbol);

        System.out.println("Moving randomly (informed random)");
        st.push(Functions.makeBoardCopy(board));

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

    public static boolean canOtherPlayersWin(char[][] board, char botSymbol, int playerNumber, int winCondition, int noOfPlayers, char[] playerList){

        int n = board.length;
        int temp = playerNumber;
        for (int k = 0; k < noOfPlayers-1; k++) {
            temp++;
            char thisPlayer = playerList[ (temp) % noOfPlayers];
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

    public static boolean canFork(char[][] board, char botSymbol, int winCondition){

        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (board[i][j] != ' ') continue;

                board[i][j] = botSymbol;
                row = i;
                col = j;

                int winningMoves = 0;
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        if (board[k][l] != ' ') continue;
                        board[k][l] = botSymbol;
                        if (Functions.checkWinner(board, winCondition, botSymbol, k, l)) winningMoves++;
                        board[k][l] = ' ';
                    }
                }

                if (winningMoves >= 2) return true;
                board[i][j] = ' ';
            }
        }

        return false;
    }

    public static boolean canNextPlayerFork(char[][] board, char botSymbol ,char nextPlayer, int winCondition){
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (board[i][j] != ' ') continue;

                board[i][j] = nextPlayer;
                row = i;
                col = j;

                int winningMoves = 0;
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        if (board[k][l] != ' ') continue;
                        board[k][l] = nextPlayer;
                        if (Functions.checkWinner(board, winCondition, nextPlayer, k, l)) winningMoves++;
                        board[k][l] = ' ';
                    }
                }

                if (winningMoves >= 2) {
                    board[i][j] = botSymbol;
                    return true;
                }
                board[i][j] = ' ';
            }
        }

        return false;
    }

    public static boolean canDoubleFork(char[][] board, char botSymbol, int winCondition){
        int n = board.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (board[i][j] != ' ') continue;

                board[i][j] = botSymbol;
                row = i;
                col = j;

                int winningMoves = 0, forks=0;

                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        if (board[k][l] != ' ') continue;

                        board[k][l] = botSymbol;
                        if (Functions.checkWinner(board, winCondition, botSymbol, k, l)) winningMoves++;

                        int secondaryWinningMoves = 0;

                        for (int a = 0; a < n; a++) {
                            for (int b = 0; b < n; b++) {
                                if (board[a][b] != ' ' ) continue;
                                board[a][b] = botSymbol;
                                if (Functions.checkWinner(board, winCondition, botSymbol, a, b)) secondaryWinningMoves++;
                                board[a][b] = ' ';
                            }
                        }

                        if (secondaryWinningMoves >= 2) forks++;
                        board[k][l] = ' ';
                    }
                }

                if ( (winningMoves >= 1 && forks >= 1) || (forks >= 2) ) return true;
                board[i][j] = ' ';

            }
        }
        return false;
    }

    public static boolean canNextPlayerDoubleFork(char[][] board, char botSymbol, char nextPlayer, int winCondition){
        int n = board.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (board[i][j] != ' ') continue;

                board[i][j] = nextPlayer;
                row = i;
                col = j;

                int winningMoves = 0, forks=0;

                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        if (board[k][l] != ' ') continue;

                        board[k][l] = nextPlayer;
                        if (Functions.checkWinner(board, winCondition, nextPlayer, k, l)) winningMoves++;

                        int secondaryWinningMoves = 0;

                        for (int a = 0; a < n; a++) {
                            for (int b = 0; b < n; b++) {
                                if (board[a][b] != ' ' ) continue;
                                board[a][b] = nextPlayer;
                                if (Functions.checkWinner(board, winCondition, nextPlayer, a, b)) secondaryWinningMoves++;
                                board[a][b] = ' ';
                            }
                        }

                        if (secondaryWinningMoves >= 2) forks++;
                        board[k][l] = ' ';
                    }
                }

                if ( (winningMoves >= 1 && forks >= 1) || (forks >= 2) ) {
                    board[i][j] = botSymbol;
                    return true;
                }
                board[i][j] = ' ';

            }
        }
        return false;
    }

    public static void makeInformedRandomMove(char[][] board, char botSymbol){
        int n = board.length;

        if (board[n/2][n/2] == ' ') {
            row = n/2;
            col = n/2;
            board[n/2][n/2] = botSymbol;
            return;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != ' ' ){
                    if ( i < n/2 && j< n/2){
                        if (board[i+1][j+1] == ' ') {
                            System.out.println(1);
                            row = i+1;
                            col = j+1;
                            board[row][col] = botSymbol;
                            return;
                        }
                    } else if ( i >= n/2 && j < n/2){
                        if (board[i-1][j+1] == ' ') {
                            System.out.println(2);
                            row = i-1;
                            col = j+1;
                            board[row][col] = botSymbol;
                            return;
                        }
                    } else if ( i < n/2 && j >= n/2){
                        if (board[i+1][j-1] == ' ') {
                            System.out.println(3);
                            row = i+1;
                            col = j-1;
                            board[row][col] = botSymbol;
                            return;
                        }
                    } else {
                        if (board[i-1][j-1] == ' ') {
                            System.out.println(4);
                            row = i-1;
                            col = j-1;
                            board[row][col] = botSymbol;
                            return;
                        }
                    }
                }
            }
        }

        ArrayList<Integer> emptyCells = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ( board[i][j] == ' ') {
                    emptyCells.add(i*n + j);
                }
            }
        }

        int k = emptyCells.size();
        int randPos = emptyCells.get(random.nextInt(k));

        row = randPos/n;
        col = randPos%n;
        System.out.println(board[row][col]);
        board[row][col] = botSymbol;

    }

}
