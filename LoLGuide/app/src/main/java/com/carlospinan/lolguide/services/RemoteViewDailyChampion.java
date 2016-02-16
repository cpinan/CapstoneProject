package com.carlospinan.lolguide.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.enums.ChampDataEnum;
import com.carlospinan.lolguide.data.models.Champion;
import com.carlospinan.lolguide.data.responses.ChampionsResponse;
import com.carlospinan.lolguide.helpers.APIHelper;
import com.carlospinan.lolguide.helpers.Helper;
import com.carlospinan.lolguide.helpers.StorageHelper;
import com.carlospinan.lolguide.providers.LolStaticDataAPI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Response;

/**
 * @author Carlos Piñan
 */
public class RemoteViewDailyChampion implements RemoteViewsService.RemoteViewsFactory {

    private List<Champion> items = new ArrayList<>();
    private Context mContext;
    private Bitmap portraitBitmap;

    public RemoteViewDailyChampion(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        updateItems();
    }

    @Override
    public void onDataSetChanged() {
        updateItems();
    }

    @Override
    public void onDestroy() {
        items.clear();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Champion champion = items.get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_daily_champion);
        String title = champion.getName() + " - " + champion.getTitle();
        rv.setTextViewText(R.id.championTextView, title);
        rv.setContentDescription(R.id.portraitImageView, title);
        if (portraitBitmap != null) {
            rv.setImageViewBitmap(R.id.portraitImageView, portraitBitmap);
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void updateItems() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        LolStaticDataAPI api = APIHelper.get().lolStaticAPI().api();
        Call<ChampionsResponse> call = api.getChampions(
                StorageHelper.get().getRegion(),
                Helper.get().getCodeLanguage(),
                null,
                null,
                Helper.get().arrayStringsToStringByComma(
                        ChampDataEnum.image,
                        ChampDataEnum.spells,
                        ChampDataEnum.info,
                        ChampDataEnum.passive
                )
        );
        try {
            Response<ChampionsResponse> execute = call.execute();
            ChampionsResponse response = execute.body();
            List<Champion> tempList = new ArrayList<>();
            for (Map.Entry<String, Champion> entry : response.getData().entrySet()) {
                tempList.add(entry.getValue());
            }
            items.clear();
            int random = (int) (Math.random() * tempList.size() - 1);
            Champion c = tempList.get(random);
            String portraitImage = c.getImage().getFull();
            String portraitImageUrl = StorageHelper.get().getChampionPortraitUrl(portraitImage);
            URL portraitUrl = new URL(portraitImageUrl);
            portraitBitmap = BitmapFactory.decodeStream(portraitUrl.openStream());
            items.add(c);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NetworkOnMainThreadException e) {
            e.printStackTrace();
        }
    }
}
