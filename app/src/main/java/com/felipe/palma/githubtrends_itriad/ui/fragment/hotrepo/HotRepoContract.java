package com.felipe.palma.githubtrends_itriad.ui.fragment.hotrepo;

import com.felipe.palma.githubtrends_itriad.domain.model.GithubUser;
import com.felipe.palma.githubtrends_itriad.domain.model.Repository;

import java.util.ArrayList;

/**
 * Created by Felipe Palma on 07/08/2019.
 */
public interface HotRepoContract {

    interface View {

        void hideDialog();
        void showDialog();
        void showError(String error);
        void showRepositories(ArrayList<GithubUser> itens);
        void showAnimation();
        void sendUserDetails(GithubUser user);
    }

    interface Presenter{
        void loadRepositories(String query, int page);
        void loadUserDetails(String username);
    }

}
