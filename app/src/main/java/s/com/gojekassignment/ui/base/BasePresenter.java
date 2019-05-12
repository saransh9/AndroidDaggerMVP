package s.com.gojekassignment.ui.base;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends BaseViewContract> implements BasePresneterContract<V> {
    private V mBaseView;

    private CompositeDisposable mCompositeDisposable;

    @Inject
    public BasePresenter(CompositeDisposable compositeDisposable) {
        this.mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {

        this.mBaseView = mvpView;
    }

    public V getView() {
        return mBaseView;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    @Override
    public void onDetach() {
        this.mBaseView = null;
        mCompositeDisposable.dispose();
    }

    public boolean isViewAttached() {
        return mBaseView != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(View) before" +
                    " requesting data to the Presenter");
        }
    }
}
