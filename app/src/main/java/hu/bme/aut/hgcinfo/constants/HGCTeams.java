package hu.bme.aut.hgcinfo.constants;

import java.util.ArrayList;

import hu.bme.aut.hgcinfo.db_model.SugarTeam;

public final class HGCTeams {
    public static final ArrayList<Integer> HGCEU = new ArrayList<Integer>() {{
        add(142);
        add(21);
        add(178);
        add(20);
        add(150);
        add(19);
        add(208);
        add(149);
    }};

    public static final ArrayList<Integer> getHGCTeams(){
        return  new ArrayList<Integer>() {{
                add(142);
                add(21);
                add(178);
                add(20);
                add(150);
                add(19);
                add(208);
                add(149);
            }};
    }

    public static final boolean isHGCTeam(SugarTeam t){
        return HGCEU.contains(t);
    }
}
