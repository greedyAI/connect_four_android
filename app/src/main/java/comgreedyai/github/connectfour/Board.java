package comgreedyai.github.connectfour;

/**
 * Created by Waley on 2017/10/22.
 */

public class Board {
    private int cols;
    private int rows;
    public boolean won = false;
    private Slot[][] slots;

    public enum Player {
        A, B
    }

    public Player player;

    public Board(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        slots = new Slot[cols][rows];
        reset();
    }

    // resets the board by creating new slots for each array element
    public void reset() {
        won = false;
        player = Player.A;
        for (int col = 0 ; col < cols ; col++) {
            for (int row = 0 ; row < rows ; row++) {
                    slots[col][row] = new Slot();
            }
        }
    }

    // determines the top empty slot of a column: connect 4 pieces only drop to top empty slot
    public int topEmptySlot(int col) {
        for (int row = rows - 1 ; row >= 0 ; row--) {
            if (slots[col][row].isEmpty()) {
                return row;
            }
        }
        return -1;
    }

    public void fillSlot(int col, int row) {
        slots[col][row].setPlayer(player);
    }

    public void changeTurn() {
        if (player == Player.A) {
            player = Player.B;
        } else {
            player = Player.A;
        }
    }

    // checks for four continguous filled slots of the same color vertically
    private boolean upCheck(int col, int row) {
        if (row > rows - 4) {
            return false;
        } else {
            if (!slots[col][row].isEmpty() && !slots[col][row+1].isEmpty() &&
                    !slots[col][row+2].isEmpty() && !slots[col][row+3].isEmpty()) {
                return ((slots[col][row].thisPlayer() == slots[col][row+1].thisPlayer()) &&
                         (slots[col][row+1].thisPlayer() == slots[col][row+2].thisPlayer()) &&
                         (slots[col][row+2].thisPlayer() == slots[col][row+3].thisPlayer()));
            }
        }
        return false;
    }

    // checks for four continguous filled slots of the same color horizontally
    private boolean rightCheck(int col, int row) {
        if (col > cols - 4) {
            return false;
        } else {
            if (!slots[col][row].isEmpty() && !slots[col+1][row].isEmpty() &&
                    !slots[col+2][row].isEmpty() && !slots[col+3][row].isEmpty()) {
                return ((slots[col][row].thisPlayer() == slots[col+1][row].thisPlayer()) &&
                        (slots[col+1][row].thisPlayer() == slots[col+2][row].thisPlayer()) &&
                        (slots[col+2][row].thisPlayer() == slots[col+3][row].thisPlayer()));
            }
        }
        return false;
    }

    // checks for four continguous filled slots of the same color diagonally from bottom left to
    // top right
    private boolean diagonalPositiveCheck(int col, int row) {
        if (col > cols - 4 || row > rows - 4) {
            return false;
        } else {
            if (!slots[col][row].isEmpty() && !slots[col+1][row+1].isEmpty() &&
                    !slots[col+2][row+2].isEmpty() && !slots[col+3][row+3].isEmpty()) {
                return ((slots[col][row].thisPlayer() == slots[col+1][row+1].thisPlayer()) &&
                        (slots[col+1][row+1].thisPlayer() == slots[col+2][row+2].thisPlayer()) &&
                        (slots[col+2][row+2].thisPlayer() == slots[col+3][row+3].thisPlayer()));
            }
        }
        return false;
    }

    // checks for four continguous filled slots of the same color diagonally from top left to
    // bottom right
    private boolean diagonalNegativeCheck(int col, int row) {
        if (col < 3 || row > rows - 4) {
            return false;
        } else {
            if (!slots[col][row].isEmpty() && !slots[col-1][row+1].isEmpty() &&
                    !slots[col-2][row+2].isEmpty() && !slots[col-3][row+3].isEmpty()) {
                return ((slots[col][row].thisPlayer() == slots[col-1][row+1].thisPlayer()) &&
                        (slots[col-1][row+1].thisPlayer() == slots[col-2][row+2].thisPlayer()) &&
                        (slots[col-2][row+2].thisPlayer() == slots[col-3][row+3].thisPlayer()));
            }
        }
        return false;
    }

    public boolean winningConditionCheck() {
        for (int col = 0 ; col < cols ; col++) {
            for (int row = 0 ; row < rows ; row++) {
                if (upCheck(col, row) || rightCheck(col, row) || diagonalPositiveCheck(col, row)
                        || diagonalNegativeCheck(col, row)) {
                    won = true;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean tieConditionCheck() {
        for (int col = 0 ; col < cols ; col++) {
            for (int row = 0 ; row < rows ; row++) {
                if (slots[col][row].isEmpty()) {
                    return false;
                }
            }
        }
        return !winningConditionCheck();
    }
}
