package hu.bme.aut.hgcinfo.constants;

import hu.bme.aut.hgcinfo.R;

/**
 * Created by locsi on 22/10/2017.
 */

public final class RoleImageFinder {

    public static final int findRoleImage (int role){
        switch (role){
            case 1: return R.drawable.ic_warrior;
            case 2: return R.drawable.ic_support;
            case 3: return R.drawable.ic_assassin;
            case 4: return R.drawable.ic_flex; // can be spec or flex, usually the same in HGC
            default: return R.drawable.ic_hgc;
        }
    }

}
