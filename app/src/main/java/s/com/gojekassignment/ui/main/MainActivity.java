package s.com.gojekassignment.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import s.com.gojekassignment.R;
import s.com.gojekassignment.data.model.GithubModel;
import s.com.gojekassignment.databinding.ActivityMainBinding;
import s.com.gojekassignment.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainActivityViewContract {

    @Inject
    MainActivityPresenterContract<MainActivityViewContract> mPresenter;

    ActivityMainBinding mBinding;
    Adapter mAdapter;

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
        mAdapter = new Adapter(this, null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mBinding.rvRecycler.setLayoutManager(layoutManager);
        mBinding.rvRecycler.setItemAnimator(null);
        DividerItemDecoration myDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));
        mBinding.rvRecycler.addItemDecoration(myDivider);
        mBinding.rvRecycler.setAdapter(mAdapter);
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
        mBinding.lInternetNotFound.clParentNoInternet.setVisibility(View.GONE);
    }

    @Override
    public void setApiResponse(ArrayList<GithubModel> githubModelArrayList) {
        mBinding.srlSwipeRefreshList.setVisibility(View.VISIBLE);
        mBinding.srlSwipeRefreshList.setRefreshing(false);
        mAdapter.setmGithubModelArrayList(githubModelArrayList);
        mAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        ArrayList<GithubModel> arrayList = null;
        if (mAdapter != null) {
            arrayList = mAdapter.getmGithubModelArrayList();
        }
        switch (id) {
            case R.id.sort_by_name:
                if (arrayList != null && mAdapter != null) {
                    Collections.sort(arrayList, new GithubModel.SortByName());
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.sort_by_stars:
                if (arrayList != null && mAdapter != null) {
                    Collections.sort(arrayList, new GithubModel.SortByStars());
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
