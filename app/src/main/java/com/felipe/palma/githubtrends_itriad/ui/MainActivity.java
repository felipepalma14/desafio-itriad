package com.felipe.palma.githubtrends_itriad.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.felipe.palma.githubtrends_itriad.R;
import com.felipe.palma.githubtrends_itriad.ui.fragment.favoritos.FavoritosFragment;
import com.felipe.palma.githubtrends_itriad.ui.fragment.hotrepo.HotRepoFragment;
import com.felipe.palma.githubtrends_itriad.ui.fragment.tendencias.TrendingFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Felipe Palma on 05/08/2019.
 */

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.bottom_menu)
    ChipNavigationBar mChipNavigationBar;

    //@BindView(R.id.container)
    //View container;

    private FavoritosFragment mFavoritosFragment;
    private HotRepoFragment mHotRepoFragment;
    private TrendingFragment mTrendingFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);


        selectFragment(R.id.menu_famous_users);
        mChipNavigationBar.setItemSelected(R.id.menu_famous_users);

        mChipNavigationBar.setOnItemSelectedListener(id -> {
            selectFragment(id);
        });
    }


    private void selectFragment(final int fragmentId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        hideAllFragment(transaction);

        switch (fragmentId) {
            case R.id.menu_famous_users:
                if (mHotRepoFragment == null) {
                    mHotRepoFragment = new HotRepoFragment();
                    // todo diff with transaction.replace() ?
                    transaction.add(R.id.fragment_container, mHotRepoFragment, "hotRepos");
                } else {
                    transaction.show(mHotRepoFragment);
                }
                break;
            case R.id.menu_hot_repo:
                if (null == mFavoritosFragment) {
                    mFavoritosFragment = new FavoritosFragment();
                    transaction.add(R.id.fragment_container, mFavoritosFragment, "famousUser");
                } else {
                    transaction.show(mFavoritosFragment);
                }
                break;
            case R.id.menu_trending:
                if (null == mTrendingFragment) {
                    mTrendingFragment = new TrendingFragment();
                    transaction.add(R.id.fragment_container, mTrendingFragment, "trending");
                } else {
                    transaction.show(mTrendingFragment);
                }
                break;
        }

        transaction.commit();
    }

    private void hideAllFragment(final FragmentTransaction transaction) {
        if (null != mHotRepoFragment) {
            transaction.hide(mHotRepoFragment);
        }

        if (null != mFavoritosFragment) {
            transaction.hide(mFavoritosFragment);
        }

        if (null != mTrendingFragment) {
            transaction.hide(mTrendingFragment);
        }
    }
}
