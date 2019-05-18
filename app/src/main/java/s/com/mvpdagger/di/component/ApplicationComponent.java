package s.com.mvpdagger.di.component;


import android.content.Context;

import dagger.Component;
import s.com.mvpdagger.ApplicationClass;
import s.com.mvpdagger.data.ApiCalls;
import s.com.mvpdagger.di.Scope.ApplicationContext;
import s.com.mvpdagger.di.Scope.ApplicationScope;
import s.com.mvpdagger.di.module.ApiModule;
import s.com.mvpdagger.di.module.ApplicationModule;


@ApplicationScope
@Component(modules = {ApiModule.class, ApplicationModule.class})
public interface ApplicationComponent {

    void inject(ApplicationClass applicationClass);

    ApiCalls getapiCalls();

    @ApplicationContext
    Context getContext();
}
