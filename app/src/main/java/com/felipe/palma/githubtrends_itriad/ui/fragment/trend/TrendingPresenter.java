package com.felipe.palma.githubtrends_itriad.ui.fragment.trend;

import com.felipe.palma.githubtrends_itriad.domain.model.Repository;
import com.felipe.palma.githubtrends_itriad.domain.response.RepositoriesResponse;
import com.felipe.palma.githubtrends_itriad.network.IServiceGithub;
import com.felipe.palma.githubtrends_itriad.network.ServiceGithubImp;
import com.felipe.palma.githubtrends_itriad.utils.Config;

import java.util.ArrayList;

/**
 * Created by Felipe Palma on 12/07/2019.
 */
public class TrendingPresenter implements TrendingContract.Presenter{

    private TrendingContract.View mView;

    public TrendingPresenter(TrendingContract.View view) {
        this.mView = view;
    }

    @Override
    public void loadTrendingRepositories(String since,String language) {
        this.mView.showDialog();
        ServiceGithubImp mServiceGithubImp = new ServiceGithubImp();
        mServiceGithubImp.getTrendingRepo(since,language, new IServiceGithub.IServiceCallback<ArrayList<Repository>>() {
            @Override
            public void onSuccess(ArrayList<Repository> response) {
                mView.showRepositories(response);
                mView.hideDialog();
            }

            @Override
            public void onError(String error) {
                mView.showError(error);
                mView.hideDialog();
            }
        });

    }
}
