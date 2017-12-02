package hu.bme.aut.hgcinfo.ui.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by locsi on 12/2/2017.
 */

public class FragmentTeams extends android.app.Fragment {
    private RecyclerView recyclerView;
    private int regionId=1;
    private TeamAdapter adapter;
    private static final String TAG = "FragmentTeams";
    private TeamList teamList = null;
    private Boolean FULL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FULL = getArguments().getBoolean("full");
        setHasOptionsMenu(true);
        View rootview = inflater.inflate(R.layout.fragment_teams_content,container,false);
        initRecyclerView(rootview);
        return rootview;
    }


    private void initRecyclerView(View rootview) {
        recyclerView = rootview.findViewById(R.id.MainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TeamAdapter(
                new OnTeamSelectedListener() {
                    @Override
                    public void onTeamSelected(SugarTeam team) {
                        Log.e(TAG, "out:" +String.valueOf(team.getId()));
                        Intent showDetailsIntent = new Intent();
                        showDetailsIntent.setClass(getActivity(),
                                TeamDetailsActivity.class);
                        showDetailsIntent.putExtra(
                                TeamDetailsActivity.EXTRA_TEAM, team);
                        showDetailsIntent.putExtra(
                                TeamDetailsActivity.EXTRA_TEAM_ID, team.getId());
                        startActivity(showDetailsIntent);
                    }
                }, getActivity(),regionId);
        //Team ds = new Team();
        //ds.teamId=142;
        //ds.name = "DS";
        //adapter.addTeam(ds);
        recyclerView.setAdapter(adapter);
        loadItemsInBackgroundDB();
    }


    private void loadItemsInBackgroundDB() {
        new AsyncTask<Void, Void, List<SugarTeam>>() {

            @Override
            protected List<SugarTeam> doInBackground(Void... voids) {
                return SugarTeam.listAll(SugarTeam.class);
            }

            @Override
            protected void onPostExecute(List<SugarTeam> teams) {
                super.onPostExecute(teams);

                ArrayList<SugarTeam> selectedTeams = new ArrayList<SugarTeam>();

                if(FULL) {
                    // Give to the adapter the teams of this region

                    for (SugarTeam t : teams) {
                        if (t.region == regionId) {
                            selectedTeams.add(t);
                            Log.d(TAG, t.name + " " + t.isFavourite);
                        }
                    }

                    // If the database is empty try to ask the API instead
                    if (selectedTeams.isEmpty()) {
                        loadTeamsAPI();
                        return;
                    }
                }
                else{
                    // TODO
                    for (SugarTeam t : teams) {
                        if (t.isFavourite == true) {
                            selectedTeams.add(t);
                            Log.d(TAG, t.name + " " + t.isFavourite);
                        }
                    }
                }

                adapter.update(selectedTeams);
            }
        }.execute();
    }

    private void loadTeamsAPI() {
        if(!FULL){
            return;
        }
        Toast.makeText(getActivity(), "API call MainActivity",
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
                    Toast.makeText(getActivity(),
                            "Error: "+response.message(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TeamList> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(),
                        "Error in network request, check LOG",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadItemsInBackgroundDB();
    }

    private void doRefreshMenuTasks(MenuItem item){
        item.setChecked(true);
        adapter.removeTeamsFromAdaper();
        loadItemsInBackgroundDB();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_teams:
                adapter.removeTeams();
                loadTeamsAPI();
                return true;
            case R.id.menu_region_eu:
                regionId = 1;
                doRefreshMenuTasks(item);
                return true;
            case R.id.menu_region_na:
                regionId = 2;
                doRefreshMenuTasks(item);
                return true;
            case R.id.menu_region_kr:
                regionId = 3;
                doRefreshMenuTasks(item);
                return true;
            case R.id.menu_region_ch:
                regionId = 4;
                doRefreshMenuTasks(item);
                return true;
            case R.id.menu_region_tw:
                regionId = 5;
                doRefreshMenuTasks(item);
                return true;
            case R.id.menu_region_anz:
                regionId = 6;
                doRefreshMenuTasks(item);
                return true;
            case R.id.menu_region_sea:
                regionId = 7;
                doRefreshMenuTasks(item);
                return true;
            case R.id.menu_region_la:
                regionId = 8;
                doRefreshMenuTasks(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_teams, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
