package EventBusPOJO;

/**
 * Created by ivanessence on 23.05.2017.
 */

public class InviteAccept {

    public String inviteAccept;
    public String gameid;
    public String enemy;

    public InviteAccept(String inviteAccept, String gameid, String enemy) {
        this.inviteAccept = inviteAccept;
        this.gameid = gameid;
        this.enemy = enemy;
    }

}
