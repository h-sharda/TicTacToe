import javax.swing.*;
import java.awt.*;

public class Main {

    // INITIALIZING DEFAULT LISTS AND LIMITS CONSTANTS
    public static final int MAX_BOARD_SIZE_LIMIT = 10;
    public static final int MAX_PLAYERS_LIMIT = 5;

    public static final char[] DEFAULT_PLAYER_LIST = {'✖', '◯', '△','☐', '◇'};
    public static final char[] DEFAULT_PLAYER_TYPES = {'H', 'H', 'H', 'H', 'H'};


    // COMMON FONTS AND COLORS ACROSS THE GAME
    public static final Color BACKGROUND_COLOR = new Color(220, 220, 220);
    public static final Color WELCOME_MESSAGE_COLOR = new Color(60, 60, 60);
    public static final Color BUTTON_COLOR = new Color(200,200, 255);
    public static final Color BOARD_BACKGROUND_COLOR = new Color(211, 211, 211);
    public static final Color LAST_MOVE_COLOR = new Color(255, 250, 205);

    public static final Font WELCOME_MESSAGE_FONT = new Font("Goudy Old Style", Font.BOLD, 35);
    public static final Font HEADER_FONT = new Font("Century Gothic", Font.PLAIN, 28);
    public static final Font DEFAULT_FONT = new Font("Century Gothic", Font.PLAIN, 18);
    public static final Font SYMBOL_FONT = new Font("Arial Unicode MS", Font.BOLD, 30);
    public static final Font BUTTON_FONT = new Font("Century Gothic", Font.PLAIN, 22);
    public static final Font TOOLTIP_FONT = UIManager.getFont("ToolTip.font");
    public static final Font JOPTIONPANE_FONT = new Font("Century Gothic", Font.PLAIN, 14);


    // SETTING UP THE CLASSIC GAME CONFIGURATIONS
    public static int boardSize = 3;
    public static int winSequence = 3;
    public static int noOfPlayers = 2;
    public static char[] playerList = {'✖', '◯', '△','☐', '◇'};
    public static char[] playerTypes = {'H', 'H', 'H', 'H', 'H'};


    // BUILDING THE MAIN MENU
    public static void main(String[] args) {

        // REDUCES THE TOOLTIP DELAY FOR FASTER RESPONSE
        ToolTipManager.sharedInstance().setInitialDelay(250);
        // SETTING UP A COMMON TOOL TIP FONT THROUGHT THE GAME
        UIManager.put("ToolTip.font", TOOLTIP_FONT);

        MenuGUI.main(new String[] {""});
    }


    // HANDLING BACK AND FORTH INTERACRIONS
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
