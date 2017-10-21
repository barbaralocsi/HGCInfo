package hu.bme.aut.hgcinfo.network;

import hu.bme.aut.hgcinfo.model.Team;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by locsi on 21/10/2017.
 */

public interface TeamApi {
    @GET("/teams/{teamid}/?format=json")
    Call<Team> getTeam(@Path("teamid") Integer teamId);
}
