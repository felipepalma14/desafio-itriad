package com.felipe.palma.githubtrends_itriad.ui.fragment.favoritos;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.felipe.palma.githubtrends_itriad.R;
import com.felipe.palma.githubtrends_itriad.domain.model.Language;
import com.felipe.palma.githubtrends_itriad.ui.fragment.trend.TrendListFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavoritosFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.app_bar)
    AppBarLayout appbar;

    private Unbinder mButterKnife;
    private String mTimeSpan;

    protected ViewPager.OnPageChangeListener onPageChangeListener;
    protected int mCurrentPosition = 0;
    private ActionBar actionBar;

    protected int appbarOffset;
    private LanguagesPagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_appbar,container, false);

        mButterKnife = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mButterKnife.unbind();
    }

    private void initToolbar() {
        actionBar.setTitle("Favoritos Repo");

    }


    private void initViews() {

        if (null != toolbar) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (null != actionBar) {
                initToolbar();
            }
        }



        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                appbarOffset = verticalOffset;
            }
        });


        mPagerAdapter = new LanguagesPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if (null != onPageChangeListener) {
            viewPager.removeOnPageChangeListener(onPageChangeListener);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                updateTimeSpan(mCurrentPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateTimeSpan(final int position) {
        Fragment fragment = getChildFragmentManager().findFragmentByTag(mPagerAdapter.getFragmentTag(R.id.pager, position));
        if (fragment instanceof FavoritosListFragment) {
            if (fragment.isAdded()) {
                ((FavoritosListFragment) fragment).updateTimeSpan(mTimeSpan);
            }
        }
    }



    public class LanguagesPagerAdapter extends FragmentPagerAdapter {
        List<Language> languagesArray = new ArrayList<>();

        public LanguagesPagerAdapter(FragmentManager fm) {
            super(fm);
            Language c = new Language("C","c");
            Language java = new Language("Java","java");
            Language php = new Language("PHP","php");
            Language javascript = new Language("Javascript","javascript");
            Language python = new Language("Python","python");
            Language go = new Language("Go","go");


            languagesArray.add(c);
            languagesArray.add(java);
            languagesArray.add(php);
            languagesArray.add(javascript);
            languagesArray.add(python);
            languagesArray.add(go);


        }

        @Override
        public Fragment getItem(int position) {
            return FavoritosListFragment.newInstance(getContext(), languagesArray.get(position),mTimeSpan);
        }

        @Override
        public int getCount() {
            return languagesArray.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return languagesArray.get(position).name;
        }

        public String getFragmentTag(int viewPagerId, int fragmentPosition) {
            return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
        }
    }


}
