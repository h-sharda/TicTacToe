import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class BoardGUI {

    // FONTS AND COLORS CONSTANTS
    static final Color BACKGROUND_COLOR = new Color(220, 220, 220);

    static final Font LABEL_FONT = new Font("Arial Unicode MS", Font.PLAIN, 20);
    static final Font SYMBOL_FONT = new Font("Arial Unicode MS", Font.BOLD, 30);

    static final Color BUTTON_COLOR = new Color(200,200,255);
    static final Font BUTTON_FONT = new Font("Arial Unicode MS", Font.PLAIN, 18);


    public static JFrame gameFrame;

    static int boardSize;
    static int winCondition;
    static int noOfPlayers;
    static char[] playerList;
    static char[] playerTypes;

    static int playerNumber;
    static boolean gameEnd;

    public static char[][] actualBoard;
    public static Stack<char[][]> boardHistory;

    static JPanel boardPanel;
    static JButton[][] displayBoard;
    static JLabel lblDisplayCurrentPlayer;
    static JPanel controlPanel;
    static JButton btnUndo;
    static JButton btnReset = new JButton("RESET");
    static JButton btnMainMenu = new JButton("MAIN MENU");


    public static void main(String[] args) {
        boardSize = Main.boardSize;
        winCondition = Main.winCondition;
        noOfPlayers = Main.noOfPlayers;

        playerList = new char[noOfPlayers];
        playerTypes = new char[noOfPlayers];
        for (int i = 0; i < noOfPlayers; i++) {
            playerList[i] = Main.playerList[i];
            playerTypes[i] = Main.playerTypes[i];
        }

        actualBoard = new char[boardSize][boardSize];
        boardHistory = new Stack<>();

        if (gameFrame != null) gameFrame.dispose();
        gameFrame = new JFrame("Game Menu");

        gameFrame.getContentPane().setBackground(BACKGROUND_COLOR);
        gameFrame.setSize(boardSize * 75, boardSize * 75 + 80);
        gameFrame.setMinimumSize(new Dimension(500,500));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setLayout(new BorderLayout());

        Functions.initializeBoard(actualBoard, boardHistory);

        playerNumber = 0;
        gameEnd = false;

        // ADDING WELCOME HEADER MESSAGE
        lblDisplayCurrentPlayer = new JLabel(playerList[playerNumber] +"'s TURN", SwingConstants.CENTER);
        lblDisplayCurrentPlayer.setFont(LABEL_FONT);
        lblDisplayCurrentPlayer.setPreferredSize(new Dimension(boardSize * 75, 40));
        gameFrame.add(lblDisplayCurrentPlayer, "North");

        boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        displayBoard = new JButton[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int row = i, col =j;
                displayBoard[i][j] = new JButton();
                displayBoard[i][j].setFont(SYMBOL_FONT);
                displayBoard[i][j].setPreferredSize(new Dimension(100, 100));
                displayBoard[i][j].addActionListener(e-> handleClick(row, col));
                boardPanel.add(displayBoard[i][j]);
            }
        }
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gameFrame.add(boardPanel, "Center");

        controlPanel = new JPanel(new BorderLayout(5,0));

        btnMainMenu = new JButton("MAIN MENU");
        btnMainMenu.setFont(BUTTON_FONT);
        btnMainMenu.setBackground(BUTTON_COLOR);
        btnMainMenu.setPreferredSize(new Dimension(150, 40));
        btnMainMenu.addActionListener(e-> handleMainMenu());
        btnMainMenu.setToolTipText("Takes you back to Main Menu");
        controlPanel.add(btnMainMenu, "West");

        btnUndo = new JButton("UNDO");
        btnUndo.setFont(BUTTON_FONT);
        btnUndo.setBackground(BUTTON_COLOR);
        btnUndo.setPreferredSize(new Dimension(150, 40));
        btnUndo.addActionListener(e-> handleUndo());
        btnUndo.setToolTipText("Undoes the last human move made");
        controlPanel.add(btnUndo, "Center");

        btnReset = new JButton("RESET");
        btnReset.setFont(BUTTON_FONT);
        btnReset.setBackground(BUTTON_COLOR);
        btnReset.setPreferredSize(new Dimension(150, 40));
        btnReset.addActionListener(e-> handleReset());
        btnReset.setToolTipText("Reset the board entirely");
        controlPanel.add(btnReset, "East");

        gameFrame.add(controlPanel, "South");

        makeBotTurn();

        gameFrame.setVisible(true);
    }

    public static void makeBotTurn() {
        if (playerTypes[playerNumber] == 'C') {
            char currentPlayer = playerList[playerNumber];

            int[] botMove = Bot.makeMove(actualBoard, boardHistory, currentPlayer);

            int row = botMove[0], col = botMove[1];
            displayBoard[row][col].setText(""+currentPlayer);

            if(Functions.checkWinner(actualBoard, winCondition, currentPlayer, row, col)){
                String message = "Player " + (playerNumber+1) + ": '" + currentPlayer+"' WON THE GAME";
                JOptionPane.showMessageDialog(gameFrame, message);
                gameEnd = true;
            } else if (Functions.isBoardFull(actualBoard)){
                JOptionPane.showMessageDialog(gameFrame, "GAME IS DRAW");
                gameEnd = true;
            }
            playerNumber = (playerNumber+1)%noOfPlayers;
            lblDisplayCurrentPlayer.setText(playerList[playerNumber] +"'s TURN");
        } else return;

        if (!gameEnd) makeBotTurn();
    }

    public static void handleClick(int row, int col){

        char currentPlayer = playerList[playerNumber];
        if (Functions.makeMove(actualBoard, boardHistory, currentPlayer, row, col)){
            displayBoard[row][col].setText(""+currentPlayer);
            if(Functions.checkWinner(actualBoard, winCondition, currentPlayer, row, col)){
                String message = "Player " + (playerNumber+1) + ": '" + currentPlayer+"' WON THE GAME";
                JOptionPane.showMessageDialog(gameFrame, message);
                gameEnd = true;
            } else if (Functions.isBoardFull(actualBoard)){
                JOptionPane.showMessageDialog(gameFrame, "GAME IS DRAW");
                gameEnd = true;
            }

            playerNumber = (playerNumber+1)% noOfPlayers;
            lblDisplayCurrentPlayer.setText(playerList[playerNumber] +"'s TURN");
            if (!gameEnd) makeBotTurn();

        }
    }

    public static void handleMainMenu(){
        Main.gameMenu_to_mainMenu();
    }

    public static void handleUndo(){

        if (boardHistory.size() <= 1) return;

        int prevPlayer = (playerNumber - 1 + noOfPlayers) % noOfPlayers;
        while (playerTypes[prevPlayer] == 'C') {
            if(boardHistory.size() <= 1) break;
            Functions.undoMove(actualBoard, boardHistory);
            prevPlayer = (prevPlayer - 1 + noOfPlayers) % noOfPlayers;
        }

        // If the starting players where bots and the board is reset to initial state
        if(boardHistory.size() <= 1) {
            playerNumber = 0;
            makeBotTurn(); // this will only make initial moves if starting players are bot
            updateDisplayBoard();
            return;
        }

        // Now the board state is at the last human move
        Functions.undoMove(actualBoard, boardHistory);
        playerNumber = prevPlayer;
        gameEnd = false;

        lblDisplayCurrentPlayer.setText(playerList[playerNumber] +"'s TURN");
        updateDisplayBoard();
    }

    public static void handleReset(){
        Functions.resetBoard(actualBoard, boardHistory);
        playerNumber = 0;
        gameEnd = false;
        lblDisplayCurrentPlayer.setText(playerList[playerNumber] +"'s TURN");
        makeBotTurn();
        updateDisplayBoard();
    }

    public static void updateDisplayBoard(){
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                displayBoard[i][j].setText("" + actualBoard[i][j]);
            }
        }
    }
}