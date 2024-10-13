import java.util.ArrayList;
import java.util.Random;

public class BotFunctions {

    static Random random = new Random();

    static int row, col;

    public static boolean canBotWin(char[][] board, char botSymbol, int winSequence){
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != ' ') continue;
                board[i][j] = botSymbol;
                if (Functions.checkWinner(board, winSequence, botSymbol, i, j)) {
                    row = i;
                    col = j;
                    return true;
                }
                board[i][j] = ' ';
            }
        }
        return false;
    }

    public static boolean canOtherPlayersWin(char[][] board, char botSymbol, int playerNumber, int winSequence, int noOfPlayers, char[] playerList){

        int n = board.length;
        int temp = playerNumber;
        for (int k = 0; k < noOfPlayers-1; k++) {
            temp++;
            char thisPlayer = playerList[ (temp) % noOfPlayers];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] != ' ') continue;
                    board[i][j] = thisPlayer;
                    if (Functions.checkWinner(board, winSequence, thisPlayer, i, j)) {
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

    public static boolean canFork(char[][] board, char botSymbol, int winSequence){

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
                        if (Functions.checkWinner(board, winSequence, botSymbol, k, l)) winningMoves++;
                        board[k][l] = ' ';
                    }
                }

                if (winningMoves >= 2) return true;
                board[i][j] = ' ';
            }
        }
        return false;
    }

    public static boolean canNextPlayerFork(char[][] board, char botSymbol ,char nextPlayer, int winSequence){
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
                        if (Functions.checkWinner(board, winSequence, nextPlayer, k, l)) winningMoves++;
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

    public static boolean canDoubleFork(char[][] board, char botSymbol, int winSequence){
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
                        if (Functions.checkWinner(board, winSequence, botSymbol, k, l)) winningMoves++;

                        int secondaryWinningMoves = 0;

                        for (int a = 0; a < n; a++) {
                            for (int b = 0; b < n; b++) {
                                if (board[a][b] != ' ' ) continue;
                                board[a][b] = botSymbol;
                                if (Functions.checkWinner(board, winSequence, botSymbol, a, b)) secondaryWinningMoves++;
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

    public static boolean canNextPlayerDoubleFork(char[][] board, char botSymbol, char nextPlayer, int winSequence){
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
                        if (Functions.checkWinner(board, winSequence, nextPlayer, k, l)) winningMoves++;

                        int secondaryWinningMoves = 0;

                        for (int a = 0; a < n; a++) {
                            for (int b = 0; b < n; b++) {
                                if (board[a][b] != ' ' ) continue;
                                board[a][b] = nextPlayer;
                                if (Functions.checkWinner(board, winSequence, nextPlayer, a, b)) secondaryWinningMoves++;
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

    public static boolean makeInformedMove(char[][] board, char botSymbol){
        int n = board.length;

        if (board[n/2][n/2] == ' ') {
            row = n/2;
            col = n/2;
            board[n/2][n/2] = botSymbol;
            return true;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != ' ' ){
                    if ( i < n/2 && j< n/2){
                        if (board[i+1][j+1] == ' ') {
                            row = i+1;
                            col = j+1;
                            board[row][col] = botSymbol;
                            return true;
                        }
                    } else if ( i >= n/2 && j < n/2){
                        if (board[i-1][j+1] == ' ') {
                            row = i-1;
                            col = j+1;
                            board[row][col] = botSymbol;
                            return true;
                        }
                    } else if ( i < n/2 && j >= n/2){
                        if (board[i+1][j-1] == ' ') {
                            row = i+1;
                            col = j-1;
                            board[row][col] = botSymbol;
                            return true;
                        }
                    } else {
                        if (board[i-1][j-1] == ' ') {
                            row = i-1;
                            col = j-1;
                            board[row][col] = botSymbol;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void makeTrueRandomMove(char[][] board, char botSymbol){
        int n = board.length;
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
        board[row][col] = botSymbol;
    }

}
