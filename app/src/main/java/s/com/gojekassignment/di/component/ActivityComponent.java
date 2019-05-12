package s.com.gojekassignment.di.component;

import android.content.Context;


import dagger.Component;
import s.com.gojekassignment.ui.main.MainActivity;
import s.com.gojekassignment.di.Scope.ActivityContext;
import s.com.gojekassignment.di.Scope.PerActivity;
import s.com.gojekassignment.di.module.ActivityModule;


@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {

    @ActivityContext
    Context getContext();

    void inject(MainActivity mainActivity);

}
