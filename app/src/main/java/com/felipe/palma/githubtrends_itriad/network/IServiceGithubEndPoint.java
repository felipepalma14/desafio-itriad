package com.felipe.palma.githubtrends_itriad.network;


import com.felipe.palma.githubtrends_itriad.domain.model.Repository;
import com.felipe.palma.githubtrends_itriad.domain.response.RepositoriesResponse;
import com.felipe.palma.githubtrends_itriad.utils.Config;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Felipe Palma on 05/08/2019.
 */
public interface IServiceGithubEndPoint {

    @GET("search/repositories")
    Call<RepositoriesResponse> getRepositoryList(
            @Query("q") String q,
            @Query("sort") String sort,
            @Query("page") int page
    );

    @GET(Config.BASE_URL_TRENDING)
    Call<ArrayList<Repository>> getTrendingRepo();
}
