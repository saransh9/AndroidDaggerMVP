package s.com.gojekassignment.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import s.com.gojekassignment.di.Scope.ActivityContext;
import s.com.gojekassignment.ui.main.MainActivityPresenter;
import s.com.gojekassignment.ui.main.MainActivityPresenterContract;
import s.com.gojekassignment.ui.main.MainActivityViewContract;


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
