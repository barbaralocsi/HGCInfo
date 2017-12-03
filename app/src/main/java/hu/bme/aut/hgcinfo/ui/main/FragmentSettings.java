package hu.bme.aut.hgcinfo.ui.main;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import hu.bme.aut.hgcinfo.R;

/**
 * Created by locsi on 12/2/2017.
 */

public class FragmentSettings extends PreferenceFragment {
    //@Override
    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //    View rootview = inflater.inflate(R.layout.fragment_settings,container,false);

    //    return rootview;
    //}
    public static final String KEY_PREF_DEFAULT_REGION = "pref_default_region2";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
    }
}
