package hu.bme.aut.hgcinfo.network;

import hu.bme.aut.hgcinfo.model.Team;
import hu.bme.aut.hgcinfo.model.TeamList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by locsi on 21/10/2017.
 */

public interface TeamApi {
    @GET("/teams/{teamid}/?format=json")
    Call<Team> getTeam(@Path("teamid") Integer teamId);

    @GET("/teams/?format=json&page_size=100")
    Call<TeamList> getAllTeams(@Query("region") Integer regionId);
}
