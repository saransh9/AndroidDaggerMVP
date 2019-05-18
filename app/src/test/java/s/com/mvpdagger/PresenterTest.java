package s.com.mvpdagger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import s.com.mvpdagger.data.ApiCalls;
import s.com.mvpdagger.data.model.GithubModel;
import s.com.mvpdagger.ui.main.MainActivity;
import s.com.mvpdagger.ui.main.MainActivityPresenter;
import s.com.mvpdagger.ui.main.MainActivityPresenterContract;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresenterTest {
    @Mock
    Throwable e;
    @Mock
    private
    ApiCalls apiCalls;
    private MainActivityPresenterContract presenter;
    @Mock
    private
    MainActivity activity;

    @Before
    public void SetUp() {
        presenter = new MainActivityPresenter(apiCalls, new CompositeDisposable());
        presenter.onAttach(activity);

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void dataFetchTestError() {

        when(apiCalls.fetchRepo(false)).thenReturn(Single.error(e));

        presenter.fetchData(true, false);
        verify(activity).showLoader();
        verify(activity).setApiError(e);

    }

    @Test
    public void dataFetchTestSuccess() {

        ArrayList<GithubModel> arrayList = new ArrayList<>();
        when(apiCalls.fetchRepo(false)).thenReturn(Single.just(arrayList));

        presenter.fetchData(true, false);
        verify(activity).showLoader();
        verify(activity).dismissLoader();
        verify(activity).setApiResponse(arrayList);

    }


    @After
    public void tearDown() {
        RxAndroidPlugins.reset();
    }
}
