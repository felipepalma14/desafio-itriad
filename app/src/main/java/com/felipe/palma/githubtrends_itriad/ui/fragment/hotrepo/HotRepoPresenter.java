package com.felipe.palma.githubtrends_itriad.ui.fragment.hotrepo;

import com.felipe.palma.githubtrends_itriad.domain.model.GithubUser;
import com.felipe.palma.githubtrends_itriad.domain.response.RepositoriesResponse;
import com.felipe.palma.githubtrends_itriad.network.IServiceGithub;
import com.felipe.palma.githubtrends_itriad.network.ServiceGithubImp;
import com.felipe.palma.githubtrends_itriad.utils.Config;

/**
 * Created by Felipe Palma on 11/07/2019.
 */
public class HotRepoPresenter implements HotRepoContract.Presenter{

    private HotRepoContract.View mView;

    public HotRepoPresenter(HotRepoContract.View view) {
        this.mView = view;
    }

    @Override
    public void loadRepositories(int page) {
        this.mView.showDialog();
        ServiceGithubImp mServiceGithubImp = new ServiceGithubImp();
        mServiceGithubImp.getHotUsers(Config.PARAM_QUERY, Config.PARAM_SORT_FOLLOWERS, page, new IServiceGithub.IServiceCallback<RepositoriesResponse<GithubUser>>() {
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
