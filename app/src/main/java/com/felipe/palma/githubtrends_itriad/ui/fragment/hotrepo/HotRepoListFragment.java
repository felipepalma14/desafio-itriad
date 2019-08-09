package com.felipe.palma.githubtrends_itriad.ui.fragment.hotrepo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.felipe.palma.githubtrends_itriad.R;
import com.felipe.palma.githubtrends_itriad.domain.model.GithubUser;
import com.felipe.palma.githubtrends_itriad.domain.model.Language;
import com.felipe.palma.githubtrends_itriad.domain.model.Repository;
import com.felipe.palma.githubtrends_itriad.ui.fragment.favoritos.RepositoriesContract;
import com.felipe.palma.githubtrends_itriad.ui.fragment.favoritos.RepositoriesPresenter;
import com.felipe.palma.githubtrends_itriad.ui.fragment.trend.TrendDetailActivity;
import com.felipe.palma.githubtrends_itriad.ui.fragment.trend.TrendListFragment;
import com.felipe.palma.githubtrends_itriad.utils.Config;
import com.felipe.palma.githubtrends_itriad.view.EndlessRecyclerViewScrollListener;
import com.felipe.palma.githubtrends_itriad.view.adapter.AnimationItem;
import com.felipe.palma.githubtrends_itriad.view.adapter.GithubUserAdapter;
import com.felipe.palma.githubtrends_itriad.view.adapter.RecyclerItemClickListener;
import com.felipe.palma.githubtrends_itriad.view.adapter.RepositoryAdapter;
import com.felipe.palma.githubtrends_itriad.view.adapter.decoration.ItemOffsetDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotRepoListFragment extends Fragment implements HotRepoContract.View {

    private Language language;
    private String timeSpan;
    private Unbinder mUnbinder;


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.loading_view)
    ProgressBar mLoading;



    private HotRepoContract.Presenter mPresenter;
    private ArrayList<GithubUser> mListGithubUsers = new ArrayList<>();
    private GithubUserAdapter mAdapter;

    private LinearLayoutManager mLinearLayoutManager;



    public HotRepoListFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Context context, Language language, String timeSpan) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.SAVE_STATE_LANGUAGE, language);
        bundle.putSerializable(Config.SAVE_STATE_TIME_SPAN, timeSpan);
        return Fragment.instantiate(context, HotRepoListFragment.class.getName(), bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        language = (Language) getArguments().getSerializable(Config.SAVE_STATE_LANGUAGE);
        timeSpan = (String) getArguments().getSerializable(Config.SAVE_STATE_TIME_SPAN);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hot_repo,container, false);

        mUnbinder = ButterKnife.bind(this, rootView);
        mPresenter = new HotRepoPresenter(this);

        initViews();

        callData();


        return rootView;
    }

    public void updateTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
        mPresenter.loadRepositories(language.getPath(), 1);

    }

    private void callData() {
        mAdapter.clearItems();
        mPresenter.loadRepositories(language.getPath(), 1);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initViews(){
        mAdapter = new GithubUserAdapter(getContext(),mListGithubUsers,recyclerItemClickListener);
        setupRecyclerView();
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            callData();
            onItemsLoadComplete();
        });

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mPresenter.loadRepositories(language.getPath(), page);
            }
        });

    }

    @Override
    public void hideDialog() {

        mLoading.setVisibility(View.GONE);

        showAnimation();
    }

    @Override
    public void showDialog() {
        if(!mSwipeRefreshLayout.isRefreshing())
            mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String error) {
        Log.d("ERROR", error);
    }

    @Override
    public void showRepositories(ArrayList<GithubUser> itens) {
        mAdapter.addRepoItems(itens);
        mListGithubUsers = mAdapter.getItems();

        int curSize = mAdapter.getItemCount();
        mAdapter.notifyItemRangeInserted(curSize, mListGithubUsers.size() - 1);
        Log.d("ITEM", curSize+"");

    }

    @Override
    public void showAnimation() {
        Context context = mRecyclerView.getContext();
        AnimationItem mAnimationItem =  new AnimationItem("Slide from bottom", R.anim.layout_animation_from_bottom);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, mAnimationItem.getResourceId());

        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void sendUserDetails(GithubUser user) {
        Intent mIntent = new Intent(getContext(), HotRepoDetailActivity.class);
        mIntent.putExtra(Config.USER, user);
        startActivity(mIntent);
    }

    private void setupRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        int spacing = getResources().getDimensionPixelOffset(R.dimen.default_spacing_small);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(spacing));
    }

    private void onItemsLoadComplete() {

        mSwipeRefreshLayout.setRefreshing(false);
    }

    private RecyclerItemClickListener<GithubUser> recyclerItemClickListener = item -> {
        mPresenter.loadUserDetails(item.getLogin());

    };

}
