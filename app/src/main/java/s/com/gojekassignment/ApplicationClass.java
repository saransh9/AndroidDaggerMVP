package s.com.gojekassignment;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import s.com.gojekassignment.di.component.ApplicationComponent;
import s.com.gojekassignment.di.component.DaggerApplicationComponent;
import s.com.gojekassignment.di.module.ApiModule;
import s.com.gojekassignment.di.module.ApplicationModule;


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
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
