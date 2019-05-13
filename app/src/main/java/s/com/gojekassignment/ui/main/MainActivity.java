package s.com.gojekassignment.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import javax.inject.Inject;

import s.com.gojekassignment.R;
import s.com.gojekassignment.data.model.GithubModel;
import s.com.gojekassignment.databinding.ActivityMainBinding;
import s.com.gojekassignment.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainActivityViewContract {

    @Inject
    MainActivityPresenterContract<MainActivityViewContract> mPresenter;

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setView(this);
        mBinding.lInternetNotFound.setView(this);
        getActivityComponent().inject(this);
        mPresenter.onAttach(MainActivity.this);

        setSupportActionBar(mBinding.vToolbar.myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mBinding.vToolbar.toolbarTitle.setText(R.string.trending);
        mPresenter.fetchData(true);
        mBinding.srlSwipeRefreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.fetchData(false);
            }
        });

    }

    public void onClickRetryButton(View view) {
        mPresenter.fetchData(true);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showLoader() {
        mBinding.progressCircular.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoader() {
        mBinding.progressCircular.setVisibility(View.GONE);
    }

    @Override
    public void hideInternetError() {
        mBinding.srlSwipeRefreshList.setVisibility(View.VISIBLE);
        mBinding.lInternetNotFound.clParentNoInternet.setVisibility(View.GONE);
    }

    @Override
    public void setApiResponse(ArrayList<GithubModel> githubModelArrayList) {
        mBinding.srlSwipeRefreshList.setRefreshing(false);
        Adapter adapter = new Adapter(this, githubModelArrayList);
        mBinding.rvRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mBinding.rvRecycler.setLayoutManager(layoutManager);
        mBinding.rvRecycler.setItemAnimator(null);
        DividerItemDecoration myDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));
        mBinding.rvRecycler.addItemDecoration(myDivider);
        // mBinding.rvRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    public void setApiError(Throwable e) {
        mBinding.srlSwipeRefreshList.setRefreshing(false);
        dismissLoader();
        mBinding.srlSwipeRefreshList.setVisibility(View.GONE);
        mBinding.lInternetNotFound.clParentNoInternet.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
