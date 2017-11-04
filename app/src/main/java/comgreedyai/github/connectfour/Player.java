package comgreedyai.github.connectfour;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Waley on 2017/10/23.
 */

public class Player implements Parcelable, Comparable<Player> {

    private String name;
    private int wonGames;
    private int lostGames;
    private int drawnGames;

    public Player(String name) {
        this.name = name;
        this.wonGames = 0;
        this.lostGames = 0;
        this.drawnGames = 0;
    }

    public Player(Parcel parcel){
        this.name = parcel.readString();
        this.wonGames = parcel.readInt();
        this.lostGames = parcel.readInt();
        this.drawnGames = parcel.readInt();
    }

    public Player(String name, int won, int loss, int drawn) {
        this.name = name;
        this.wonGames = won;
        this.lostGames = loss;
        this.drawnGames = drawn;
    }

    public void addWin() {
        wonGames++;
    }

    public void addLoss() {
        lostGames++;
    }

    public void addDraw() {
        drawnGames++;
    }

    public void addKWin(int k) {
        wonGames = wonGames + k;
    }

    public void addKLoss(int k) {
        lostGames = lostGames + k;
    }

    public void addKDraw(int k) {
        drawnGames = drawnGames + k;
    }

    public String getName() {
        return name;
    }

    public int getWonGames() {
        return wonGames;
    }

    public int getLostGames() {
        return lostGames;
    }

    public int getDrawnGames() {
        return drawnGames;
    }

    @Override
    public boolean equals(Object O) {
        if (! (O instanceof Player)) {
            return false;
        }
        Player that = (Player) O;
        return this.getName().equals(that.getName());
    }

    @Override
    public int compareTo(Player player) {
        if (this.wonGames > player.wonGames) {
            return -1;
        } else if (this.wonGames < player.wonGames) {
            return 1;
        } else {
            if (this.lostGames < player.lostGames) {
                return -1;
            } else if (this.lostGames > player.lostGames) {
                return 1;
            } else {
                if (this.drawnGames < player.drawnGames) {
                    return 1;
                } else if (this.drawnGames > player.drawnGames) {
                    return -1;
                } else {
                    return this.name.compareTo(player.name);
                }
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(wonGames);
        parcel.writeInt(lostGames);
        parcel.writeInt(drawnGames);
    }

    public static final Parcelable.Creator<Player> CREATOR
            = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

}
