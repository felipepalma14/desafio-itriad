package com.felipe.palma.githubtrends_itriad.ui.fragment.hotrepo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.felipe.palma.githubtrends_itriad.R;
import com.felipe.palma.githubtrends_itriad.domain.model.GithubUser;
import com.felipe.palma.githubtrends_itriad.domain.response.RepositoriesResponse;
import com.felipe.palma.githubtrends_itriad.network.IServiceGithub;
import com.felipe.palma.githubtrends_itriad.network.ServiceGithubImp;
import com.felipe.palma.githubtrends_itriad.utils.AppUtils;
import com.felipe.palma.githubtrends_itriad.utils.Config;
import com.google.android.material.appbar.AppBarLayout;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HotRepoDetailActivity extends AppCompatActivity {


    private Unbinder mUnbinder;

    private GithubUser mGithubUser;

    //View
    @BindView(R.id.item_profile_img)
    ImageView mImageViewProfileImage;


    @BindView(R.id.toolbar_detail)
    Toolbar mToolbar;

    @BindView((R.id.appBarLayout))
    AppBarLayout mAppBarLayout;

    @BindView(R.id.item_user_name)
    TextView mTextViewUserName;

    @BindView(R.id.item_user_location)
    TextView mTextViewUserLocation;

    @BindView(R.id.item_user_login)
    TextView mTextViewUserLogin;

    @BindView(R.id.item_count_followers)
    TextView mTextViewCountFollowers;

    @BindView(R.id.item_count_following)
    TextView mTextViewCountFollowing;

    @BindView(R.id.item_count_public_repo)
    TextView mTextViewCountPublicRepo;


//    @BindView(R.id.btn_view_github)
//    Button mBtnViewGithubPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_repo_detail);

        mUnbinder = ButterKnife.bind(this);

        mGithubUser = getIntent().getParcelableExtra(Config.USER);

        setupToolBar();
        initViews();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private void initViews() {
        mTextViewUserName.setText(mGithubUser.getName());
        mTextViewUserLogin.setText(mGithubUser.getLogin());
        mTextViewUserLocation.setText(mGithubUser.getLocation());
        mTextViewCountFollowers.setText(String.valueOf(mGithubUser.getFollowers()));
        mTextViewCountFollowing.setText(String.valueOf(mGithubUser.getFollowing()));
        mTextViewCountPublicRepo.setText(String.valueOf(mGithubUser.getPublicRepos()));

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(20)
                .oval(true)
                .build();

        Picasso.with(this)
                .load(mGithubUser.getAvatarUrl())
                .placeholder(R.drawable.ic_loading)
                .fit()
                .transform(transformation)
                .into(mImageViewProfileImage);

    }



    private void setupToolBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_view_github)
    void viewGithbPage(){

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mGithubUser.getHtmlUrl()));
        startActivity(intent);
    }

}
