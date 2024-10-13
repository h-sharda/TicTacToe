import javax.swing.*;
import java.awt.*;

public class MenuGUI {

    // STARTING MAIN MENU COMPONENTS
    static JFrame menuFrame = new JFrame("Main Menu");
    static JLabel lblWelcomeMessage = new JLabel("<html>✖️⭕ Welcome to Tic Tac Toe!! ⭕✖️<br></html>");
    static JLabel lblHeaderMessage = new JLabel("<html>Main Menu<br><br></html>");

    static JLabel lblBoardSize = new JLabel("Enter Size of Board:");
    static JTextField txtBoardSize = new JTextField(10);

    static JLabel lblWinSequence = new JLabel("Enter Winning Sequence:");
    static JTextField txtWinSequence = new JTextField(10);

    static JLabel lblNoOfPlayers = new JLabel("Enter Number of Players:");
    static JTextField txtNoOfPlayers = new JTextField(10);

    static JButton btnNext = new JButton("NEXT");
    static JButton btnHelp = new JButton("HELP");


    // MAIN RUNNER FOR THE MENU
    public static void main(String[] args) {

        menuFrame.getContentPane().setBackground(Main.BACKGROUND_COLOR);
        menuFrame.setSize(720, 520);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // ADDING WELCOME MESSAGE
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        gbc.gridx = 0;
        lblWelcomeMessage.setForeground(Main.WELCOME_MESSAGE_COLOR);
        lblWelcomeMessage.setFont(Main.WELCOME_MESSAGE_FONT);
        menuFrame.add(lblWelcomeMessage, gbc);

        // ADDING HEADER MESSAGE
        gbc.gridy = 1;
        lblHeaderMessage.setFont(Main.HEADER_FONT);
        menuFrame.add(lblHeaderMessage, gbc);

        // ADDING INPUT COMPONENTS FOR GAME CONFIGURATIONS
        gbc.gridwidth = 1;

        // INPUT BOARD SIZE
        gbc.gridy = 2;
        gbc.gridx = 0;
        lblBoardSize.setFont(Main.DEFAULT_FONT);
        menuFrame.add(lblBoardSize, gbc);

        gbc.gridx = 1;
        txtBoardSize.setText("3");
        txtBoardSize.setFont(Main.DEFAULT_FONT);
        txtBoardSize.setToolTipText("Board size should be integer, 3 <= size <= 10");
        menuFrame.add(txtBoardSize, gbc);

        // INPUT WIN SEQUENCE
        gbc.gridy = 3;
        gbc.gridx = 0;
        lblWinSequence.setFont(Main.DEFAULT_FONT);
        menuFrame.add(lblWinSequence, gbc);

        gbc.gridx = 1;
        txtWinSequence.setText("3");
        txtWinSequence.setFont(Main.DEFAULT_FONT);
        txtWinSequence.setToolTipText("Winning Sequence should be integer, 3 <= Winning Sequence <= Board size");
        menuFrame.add(txtWinSequence, gbc);

        // INPUT NUMBER OF PLAYERS
        gbc.gridy = 4;
        gbc.gridx = 0;
        lblNoOfPlayers.setFont(Main.DEFAULT_FONT);
        menuFrame.add(lblNoOfPlayers, gbc);

        gbc.gridx = 1;
        txtNoOfPlayers.setText("2");
        txtNoOfPlayers.setFont(Main.DEFAULT_FONT);
        txtNoOfPlayers.setToolTipText("Number of players should be a integer, 2 <= players <= 5");
        menuFrame.add(txtNoOfPlayers, gbc);

        // ADDING HELP BUTTON
        gbc.gridy = 5;
        gbc.gridx = 0;
        btnHelp.setBackground(Main.BUTTON_COLOR);
        btnHelp.setFont(Main.BUTTON_FONT);
        btnHelp.addActionListener(e-> handleHelp());
        btnHelp.setToolTipText("Click to view Help Dialogue");
        menuFrame.add(btnHelp, gbc);

        // ADDING NEXT BUTTON
        gbc.gridx = 1;
        btnNext.setBackground(Main.BUTTON_COLOR);
        btnNext.setFont(Main.BUTTON_FONT);
        btnNext.addActionListener(e -> handleNext());
        btnNext.setToolTipText("Click to input player details");
        menuFrame.add(btnNext, gbc);

        // DISPLAYING THE FRAME
        menuFrame.setVisible(true);
    }


    // BUTTON ACTION LISTENERS
    private static void handleHelp(){
        JLabel label = new JLabel();
        String helpMessage = "<html> " +
                "<b>1)</b> Board Size refers to the number of rows and columns of the tic tac toe board.<br>"+
                "<b>2)</b> Winning Sequence refers to the number of symbols you need in a row to win<br>"+
                "&emsp;&nbsp;for eg: In the classic game Winning Sequence is 3.<br>"+
                "<b>3)</b> You can choose up to 5 players and can have a mixture of real players and<br>" +
                "&emsp;&nbsp;bots (with varying difficulties).<br><br>" +
                "<b>Tip:</b> You could choose any Board Size and Player Configuration as you desire but keep<br>" +
                "&emsp;&emsp;Winning Sequence &lt; 5, otherwise the game would most probably be a draw.<br><br>" +
                "<b>Challenge:</b> Classic game of Tic Tac Toe obviously results in a draw, once you get hang<br>" +
                "&emsp;&emsp;of it. So, try beating the level 5 bot in the bigger boards." +
                "</html>";

        label.setText(helpMessage);
        label.setFont(Main.JOPTIONPANE_FONT);
        JOptionPane.showMessageDialog(menuFrame, label, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void handleNext(){
        try {
            int boardSize = Integer.parseInt(txtBoardSize.getText());
            if (boardSize < 3 || boardSize > Main.MAX_BOARD_SIZE_LIMIT) throw new IllegalArgumentException("3 <= Board Size <= 10");
            Main.boardSize = boardSize;

            int winSequence = Integer.parseInt(txtWinSequence.getText());
            if (winSequence < 3 || winSequence > boardSize) throw new IllegalArgumentException("3 <= Win Sequence <= Board Size");
            Main.winSequence = winSequence;

            int noOfPlayers = Integer.parseInt(txtNoOfPlayers.getText());
            if (noOfPlayers < 2 || noOfPlayers > Main.MAX_PLAYERS_LIMIT) throw new IllegalArgumentException("2 <= Number of Players <= 5");
            Main.noOfPlayers = noOfPlayers;

            // MOVING TO PLAYER INPUT FRAME IF AND ONLY IF ALL INPUTS ARE VALID
            Main.mainMain_to_playerInputMenu();

        } catch (NumberFormatException e){ // CATCHING EXCEPTION CAUSED DURING PARING OF STRING TO INT
            JLabel label = new JLabel();
            String warning = "Please enter valid integers";
            label.setText(warning);
            label.setFont(Main.JOPTIONPANE_FONT);
            JOptionPane.showMessageDialog(menuFrame, label, "Input Warning", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException e) {
            JLabel label = new JLabel();
            label.setText(e.getMessage());
            label.setFont(Main.JOPTIONPANE_FONT);
            JOptionPane.showMessageDialog(menuFrame, label, "Input Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

}
