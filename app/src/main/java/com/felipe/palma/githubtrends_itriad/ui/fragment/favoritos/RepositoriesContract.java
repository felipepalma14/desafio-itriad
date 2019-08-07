package com.felipe.palma.githubtrends_itriad.ui.fragment.favoritos;

import com.felipe.palma.githubtrends_itriad.domain.model.Repository;

import java.util.ArrayList;

/**
 * Created by Felipe Palma on 11/07/2019.
 */
public interface RepositoriesContract {

    interface View {

        void hideDialog();
        void showDialog();
        void showError(String error);
        void showRepositories(ArrayList<Repository> itens);
        void showAnimation();
    }

    interface Presenter{
        void loadRepositories(int page);
    }

}
