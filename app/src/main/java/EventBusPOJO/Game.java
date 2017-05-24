package EventBusPOJO;

/**
 * Created by ivanessence on 23.05.2017.
 */

public class Game {

    public String game;
    public String gameid;
    public String enemynickname;
    public String key;

    public Game(String game, String gameid, String enemynickname, String key) {
        this.game = game;
        this.gameid = gameid;
        this.enemynickname = enemynickname;
        this.key = key;
    }

}
