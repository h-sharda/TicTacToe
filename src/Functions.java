import java.util.Stack;

public class Functions {

    // FOR A DEEP COPY OF THE BOARD
    public static char[][] makeBoardCopy (char[][] board){
        int n = board.length;
        char[][] copy = new char[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, n);
        }

        return copy;
    }

    // INITIALIZE BOARD
    public static void initializeBoard (char[][] board, Stack<char[][]> st){
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = ' ';
            }
        }
        st.push(makeBoardCopy(board));
    }

    // MAKES MOVE
    public static boolean makeMove(char[][] board, Stack<char[][]> st, char player, int row, int col){
        if (board[row][col] != ' ') return false;

        board[row][col] = player;
        st.push(makeBoardCopy(board));
        return true;
    }

    // CHECKS WINNER
    public static boolean checkWinner(char[][] board, int winCondition, char player, int row, int col ){
        int n = board.length;
        
        int rowStart = Math.max(row - winCondition + 1, 0), rowEnd = Math.min(row + winCondition - 1, n-1);
        int colStart = Math.max(col - winCondition + 1, 0), colEnd = Math.min(col + winCondition - 1, n-1);
        
        // VERTICAL CHECK
        int len = 0;
        for (int i = rowStart ; i<= rowEnd; i++) {
            len = board[i][col] == player ? len+1 : 0;
            if (len == winCondition) return true;
        }
        
        // HORIZONTAL CHECK
        len = 0;
        for (int i = colStart ; i<= colEnd; i++) {
            len = board[row][i] == player ? len+1 : 0;
            if (len == winCondition) return true;
        }
        
        // DIAGONAL CHECK
        len = 0;
        for (int i = 1-winCondition; i <= winCondition-1; i++) {
            if ( (row + i < 0) || (row + i >= n) || (col + i < 0) || (col + i >= n) ) continue;
            len = board[row+i][col+i] == player ? len+1 : 0;
            if (len == winCondition) return true;
        }
        len = 0;
        for (int i = 1-winCondition; i <= winCondition-1; i++) {
            if ( (row - i < 0) || (row - i >= n) || (col + i < 0) || (col + i >= n) ) continue;
            len = board[row-i][col+i] == player ? len+1 : 0;
            if (len == winCondition) return true;
        }

        return false;
    }

    // CHECKS IF BOARD IS FULL
    public static boolean isBoardFull(char[][] board){
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }

    // UNDOES LAST MOVE
    public static void undoMove(char[][] board, Stack<char[][]> st){
        if (st.size() <= 1) return;
        st.pop();
        char[][] lastState = st.peek();

        int n = board.length;
        for (int i = 0; i < n; i++) {
            System.arraycopy(lastState[i], 0, board[i], 0, n);
        }
    }

    // RESET BOARD TO INITIAL STATE
    public static void resetBoard(char[][] board, Stack<char[][]> st){
        st.clear();
        initializeBoard(board, st);
    }

}
