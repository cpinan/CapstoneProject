package com.carlospinan.lolguide.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.ChampDataEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author Carlos Piñan
 */
public class Helper {

    private static final String DELIMITER = ";;;";
    private static Helper instance;

    private Helper() { /* UNUSED */ }

    public static Helper get() {
        if (instance == null) {
            instance = new Helper();
        }
        return instance;
    }

    public boolean hasInternetConnection(Context context) {
        return !isInAirplaneMode(context) && isNetworkAvailable(context);
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public boolean isInAirplaneMode(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    public String arrayStringsToStringByComma(ChampDataEnum... array) {
        String string = "";
        if (array != null && array.length > 0) {
            int length = array.length;
            if (length == 1) {
                string = String.valueOf(array[0]);
            } else {
                for (ChampDataEnum champData : array) {
                    string += String.valueOf(champData) + ",";
                }
                string = string.substring(0, string.length() - 1);
            }
        }
        return string;
    }

    public void loadImage(ImageView imageView, final String path) {
        final Context context = imageView.getContext();
        Glide.with(context).
                load(path).
                placeholder(R.color.colorPrimaryDark).
                error(android.R.color.holo_red_dark).
                listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Globals.l(path);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                }).
                into(imageView);
    }

    public String getCodeLanguage() {
        String codeLanguage = Locale.getDefault().toString();
        List<String> languages = StorageHelper.get().getLanguages();
        if (languages != null) {
            if (!languages.contains(codeLanguage)) {
                codeLanguage = "en_US";
            }
        }
        return codeLanguage == null ? "en_US" : codeLanguage;
    }

    public ChampDataEnum getChampDataFromString(String value) {
        try {
            return ChampDataEnum.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }

    // String array utility
    public static String getStringFromList(List<String> list) {
        String response = "";
        if (list != null && !list.isEmpty()) {
            if (list.size() == 1) {
                response = list.get(0);
            } else {
                for (Object object : list) {
                    response += object + DELIMITER;
                }
                response = response.substring(0, response.length() - DELIMITER.length());
            }
        }
        return response;
    }

    public static String getStringFromDoubleList(List<Double> list) {
        List<String> newList = new ArrayList<>();
        for (Double d : list) {
            newList.add(String.valueOf(d));
        }
        return getStringFromList(newList);
    }

    public static String getStringFromIntegerList(List<Integer> list) {
        List<String> newList = new ArrayList<>();
        for (Integer i : list) {
            newList.add(String.valueOf(i));
        }
        return getStringFromList(newList);
    }

    // from String to List
    public static List<String> getListStringFromString(String string) {
        String[] split = string.split(DELIMITER);
        return Arrays.asList(split);
    }
}
