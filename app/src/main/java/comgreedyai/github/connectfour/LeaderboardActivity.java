package comgreedyai.github.connectfour;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LeaderboardActivity extends AppCompatActivity {

    private final String WON_POSTFIX = "_w";
    private final String LOSS_POSTFIX = "_l";
    private final String TIE_POSTFIX = "_t";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        Button clear = (Button) findViewById(R.id.clear);

        ArrayList<Player> players = getIntent().getParcelableArrayListExtra(getString(R.string.users));

        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        Set<String> usernames = sp.getStringSet(getString(R.string.users), new HashSet<String>());

        final SharedPreferences.Editor editor = sp.edit();

        for (String name : usernames) {
            int won = sp.getInt(name + WON_POSTFIX, 0);
            int loss = sp.getInt(name + LOSS_POSTFIX, 0);
            int tie = sp.getInt(name + TIE_POSTFIX, 0);
            Player newPlayer = new Player(name, won, loss, tie);
            if (!players.contains(newPlayer) && GameActivity.hasRestarted == true) {
                players.add(newPlayer);
            } else if (GameActivity.hasRestarted == true) {
                for (Player player : players) {
                    if (player.equals(newPlayer)) {
                        player.addKWin(won);
                        player.addKLoss(loss);
                        player.addKDraw(tie);
                    }
                }
            }
        }

        if (GameActivity.hasRestarted == true) {
            for (Player player: players) {
                usernames.add(player.getName());
            }

            for (Player player: players) {
                editor.putStringSet(getString(R.string.users), usernames);
                editor.putInt(player.getName() + WON_POSTFIX, player.getWonGames());
                editor.putInt(player.getName() + LOSS_POSTFIX, player.getLostGames());
                editor.putInt(player.getName() + TIE_POSTFIX, player.getDrawnGames());
                editor.apply();
            }

        }

        ListView listView = (ListView) findViewById(R.id.list);
        LeaderboardAdapter adapter = new LeaderboardAdapter(this, players);
        listView.setAdapter(adapter);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset(view, editor);
            }
        });

    }

    public void reset(View v, final SharedPreferences.Editor editor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.reset_title)
                .setMessage(R.string.reset_stats)
                .setPositiveButton(R.string.reset_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), LeaderboardActivity.class);
                        ArrayList<Player> playerSet = new ArrayList<Player>();
                        intent.putParcelableArrayListExtra(getString(R.string.users), playerSet);
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.reset_reject, null);
        builder.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GameActivity.hasRestarted = false;
    }
}
