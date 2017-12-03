package hu.bme.aut.hgcinfo.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by locsi on 12/2/2017.
 */

public class FragmentFavs extends FragmentTeams {
    private static final String TAG = "FragmentFavs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    //@Override
    protected void loadTeamsAPI() {
        //Empty
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

}
