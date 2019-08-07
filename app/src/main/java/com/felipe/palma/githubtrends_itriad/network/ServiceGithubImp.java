package com.felipe.palma.githubtrends_itriad.network;


import com.felipe.palma.githubtrends_itriad.domain.model.GithubUser;
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
    public void getFavouriteRepositories(String query, String sort, int page, IServiceCallback<RepositoriesResponse<Repository>> callback) {


        Call<RepositoriesResponse<Repository>> mCall = mService.getRepositoryList(query,sort,page);

        mCall.enqueue(new Callback<RepositoriesResponse<Repository>>() {
            @Override
            public void onResponse(Call<RepositoriesResponse<Repository>> call, Response<RepositoriesResponse<Repository>> response) {
                if(!response.isSuccessful()){
                    callback.onError("Ocorreu um erro: " + response.errorBody().toString() );
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<RepositoriesResponse<Repository>> call, Throwable t) {
                callback.onError(t.getMessage());

            }
        });



    }

    @Override
    public void getHotUsers(String query, String sort, int page, IServiceCallback<RepositoriesResponse<GithubUser>> callback) {
        Call<RepositoriesResponse<GithubUser>> mCall = mService.getUserList(query,sort,page);

        mCall.enqueue(new Callback<RepositoriesResponse<GithubUser>>() {
            @Override
            public void onResponse(Call<RepositoriesResponse<GithubUser>> call, Response<RepositoriesResponse<GithubUser>> response) {
                if(!response.isSuccessful()){
                    callback.onError("Ocorreu um erro: " + response.errorBody().toString() );
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<RepositoriesResponse<GithubUser>> call, Throwable t) {
                callback.onError(t.getMessage());

            }
        });


    }

    @Override
    public void getTrendingRepo(String since, String language, IServiceCallback<ArrayList<Repository>> callback) {
        Call<ArrayList<Repository>> mCall = mService.getTrendingRepo(since,language);

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
