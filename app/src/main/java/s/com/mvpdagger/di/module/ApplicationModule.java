package s.com.mvpdagger.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import s.com.mvpdagger.di.Scope.ApplicationContext;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }
}
