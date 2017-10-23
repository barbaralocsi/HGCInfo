package hu.bme.aut.hgcinfo.db_model;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.hgcinfo.model.team.Logo;
import hu.bme.aut.hgcinfo.model.team.Team;

public class SugarTeam extends SugarRecord implements Serializable
{
    public int teamId;
    public String name;
    public int region;
    public String url;
    public String logoSmall;
    public String logoBig;
    public String logoMedium;
    public boolean isHgc;

    public SugarTeam(int id, String name, int region, String url, Logo logo) {
        this.teamId = id;
        this.name = name;
        this.region = region;
        this.url = url;
        this.logoSmall = logo.small;
        this.logoBig = logo.big;
        this.logoMedium = logo.medium;
        isHgc=false;
    }

    public SugarTeam() {
        isHgc=false;
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
    }

    public static ArrayList<SugarTeam> makeSugar(List<Team> teams){
        ArrayList<SugarTeam> st = new ArrayList<>();
        for (Team t: teams) {
            st.add(new SugarTeam(t));
        }
        return st;
    }

}