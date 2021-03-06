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
import com.google.android.gms.ads.AdRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Carlos Piñan
 */
public class Helper {

    private static final String ADS_DEVICE_TEST = "E55B949B3153747DAAB92A74F4CE6BED";
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
        if (context == null) {
            return true;
        }
        return !isInAirplaneMode(context) && isNetworkAvailable(context);
    }

    public boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return true;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public boolean isInAirplaneMode(Context context) {
        if (context == null) {
            return true;
        }
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
        return StorageHelper.get().getDefaultLanguage();
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

    public AdRequest getAdRequest() {
        // addTestDevice(ADS_DEVICE_TEST)
        return new AdRequest.Builder().build();
    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromResource(InputStream stream,
                                                  int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream, null, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(stream, null, options);
    }

}
