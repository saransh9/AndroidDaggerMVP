package s.com.gojekassignment.di.component;


import android.content.Context;

import dagger.Component;
import s.com.gojekassignment.ApplicationClass;
import s.com.gojekassignment.data.ApiCalls;
import s.com.gojekassignment.di.Scope.ApplicationContext;
import s.com.gojekassignment.di.Scope.ApplicationScope;
import s.com.gojekassignment.di.module.ApiModule;
import s.com.gojekassignment.di.module.ApplicationModule;


@ApplicationScope
@Component(modules = {ApiModule.class, ApplicationModule.class})
public interface ApplicationComponent {

    void inject(ApplicationClass applicationClass);

    ApiCalls getapiCalls();

    @ApplicationContext
    Context getContext();
}
