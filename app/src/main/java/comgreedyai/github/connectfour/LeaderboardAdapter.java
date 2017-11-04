package comgreedyai.github.connectfour;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Waley on 2017/10/23.
 */

public class LeaderboardAdapter extends ArrayAdapter<Player> {

    LayoutInflater inflator;
    public LeaderboardAdapter(Context context, ArrayList<Player> players) {
        super(context, R.layout.single_player, players);
        inflator = LayoutInflater.from(context);
    }

    @Override
    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = inflator.inflate(R.layout.single_player, parent, false);
        }
        Player player = getItem(position);
        String text = player.getName() + ": W" + player.getWonGames()
                + " | L" + player.getLostGames() + " | D" + player.getDrawnGames();
        ((TextView) view.findViewById(R.id.result)).setText(text);
        return view;
    }
}
