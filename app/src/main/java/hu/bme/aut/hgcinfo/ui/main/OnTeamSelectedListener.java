package hu.bme.aut.hgcinfo.ui.main;

import hu.bme.aut.hgcinfo.db_model.SugarTeam;

interface OnTeamSelectedListener {
    void onTeamSelected(SugarTeam team);

    void onTeamToHGC(SugarTeam sugarTeam, boolean b);
}