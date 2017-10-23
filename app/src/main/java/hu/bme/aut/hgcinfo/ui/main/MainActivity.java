package hu.bme.aut.hgcinfo.ui.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.hgcinfo.R;
import hu.bme.aut.hgcinfo.db_model.SugarTeam;
import hu.bme.aut.hgcinfo.model.team.TeamList;
import hu.bme.aut.hgcinfo.network.NetworkManager;
import hu.bme.aut.hgcinfo.ui.teamdetails.TeamDetailsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private TeamAdapter adapter;
    private TeamList teamList = null;
    private int regionId=1;

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

        //mTextMessage = (TextView) findViewById(R.teamId.message);
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
                    public void onTeamSelected(SugarTeam team) {
                        Intent showDetailsIntent = new Intent();
                        showDetailsIntent.setClass(MainActivity.this,
                                TeamDetailsActivity.class);
                        showDetailsIntent.putExtra(
                                TeamDetailsActivity.EXTRA_TEAM_ID, team);
                        startActivity(showDetailsIntent);
                    }
                }, MainActivity.this,regionId);
        //Team ds = new Team();
        //ds.teamId=142;
        //ds.name = "DS";
        //adapter.addTeam(ds);
        recyclerView.setAdapter(adapter);
        loadItemsInBackground();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter.getItemCount()<=0) {
            //loadTeams();
            loadItemsInBackground();
        }
    }

    private void loadTeams() {

        Toast.makeText(MainActivity.this, "API call MainActivity",
                Toast.LENGTH_SHORT).show();

        NetworkManager.getInstance().getAllTeams(regionId).enqueue(new Callback<TeamList>() {
            @Override
            public void onResponse(Call<TeamList> call,
                                   Response<TeamList> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    teamList = response.body();
                    ArrayList<SugarTeam> sugarTeams = SugarTeam.makeSugar(teamList.results);
                    adapter.addTeams(sugarTeams);
                } else {
                    Toast.makeText(MainActivity.this,
                            "Error: "+response.message(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TeamList> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this,
                        "Error in network request, check LOG",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh_teams) {
            adapter.removeTeams();
            loadTeams();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_teams, menu);
        return true;
    }

    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<SugarTeam>>() {

            @Override
            protected List<SugarTeam> doInBackground(Void... voids) {
                return SugarTeam.listAll(SugarTeam.class);
            }

            @Override
            protected void onPostExecute(List<SugarTeam> team) {
                super.onPostExecute(team);
                adapter.update(team);
            }
        }.execute();
    }

}
