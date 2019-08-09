package com.felipe.palma.githubtrends_itriad.ui.fragment.trend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.felipe.palma.githubtrends_itriad.R;
import com.felipe.palma.githubtrends_itriad.domain.model.Repository;
import com.felipe.palma.githubtrends_itriad.utils.AppUtils;
import com.felipe.palma.githubtrends_itriad.utils.Config;
import com.google.android.material.appbar.AppBarLayout;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TrendDetailActivity extends AppCompatActivity {


    private Unbinder mUnbinder;

    private Repository mRepository;

    //View
    @BindView(R.id.item_profile_img)
    ImageView mImageViewProfileImage;

    @BindView(R.id.item_img_language)
    ImageView mImageViewLanguageImage;

    @BindView(R.id.toolbar_detail)
    Toolbar mToolbar;

    @BindView((R.id.appBarLayout))
    AppBarLayout mAppBarLayout;

    @BindView(R.id.item_user_name)
    TextView mTextViewUserName;

    @BindView(R.id.item_profile_title)
    TextView mTextViewProfileTitle;

    @BindView(R.id.item_count_forks)
    TextView mTextViewCountForks;

    @BindView(R.id.item_count_stars)
    TextView mTextViewCountStars;

    @BindView(R.id.item_count_watchers)
    TextView mTextViewCountWatchers;


    @BindView(R.id.item_language)
    TextView mTextViewLanguage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_detail);

        mUnbinder = ButterKnife.bind(this);

        mRepository = getIntent().getParcelableExtra(Config.REPO);

        setupToolBar();
        initViews();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private void initViews(){
        mTextViewUserName.setText(mRepository.getOwner().getLogin());
        mTextViewProfileTitle.setText(mRepository.getName());
        mTextViewCountStars.setText(String.valueOf(mRepository.getStargazersCount()));
        mTextViewCountWatchers.setText(String.valueOf(mRepository.getWatchers()));
        mTextViewCountForks.setText(String.valueOf(mRepository.getForks()));

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(20)
                .oval(true)
                .build();

        Picasso.with(this)
                .load(mRepository.getOwner().getAvatarUrl())
                .placeholder(R.drawable.ic_loading)
                .fit()
                .transform(transformation)
                .into(mImageViewProfileImage);


        if(mRepository.getLanguage() != null) {
            mImageViewLanguageImage.setVisibility(View.VISIBLE);
            mTextViewLanguage.setVisibility(View.VISIBLE);
            mTextViewLanguage.setText(mRepository.getLanguage());
            updateColorTheme();

        } else {
            mImageViewLanguageImage.setVisibility(View.INVISIBLE);
            mTextViewLanguage.setVisibility(View.INVISIBLE);
        }
    }

    private void updateColorTheme() {
        int bgColor = AppUtils.getColorByLanguage(getApplicationContext(), mRepository.getLanguage());

        mAppBarLayout.setBackgroundColor(bgColor);
        mToolbar.setBackgroundColor(bgColor);
        //mImageViewLanguageImage.setImageDrawable(AppUtils.updateGradientDrawableColor(getApplicationContext(), bgColor));
        mImageViewLanguageImage.setImageDrawable(AppUtils.updateDrawableImageLanguage(getApplicationContext(),mRepository.getLanguage()));




        AppUtils.updateStatusBarColor(this, bgColor);
    }

    private void setupToolBar(){
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
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
}
