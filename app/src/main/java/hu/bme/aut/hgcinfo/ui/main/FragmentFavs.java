package hu.bme.aut.hgcinfo.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.bme.aut.hgcinfo.R;

/**
 * Created by locsi on 12/2/2017.
 */

public class FragmentFavs extends android.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_favs,container,false);

        return rootview;
    }
}
