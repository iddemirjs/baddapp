package com.idrisdemir.badapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idrisdemir.badapp.Entity.New;
import com.idrisdemir.badapp.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    ArrayList<New> news;

    public SliderAdapter(ArrayList<New>news)
    {
        this.news = news;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_slider_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position)
    {
        viewHolder.newsText.setText(news.get(position).getTitle());
        Picasso.get().load(news.get(position).getImageUrl()).into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return news.size();
    }

    public class Holder extends SliderViewAdapter.ViewHolder{
        ImageView imageView;
        TextView newsText;
        public Holder(View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.home_image_slider_item_iv);
            newsText=itemView.findViewById(R.id.news_title);

        }
    }


}
