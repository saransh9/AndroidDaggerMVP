package s.com.gojekassignment.ui.main;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

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
import s.com.gojekassignment.databinding.LayoutPopUpMenuBinding;
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
        mPresenter.fetchData(true, false);
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
                mPresenter.fetchData(false, true);
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onClickRetryButton(View view) {
        mPresenter.fetchData(true, true);
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
        ImageView imageView = menu.findItem(R.id.overflow_menu).getActionView().findViewById(R.id.iv_overflow);
        imageView.setOnClickListener(this::showPopUpMenu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showPopUpMenu(View view) {

        ArrayList<GithubModel> arrayList = null;
        if (mAdapter != null) {
            arrayList = mAdapter.getmGithubModelArrayList();
        }

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LayoutPopUpMenuBinding popUpMenuBinding = LayoutPopUpMenuBinding.inflate(inflater);

        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setContentView(popUpMenuBinding.getRoot());
        popupWindow.setFocusable(true);
        popupWindow.setWidth(400);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ArrayList<GithubModel> finalArrayList = arrayList;
        popUpMenuBinding.tvSortByName.setOnClickListener(v -> {
            if (finalArrayList != null && mAdapter != null) {
                Collections.sort(finalArrayList, new GithubModel.SortByName());
                mAdapter.setmGithubModelArrayList(finalArrayList);
                mAdapter.notifyDataSetChanged();
            }
            popupWindow.dismiss();
        });

        popUpMenuBinding.tvSortByStars.setOnClickListener(v -> {
            if (finalArrayList != null && mAdapter != null) {
                Collections.sort(finalArrayList, new GithubModel.SortByStars());
                mAdapter.setmGithubModelArrayList(finalArrayList);
                mAdapter.notifyDataSetChanged();
            }
            popupWindow.dismiss();
        });
        popupWindow.showAsDropDown(view);


    }
}
