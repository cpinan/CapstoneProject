package com.carlospinan.lolguide.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.listeners.ChampionsAdapterListener;
import com.github.florent37.glidepalette.GlidePalette;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Carlos Piñan
 */
public class ChampionsAdapter extends RecyclerView.Adapter<ChampionsAdapter.ViewHolder> implements Filterable {

    private LayoutInflater inflater;

    private List<Champion> champions;
    private List<Champion> filteredChampions;
    private ChampionFilter filter;

    private ChampionsAdapterListener listener;

    public ChampionsAdapter(Context context, List<Champion> champions, ChampionsAdapterListener listener) {
        this.champions = champions;
        Collections.sort(this.champions, new Champion());
        this.filteredChampions = this.champions;
        this.filter = new ChampionFilter();
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_champion_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Champion champion = filteredChampions.get(position);
        if (champion != null && holder != null && holder.championImage != null) {
            ImageView championImage = holder.championImage;
            final TextView championNameTextView = holder.championNameTextView;
            Context context = championImage.getContext();
            final String path = StorageHelper.get().getChampionPortraitUrl(champion.getImage().getFull());

            Glide.with(context).
                    load(path).
                    placeholder(R.color.colorPrimaryDark).
                    error(R.color.orange).
                    listener(
                            GlidePalette.with(path).use(GlidePalette.Profile.VIBRANT)
                                    .intoBackground(championNameTextView)
                                    .intoTextColor(championNameTextView)
                                    .crossfade(true)).
                    into(championImage);

            championNameTextView.setText(champion.getName());
            championImage.setContentDescription(champion.getName());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                championImage.setTransitionName("championPortrait" + position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return filteredChampions.size();
    }

    public void updateChampions(Map<String, Champion> hash) {
        this.champions = new ArrayList<>();
        for (Map.Entry<String, Champion> entry : hash.entrySet()) {
            this.champions.add(entry.getValue());
        }
        Collections.sort(this.champions, new Champion());
        this.filteredChampions = this.champions;
    }

    public List<Champion> getChampions() {
        return champions;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView championImage;
        public TextView championNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            championImage = (ImageView) itemView.findViewById(R.id.championImage);
            championNameTextView = (TextView) itemView.findViewById(R.id.championNameTextView);
            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClickChampion(championImage, filteredChampions.get(getAdapterPosition()));
                    }
                });
            }
        }
    }

    private class ChampionFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            if (filterString == null || filterString.trim().length() == 0) {
                results.values = champions;
                results.count = champions.size();
            } else {
                final List<Champion> filteredList = new ArrayList<>();
                for (Champion champion : champions) {
                    String championName = champion.getName().toLowerCase();
                    String championKey = champion.getKey().toLowerCase();
                    if (championName.contains(filterString) || championKey.contains(filterString)) {
                        filteredList.add(champion);
                    }
                }
                results.values = filteredList;
                results.count = filteredList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredChampions = (ArrayList<Champion>) results.values;
            notifyDataSetChanged();
        }
    }
}
