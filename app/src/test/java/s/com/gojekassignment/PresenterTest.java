package s.com.gojekassignment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import s.com.gojekassignment.data.ApiCalls;
import s.com.gojekassignment.ui.main.MainActivity;
import s.com.gojekassignment.ui.main.MainActivityPresenter;
import s.com.gojekassignment.ui.main.MainActivityPresenterContract;

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

        when(apiCalls.fetchRepo()).thenReturn(Single.error(new IOException()));

        presenter.fetchData(true);
        verify(activity).showLoader();
        // verify(activity).setApiError(e);

    }


    @After
    public void tearDown() {
        RxAndroidPlugins.reset();
    }
}