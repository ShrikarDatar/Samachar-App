package com.example.samachar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samachar.parameter.Articles;
import com.example.samachar.parameter.Source;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    List<Articles>articles;
    public Adapter(Context context,List<Articles>articles)
    {
        this.context=context;
        this.articles=articles;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
            final Articles art = articles.get(position);
            final String url =art.getUrl();
            holder.newsTitle.setText(art.getTitle());
            holder.newsDate.setText(art.getPublishedAt());
            String imageUrl =art.getUrlToImage();
            Picasso.get().load(imageUrl).into(holder.imageView);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent  = new Intent(context, NewsInDetails.class);
                    intent.putExtra("url" ,art.getUrl());
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
      //  return articles.size();
        int a ;

        if(articles != null && !articles.isEmpty()) {

            a = articles.size();
        }
        else {

            a = 0;

        }

        return a;
   }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle,newsDate;
        ImageView imageView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsDate=itemView.findViewById(R.id.newDate);
            newsTitle=itemView.findViewById(R.id.newsTitle);
            imageView =itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    public String getCountry()
    {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();

    }
}