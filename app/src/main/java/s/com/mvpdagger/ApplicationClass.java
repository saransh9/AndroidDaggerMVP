package s.com.mvpdagger;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import s.com.mvpdagger.di.component.ApplicationComponent;
import s.com.mvpdagger.di.component.DaggerApplicationComponent;
import s.com.mvpdagger.di.module.ApiModule;
import s.com.mvpdagger.di.module.ApplicationModule;
import s.com.mvpdagger.di.module.SharedPreferencesModule;


public class ApplicationClass extends Application {
    ApplicationComponent applicationComponent;
    public static final String API_URL = "https://api.apixu.com/";

    public static ApplicationClass get(AppCompatActivity activity) {
        return (ApplicationClass) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .apiModule(new ApiModule())
                .applicationModule(new ApplicationModule(this))
                .sharedPreferencesModule(new SharedPreferencesModule(getApplicationContext()))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
