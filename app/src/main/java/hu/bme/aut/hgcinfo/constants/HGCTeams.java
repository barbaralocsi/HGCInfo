package hu.bme.aut.hgcinfo.constants;

import java.util.ArrayList;

import hu.bme.aut.hgcinfo.db_model.SugarTeam;

public final class HGCTeams {
    private static final ArrayList<Integer> HGCEU = new ArrayList<Integer>() {{
        add(142);
        add(21);
        add(178);
        add(20);
        add(150);
        add(19);
        add(208);
        add(149);
    }};
    private static final ArrayList<Integer> HGCNA = new ArrayList<Integer>() {{
            add(188);
            add(147);
            add(36);
            add(29);
            add(202);
            add(123);
            add(205);
            add(203);
    }};

    private static final ArrayList<Integer> HGCKR = new ArrayList<Integer>() {{
            add(139);
            add(167);
            add(41);
            add(42);
            add(78);
            add(41);
            add(209);
            add(210);
    }};

    public static final ArrayList<Integer> getHGCTeams(){

        ArrayList<Integer> allHGC = new ArrayList<>();
        allHGC.addAll(HGCEU);
        allHGC.addAll(HGCNA);
        allHGC.addAll(HGCKR);

        return allHGC;
    }

    public static final boolean isHGCTeam(SugarTeam t){
        return HGCEU.contains(t)  || HGCNA.contains(t) || HGCKR.contains(t);
    }
}
