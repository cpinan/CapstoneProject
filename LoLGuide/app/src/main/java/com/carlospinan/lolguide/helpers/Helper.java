package com.carlospinan.lolguide.helpers;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.carlospinan.lolguide.R;

import java.util.Locale;

/**
 * @author Carlos Piñan
 */
public class Helper {

    private static Helper instance;

    private Helper() { /* UNUSED */ }

    public static Helper get() {
        if (instance == null) {
            instance = new Helper();
        }
        return instance;
    }

    public boolean isNetworkAvailable() {
        return true;
    }

    public boolean isInAirplaneMode() {
        return false;
    }

    public String arrayStringsToStringByComma(String... array) {
        String string = null;
        if (array != null && array.length > 0) {
            int length = array.length;
            if (length == 1) {
                string = array[0];
            } else {
                for (int i = 0; i < length - 1; i++) {
                    string += array[i] + ",";
                }
                string = string.substring(0, string.length() - 1);
            }
        }
        return string;
    }

    public void loadImage(ImageView imageView, String path) {
        final Context context = imageView.getContext();
        Glide.with(context).
                load(path).
                placeholder(R.color.colorPrimaryDark).
//                    error(R.drawable.not_available).
        into(imageView);
    }

    public String getCodeLanguage() {
        String codeLanguage = Locale.getDefault().toString();
        return codeLanguage == null ? "en_US" : codeLanguage;
    }
}
