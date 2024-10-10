import java.util.Random;

public class Bot {

    static int BOARD_SIZE = TicTacToeGUI.BOARD_SIZE;

    static Random random = new Random();
    static int BOT_NUMBER;
    static char BOT_CHAR = TicTacToeGUI.PLAYER_LIST[BOT_NUMBER];

    Bot(int bot_number){
        BOT_NUMBER = bot_number-1;
    }

    public void makeMove(){
        boolean moved = false;

        while (!moved) {
            int randomRow = random.nextInt(BOARD_SIZE);
            int randomCol = random.nextInt(BOARD_SIZE);

            if (TicTacToeGUI.ACTUAL_BOARD[randomRow][randomCol] == ' ') {
                TicTacToeGUI.ACTUAL_BOARD[randomRow][randomCol] = BOT_CHAR;
                TicTacToeGUI.DISPLAY_BOARD[randomRow][randomCol].setText(""+BOT_CHAR);
                moved = true;
            }
        }

    }

}
