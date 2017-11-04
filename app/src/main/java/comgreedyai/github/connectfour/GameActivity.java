package comgreedyai.github.connectfour;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private ImageView[][] slots;
    private View boardView;
    private Board board;
    private ViewHolder viewHolder;
    private static int NUMROWS = 6;
    private static int NUMCOLS = 7;
    private ArrayList<Player> playerSet = new ArrayList<Player>();
    public static boolean hasRestarted = true;

    private class ViewHolder {
        public TextView winnerText;
        public ImageView turnIndicateImageView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // initialize the game state and create the game board and its onClickListeners
        board = new Board(NUMCOLS, NUMROWS);
        boardView = findViewById(R.id.game_board);
        hasRestarted = true;
        makeSlots();
        for (int r = 0; r < NUMROWS; r++) {
            for (int c = 0; c < NUMCOLS; c++) {
                final int current = c;
                slots[r][c].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dropCounter(current);
                    }
                });
            }
        }
        viewHolder = new ViewHolder();
        viewHolder.turnIndicateImageView =
                (ImageView) findViewById(R.id.turn_indicator_image_view);
        viewHolder.turnIndicateImageView.setImageResource(resourceForTurn());
        viewHolder.winnerText = (TextView) findViewById(R.id.winner_text);
        viewHolder.winnerText.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks (new game and leaderboard)
        int id = item.getItemId();
        if (id == R.id.action_leaderboard) {
            if (board.winningConditionCheck() || board.tieConditionCheck()) {
                Intent intent = new Intent(this, LeaderboardActivity.class);
                intent.putParcelableArrayListExtra(getString(R.string.users), playerSet);
                startActivity(intent);
            } else {
                warning();
            }
            return true;
        } else if (id == R.id.action_newgame) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    // create imageviews for each of the slots in the playing board
    private void makeSlots() {
        slots = new ImageView[NUMROWS][NUMCOLS];
        for (int r = 0; r < NUMROWS; r++) {
            ViewGroup row = (ViewGroup) ((ViewGroup) boardView).getChildAt(r);
            row.setClipChildren(false);
            for (int c = 0; c < NUMCOLS; c++) {
                ImageView imageView = (ImageView) row.getChildAt(c);
                imageView.setImageResource(android.R.color.transparent);
                slots[r][c] = imageView;
            }
        }
    }

    // make a move by dropping counter in desired row, with animations
    // does nothing if the row is empty or clicked location already contains a playing piece
    private void dropCounter(int col) {
        if (board.won) {
            return;
        }
        int row = board.topEmptySlot(col);
        if (row == -1) {
            return;
        }
        final ImageView cell = slots[row][col];
        float move = -(cell.getHeight() * row + cell.getHeight() + 15);
        cell.setY(move);
        cell.setImageResource(resourceForTurn());
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, Math.abs(move));
        anim.setDuration(850);
        anim.setFillAfter(true);
        cell.startAnimation(anim);
        board.fillSlot(col, row);
        if (board.winningConditionCheck() || board.tieConditionCheck()) {
            winGame();
        } else {
            switchPlayer();
        }
    }

    // checks for winning condition and tie and updates the player's win/loss/tie counts as necessary
    // if a win/tie is detected, it immediately redirects to the leaderboard; however, one can press'
    // the back button to go back
    private void winGame() {
        int color = 0;
        if (board.tieConditionCheck()) {
            color = ContextCompat.getColor(this, R.color.draw_color);
            viewHolder.winnerText.setText(R.string.draw);
        } else if (board.player == Board.Player.A) {
            color = ContextCompat.getColor(this, R.color.primary_player);
            viewHolder.winnerText.setText(R.string.red_wins);
        } else {
            color = ContextCompat.getColor(this, R.color.secondary_player);
            viewHolder.winnerText.setText(R.string.yellow_wins);
        }
        viewHolder.winnerText.setTextColor(color);
        viewHolder.winnerText.setVisibility(View.VISIBLE);
        EditText text1 = (EditText) findViewById(R.id.red_name);
        EditText text2 = (EditText) findViewById(R.id.yellow_name);
        String value1 = text1.getText().toString();
        String value2 = text2.getText().toString();
        Player redPlayer = new Player(value1);
        Player yellowPlayer = new Player(value2);
        if (!playerSet.contains(redPlayer)) {
            playerSet.add(redPlayer);
        }
        if (!playerSet.contains(yellowPlayer)) {
            playerSet.add(yellowPlayer);
        }
        if (board.winningConditionCheck()) {
            if (board.player == Board.Player.A) {
                for (Player player : playerSet) {
                    if (player.equals(redPlayer)) {
                        player.addWin();
                    }
                    if (player.equals(yellowPlayer)) {
                        player.addLoss();
                    }
                }
            } else {
                for (Player player : playerSet) {
                    if (player.equals(redPlayer)) {
                        player.addLoss();
                    }
                    if (player.equals(yellowPlayer)) {
                        player.addWin();
                    }
                }
            }
            Intent intent = new Intent(this, LeaderboardActivity.class);
            intent.putParcelableArrayListExtra(getString(R.string.users), playerSet);
            startActivity(intent);
        } else if (board.tieConditionCheck()) {
            for (Player player : playerSet) {
                if (player.equals(redPlayer)) {
                    player.addDraw();
                }
                if (player.equals(yellowPlayer)) {
                    player.addDraw();
                }
            }
            Intent intent = new Intent(this, LeaderboardActivity.class);
            intent.putParcelableArrayListExtra(getString(R.string.users), playerSet);
            startActivity(intent);
        }
    }

    private void switchPlayer() {
        board.changeTurn();
        viewHolder.turnIndicateImageView.setImageResource(resourceForTurn());
    }

    // for determining the drawable for the turn indicator on the top left corner of the playing field
    private int resourceForTurn() {
        switch (board.player) {
            case A:
                return R.drawable.redtoken;
            case B:
                return R.drawable.yellowtoken;
        }
        return R.drawable.redtoken;
    }

    // cannot go to the leaderboard if a game is incomplete (can only navigate back and forth if a
    // game is finished)
    public void warning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ban_title)
                .setMessage(R.string.ban_text)
                .setPositiveButton(R.string.ban_confirm, null);
        builder.show();
    }
}
