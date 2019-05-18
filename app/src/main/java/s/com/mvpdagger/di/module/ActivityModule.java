package s.com.mvpdagger.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import s.com.mvpdagger.di.Scope.ActivityContext;
import s.com.mvpdagger.ui.main.MainActivityPresenter;
import s.com.mvpdagger.ui.main.MainActivityPresenterContract;
import s.com.mvpdagger.ui.main.MainActivityViewContract;


@Module
public class ActivityModule {
    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context context() {
        return activity;
    }


    @Provides
    AppCompatActivity activity() {
        return activity;
    }

    @Provides
    CompositeDisposable getCompositDisposible(){
        return  new CompositeDisposable();
    }


    @Provides
    MainActivityPresenterContract<MainActivityViewContract> provideMvpPresenter(MainActivityPresenter<MainActivityViewContract> presenter){
        return presenter;
    }
}
