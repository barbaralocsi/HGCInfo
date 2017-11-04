package hu.bme.aut.hgcinfo.ui.teamdetails;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.hgcinfo.R;
import hu.bme.aut.hgcinfo.constants.RoleImageFinder;
import hu.bme.aut.hgcinfo.db_model.SugarPlayer;
import hu.bme.aut.hgcinfo.db_model.SugarTeam;
import hu.bme.aut.hgcinfo.model.player.PlayerList;
import hu.bme.aut.hgcinfo.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamDetailsActivity extends AppCompatActivity{

    // store the ongoing call so we can cancel it onDestroy
    private List< Call<PlayerList> > calls = new ArrayList<>();

    private static final String TAG = "TeamDetailsActivity";
    public static final String EXTRA_TEAM_ID = "extra.team_id";

    //private Team teamData = null;

    private SugarTeam team;

    //private TextView tvTeamName;
    //private ImageView tvTeamIcon;

    private LinearLayout listOfRows;
    private LayoutInflater inflater;
    private AVLoadingIndicatorView avi;

    //private PlayerList playerList = null;

    private List<SugarPlayer> teamPlayers;

    public TeamDetailsActivity(){
        teamPlayers = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        //team = getIntent().getIntExtra(EXTRA_TEAM_ID, 0);
        team = (SugarTeam) getIntent().getSerializableExtra(EXTRA_TEAM_ID);

        getSupportActionBar().setTitle(team.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avi= (AVLoadingIndicatorView) findViewById(R.id.avi);
        stopAnim();

        listOfRows = (LinearLayout) findViewById(R.id.list_of_rows);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //tvTeamName = (TextView) findViewById(R.teamId.tvTeamName);

        //tvTeamIcon = (ImageView) findViewById(R.teamId.tvTeamIcon);
        //displayTeamData();

    }

    private void displayTeamData() {
        listOfRows.removeAllViews();
        for (SugarPlayer p: teamPlayers) {
            View rowItem = inflater.inflate(R.layout.player_row, null);

            ImageView photo = (ImageView) rowItem.findViewById(R.id.player_row_photo);
            TextView nickName = (TextView) rowItem.findViewById(R.id.player_row_nick);
            TextView realName = (TextView) rowItem.findViewById(R.id.player_row_real);
            ImageView role = (ImageView) rowItem.findViewById(R.id.player_row_role);

            Glide.with(this)
                    .load(p.mediumPhoto)
                    .crossFade()
                    .into(photo);

            nickName.setText(p.nickname);
            realName.setText(p.realname);

            int roleImageId = RoleImageFinder.findRoleImage(p.role);
            role.setImageResource(roleImageId);

            listOfRows.addView(rowItem);

        }
    }

    private void saveTeamsToDatabase(){
        for (SugarPlayer p : teamPlayers) {
            p.save();
        }
    }

    private void loadTeamData() {

        Toast.makeText(TeamDetailsActivity.this, "API call TeamDetailsActivity",
                Toast.LENGTH_SHORT).show();
        Log.e(TAG, "loadTeamData");
        startAnim();
        final Call<PlayerList> thisCall =  NetworkManager.getInstance().getPlayersOfTeam(team.teamId);
        calls.add(thisCall);
        thisCall.enqueue(new Callback<PlayerList>() {
            @Override
            public void onResponse(Call<PlayerList> call,
                                   Response<PlayerList> response) {
                Log.e(TAG, "onResponse: " + response.code());
                // TODO: beter solution?
                removePlayers(); // Delete the players again

                stopAnim();

                if (response.isSuccessful()) {
                    PlayerList playerList = response.body();
                    teamPlayers = SugarPlayer.makeSugar(playerList);
                    displayTeamData();
                    saveTeamsToDatabase();
                } else {
                    Toast.makeText(TeamDetailsActivity.this,
                            "Error: "+response.message(),
                            Toast.LENGTH_SHORT).show();
                }
                calls.remove(thisCall);
            }

            @Override
            public void onFailure(Call<PlayerList> call, Throwable t) {
                Log.e(TAG, "onFailure ");
                stopAnim();
                t.printStackTrace();
                Toast.makeText(TeamDetailsActivity.this,
                        "Error in network request, check LOG",
                        Toast.LENGTH_SHORT).show();
                calls.remove(thisCall);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(teamPlayers == null ||  teamPlayers.isEmpty()) {
            loadItemsInBackground();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
        for (Call call: calls) {
            call.cancel();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh_team_players) {
            removePlayers();
            loadTeamData();
        }
        else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void startAnim(){
        avi.show();
        // or avi.smoothToShow();
    }

    void stopAnim(){
        avi.hide();
        // or avi.smoothToHide();
    }

    public void removePlayers(){
        removeAllFromDB();
        teamPlayers.clear();
        //displayTeamData(); //TODO lehet eleg lenne csak a betoltesnel frissiteni a kepet?
    }

    private void removeAllFromDB(){
        for (SugarPlayer p : teamPlayers) {
            p.delete();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_team_info, menu);
        return true;
    }

    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<SugarPlayer>>() {

            @Override
            protected List<SugarPlayer> doInBackground(Void... voids) {
                return SugarPlayer.listAll(SugarPlayer.class);
            }

            @Override
            protected void onPostExecute(List<SugarPlayer> players) {
                super.onPostExecute(players);
                update(players);
            }
        }.execute();
    }

    /**
     * Updates the whole list from database
     * called by loadItemsInBackground
     * @param players
     */
    public void update(List<SugarPlayer> players) {
        teamPlayers.clear();
        // Get only the players of the chosen team
        for (SugarPlayer player: players) {
            if (player.teamId == team.teamId){
                teamPlayers.add(player);
            }
        }
        // If the database is empty try to ask the API instead -- TODO shoudnt this be in the onPostExecute?
        if(teamPlayers.isEmpty()){
            loadTeamData();
            return;
        }

        //addAll(playersOfTeam); // direkt nem addteams hogy ne mentse ujra
        //addTeams(teams);
        //notifyDataSetChanged();
        displayTeamData();
    }

}