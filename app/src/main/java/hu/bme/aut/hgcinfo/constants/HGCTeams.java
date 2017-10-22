package hu.bme.aut.hgcinfo.constants;

import java.util.ArrayList;

/**
 * Created by locsi on 22/10/2017.
 */

public final class HGCTeams {
    public static final ArrayList<Integer> getHGCTeams(int regionId){
        switch (regionId) {
            case 1: return  new ArrayList<Integer>() {{
                add(142);
                add(21);
                add(178);
                add(20);
                add(150);
                add(19);
                add(208);
                add(149);
            }};
            default: return null;
        }
    }
}
