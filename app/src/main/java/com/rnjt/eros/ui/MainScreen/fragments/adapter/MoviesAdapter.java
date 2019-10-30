package com.rnjt.eros.ui.MainScreen.fragments.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.rnjt.eros.R;
import com.rnjt.eros.api.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.UserHolder> implements View.OnClickListener, Filterable {

    private ItemClick itemClick;
    ArrayList<Movie> userEntities = new ArrayList<>();
    Context context;
    private ArrayList<Movie> movieArrayListFiltered;

    public MoviesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_movies, viewGroup, false);
        itView.setOnClickListener(this);

        return new UserHolder(itView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int i) {
        Movie userEntity = userEntities.get(i);
        holder.txtDate.setText(userEntity.getReleaseDate());
        holder.txtTitle.setText(userEntity.getTitle());
        holder.txtDesc.setText(userEntity.getOverview());
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w185_and_h278_bestv2" + userEntity.getPosterPath())
                .into(holder.imgMovie);
        if (!userEntity.isFavourite()) {
            holder.ivFavourite.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_off));
        } else {
            holder.ivFavourite.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_on));
        }
        holder.itemView.setTag(userEntity);


    }

    @Override
    public int getItemCount() {
        return userEntities.size();
    }

    @Override
    public void onClick(View v) {
        itemClick.onItemClick(v, (Movie) v.getTag());

    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    movieArrayListFiltered = userEntities;
                } else {
                    ArrayList<Movie> filteredList = new ArrayList<>();
                    for (Movie row : userEntities) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    movieArrayListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = movieArrayListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                movieArrayListFiltered = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class UserHolder extends RecyclerView.ViewHolder {

        TextView txtDesc, txtTitle, txtDate, txtMore;
        ImageView ivFavourite, imgMovie;

        public UserHolder(@NonNull final View itemView) {
            super(itemView);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtMore = itemView.findViewById(R.id.txtMore);
            imgMovie = itemView.findViewById(R.id.imgMovie);
            ivFavourite = itemView.findViewById(R.id.ivFavourite);

//            txtMore.setOnClickListener(v -> {
//                if (itemView != null && getAdapterPosition() != RecyclerView.NO_POSITION)
//                    itemClick.onItemClick(itemView, userEntities.get(getAdapterPosition()));
//            });
            ivFavourite.setOnClickListener(v -> {
                if (itemView != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                    itemClick.onFavouriteClick(userEntities.get(getAdapterPosition()));
            });
        }
    }

    public void setUserEntities(ArrayList<Movie> userEntities) {
        this.userEntities = userEntities;
        movieArrayListFiltered = userEntities;
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getUserEntities() {
        return this.userEntities;
    }

    public void setFilter(ArrayList<Movie> FilteredDataList) {
        this.userEntities = FilteredDataList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ItemClick listener) {
        this.itemClick = listener;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public interface ItemClick {
        void onItemClick(View view, Movie userEntity);

        void onFavouriteClick(Movie userEntity);
    }
}