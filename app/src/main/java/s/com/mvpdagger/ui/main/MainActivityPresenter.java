package s.com.mvpdagger.ui.main;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import s.com.mvpdagger.R;
import s.com.mvpdagger.data.ApiCalls;
import s.com.mvpdagger.data.model.GithubModel;
import s.com.mvpdagger.ui.base.BasePresenter;
import s.com.mvpdagger.utils.EspressoIdlingResource;

public class MainActivityPresenter<V extends MainActivityViewContract> extends BasePresenter<V> implements MainActivityPresenterContract<V> {

    private static final String TAG = "MainActivityPresenter";

    ApiCalls mApiCalls;
    @Inject
    SharedPreferences sharedPreferences;
    private MainActivityViewContract mViewContract;

    @Inject
    public MainActivityPresenter(ApiCalls apiCalls, CompositeDisposable compositeDisposable) {
        super(compositeDisposable);
        this.mApiCalls = apiCalls;

    }

    @Override
    public void fetchData(boolean showLoader, boolean forceRefresh) {
        if (!isViewAttached()) {
            return;
        }
        if (showLoader) {
            getView().showLoader();
        } else {
            getView().dismissLoader();
        }
        getView().hideInternetError();

        getCompositeDisposable().add(mApiCalls.fetchRepo(forceRefresh)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    EspressoIdlingResource.getInstance().increment();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    EspressoIdlingResource.getInstance().decrement();
                })
                .subscribeWith(new DisposableSingleObserver<ArrayList<GithubModel>>() {
                    @Override
                    public void onSuccess(ArrayList<GithubModel> githubModel) {

                        if (isViewAttached()) {
                            setTimeHeader();
                            getView().dismissLoader();
                            getView().setApiResponse(githubModel);
                        }

                        Log.v("resulttest", githubModel.toString() + "    " + sharedPreferences.getBoolean("from_cache", false));
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().hideTimeText();
                            getView().setApiError(e);

                        }
                    }
                }));

    }

    private void setTimeHeader() {
        if (sharedPreferences.getBoolean("from_cache", false)) {
            long time = System.currentTimeMillis() - sharedPreferences.getLong("time", 0);
            int textToShow = 0;
            if (time / (60 * 1000) < 30) {
                textToShow = R.string.thirty_mins;
            } else if (time / (60 * 1000) > 30 && time / (60 * 1000) < 60) {
                textToShow = R.string.sixty_mins;
            } else if (time / (60 * 1000) > 60 && time / (60 * 1000) < 120) {
                textToShow = R.string.ninety_mins;
            }
            if (textToShow != 0) {
                getView().setTimeText(textToShow);
            } else {
                getView().hideTimeText();
            }

        } else {
            sharedPreferences.edit().putLong("time", System.currentTimeMillis()).apply();
            getView().hideTimeText();
        }
    }
}
