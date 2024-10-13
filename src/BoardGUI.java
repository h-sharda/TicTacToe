import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class BoardGUI {

    // DECLARING BOARD AND PLAYER CONFIGURATIONS
    private static int boardSize;
    private static int winSequence;
    private static int noOfPlayers;
    private static char[] playerList;
    private static char[] playerTypes;

    private static int playerNumber;
    private static boolean gameEnd;

    static char[][] actualBoard;
    static Stack<char[][]> boardHistory;


    // DECLARING GAME SCREEN COMPONENTS
    static JFrame gameFrame;
    static JLabel lblDisplayCurrentPlayer;

    static JPanel boardPanel;
    static JButton[][] displayBoard;

    static JPanel controlPanel;
    static JButton btnUndo;
    static JButton btnReset = new JButton("RESET");
    static JButton btnMainMenu = new JButton("MAIN MENU");


    // MAIN RUNNER FOR THE GAME SCREEN
    public static void main(String[] args) {

        // INITIALIZING BOARD AND PLAYER CONFIGURATIONS
        boardSize = Main.boardSize;
        winSequence = Main.winSequence;
        noOfPlayers = Main.noOfPlayers;

        playerList = new char[noOfPlayers];
        playerTypes = new char[noOfPlayers];
        for (int i = 0; i < noOfPlayers; i++) {
            playerList[i] = Main.playerList[i];
            playerTypes[i] = Main.playerTypes[i];
        }

        actualBoard = new char[boardSize][boardSize];
        boardHistory = new Stack<>();

        // RESETTING THE FRAME DYNAMICALLY
        if (gameFrame != null) gameFrame.dispose();
        gameFrame = new JFrame("Game Menu");

        // SETTING UP THE GAME SCREEN
        gameFrame.getContentPane().setBackground(Main.BACKGROUND_COLOR);
        gameFrame.setSize(boardSize * 75, boardSize * 75 + 80);
        gameFrame.setMinimumSize(new Dimension(500,500));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setLayout(new BorderLayout());

        Functions.initializeBoard(actualBoard, boardHistory);
        playerNumber = 0;
        gameEnd = false;

        // ADDING WELCOME HEADER MESSAGE
        String displayCurrentPlayerText = "<html>"+playerList[playerNumber] +"'s TURN</html>";
        lblDisplayCurrentPlayer = new JLabel(displayCurrentPlayerText, SwingConstants.CENTER);
        lblDisplayCurrentPlayer.setFont(Main.HEADER_FONT);
        lblDisplayCurrentPlayer.setPreferredSize(new Dimension(boardSize * 75, 40));
        gameFrame.add(lblDisplayCurrentPlayer, "North");

        boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        displayBoard = new JButton[boardSize][boardSize];

        // INITIALIZING THE DISPLAY BOARD
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int row = i, col =j;
                displayBoard[i][j] = new JButton();
                displayBoard[i][j].setFont(Main.SYMBOL_FONT);
                displayBoard[i][j].setBackground(Main.BOARD_BACKGROUND_COLOR);
                displayBoard[i][j].setPreferredSize(new Dimension(100, 100));
                displayBoard[i][j].addActionListener(e-> handleClick(row, col));
                boardPanel.add(displayBoard[i][j]);
            }
        }
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gameFrame.add(boardPanel, "Center");

        // INITIALIZING CONTROL PANEL
        controlPanel = new JPanel(new BorderLayout(5,0));

        btnMainMenu = new JButton("MAIN MENU");
        btnMainMenu.setFont(Main.BUTTON_FONT);
        btnMainMenu.setBackground(Main.BUTTON_COLOR);
        btnMainMenu.setPreferredSize(new Dimension(165, 40));
        btnMainMenu.addActionListener(e-> handleMainMenu());
        btnMainMenu.setToolTipText("Takes you back to Main Menu");
        controlPanel.add(btnMainMenu, "West");

        btnUndo = new JButton("UNDO");
        btnUndo.setFont(Main.BUTTON_FONT);
        btnUndo.setBackground(Main.BUTTON_COLOR);
        btnUndo.setPreferredSize(new Dimension(165, 40));
        btnUndo.addActionListener(e-> handleUndo());
        btnUndo.setToolTipText("Undoes the last human move made");
        controlPanel.add(btnUndo, "Center");

        btnReset = new JButton("RESET");
        btnReset.setFont(Main.BUTTON_FONT);
        btnReset.setBackground(Main.BUTTON_COLOR);
        btnReset.setPreferredSize(new Dimension(165, 40));
        btnReset.addActionListener(e-> handleReset());
        btnReset.setToolTipText("Reset the board entirely");
        controlPanel.add(btnReset, "East");

        gameFrame.add(controlPanel, "South");

        // DISPLAYING THE FRAME
        gameFrame.setVisible(true);

        // MAKING THE BOT MOVE IF ITS BOT MOVE IN STARTING
        makeBotTurn();
    }


    // UPDATES THE DISPLAY BOARD AFTER EVERY MOVE
    private static void updateDisplayBoard(){
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                displayBoard[i][j].setText("" + actualBoard[i][j]);
                displayBoard[i][j].setBackground(Main.BOARD_BACKGROUND_COLOR);
            }
        }
    }


    // HANDLES THE BOT MOVES
    private static void makeBotTurn() {
        if (playerTypes[playerNumber] == 'C') {
            char currentPlayer = playerList[playerNumber];

            int[] botMove = Bot.makeMove(actualBoard, boardHistory, currentPlayer, playerNumber, winSequence, noOfPlayers, playerList);
            int row = botMove[0], col = botMove[1];

            displayBoard[row][col].setText(""+currentPlayer);
            updateDisplayBoard();
            displayBoard[row][col].setBackground(Main.LAST_MOVE_COLOR);

            if(Functions.checkWinner(actualBoard, winSequence, currentPlayer, row, col)){
                String message = "Player " + (playerNumber+1) + ": '" + currentPlayer+"' WON THE GAME";
                JOptionPane.showMessageDialog(gameFrame, message);
                gameEnd = true;
            } else if (Functions.isBoardFull(actualBoard)){
                JOptionPane.showMessageDialog(gameFrame, "GAME IS DRAW");
                gameEnd = true;
            }

            playerNumber = (playerNumber+1)%noOfPlayers;
            lblDisplayCurrentPlayer.setText("<html>"+playerList[playerNumber] +"'s TURN</html>");
        } else return;

        if (!gameEnd) makeBotTurn();
    }

    // HANDLE HUMAN MOVES
    private static void handleClick(int row, int col){

        char currentPlayer = playerList[playerNumber];
        if (Functions.makeMove(actualBoard, boardHistory, currentPlayer, row, col)){
            displayBoard[row][col].setText(""+currentPlayer);
            updateDisplayBoard();
            displayBoard[row][col].setBackground(Main.LAST_MOVE_COLOR);

            if(Functions.checkWinner(actualBoard, winSequence, currentPlayer, row, col)){
                JLabel label = new JLabel("", SwingConstants.CENTER);
                String message = "<html>Player " + (playerNumber+1) + ": '" + currentPlayer+"' WON THE GAME</html>";
                label.setText(message);
                label.setFont(Main.JOPTIONPANE_FONT);
                JOptionPane.showMessageDialog(gameFrame, label, "Game Over", JOptionPane.PLAIN_MESSAGE);
                gameEnd = true;
            } else if (Functions.isBoardFull(actualBoard)){
                JLabel label = new JLabel("", SwingConstants.CENTER);
                String message = "GAME IS DRAW";
                label.setText(message);
                label.setFont(Main.JOPTIONPANE_FONT);
                JOptionPane.showMessageDialog(gameFrame, label, "Game Over", JOptionPane.PLAIN_MESSAGE);
                gameEnd = true;
            }

            playerNumber = (playerNumber+1)% noOfPlayers;
            lblDisplayCurrentPlayer.setText("<html>"+playerList[playerNumber] +"'s TURN</html>");

            if (!gameEnd) makeBotTurn();

        }
    }


    // BUTTON ACTION LISTENERS
    private static void handleMainMenu(){
        Main.gameMenu_to_mainMenu();
    }

    private static void handleUndo(){
        if (boardHistory.size() <= 1) return;

        int prevPlayer = (playerNumber - 1 + noOfPlayers) % noOfPlayers;
        boolean humanMoveFound = false;

        while (!humanMoveFound) {
            if(boardHistory.size() <= 1) {
                handleReset();
                return;
            }
            Functions.undoMove(actualBoard, boardHistory);
            if (playerTypes[prevPlayer] == 'H') humanMoveFound = true;
            else prevPlayer = (prevPlayer - 1 + noOfPlayers) % noOfPlayers;
        }

        // NOW THE BOARD WILL BE AT THE LAST HUMAN MOVE
        System.out.println("Undoing the last human move");
        playerNumber = prevPlayer;
        gameEnd = false;

        lblDisplayCurrentPlayer.setText("<html>"+playerList[playerNumber] +"'s TURN</html>");
        updateDisplayBoard();
    }

    private static void handleReset(){
        Functions.resetBoard(actualBoard, boardHistory);
        System.out.println("Resetting the board");
        playerNumber = 0;
        gameEnd = false;
        lblDisplayCurrentPlayer.setText("<html>"+playerList[playerNumber] +"'s TURN</html>");
        updateDisplayBoard();
        makeBotTurn();
    }

}
