import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private static final int SIZE = 7;
    private static final int WIN_CONDITION = 4;
    private static final int NUMBER_OF_PLAYERS = 5;
    private final JButton[][] buttons;
    private char currentPlayer;
    private boolean gameEnded = false;
    private final Stack<Move> moveStack = new Stack<>();
    private final JLabel statusLabel = new JLabel("", SwingConstants.CENTER);

    // Player symbols
    private final char[] playerSymbols = {'X','Y' , '\u0394','\u25C7', '\u2606'};

    public TicTacToeGUI() {
        buttons = new JButton[SIZE][SIZE];
        currentPlayer = playerSymbols[0]; // X starts first

        setTitle("Tic Tac Toe");
        setSize(80 * SIZE, 80 * SIZE + 100); // Adjusted size based on board size
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        statusLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 20));
        add(statusLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(SIZE, SIZE));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial Unicode MS", Font.PLAIN, 50));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                boardPanel.add(buttons[i][j]);
            }
        }

        JPanel controlPanel = new JPanel();
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> undoMove());
        controlPanel.add(undoButton);

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> restartGame());
        controlPanel.add(restartButton);

        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        updateStatusLabel();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameEnded) return;

        JButton button = (JButton) e.getSource();
        if (button.getText().isEmpty()) {
            button.setText(String.valueOf(currentPlayer));
            moveStack.push(new Move(button, currentPlayer));
            if (checkWin()) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " Wins!");
                statusLabel.setText("Game Over");
                gameEnded = true;
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a Tie!");
                statusLabel.setText("Game Over");
                gameEnded = true;
            } else {
                switchPlayer();
                updateStatusLabel();
            }
        }
    }

    private void undoMove() {
        if (moveStack.isEmpty() || gameEnded) return;

        Move lastMove = moveStack.pop();
        lastMove.button.setText("");
        currentPlayer = lastMove.player; // Revert to the previous player
        updateStatusLabel();
    }

    private void restartGame() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j].setText("");
            }
        }
        moveStack.clear();
        currentPlayer = playerSymbols[0]; // X starts first
        gameEnded = false;
        updateStatusLabel();
    }

    private void switchPlayer() {
        int currentIndex = -1;
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            if (currentPlayer == playerSymbols[i]) {
                currentIndex = i;
                break;
            }
        }
        if (currentIndex == -1) return;
        currentPlayer = playerSymbols[(currentIndex + 1) % NUMBER_OF_PLAYERS];
    }

    private void updateStatusLabel() {
        statusLabel.setText("Player " + currentPlayer + "'s Turn");
    }

    private boolean checkWin() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (checkDirection(i, j, 1, 0) || // Horizontal
                        checkDirection(i, j, 0, 1) || // Vertical
                        checkDirection(i, j, 1, 1) || // Diagonal down-right
                        checkDirection(i, j, 1, -1))  // Diagonal down-left
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int rowDir, int colDir) {
        // Check if the direction is within bounds
        if (row + (WIN_CONDITION - 1) * rowDir >= SIZE || row + (WIN_CONDITION - 1) * rowDir < 0 ||
                col + (WIN_CONDITION - 1) * colDir >= SIZE || col + (WIN_CONDITION - 1) * colDir < 0) {
            return false;
        }

        // Get the starting symbol
        String text = buttons[row][col].getText();
        if (text.isEmpty()) return false;
        char symbol = text.charAt(0);

        // Check if the rest of the line matches
        for (int k = 1; k < WIN_CONDITION; k++) {
            text = buttons[row + k * rowDir][col + k * colDir].getText();
            if (text.isEmpty() || text.charAt(0) != symbol) {
                return false;
            }
        }

        return true;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static class Move {
        JButton button;
        char player;

        Move(JButton button, char player) {
            this.button = button;
            this.player = player;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeGUI::new);
    }
}
