package com.felipe.palma.githubtrends_itriad.network;


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

    void getListRepository(String q, String sort, int page,
                           IServiceCallback<RepositoriesResponse> callback);

    void getTrendingRepo(IServiceCallback<ArrayList<Repository>> callback);


}
