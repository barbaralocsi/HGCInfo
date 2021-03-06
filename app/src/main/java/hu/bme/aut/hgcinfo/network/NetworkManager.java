package hu.bme.aut.hgcinfo.network;

import hu.bme.aut.hgcinfo.model.player.PlayerList;
import hu.bme.aut.hgcinfo.model.team.TeamList;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static final String ENDPOINT_ADDRESS = "http://api.masterleague.net";
    //private static final String APP_ID = "f3d694bc3e1d44c1ed5a97bd1120e8fe";

    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private TeamApi teamApi;

    private NetworkManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT_ADDRESS)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        teamApi = retrofit.create(TeamApi.class);
    }


    public Call<TeamList> getAllTeams(Integer regionId) {
        return teamApi.getAllTeams(regionId);
    }

    public Call<PlayerList> getPlayersOfTeam(Integer teamId) {
        return teamApi.getPlayersOfTeam(teamId);
    }


}