package com.idrisdemir.badapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idrisdemir.badapp.Entity.BadGame;
import com.idrisdemir.badapp.R;

import java.util.ArrayList;

public class DuelListAdapter extends RecyclerView.Adapter<DuelListAdapter.MyViewHolder> {

    private ArrayList<BadGame> data;
    private ItemClickListener itemClickListener;

    public DuelListAdapter(ArrayList<BadGame> data,ItemClickListener itemClickListener)
    {
        this.data=data;
        this.itemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public DuelListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.duel_button_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DuelListAdapter.MyViewHolder holder, int position)
    {
        holder.brainCoinCount.setText(String.valueOf(data.get(position).getJoinPrice()));
        holder.questionCount.setText(String.valueOf(data.get(position).getQuestionSize()));
        holder.batUserCount.setText(String.valueOf(data.get(position).getGameQuota()));
        holder.category.setText(data.get(position).getCategoryName());
        holder.username.setText(data.get(position).getChallengeOwnerUserName());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                itemClickListener.onItemClick(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView brainCoinCount,questionCount,batUserCount,category,username;
        public MyViewHolder(View view)
        {
            super(view);
            brainCoinCount=view.findViewById(R.id.duel_Braincoin);
            questionCount=view.findViewById(R.id.duel_Question);
            batUserCount=view.findViewById(R.id.duel_BatUser);
            category=view.findViewById(R.id.duel_Category);
            username=view.findViewById(R.id.duel_User);
        }
    }

    public interface ItemClickListener
    {
        public void onItemClick(BadGame badgame);
    }
}
