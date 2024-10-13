import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class InputPlayers {

    // DECLARING NUMBER OF PLAYERS
    private static int noOfPlayers;


    // DECLARING PLAYER INPUT MENU COMPONENTS
    static JFrame playerInputFrame;
    static JLabel lblHeaderMessage;

    static ArrayList<JLabel> lblPlayersList;
    static ArrayList<JTextField> txtPlayerSymbolsList;
    static ArrayList<JComboBox<String>> cmbPlayerTypesList;

    static JButton btnStart;
    static JButton btnBack;
    static JButton btnResetSymbols;


    // MAIN RUNNER FOR THE PLAYER INPUT MENU
    public static void main(String[] args) {

        noOfPlayers = Main.noOfPlayers;

        // RESETTING THE LISTS TO PREVENT ANY ERROR CAUSED BY BACK AND FORTH MOVEMENT
        for (int i = 0; i< noOfPlayers; i++){
            Main.playerList[i] = Main.DEFAULT_PLAYER_LIST[i];
            Main.playerTypes[i] = Main.DEFAULT_PLAYER_TYPES[i];
        }

        // RESETTING THE FRAME DYNAMICALLY
        if(playerInputFrame != null) playerInputFrame.dispose();
        playerInputFrame = new JFrame("Player Input Menu");

        // SETTING UP THE PLAYER INPUT MENU
        playerInputFrame.getContentPane().setBackground(Main.BACKGROUND_COLOR);
        playerInputFrame.setSize(720, 520);
        playerInputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerInputFrame.setLocationRelativeTo(null);
        playerInputFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.CENTER;

        // ADDING HEADER MESSAGE
        gbc.gridwidth = 3;
        gbc.gridy = 0;
        gbc.gridx = 0;
        lblHeaderMessage = new JLabel("<html>Customize Players<br<br></html>");
        lblHeaderMessage.setFont(Main.HEADER_FONT);
        playerInputFrame.add(lblHeaderMessage, gbc);

        // DYNAMICALLY ADDING THE PLAYER INPUT OPTION
        lblPlayersList = new ArrayList<>();
        txtPlayerSymbolsList = new ArrayList<>();
        cmbPlayerTypesList = new ArrayList<>();

        gbc.gridwidth = 1;

        for (int i = 0; i < noOfPlayers; i++) {
            int playerNo = i;
            gbc.gridy = i+1;

            gbc.gridx = 0;
            lblPlayersList.add(new JLabel("Player " + (i+1) + " : "));
            lblPlayersList.get(i).setFont(Main.DEFAULT_FONT);
            playerInputFrame.add(lblPlayersList.get(i), gbc);

            gbc.gridx =1;
            txtPlayerSymbolsList.add(new JTextField(5));
            txtPlayerSymbolsList.get(i).setFont(Main.SYMBOL_FONT);
            txtPlayerSymbolsList.get(i).setText(""+ Main.playerList[i]);
            txtPlayerSymbolsList.get(i).setHorizontalAlignment(JTextField.CENTER);
            playerInputFrame.add(txtPlayerSymbolsList.get(i), gbc);

            gbc.gridx = 2;
            String[] playerTypeOptions = new String[] {"Human", "Computer 1", "Computer 2", "Computer 3", "Computer 4", "Computer 5"};
            cmbPlayerTypesList.add( new JComboBox<>(playerTypeOptions));
            cmbPlayerTypesList.get(i).addActionListener(e-> handlePlayerType(playerNo));
            cmbPlayerTypesList.get(i).setFont(Main.DEFAULT_FONT);
            playerInputFrame.add(cmbPlayerTypesList.get(i), gbc);
        }

        // ADDING NAVIGATION BUTTONS
        gbc.gridy = noOfPlayers +1;

        // BACK BUTTON TO MAIN MENU
        btnBack = new JButton("BACK");
        gbc.gridx = 0;
        btnBack.setBackground(Main.BUTTON_COLOR);
        btnBack.setFont(Main.BUTTON_FONT);
        btnBack.addActionListener(e-> handleBack());
        btnBack.setToolTipText("Click to go back to Main Menu");
        playerInputFrame.add(btnBack, gbc);

        // RESET SYMBOLS BUTTON
        btnResetSymbols = new JButton("RESET");
        gbc.gridx = 1;
        btnResetSymbols.setBackground(Main.BUTTON_COLOR);
        btnResetSymbols.setFont(Main.BUTTON_FONT);
        btnResetSymbols.addActionListener(e-> handleReset());
        btnResetSymbols.setToolTipText("Click to view various symbols to use");
        playerInputFrame.add(btnResetSymbols, gbc);

        // GAME START BUTTON
        btnStart = new JButton("START");
        gbc.gridx = 2;
        btnStart.setBackground(Main.BUTTON_COLOR);
        btnStart.setFont(Main.BUTTON_FONT);
        btnStart.addActionListener(e-> handleStart() );
        btnStart.setToolTipText("Click to start the game");
        playerInputFrame.add(btnStart, gbc);

        // DISPLAYING THE FRAME
        playerInputFrame.setVisible(true);
    }


    // HANDLE TYPE OF PLAYERS
    private static void handlePlayerType(int playerNo){
        int selection = cmbPlayerTypesList.get(playerNo).getSelectedIndex();

        if (selection == 0) Main.playerTypes[playerNo] = 'H';
        else if (selection == 1) Main.playerTypes[playerNo] = '1';
        else if (selection == 2) Main.playerTypes[playerNo] = '2';
        else if (selection == 3) Main.playerTypes[playerNo] = '3';
        else if (selection == 4) Main.playerTypes[playerNo] = '4';
        else Main.playerTypes[playerNo] = '5';
    }


    // BUTTON ACTION LISTENERS
    private static void handleBack(){
        Main.playerInputMenu_to_mainMenu();
    }

    private static void handleReset(){
        for (int i = 0; i< noOfPlayers; i++){
            txtPlayerSymbolsList.get(i).setText("" + Main.DEFAULT_PLAYER_LIST[i]);
        }
    }

    private static void handleStart(){
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < noOfPlayers; i++) {
            String symbol = txtPlayerSymbolsList.get(i).getText();
            if (symbol.length() != 1){
                JLabel label = new JLabel();
                String message = "All symbols must be a single character";
                label.setText(message);
                label.setFont(Main.JOPTIONPANE_FONT);
                JOptionPane.showMessageDialog(playerInputFrame, label, "Input Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!set.add(symbol.charAt(0))) {
                JLabel label = new JLabel();
                String message = "All symbols must be unique to a player";
                label.setText(message);
                label.setFont(Main.JOPTIONPANE_FONT);
                JOptionPane.showMessageDialog(playerInputFrame, label, "Input Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Main.playerList[i] = symbol.charAt(0);
        }

        Main.playerInputMenu_to_gameMenu();
    }

}
