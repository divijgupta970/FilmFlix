package com.example.filmflix.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmflix.R;
import com.example.filmflix.activities.ViewMovieActivity;
import com.example.filmflix.databinding.CardMainBinding;
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

        CardMainBinding cardMainBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_main, parent, false);
        return new PopularMoviesViewHolder(cardMainBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final PopularMoviesViewHolder holder, int position) {
        Result result = resultList.get(position);

        holder.cardMainBinding.setMovie(result);

        holder.btnLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Animation animation = AnimationUtils.loadAnimation(mCtx, R.anim.anim_like);
                holder.btnLike.startAnimation(animation);
            }
        });


    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class PopularMoviesViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster;
        private CheckBox btnLike;
        private CardMainBinding cardMainBinding;

        public PopularMoviesViewHolder(@NonNull CardMainBinding cardMainBinding) {
            super(cardMainBinding.getRoot());
            this.cardMainBinding=cardMainBinding;
            ivPoster = cardMainBinding.ivPoster;
            btnLike = cardMainBinding.btnLike;
            cardMainBinding.cardLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Result result = resultList.get(getAdapterPosition());
                        Intent intent = new Intent(mCtx, ViewMovieActivity.class);
                        intent.putExtra("movie", result);
                        String imageTransition = mCtx.getString(R.string.transition_image);
                        Pair<View, String> p1 = Pair.create((View) ivPoster, imageTransition);

                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mCtx, p1);
                        mCtx.startActivity(intent, options.toBundle());
                    }

                }
            });
        }
    }
}
