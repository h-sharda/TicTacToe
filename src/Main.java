import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void initializeBoard(char[][]board, int size){
        for (int i =0; i< size;i++){
            for (int j =0; j< size; j++){
                board[i][j] = ' ';
            }
        }
    }

    public static boolean makeMove(char[][]board, int row, int col, char player, int size){
        if(row >= 0 && row < size && col>=0 && col < size && board[row][col] == ' ') {
            board[row][col] = player;
        }
        else{
            System.out.println("Can't place here, try again");
            return false;
        }
        return true;
    }

    public static boolean checkWinner(char[][] board, int size, int condition) {
        // Check rows
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - condition + 1; j++) {
                boolean rowWin = true;
                for (int k = 0; k < condition; k++) {
                    if (board[i][j + k] == ' ' || board[i][j + k] != board[i][j]) {
                        rowWin = false;
                        break;
                    }
                }
                if (rowWin) return true;
            }
        }

        // Check columns
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - condition + 1; j++) {
                boolean colWin = true;
                for (int k = 0; k < condition; k++) {
                    if (board[j + k][i] == ' ' || board[j + k][i] != board[j][i]) {
                        colWin = false;
                        break;
                    }
                }
                if (colWin) return true;
            }
        }

        // Check diagonals (top-left to bottom-right)
        for (int i = 0; i < size - condition + 1; i++) {
            for (int j = 0; j < size - condition + 1; j++) {
                boolean diagWin1 = true;
                for (int k = 0; k < condition; k++) {
                    if (board[i + k][j + k] == ' ' || board[i + k][j + k] != board[i][j]) {
                        diagWin1 = false;
                        break;
                    }
                }
                if (diagWin1) return true;
            }
        }

        // Check diagonals (bottom-left to top-right)
        for (int i = condition - 1; i < size; i++) {
            for (int j = 0; j < size - condition + 1; j++) {
                boolean diagWin2 = true;
                for (int k = 0; k < condition; k++) {
                    if (board[i - k][j + k] == ' ' || board[i - k][j + k] != board[i][j]) {
                        diagWin2 = false;
                        break;
                    }
                }
                if (diagWin2) return true;
            }
        }

        return false;
    }


    public static boolean boardFull(char[][] board, int size){
        for (int i=0;i< size; i++){
            for (int j=0; j<size; j++){
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }

    public static void printBoard(char[][] board, int size){
        for(int i=0; i< size; i++){
            for (int j=0; j< size; j++){
                System.out.print(board[i][j]);
                if (j != size-1) System.out.print(" | ");
            }
            if(i != size -1 ) {
                System.out.println();
                for (int j =0; j< size; j++){
                    System.out.print("----");
                }
                System.out.println();
            }
        }
    }

    public static void undo(char[][] board, int row, int col){
        board[row][col] = ' ';
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int BOARD_SIZE, WIN_CONDITION, PLAYERS;
        while (true) {
            System.out.print("Enter Board Size: ");
            BOARD_SIZE = sc.nextInt();
            if(BOARD_SIZE > 0 ) break;
            else System.out.println("Enter valid size");
        }

        System.out.print("Enter Win condition > 0 and <=size : ");
        WIN_CONDITION = sc.nextInt();
        System.out.print("Enter number of players <=5 : ");
        PLAYERS = sc.nextInt();

        char[][] BOARD = new char[BOARD_SIZE][BOARD_SIZE];
        char[] playerList = {'X','O','◇','Δ','☆'};
        char currentPlayer = playerList[0];
        int playerPos =0;

        initializeBoard(BOARD, BOARD_SIZE);

        while (!boardFull(BOARD, BOARD_SIZE)) {
            int i, j;
            while (true) {
                try {
                    System.out.println("\n" + currentPlayer + "'s Turn");
                    System.out.print("Enter row: ");
                    i = sc.nextInt();
                    System.out.print("Enter col: ");
                    j = sc.nextInt();
                    if (makeMove(BOARD, i, j, currentPlayer, BOARD_SIZE)) {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter integers for row and column.");
                    sc.next();
                }
            }
            printBoard(BOARD,BOARD_SIZE);
            if (checkWinner(BOARD,BOARD_SIZE,WIN_CONDITION)){
                System.out.println(currentPlayer+" Wins the game");
                sc.close();
                return;
            }

            System.out.print("\nWant undo(Y): ");
            char ch = sc.next().charAt(0);

            if(ch == 'Y' || ch =='y'){
                undo(BOARD, i, j);
                printBoard(BOARD,BOARD_SIZE);
            }
            else currentPlayer = playerList[(++playerPos) % PLAYERS];
        }

        sc.close();
        System.out.println("Game is a draw");
    }
}