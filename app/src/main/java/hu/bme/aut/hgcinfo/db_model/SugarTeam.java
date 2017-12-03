package hu.bme.aut.hgcinfo.db_model;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.hgcinfo.model.team.Logo;
import hu.bme.aut.hgcinfo.model.team.Team;

public class SugarTeam extends SugarRecord implements Serializable, Comparable<SugarTeam>
{
    public int teamId;
    public String name;
    public int region;
    public String url;
    public String logoSmall;
    public String logoBig;
    public String logoMedium;
    public boolean isHgc;
    public boolean isFavourite;

    public SugarTeam(int id, String name, int region, String url, Logo logo) {
        this.teamId = id;
        this.name = name;
        this.region = region;
        this.url = url;
        this.logoSmall = logo.small;
        this.logoBig = logo.big;
        this.logoMedium = logo.medium;
        isHgc=false;
        isFavourite=false;
    }

    public SugarTeam() {
        isHgc=false;
        isFavourite=false;
    }

    public SugarTeam(Team team){
        this.teamId = team.id;
        this.name = team.name;
        this.region = team.region;
        this.url = team.url;
        this.logoSmall = team.logo.small;
        this.logoBig = team.logo.big;
        this.logoMedium = team.logo.medium;
        isHgc=false;
        isFavourite=false;
    }

    public static ArrayList<SugarTeam> makeSugar(List<Team> teams){
        ArrayList<SugarTeam> st = new ArrayList<>();
        for (Team t: teams) {
            st.add(new SugarTeam(t));
        }
        return st;
    }

    public void setFavouirte(boolean fav) {
        this.isFavourite = fav;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHGC(boolean HGC) {
        this.isHgc = HGC;
    }

    @Override
    public int compareTo(@NonNull SugarTeam sugarTeam) {
        if (isHgc == true && sugarTeam.isHgc == true) return 0;
        if (isHgc == false && sugarTeam.isHgc == false) return 0;
        if (isHgc == true && sugarTeam.isHgc == false) return -1;
        else return 1;
    }
}