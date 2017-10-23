package hu.bme.aut.hgcinfo.db_model;

import com.orm.SugarRecord;

import java.util.ArrayList;

import hu.bme.aut.hgcinfo.model.player.Player;
import hu.bme.aut.hgcinfo.model.player.PlayerList;

public class SugarPlayer extends SugarRecord{
    public int playerId;
    public int teamId;
    public int region;
    public String nickname;
    public String realname;
    public String country;
    public int role;
    public String url;
    public String smallPhoto;
    public String bigPhoto;
    public String mediumPhoto;

    public SugarPlayer() {
    }

    public SugarPlayer(Player p){
        this.playerId = p.id;
        this.teamId = p.team;
        this.region = p.region;
        this.nickname = p.nickname;
        this.realname = p.realname;
        this.country = p.country;
        this.role = p.role;
        this.url = p.url;
        this.smallPhoto = p.photo.small;
        this.bigPhoto = p.photo.big;
        this.mediumPhoto = p.photo.medium;
    }

    public SugarPlayer(int playerId, int teamId, int region, String nickname, String realname, String country, int role, String url, String smallPhoto, String bigPhoto, String mediumPhoto) {
        this.playerId = playerId;
        this.teamId = teamId;
        this.region = region;
        this.nickname = nickname;
        this.realname = realname;
        this.country = country;
        this.role = role;
        this.url = url;
        this.smallPhoto = smallPhoto;
        this.bigPhoto = bigPhoto;
        this.mediumPhoto = mediumPhoto;
    }

    public static ArrayList<SugarPlayer> makeSugar(PlayerList players){
        ArrayList<SugarPlayer> sp = new ArrayList<>();
        for (Player p: players.results) {
            sp.add(new SugarPlayer(p));
        }
        return sp;
    }
}
