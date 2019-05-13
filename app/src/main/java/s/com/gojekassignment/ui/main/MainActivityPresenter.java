package s.com.gojekassignment.ui.main;

import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import s.com.gojekassignment.data.ApiCalls;
import s.com.gojekassignment.data.model.GithubModel;
import s.com.gojekassignment.ui.base.BasePresenter;

public class MainActivityPresenter<V extends MainActivityViewContract> extends BasePresenter<V> implements MainActivityPresenterContract<V> {

    private static final String TAG = "MainActivityPresenter";
    @Inject
    ApiCalls mApiCalls;
    private MainActivityViewContract mViewContract;

    @Inject
    public MainActivityPresenter(CompositeDisposable compositeDisposable) {
        super(compositeDisposable);

    }

    @Override
    public void fetchData(boolean showLoader) {
        if (!isViewAttached()) {
            return;
        }
        if (showLoader) {
            getView().showLoader();
        } else {
            getView().dismissLoader();
        }
        getView().hideInternetError();

        getCompositeDisposable().add(mApiCalls.fetchRepo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<GithubModel>>() {
                    @Override
                    public void onSuccess(ArrayList<GithubModel> githubModel) {
                        if (isViewAttached()) {
                            getView().dismissLoader();
                            getView().setApiResponse(githubModel);
                        }

                        Log.v("resulttest", githubModel.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().setApiError(e);

                        }
                    }
                }));

    }
}
