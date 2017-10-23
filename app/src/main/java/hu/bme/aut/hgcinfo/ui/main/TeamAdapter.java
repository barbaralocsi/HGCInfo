package hu.bme.aut.hgcinfo.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hu.bme.aut.hgcinfo.R;
import hu.bme.aut.hgcinfo.constants.HGCTeams;
import hu.bme.aut.hgcinfo.db_model.SugarTeam;


public class TeamAdapter extends
  RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private final List<SugarTeam> teams;
    private OnTeamSelectedListener listener;
    private Context mContext;
    private int regionId;

    public TeamAdapter(OnTeamSelectedListener listener, Context mContext, int regionId) {
        this.listener = listener;
        teams = new ArrayList<>();
        this.mContext = mContext;
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent,
      int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
          R.layout.item_team, parent, false);
        TeamViewHolder viewHolder = new TeamViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TeamViewHolder holder, int position) {
        holder.position = position;
        holder.nameTextView.setText(teams.get(position).name);

        Glide.with(mContext)
                .load(teams.get(position).logoSmall)
                .crossFade()
                .into(holder.teamIcon);

        int teamId = teams.get(position).teamId;
        if(teams.get(position).isHgc){
            holder.HGCImageView.setImageResource(R.drawable.ic_hgc);
        }
        else{
            holder.HGCImageView.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void addTeam(SugarTeam newTeam) {
        teams.add(newTeam);
        notifyItemInserted(teams.size() - 1);
    }

    /**
     * Adds newTeams to the teams in an order
     * Order: Bring forward HGC teams
     * calls notifyDataSetChanged <- TODO remove this?
     * @param newTeams
     */
    public void addTeams(List<SugarTeam> newTeams){
        // Bring HGC Teams to the front
        ArrayList<Integer> HGCteamIds = HGCTeams.getHGCTeams();
        Iterator<SugarTeam> i = newTeams.iterator();
        ArrayList<SugarTeam> HGCTeams = new ArrayList<>();
        while (i.hasNext()) {
            SugarTeam t = i.next(); // must be called before you can call i.remove()
            // Do something
            if(HGCteamIds.contains(t.teamId)){
                t.isHgc=true;
                HGCTeams.add(t);
                i.remove();
            }
        }
        // HGC teams are in the begining, add the other to it
        HGCTeams.addAll(newTeams);
        List<SugarTeam> allTeams = HGCTeams;

        long min;
        for (SugarTeam t: allTeams) {
            long a = t.save();
            long b = a+2;
            min = b+4;
        }

        teams.addAll(HGCTeams);
        notifyDataSetChanged();
    }

    public void removeTeamsFromAdaper(){
        teams.clear();
        notifyDataSetChanged();
    }

    public void removeTeams(){
        removeAllFromDB();
        teams.clear();
        notifyDataSetChanged();
    }

    private void removeAllFromDB(){
        for (SugarTeam t : teams) {
            t.delete();
        }
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder {

        int position;

        ImageView HGCImageView;
        TextView nameTextView;
        ImageView teamIcon;

        public TeamViewHolder(View itemView) {
            super(itemView);
            nameTextView = 
              (TextView) itemView.findViewById(
              R.id.TeamItemNameTextView);
            teamIcon = (ImageView) itemView.findViewById(
              R.id.TeamItemImageView);
            HGCImageView = (ImageView) itemView.findViewById(
                    R.id.HGCImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onTeamSelected(teams.get(position));
                    }
                }
            });
        }
    }

    public void update(List<SugarTeam> newTeams) {
        teams.clear();


        teams.addAll(newTeams); // direkt nem addteams hogy ne mentse ujra
        //addTeams(teams);
        notifyDataSetChanged();
    }


}