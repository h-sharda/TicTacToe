import java.awt.*;

public class Main {

    public static final int MAX_BOARD_SIZE_LIMIT = 10;
    public static final int MAX_PLAYERS_LIMIT = 5;

    public static final char[] DEFAULT_PLAYER_LIST = {'✖', '◯', '△','☐', '◇'};
    public static final char[] DEFAULT_PLAYER_TYPES = {'H', 'H', 'H', 'H', 'H'};

    public static int boardSize = 3;
    public static int winCondition = 3;
    public static int noOfPlayers = 2;
    public static char[] playerList = {'✖', '◯', '△','☐', '◇'};
    public static char[] playerTypes = {'H', 'H', 'H', 'H', 'H'};


    public static void main(String[] args) {
        MenuGUI.main(args);
        MenuGUI.menuFrame.setVisible(true);
    }

    public static void mainMain_to_playerInputMenu(){
        MenuGUI.menuFrame.setVisible(false);
        InputPlayers.main(new String[] {""});
    }

    public static void playerInputMenu_to_mainMenu(){
        InputPlayers.playerInputFrame.setVisible(false);
        MenuGUI.menuFrame.setVisible(true);
    }

    public static void playerInputMenu_to_gameMenu(){
        InputPlayers.playerInputFrame.setVisible(false);
        BoardGUI.main(new String[] {""});
    }

    public static void gameMenu_to_mainMenu(){
        BoardGUI.gameFrame.setVisible(false);
        MenuGUI.menuFrame.setVisible(true);
    }

}
