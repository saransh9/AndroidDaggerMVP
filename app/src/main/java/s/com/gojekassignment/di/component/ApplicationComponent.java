package s.com.gojekassignment.di.component;


import dagger.Component;
import s.com.gojekassignment.ApplicationClass;
import s.com.gojekassignment.di.Scope.ApplicationScope;
import s.com.gojekassignment.di.module.ApiModule;


@ApplicationScope
@Component(modules = {ApiModule.class})
public interface ApplicationComponent {

    void inject(ApplicationClass applicationClass);

    //ApiCalls getapiCalls();
}
