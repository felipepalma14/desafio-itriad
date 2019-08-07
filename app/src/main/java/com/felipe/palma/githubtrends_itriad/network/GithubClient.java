package com.felipe.palma.githubtrends_itriad.network;

import android.util.Log;

import com.felipe.palma.githubtrends_itriad.utils.Config;
import com.felipe.palma.githubtrends_itriad.utils.UnsplashApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Felipe Palma on 05/08/2019.
 */
public class GithubClient {

    private static IServiceGithubEndPoint mService;
    private static GithubClient instance;
    /*
    CRIANDO INSTANCIA DO RETROFIT
     */
    public GithubClient(){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        mService = retrofit.create(IServiceGithubEndPoint.class);
    }

    public static GithubClient getInstance() {
        if (instance == null) {
            instance = new GithubClient();
        }

        return instance;
    }

    public IServiceGithubEndPoint getService() {
        return mService;
    }

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(new ResponseCacheInterceptor())
            .addInterceptor(new OfflineCacheInterceptor())
            .cache(new Cache(new File(
                    UnsplashApplication.getApplicationInstance().getApplicationContext().getCacheDir(),
                    "cacheApiResponses"),
                    5 * 1024 * 1024) // 5 MB
            )
            .build();


    private static class ResponseCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-size=" + 60)
                    .build();
        }
    }


    private static class OfflineCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!UnsplashApplication.hasNetwork()) {
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + (60 * 60 * 24 * 7)) // 1 Week
                        .build();
            }
            return chain.proceed(request);
        }
    }
}
