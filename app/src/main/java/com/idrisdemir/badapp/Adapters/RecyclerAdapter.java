package com.idrisdemir.badapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idrisdemir.badapp.AdministratorActivities.AddQuestionActivity;
import com.idrisdemir.badapp.Entity.Category;
import com.idrisdemir.badapp.Fragments.QuizFragment;
import com.idrisdemir.badapp.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>
{

    private ArrayList<Integer> data;
    private OnCategoryListener onCategoryListener;

    public RecyclerAdapter(ArrayList <Integer> data,OnCategoryListener onCategoryListener)
    {
        //this.mOnCategoryListener=mOnCategoryListener;
        this.data=data;
        this.onCategoryListener=onCategoryListener;
    }
    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.button_design,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position)
    {
        holder.buttonimage.setImageResource(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onCategoryListener.onCategoryClick(data.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView buttonimage;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            buttonimage=itemView.findViewById(R.id.button_image);
        }
    }

    public interface OnCategoryListener
    {
        public void onCategoryClick(int position);
    }

}

