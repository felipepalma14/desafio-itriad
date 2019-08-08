package com.felipe.palma.githubtrends_itriad.ui.fragment.favoritos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
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

import com.felipe.palma.githubtrends_itriad.R;
import com.felipe.palma.githubtrends_itriad.domain.model.Language;
import com.felipe.palma.githubtrends_itriad.domain.model.Repository;
import com.felipe.palma.githubtrends_itriad.ui.fragment.trend.TrendListFragment;
import com.felipe.palma.githubtrends_itriad.utils.Config;
import com.felipe.palma.githubtrends_itriad.view.EndlessRecyclerViewScrollListener;
import com.felipe.palma.githubtrends_itriad.view.adapter.AnimationItem;
import com.felipe.palma.githubtrends_itriad.view.adapter.RecyclerItemClickListener;
import com.felipe.palma.githubtrends_itriad.view.adapter.RepositoryAdapter;
import com.felipe.palma.githubtrends_itriad.view.adapter.decoration.ItemOffsetDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavoritosListFragment extends Fragment implements RepositoriesContract.View {

    private Unbinder mUnbinder;
    private Language language;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.loading_view)
    ProgressBar mLoading;

    private RepositoriesContract.Presenter mPresenter;
    private ArrayList<Repository> mListRepositories = new ArrayList<>();
    private RepositoryAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());




    public FavoritosListFragment() {
        // Required empty public constructor
    }


    public static Fragment newInstance(Context context, Language language, String timeSpan) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.SAVE_STATE_LANGUAGE, language);
        bundle.putSerializable(Config.SAVE_STATE_TIME_SPAN, timeSpan);
        return Fragment.instantiate(context, FavoritosListFragment.class.getName(), bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        language = (Language) getArguments().getSerializable(Config.SAVE_STATE_LANGUAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favoritos,container, false);

        mUnbinder = ButterKnife.bind(this, rootView);
        mPresenter = new RepositoriesPresenter(this);
        initViews();

        mPresenter.loadRepositories(language.getPath(),1);

        return rootView;
    }

    public void updateTimeSpan(String timeSpan) {
        //this.timeSpan = timeSpan;
        Log.d("LANG", language.getPath());
        mPresenter.loadRepositories(language.getPath(),1);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }


    private void initViews(){
        mAdapter = new RepositoryAdapter(getContext(),mListRepositories,recyclerItemClickListener);
        setupRecyclerView();
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mAdapter.clearItems();
            mPresenter.loadRepositories(language.getPath(),1);
            onItemsLoadComplete();
        });

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mPresenter.loadRepositories(language.getPath(),page);
                int curSize = mAdapter.getItemCount();
                mAdapter.notifyItemRangeInserted(curSize, mListRepositories.size() - 1);
            }
        });

    }

    @Override
    public void hideDialog() {
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void showDialog() {
        if(!mSwipeRefreshLayout.isRefreshing())
            mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showRepositories(ArrayList<Repository> itens) {
        mAdapter.addRepoItems(itens);
        mListRepositories = mAdapter.getItems();
        showAnimation();

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

    private void setupRecyclerView() {
        int spacing = getResources().getDimensionPixelOffset(R.dimen.default_spacing_small);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(spacing));
    }

    private void onItemsLoadComplete() {

        mSwipeRefreshLayout.setRefreshing(false);
    }

    private RecyclerItemClickListener<Repository> recyclerItemClickListener = item -> {
        String owner = item.getOwner().getLogin();
        String repository = item.getName();

//        if(UnsplashApplication.hasNetwork()){
//            Intent mIntent = new Intent(this, PullRequestActivity.class);
//            mIntent.putExtra(Config.OWNER,owner);
//            mIntent.putExtra(Config.REPO,repository);
//            startActivity(mIntent);
//        }else{
//            Toast.makeText(context, getResources().getString(R.string.internet_offline),Toast.LENGTH_LONG).show();
//        }
    };
}
