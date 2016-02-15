package com.carlospinan.lolguide.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.carlospinan.lolguide.R;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.enums.ChampDataEnum;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

/**
 * @author Carlos Piñan
 */
public class Helper {

    private static final String PATH = "/ChampionsImage/com.carlospinan.champions";

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

    public void intentWithConnection(
            View view,
            Context context,
            Intent intent
    ) {
        if (hasInternetConnection(context)) {
            context.startActivity(intent);
        } else {
            Snackbar.make(
                    view,
                    context.getString(R.string.no_internet),
                    Snackbar.LENGTH_LONG
            ).show();
        }
    }

    public String saveBitmap(Bitmap bitmap, String filename) {
        String baseDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + PATH;
        File fileDirectory = new File(baseDirectory);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        File fileBitmap = new File(fileDirectory, filename);
        if (fileBitmap.exists()) {
            fileBitmap.delete();
        }
        OutputStream outStream;
        try {
            outStream = new FileOutputStream(fileBitmap);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
        }
        return fileBitmap.getAbsolutePath();
    }

    public Bitmap getBitmapFromFilename(String filepath) {
        String baseDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + PATH;
        File fileDirectory = new File(baseDirectory);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
            return null;
        }
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(filepath, bmOptions);
        return bitmap;
    }

}
