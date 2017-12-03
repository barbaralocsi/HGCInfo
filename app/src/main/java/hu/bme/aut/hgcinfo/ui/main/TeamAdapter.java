package hu.bme.aut.hgcinfo.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
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
        return new TeamViewHolder(view);
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
            holder.HGCSelector.setChecked(true);
            //holder.HGCSelector.setImageResource(R.drawable.ic_hgc);
        }
        else{
            holder.HGCSelector.setChecked(false);
            //holder.HGCSelector.setImageDrawable(null);
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
     * calls notifyDataSetChanged
     * @param newTeams
     */
    public void addTeams(List<SugarTeam> newTeams){
        // Bring HGC Teams to the front
        ArrayList<Integer> HGCteamIds = HGCTeams.getHGCTeams();
        Iterator<SugarTeam> i = newTeams.iterator();
        ArrayList<SugarTeam> HGCTeams = new ArrayList<>();
        while (i.hasNext()) {
            SugarTeam t = i.next();

            if(tempHGCs.isEmpty() && HGCteamIds.contains(t.teamId)){
                t.isHgc=true;
            }
            if(tempHGCs.contains(t.teamId)){
                t.isHgc = true;
            }
            if(tempFavs.contains(t.teamId)){
                t.isFavourite = true;
            }
        }
        Collections.sort(newTeams);

        for (SugarTeam t: newTeams) {
            t.save();
        }

        teams.addAll(newTeams);
        notifyDataSetChanged();
    }

    public void removeTeamsFromAdaper(){
        teams.clear();
        notifyDataSetChanged();
    }

    private ArrayList<Integer> tempFavs = new ArrayList<>();
    private ArrayList<Integer> tempHGCs = new ArrayList<>();

    public void removeTeams(){
        for (SugarTeam t: teams) {
            if(t.isFavourite){
                tempFavs.add(t.teamId);
            }
            if(t.isHgc){
                tempHGCs.add(t.teamId);
            }
        }
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

        CheckBox HGCSelector;
        //ImageView HGCImageView;
        TextView nameTextView;
        ImageView teamIcon;

        public TeamViewHolder(View itemView) {
            super(itemView);
            nameTextView = 
              (TextView) itemView.findViewById(
              R.id.TeamItemNameTextView);
            teamIcon = (ImageView) itemView.findViewById(
              R.id.TeamItemImageView);
            HGCSelector = (CheckBox) itemView.findViewById(
                    R.id.HGCSelector);
            HGCSelector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(HGCSelector.isChecked()){
                        HGCSelector.setChecked(true);
                        listener.onTeamToHGC(teams.get(position), true);
                    }
                    else{
                        HGCSelector.setChecked(false);
                        listener.onTeamToHGC(teams.get(position), false);
                    }
                }
            });


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
        Collections.sort(newTeams);
        teams.addAll(newTeams); // direkt nem addteams hogy ne mentse ujra
        //addTeams(teams);
        notifyDataSetChanged();
    }
/*
    private Comparator<SugarTeam> comparator = new Comparator<SugarTeam>() {
        @Override
        public int compare(SugarTeam sugarTeam, SugarTeam t1) {
            if (sugarTeam.isHgc == true && t1.isHgc == true) return 0;
            if (sugarTeam.isHgc == false && t1.isHgc == false) return 0;
            if (sugarTeam.isHgc == true && t1.isHgc == false) return -1;
            else return 1;
        }
    };
*/
}