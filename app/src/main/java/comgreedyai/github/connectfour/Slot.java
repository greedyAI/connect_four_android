package comgreedyai.github.connectfour;

/**
 * Created by Waley on 2017/10/22.
 */

public class Slot {
    private boolean empty;
    private Board.Player player;

    public Slot() {
        empty = true;
    }

    public boolean isEmpty() {
        return empty;
    }

    public Board.Player thisPlayer() {
        return player;
    }

    public void setPlayer(Board.Player player) {
        this.player = player;
        this.empty = false;
    }
}
