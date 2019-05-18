package s.com.mvpdagger.di.component;

import android.content.Context;

import dagger.Component;
import s.com.mvpdagger.di.Scope.ActivityContext;
import s.com.mvpdagger.di.Scope.PerActivity;
import s.com.mvpdagger.di.module.ActivityModule;
import s.com.mvpdagger.ui.main.MainActivity;


@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {

    @ActivityContext
    Context getContext();

    void inject(MainActivity mainActivity);

}
