package hu.bme.aut.hgcinfo.network;

import hu.bme.aut.hgcinfo.model.player.PlayerList;
import hu.bme.aut.hgcinfo.model.team.Team;
import hu.bme.aut.hgcinfo.model.team.TeamList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface TeamApi {
    @GET("/teams/{teamid}/?format=json")
    Call<Team> getTeam(@Path("teamid") Integer teamId);

    @GET("/teams/?format=json&page_size=100")
    Call<TeamList> getAllTeams(@Query("region") Integer regionId);

    @GET("/players?format=json")
    Call<PlayerList> getPlayersOfTeam(@Query("team") Integer teamId);
}
