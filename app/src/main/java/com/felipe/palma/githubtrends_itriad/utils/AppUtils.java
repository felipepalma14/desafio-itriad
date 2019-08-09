package com.felipe.palma.githubtrends_itriad.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;

import androidx.core.content.ContextCompat;

import com.felipe.palma.githubtrends_itriad.R;


public class AppUtils {



    public static int getColorByLanguage(Context context,
                                         String language) {
        if(Config.LANGUAGE_COLOR_MAP.containsKey(language))
            return ContextCompat.getColor(context, Config.LANGUAGE_COLOR_MAP.get(language));
        else return ContextCompat.getColor(context, R.color.colorPrimary);
    }

    public static void updateStatusBarColor(Activity activity,
                                            int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        }
    }


    public static int lighten(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = lightenColor(red, fraction);
        green = lightenColor(green, fraction);
        blue = lightenColor(blue, fraction);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }

    private static int lightenColor(int color, double fraction) {
        return (int) Math.min(color + (color * fraction), 255);
    }

    public static Drawable updateGradientDrawableColor(Context context,
                                                       int bgColor) {
        GradientDrawable drawable = (GradientDrawable) context.getResources().getDrawable(R.drawable.ic_circle);
        drawable.setColor(bgColor);
        return drawable;
    }

    public static Drawable updateDrawableImageLanguage(Context context, String language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(Config.LANGUAGE_IMAGE_MAP.get(language), context.getTheme());
        } else {
            return context.getResources().getDrawable(Config.LANGUAGE_IMAGE_MAP.get(language));
        }

    }
}
