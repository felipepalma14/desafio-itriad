package com.felipe.palma.githubtrends_itriad.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.felipe.palma.githubtrends_itriad.R;
import com.felipe.palma.githubtrends_itriad.domain.model.Language;
import com.felipe.palma.githubtrends_itriad.ui.fragment.favoritos.FavoritosFragment;
import com.felipe.palma.githubtrends_itriad.ui.fragment.favoritos.RepositoriesContract;
import com.felipe.palma.githubtrends_itriad.ui.fragment.hotrepo.HotRepoFragment;
import com.felipe.palma.githubtrends_itriad.ui.fragment.trend.TrendingFragment;
import com.google.android.material.tabs.TabLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Felipe Palma on 05/08/2019.
 */

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.bottom_menu)
    ChipNavigationBar mChipNavigationBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    //@BindView(R.id.pager)
    //ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;

    private String mTimeSpan = "daily";
    private FavoritosFragment mFavoritosFragment;
    private HotRepoFragment mHotRepoFragment;
    private TrendingFragment mTrendingFragment;
    private View spinnerContainer;
    private LanguagesPagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);



        setupToolBar();

        selectFragment(R.id.menu_trending);

        mChipNavigationBar.setItemSelected(R.id.menu_trending);


        mChipNavigationBar.setOnItemSelectedListener(id -> {
            selectFragment(id);
        });


    }

    private void setupToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
    }


    private void selectFragment(final int fragmentId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        hideAllFragment(transaction);
        mToolbar.removeView(spinnerContainer);
        mTabLayout.setVisibility(View.GONE);
        //viewPager.setVisibility(View.GONE);

        switch (fragmentId) {
            case R.id.menu_famous_users:
                if (null == mFavoritosFragment) {
                    mFavoritosFragment = new FavoritosFragment();
                    transaction.add(R.id.fragment_container, mFavoritosFragment, "famousUser");
                } else {
                    transaction.show(mFavoritosFragment);
                }
                break;
            case R.id.menu_hot_repo:
                if (mHotRepoFragment == null) {
                    mHotRepoFragment = new HotRepoFragment();
                    transaction.add(R.id.fragment_container, mHotRepoFragment, "hotRepos");
                } else {
                    transaction.show(mHotRepoFragment);
                }
                break;

            case R.id.menu_trending:
                setupTrendSpinner();
                setupTabs();

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

    private void setupTrendSpinner() {
        spinnerContainer = LayoutInflater.from(this).inflate(R.layout.toolbar_spinner,
                mToolbar, false);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mToolbar.addView(spinnerContainer, lp);

        SinceSpinnerAdapter spinnerAdapter = new SinceSpinnerAdapter();

        Spinner spinner = (Spinner) spinnerContainer.findViewById(R.id.toolbar_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // daily
                        mTimeSpan = "daily";
                        break;
                    case 1: // weekly
                        mTimeSpan = "weekly";
                        break;
                    case 2: // monthly
                        mTimeSpan = "monthly";
                        break;

                }

                Fragment fragment = getSupportFragmentManager().findFragmentByTag("trending");
                if (fragment instanceof TrendingFragment) {
                    if (fragment.isAdded()) {
                        ((TrendingFragment) fragment).updateTimeSpan(mTimeSpan);
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setupTabs() {
        mPagerAdapter = new LanguagesPagerAdapter(getSupportFragmentManager());
        //viewPager.setAdapter(mPagerAdapter);

       // viewPager.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.VISIBLE);
        //mTabLayout.setupWithViewPager(viewPager);
    }

    public class SinceSpinnerAdapter extends BaseAdapter {

        final String[] timeSpanTextArray = new String[]{"De Hoje", "Desta Semana", "Deste Mês"};

        @Override
        public int getCount() {
            return timeSpanTextArray.length;
        }

        @Override
        public String getItem(int position) {
            return timeSpanTextArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView != null ? convertView :
                    getLayoutInflater().inflate(R.layout.toolbar_spinner_item_actionbar, parent, false);

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText("Tendências " + getItem(position));
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            View view = convertView != null ? convertView :
                    getLayoutInflater().inflate(R.layout.toolbar_spinner_item_dropdown, parent, false);

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }
    }

    public class LanguagesPagerAdapter extends FragmentPagerAdapter {

        Language[] languagesArray;



        public LanguagesPagerAdapter(FragmentManager fm) {
            super(fm);

            Language allLanguages = new Language("Todas",null);
            Language java = new Language("Java","java");
            Language php = new Language("PHP","php");
            Language javascript = new Language("Javascript","javascript");
            Language python = new Language("Python","python");

            languagesArray = new Language[]{allLanguages, java, php, python,javascript};
        }

        public void onLanguagesChange() {
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return TrendingFragment.newInstance(getApplicationContext(), languagesArray[position], mTimeSpan);
        }

        @Override
        public int getCount() {
            return languagesArray.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return languagesArray[position].name;
        }

    }
}
