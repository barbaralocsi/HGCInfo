package hu.bme.aut.hgcinfo.model.team;

import java.io.Serializable;

public class Logo implements Serializable
{
    public String small;
    public String big;
    public String medium;

    public Logo(String small, String big, String medium) {
        this.small = small;
        this.big = big;
        this.medium = medium;
    }
}