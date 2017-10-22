package hu.bme.aut.hgcinfo.ui.teamdetails;

import android.content.Context;
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

import hu.bme.aut.hgcinfo.R;
import hu.bme.aut.hgcinfo.constants.RoleImageFinder;
import hu.bme.aut.hgcinfo.model.player.Player;
import hu.bme.aut.hgcinfo.model.player.PlayerList;
import hu.bme.aut.hgcinfo.model.team.Team;
import hu.bme.aut.hgcinfo.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamDetailsActivity extends AppCompatActivity{

    private static final String TAG = "TeamDetailsActivity";
    public static final String EXTRA_TEAM_ID = "extra.team_id";

    //private Team teamData = null;

    private Team team;

    //private TextView tvTeamName;
    //private ImageView tvTeamIcon;

    private LinearLayout listOfRows;
    private LayoutInflater inflater;

    private PlayerList playerList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        //team = getIntent().getIntExtra(EXTRA_TEAM_ID, 0);
        team = (Team) getIntent().getSerializableExtra(EXTRA_TEAM_ID);


        getSupportActionBar().setTitle(team.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listOfRows = (LinearLayout) findViewById(R.id.list_of_rows);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //tvTeamName = (TextView) findViewById(R.id.tvTeamName);

        //tvTeamIcon = (ImageView) findViewById(R.id.tvTeamIcon);
        //displayTeamData();

    }

    private void displayTeamData() {

        for (Player p: playerList.results) {
            View rowItem = inflater.inflate(R.layout.player_row, null);

            ImageView photo = (ImageView) rowItem.findViewById(R.id.player_row_photo);
            TextView nickName = (TextView) rowItem.findViewById(R.id.player_row_nick);
            TextView realName = (TextView) rowItem.findViewById(R.id.player_row_real);
            ImageView role = (ImageView) rowItem.findViewById(R.id.player_row_role);

            Glide.with(this)
                    .load(p.photo.medium)
                    .crossFade()
                    .into(photo);

            nickName.setText(p.nickname);
            realName.setText(p.realname);

            int roleImageId = RoleImageFinder.findRoleImage(p.role);
            role.setImageResource(roleImageId);

            listOfRows.addView(rowItem);

        }
    }

    private void loadTeamData() {

        Toast.makeText(TeamDetailsActivity.this, "API call TeamDetailsActivity",
                Toast.LENGTH_SHORT).show();

        NetworkManager.getInstance().getPlayersOfTeam(team.id).enqueue(new Callback<PlayerList>() {
            @Override
            public void onResponse(Call<PlayerList> call,
                                   Response<PlayerList> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    playerList = response.body();
                    displayTeamData();
                } else {
                    Toast.makeText(TeamDetailsActivity.this,
                            "Error: "+response.message(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlayerList> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(TeamDetailsActivity.this,
                        "Error in network request, check LOG",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(playerList == null || playerList.results==null || playerList.results.isEmpty()) {
            loadTeamData();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh_teams) {
            listOfRows.removeAllViews();
            playerList=null;
            loadTeamData();
        }
        else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_teams, menu);
        return true;
    }

}