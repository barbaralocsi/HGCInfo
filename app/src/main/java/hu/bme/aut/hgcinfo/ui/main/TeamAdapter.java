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
import java.util.List;

import hu.bme.aut.hgcinfo.R;
import hu.bme.aut.hgcinfo.model.team.Team;

public class TeamAdapter extends
  RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private final List<Team> teams;
    private OnTeamSelectedListener listener;
    private Context mContext;

    public TeamAdapter(OnTeamSelectedListener listener, Context mContext) {
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
                .load(teams.get(position).logo.small)
                .crossFade()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void addTeam(Team newTeam) {
        teams.add(newTeam);
        notifyItemInserted(teams.size() - 1);
    }

    public void addTeams(List<Team> newTeams){
        teams.addAll(newTeams);
        notifyDataSetChanged();
        //notifyItemRangeChanged(); ??
    }




public void removeCity(int position) {
    teams.remove(position);
        notifyItemRemoved(position);
        if (position < teams.size()) {
            notifyItemRangeChanged(position, teams.size() - position);
        }
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder {

        int position;

        TextView nameTextView;
        ImageView imageView;

        public TeamViewHolder(View itemView) {
            super(itemView);
            nameTextView = 
              (TextView) itemView.findViewById(
              R.id.TeamItemNameTextView);
            imageView = (ImageView) itemView.findViewById(
              R.id.TeamItemImageView);
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


}