package s.com.gojekassignment.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import s.com.gojekassignment.R;
import s.com.gojekassignment.data.model.GithubModel;
import s.com.gojekassignment.databinding.ActivityMainBinding;
import s.com.gojekassignment.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainActivityViewContract {

    @Inject
    MainActivityPresenterContract<MainActivityViewContract> mPresenter;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getActivityComponent().inject(this);
        mPresenter.onAttach(MainActivity.this);
        mPresenter.fetchData();
        setSupportActionBar(binding.vToolbar.myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        binding.vToolbar.toolbarTitle.setText(R.string.trending);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showLoader() {
        binding.progressCircular.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoader() {
        binding.progressCircular.setVisibility(View.GONE);
    }

    @Override
    public void setApiResponse(ArrayList<GithubModel> githubModelArrayList) {
        Adapter adapter = new Adapter(this, githubModelArrayList);
        binding.rvRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rvRecycler.setLayoutManager(layoutManager);
        binding.rvRecycler.setItemAnimator(null);
        binding.rvRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
