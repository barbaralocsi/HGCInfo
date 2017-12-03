package hu.bme.aut.hgcinfo.ui.main;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import hu.bme.aut.hgcinfo.R;

public class FragmentSettings extends PreferenceFragment {
    public static final String KEY_PREF_DEFAULT_REGION = "pref_default_region2";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
    }
}
