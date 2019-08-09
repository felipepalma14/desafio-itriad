package com.felipe.palma.githubtrends_itriad.utils;

import com.felipe.palma.githubtrends_itriad.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Felipe Palma on 05/08/2019.
 */
public class Config {

    public static final int SPLASH_TIME = 3000;

    public static final String BASE_URL = "https://api.github.com/";

    public static final String BASE_URL_TRENDING = "http://trending.codehub-app.com/v2/trending/";

    public static final String PARAM_QUERY = "language:Java";
    public static final String PARAM_SORT_STARS = "stars";

    public static final String PARAM_SORT_FOLLOWERS = "followers";

    public static final String REPO = "REPO";
    public static final String USER = "USER";

    public static final String SAVE_STATE_LANGUAGE = "SAVE_STATE_LANGUAGE" ;
    public static final String SAVE_STATE_TIME_SPAN = "SAVE_STATE_TIME_SPAN" ;

    public static final Map<String, Integer> LANGUAGE_COLOR_MAP = Collections.unmodifiableMap(
            new HashMap<String, Integer>() {{
                put("Java", R.color.colorPrimary);
                put("C", R.color.colorAccent);
                put("PHP", R.color.color_orange);
                put("Python", R.color.color_blue);
                put("JavaScript", R.color.color_yellow);
                put("Go", R.color.color_red);
            }});

    public static final Map<String, Integer> LANGUAGE_IMAGE_MAP = Collections.unmodifiableMap(
            new HashMap<String, Integer>() {{
                put("Java", R.drawable.java_logo);
                put("C",  R.drawable.c_logo);
                put("PHP",  R.drawable.php_logo);
                put("Python", R.drawable.python_logo);
                put("JavaScript", R.drawable.js_logo);
                put("Go",  R.drawable.go_logo);
            }});

}
