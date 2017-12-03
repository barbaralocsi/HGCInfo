package hu.bme.aut.hgcinfo.ui.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import hu.bme.aut.hgcinfo.R;

public class MainActivity extends AppCompatActivity {

    private int FRAGMENT_TEAMS = 0;
    private int FRAGMENT_FAVS = 1;
    private int FRAGMENT_SETTINGS = 2;


    private void changeFragment(int fragmentId) {

        Fragment newFragment;
        Bundle bundle = new Bundle();
        if (fragmentId == FRAGMENT_TEAMS) {
            newFragment = new FragmentTeams();
            bundle.putBoolean("full", true);
        } else if (fragmentId == FRAGMENT_FAVS) {
            newFragment = new FragmentTeams();
            bundle.putBoolean("full", false);
        } else {
            newFragment = new FragmentSettings();
        }

        newFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(
                R.id.fragmentContainer, newFragment)
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_teams:
                    changeFragment(FRAGMENT_TEAMS);
                    return true;
                case R.id.navigation_favs:
                    changeFragment(FRAGMENT_FAVS);
                    return true;
                case R.id.navigation_settings:
                    changeFragment(FRAGMENT_SETTINGS);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        changeFragment(0);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }















}
