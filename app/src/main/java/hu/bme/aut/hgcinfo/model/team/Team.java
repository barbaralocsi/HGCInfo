package hu.bme.aut.hgcinfo.model.team;

import java.io.Serializable;

public class Team implements Serializable
{
    public int id;
    public String name;
    public int region;
    public String url;
    public Logo logo;

    public Team(int id, String name, int region, String url, Logo logo) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.url = url;
        this.logo = logo;
    }
}