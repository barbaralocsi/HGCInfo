package hu.bme.aut.hgcinfo.ui.teamdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import hu.bme.aut.hgcinfo.R;
import hu.bme.aut.hgcinfo.model.team.Team;
import hu.bme.aut.hgcinfo.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamDetailsActivity extends AppCompatActivity{

    private static final String TAG = "TeamDetailsActivity";
    public static final String EXTRA_TEAM_ID = "extra.team_id";

    private Team teamData = null;

    private int team;

    private TextView tvTeamName;
    private ImageView tvTeamIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        team = getIntent().getIntExtra(EXTRA_TEAM_ID, 0);


        //TODO fix this
        getSupportActionBar().setTitle("test");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tvTeamName = (TextView) findViewById(R.id.tvTeamName);

        tvTeamIcon = (ImageView) findViewById(R.id.tvTeamIcon);
        //displayTeamData();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayTeamData() {
        tvTeamName.setText(teamData.name);
        Glide.with(this)
                .load(teamData.logo.big)
                .crossFade()
                .into(tvTeamIcon);
    }

    private void loadTeamData() {
        NetworkManager.getInstance().getTeam(team).enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call,
                                   Response<Team> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    teamData = response.body();
                    displayTeamData();
                } else {
                    Toast.makeText(TeamDetailsActivity.this,
                            "Error: "+response.message(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {
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
        loadTeamData();
    }

}