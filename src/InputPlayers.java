import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class InputPlayers {

    static int noOfPlayer;

    // FONTS AND COLORS CONSTANTS
    static final Color BACKGROUND_COLOR = new Color(220, 220, 220);

    static final Font DEFAULT_FONT = new Font("Arial Unicode MS", Font.PLAIN, 16);
    static final Font SYMBOL_FONT = new Font("Arial Unicode MS", Font.BOLD, 20);

    static final Color WELCOME_MESSAGE_COLOR = new Color(60, 60, 60);
    static final Font WELCOME_MESSAGE_FONT = new Font("Arial Unicode MS", Font.BOLD, 25);

    static final Color BUTTON_COLOR = new Color(200,200,255);
    static final Font BUTTON_FONT = new Font("Arial Unicode MS", Font.BOLD, 20);

    // INITIALIZING PLAYER INPUT MENU COMPONENTS
    public static JFrame playerInputFrame;
    public static ArrayList<JLabel> lblPlayers;
    public static ArrayList<JTextField> txtPlayerSymbols;
    public static ArrayList<JComboBox<String>> cmbPlayerTypes;
    public static JLabel lblWelcomeMessage;
    public static JButton btnStart;
    public static JButton btnBack;
    public static JButton btnResetSymbols;

    // COMMENT
    public static void main(String[] args) {

        noOfPlayer = Main.noOfPlayers;

        // RESETTING THE LISTS TO PREVENT ERROR CAUSED BY BACK AND FORTH MOVEMENT
        for (int i=0; i< noOfPlayer; i++){
            Main.playerList[i] = Main.DEFAULT_PLAYER_LIST[i];
            Main.playerTypes[i] = Main.DEFAULT_PLAYER_TYPES[i];
        }

        // RESETTING THE FRAME DYNAMICALLY EVERY TIME
        if(playerInputFrame != null) playerInputFrame.dispose();
        playerInputFrame = new JFrame("Player Input Menu");

        // SETTING UP THE MENU
        playerInputFrame.getContentPane().setBackground(BACKGROUND_COLOR);
        playerInputFrame.setSize(720, 480);
        playerInputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerInputFrame.setLocationRelativeTo(null);
        playerInputFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        // ADDING WELCOME HEADER MESSAGE
        lblWelcomeMessage = new JLabel("Customize Players");
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 3;
        gbc.gridy = 0;
        gbc.gridx = 0;
        lblWelcomeMessage.setFont(WELCOME_MESSAGE_FONT);
        lblWelcomeMessage.setForeground(WELCOME_MESSAGE_COLOR);
        playerInputFrame.add(lblWelcomeMessage, gbc);

        // DYNAMICALLY ADDING THE PLAYER INPUT OPTION
        lblPlayers = new ArrayList<>();
        txtPlayerSymbols = new ArrayList<>();
        cmbPlayerTypes = new ArrayList<>();

        gbc.gridwidth = 1;

        for (int i = 0; i < noOfPlayer; i++) {
            int playerNo = i;
            gbc.gridy = i+1;

            gbc.gridx = 0;
            lblPlayers.add(new JLabel("Player " + (i+1) + " : "));
            lblPlayers.get(i).setFont(DEFAULT_FONT);
            playerInputFrame.add(lblPlayers.get(i), gbc);

            gbc.gridx =1;
            txtPlayerSymbols.add(new JTextField(5));
            txtPlayerSymbols.get(i).setFont(SYMBOL_FONT);
            txtPlayerSymbols.get(i).setText(""+ Main.playerList[i]);
            txtPlayerSymbols.get(i).setHorizontalAlignment(JTextField.CENTER);
            playerInputFrame.add(txtPlayerSymbols.get(i), gbc);

            gbc.gridx = 2;
            cmbPlayerTypes.add( new JComboBox<>(new String[] {"Human", "Computer"}));
            cmbPlayerTypes.get(i).addActionListener(e-> {
                int selection = cmbPlayerTypes.get(playerNo).getSelectedIndex();
                Main.playerTypes[playerNo] = selection == 0? 'H': 'C';
            });
            cmbPlayerTypes.get(i).setFont(DEFAULT_FONT);
            playerInputFrame.add(cmbPlayerTypes.get(i), gbc);
        }

        // ADDING NAVIGATION BUTTONS
        gbc.gridy = noOfPlayer+1;

        // BACK BUTTON TO MAIN MENU
        btnBack = new JButton("BACK");
        gbc.gridx = 0;
        btnBack.setBackground(BUTTON_COLOR);
        btnBack.setFont(BUTTON_FONT);
        btnBack.addActionListener(e-> handleBack());
        btnBack.setToolTipText("Click to go back to Main Menu");
        playerInputFrame.add(btnBack, gbc);

        // RESET SYMBOLS BUTTON
        btnResetSymbols = new JButton("RESET");
        gbc.gridx = 1;
        btnResetSymbols.setBackground(BUTTON_COLOR);
        btnResetSymbols.setFont(BUTTON_FONT);
        btnResetSymbols.addActionListener(e-> handleReset());
        btnResetSymbols.setToolTipText("Click to view various symbols to use");
        playerInputFrame.add(btnResetSymbols, gbc);

        // GAME START BUTTON
        btnStart = new JButton("START");
        gbc.gridx = 2;
        btnStart.setBackground(BUTTON_COLOR);
        btnStart.setFont(BUTTON_FONT);
        btnStart.addActionListener(e-> handleStart() );
        btnStart.setToolTipText("Click to start the game");
        playerInputFrame.add(btnStart, gbc);

        // DISPLAYING THE PANE
        playerInputFrame.setVisible(true);
    }

    public static void handleBack(){
        Main.playerInputMenu_to_mainMenu();
    }

    public static void handleReset(){
        for (int i =0; i< noOfPlayer; i++){
            txtPlayerSymbols.get(i).setText("" + Main.DEFAULT_PLAYER_LIST[i]);
        }
    }

    public static void handleStart(){
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < noOfPlayer; i++) {
            String symbol = txtPlayerSymbols.get(i).getText();
            if (symbol.length() != 1){
                String message = "All symbols must be a single character";
                JOptionPane.showMessageDialog(playerInputFrame, message);
                return;
            }
            if (!set.add(symbol.charAt(0))) {
                String message = "All symbols must be unique to a player";
                JOptionPane.showMessageDialog(playerInputFrame, message);
                return;
            }
            Main.playerList[i] = symbol.charAt(0);
        }

        Main.playerInputMenu_to_gameMenu();
    }

}
