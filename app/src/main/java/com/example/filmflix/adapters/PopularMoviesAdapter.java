package com.example.filmflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filmflix.R;
import com.example.filmflix.model.Result;

import java.util.List;

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder> {
    private List<Result> resultList;
    private Context mCtx;

    public PopularMoviesAdapter(List<Result> resultList, Context mCtx) {
        this.resultList = resultList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public PopularMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(mCtx).inflate(R.layout.card_main,parent,false);
        return new PopularMoviesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PopularMoviesViewHolder holder, int position) {
        Result result=resultList.get(position);
        holder.tvTitle.setText(result.getTitle());
        holder.tvRating.setText(String.valueOf(result.getVoteAverage()));

        holder.btnLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Animation animation = AnimationUtils.loadAnimation(mCtx, R.anim.anim_like);
                holder.btnLike.startAnimation(animation);
            }
        });

        String imagePath="https://image.tmdb.org/t/p/w500"+result.getPosterPath();
        Glide.with(mCtx)
                .load(imagePath)
                .placeholder(mCtx.getDrawable(R.drawable.placeholder))
                .into(holder.ivPoster);

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class PopularMoviesViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivPoster;
        private TextView tvRating,tvTitle;
        private CheckBox btnLike;
        public PopularMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRating=itemView.findViewById(R.id.tvRating);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            ivPoster=itemView.findViewById(R.id.ivPoster);
            btnLike=itemView.findViewById(R.id.btnLike);
        }
    }
}
