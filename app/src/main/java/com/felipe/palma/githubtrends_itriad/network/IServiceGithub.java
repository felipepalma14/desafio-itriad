package com.felipe.palma.githubtrends_itriad.network;


import com.felipe.palma.githubtrends_itriad.domain.model.GithubUser;
import com.felipe.palma.githubtrends_itriad.domain.model.Language;
import com.felipe.palma.githubtrends_itriad.domain.model.Repository;
import com.felipe.palma.githubtrends_itriad.domain.response.RepositoriesResponse;

import java.util.ArrayList;

/**
 * Created by Felipe Palma on 05/08/2019.
 */
public interface IServiceGithub {

    interface IServiceCallback<T>{
        void onSuccess(T t);
        void onError(String error);
    }

    void getFavouriteRepositories(String q, String sort, int page,
                                  IServiceCallback<RepositoriesResponse<Repository>> callback);


    void getHotUsers(String q, String sort, int page,
                                  IServiceCallback<RepositoriesResponse<GithubUser>> callback);

    void getHotUserDetail(String username, IServiceCallback<GithubUser> callback);

    void getTrendingRepo(String since, String language, IServiceCallback<ArrayList<Repository>> callback);


}
