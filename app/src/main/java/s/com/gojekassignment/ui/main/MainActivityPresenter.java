package s.com.gojekassignment.ui.main;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import s.com.gojekassignment.ui.base.BasePresenter;
import s.com.gojekassignment.ui.base.BaseViewContract;

public class MainActivityPresenter<V extends MainActivityViewContract> extends BasePresenter<V> implements MainActivityPresenterContract<V> {

    private static final String TAG = "MainActivityPresenter";
    private MainActivityViewContract mViewContract;

    @Inject
    public MainActivityPresenter(CompositeDisposable compositeDisposable) {
        super(compositeDisposable);

    }
}
