import javax.swing.*;
import java.awt.*;

public class MenuGUI {

    // FONTS AND COLORS CONSTANTS
    static final Color BACKGROUND_COLOR = new Color(220, 220, 220);
    static final Font DEFAULT_FONT = new Font("Arial Unicode MS", Font.PLAIN, 16);

    static final Color WELCOME_MESSAGE_COLOR = new Color(60, 60, 60);
    static final Font WELCOME_MESSAGE_FONT = new Font("Arial Unicode MS", Font.BOLD, 20);

    static final Color BUTTON_COLOR = new Color(200,200,255);
    static final Font BUTTON_FONT = new Font("Arial Unicode MS", Font.BOLD, 20);


    // STARTING MENU COMPONENTS
    public static JFrame menuFrame = new JFrame("Main Menu");
    public static JLabel lblWelcomeMessage = new JLabel();

    public static JLabel lblBoardSize = new JLabel("Enter Size of Board:");
    public static JTextField txtBoardSize = new JTextField(10);

    public static JLabel lblWinCondition = new JLabel("Enter Win Condition:");
    public static JTextField txtWinCondition = new JTextField(10);

    public static JLabel lblNoOfPlayers = new JLabel("Enter Number of Players:");
    public static JTextField txtNoOfPlayers = new JTextField(10);

    public static JButton btnNext = new JButton("NEXT");
    public static JButton btnHelp = new JButton("HELP");


    // MAIN RUNNER FOR THE MENU
    public static void main(String[] args) {

        menuFrame.getContentPane().setBackground(BACKGROUND_COLOR);
        menuFrame.setSize(720, 480);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setLayout(new GridBagLayout());

        // REDUCES THE TOOLTIP DELAY FOR FASTER RESPONSE
        ToolTipManager.sharedInstance().setInitialDelay(250);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // ADDING WELCOME HEADER MESSAGE
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        gbc.gridx = 0;
        String welcomeMessage = "<html><div style='text-align: center;'>"
                + "Welcome to Tic-Tac-Toe!<br><br>"
                + "Get ready for a game of Strategy and Fun!!!<br><br>"
                + "</div></html>";
        lblWelcomeMessage.setText(welcomeMessage);
        lblWelcomeMessage.setForeground(WELCOME_MESSAGE_COLOR);
        lblWelcomeMessage.setFont(WELCOME_MESSAGE_FONT);
        menuFrame.add(lblWelcomeMessage, gbc);

        // ADDING INPUT COMPONENTS
        gbc.gridwidth = 1;

        // BOARD SIZE INPUT
        gbc.gridy = 1;

        gbc.gridx = 0;
        lblBoardSize.setFont(DEFAULT_FONT);
        menuFrame.add(lblBoardSize, gbc);

        gbc.gridx = 1;
        txtBoardSize.setText("3");
        txtBoardSize.setFont(DEFAULT_FONT);
        txtBoardSize.setToolTipText("Board size should be integer, 3 <= size <= 10");
        menuFrame.add(txtBoardSize, gbc);

        // WIN CONDITION INPUT
        gbc.gridy = 2;

        gbc.gridx = 0;
        lblWinCondition.setFont(DEFAULT_FONT);
        menuFrame.add(lblWinCondition, gbc);

        gbc.gridx = 1;
        txtWinCondition.setText("3");
        txtWinCondition.setFont(DEFAULT_FONT);
        txtWinCondition.setToolTipText("Win Condition should be integer, 3 <= Win condition <= Board size");
        menuFrame.add(txtWinCondition, gbc);

        // NUMBER OF PLAYER INPUT
        gbc.gridy = 3;

        gbc.gridx = 0;
        lblNoOfPlayers.setFont(DEFAULT_FONT);
        menuFrame.add(lblNoOfPlayers, gbc);

        gbc.gridx = 1;
        txtNoOfPlayers.setText("2");
        txtNoOfPlayers.setFont(DEFAULT_FONT);
        txtNoOfPlayers.setToolTipText("Number of players should be a integer, 2 <= players <= 5");
        menuFrame.add(txtNoOfPlayers, gbc);

        // HELP BUTTON
        gbc.gridy = 4;
        gbc.gridx = 0;
        btnHelp.setBackground(BUTTON_COLOR);
        btnHelp.setFont(BUTTON_FONT);
        btnHelp.addActionListener(e-> handleHelp());
        btnHelp.setToolTipText("Click to view Help Dialogue");
        menuFrame.add(btnHelp, gbc);

        // NEXT BUTTON
        gbc.gridx = 1;
        btnNext.setBackground(BUTTON_COLOR);
        btnNext.setFont(BUTTON_FONT);
        btnNext.addActionListener(e -> handleNext());
        btnNext.setToolTipText("Click to input player details");
        menuFrame.add(btnNext, gbc);

    }


    // BUTTON ACTION LISTENERS
    public static void handleHelp(){
        String helpMessage = """
                1) Board Size refers to the number of rows and columns of the tic tac toe board
                2) Win Condition refers to the number of symbols you need in a row to win
                   For eg: In the classic game you need 3 in row to win (horizontally, vertically or diagonally)
                3) You can choose up to 5 players and can have a mixture of bots and real players""";
        JOptionPane.showMessageDialog(menuFrame, helpMessage);
    }

    public static void handleNext(){
        try {
            int boardSize = Integer.parseInt(txtBoardSize.getText());
            if (boardSize < 3 || boardSize > Main.MAX_BOARD_SIZE_LIMIT) throw new IllegalArgumentException("3 <= Board Size <= 10");
            Main.boardSize = boardSize;

            int winCondition = Integer.parseInt(txtWinCondition.getText());
            if (winCondition < 3 || winCondition > boardSize) throw new IllegalArgumentException("3 <= Win Condition <= Board Size");
            Main.winCondition = winCondition;

            int noOfPlayers = Integer.parseInt(txtNoOfPlayers.getText());
            if (noOfPlayers < 2 || noOfPlayers > Main.MAX_PLAYERS_LIMIT) throw new IllegalArgumentException("2 <= Number of Players <= 5");
            Main.noOfPlayers = noOfPlayers;

            Main.mainMain_to_playerInputMenu();

        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(menuFrame, "Please enter valid integers");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(menuFrame, e.getMessage());
        }
    }

}
