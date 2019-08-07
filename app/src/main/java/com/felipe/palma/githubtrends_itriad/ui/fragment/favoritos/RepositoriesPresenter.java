package com.felipe.palma.githubtrends_itriad.ui.fragment.favoritos;

import com.felipe.palma.githubtrends_itriad.domain.model.Repository;
import com.felipe.palma.githubtrends_itriad.domain.response.RepositoriesResponse;
import com.felipe.palma.githubtrends_itriad.network.IServiceGithub;
import com.felipe.palma.githubtrends_itriad.network.ServiceGithubImp;
import com.felipe.palma.githubtrends_itriad.utils.Config;

/**
 * Created by Felipe Palma on 11/07/2019.
 */
public class RepositoriesPresenter implements RepositoriesContract.Presenter{

    private RepositoriesContract.View mView;

    public RepositoriesPresenter(RepositoriesContract.View view) {
        this.mView = view;
    }

    @Override
    public void loadRepositories(int page) {
        this.mView.showDialog();
        ServiceGithubImp mServiceGithubImp = new ServiceGithubImp();
        mServiceGithubImp.getFavouriteRepositories(Config.PARAM_QUERY, Config.PARAM_SORT_STARS, page, new IServiceGithub.IServiceCallback<RepositoriesResponse<Repository>>() {
            @Override
            public void onSuccess(RepositoriesResponse repositoriesResponse) {
                mView.showRepositories(repositoriesResponse.getItems());
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
