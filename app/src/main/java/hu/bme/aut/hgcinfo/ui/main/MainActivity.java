package hu.bme.aut.hgcinfo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import hu.bme.aut.hgcinfo.R;
import hu.bme.aut.hgcinfo.ui.teamdetails.TeamDetailsActivity;

public class MainActivity extends AppCompatActivity {

    //private TextView mTextMessage;

    private RecyclerView recyclerView;
    private TeamAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    //TODO
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    //TODO
                    return true;
                case R.id.navigation_notifications:
                    //TODO
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView =
                (RecyclerView) findViewById(R.id.MainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamAdapter(
                new OnTeamSelectedListener() {
                    @Override
                    public void onTeamSelected(Integer team) {
                        Intent showDetailsIntent = new Intent();
                        showDetailsIntent.setClass(MainActivity.this,
                                TeamDetailsActivity.class);
                        showDetailsIntent.putExtra(
                                TeamDetailsActivity.EXTRA_TEAM_ID, team);
                        startActivity(showDetailsIntent);
                    }
                });
        adapter.addTeam(142);
        recyclerView.setAdapter(adapter);
    }
}
