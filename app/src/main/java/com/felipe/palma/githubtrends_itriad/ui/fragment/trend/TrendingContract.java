package com.felipe.palma.githubtrends_itriad.ui.fragment.trend;

import com.felipe.palma.githubtrends_itriad.domain.model.Repository;

import java.util.ArrayList;

/**
 * Created by Felipe Palma on 12/07/2019.
 */
public interface TrendingContract {

    interface View {

        void hideDialog();
        void showDialog();
        void showError(String error);
        void showRepositories(ArrayList<Repository> items);
        void showAnimation();
    }

    interface Presenter{
        void loadTrendingRepositories(String since, String language);
    }

}
