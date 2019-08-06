package com.felipe.palma.githubtrends_itriad.network;


import com.felipe.palma.githubtrends_itriad.domain.model.Repository;
import com.felipe.palma.githubtrends_itriad.domain.response.RepositoriesResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Felipe Palma on 05/08/2019.
 */
public class ServiceGithubImp implements IServiceGithub {


    private IServiceGithubEndPoint mService = GithubClient.getInstance().getService();

    @Override
    public void getListRepository(String query, String sort, int page, IServiceCallback<RepositoriesResponse> callback) {


        Call<RepositoriesResponse> mCall = mService.getRepositoryList(query,sort,page);

        mCall.enqueue(new Callback<RepositoriesResponse>() {
            @Override
            public void onResponse(Call<RepositoriesResponse> call, Response<RepositoriesResponse> response) {
                if(!response.isSuccessful()){
                    callback.onError("Ocorreu um erro: " + response.errorBody().toString() );
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<RepositoriesResponse> call, Throwable t) {
                callback.onError(t.getMessage());

            }
        });



    }

    @Override
    public void getTrendingRepo(IServiceCallback<ArrayList<Repository>> callback) {
        Call<ArrayList<Repository>> mCall = mService.getTrendingRepo();

        mCall.enqueue(new Callback<ArrayList<Repository>>() {
            @Override
            public void onResponse(Call<ArrayList<Repository>> call, Response<ArrayList<Repository>> response) {
                if(!response.isSuccessful()){
                    callback.onError("Ocorreu um erro: " + response.errorBody().toString() );
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Repository>> call, Throwable t) {
                callback.onError(t.getMessage());

            }
        });
    }


}
