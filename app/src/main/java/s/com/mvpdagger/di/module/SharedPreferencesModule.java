package s.com.mvpdagger.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import s.com.mvpdagger.di.Scope.ApplicationScope;

@Module
public class SharedPreferencesModule {

    private Context context;

    public SharedPreferencesModule(Context context) {
        this.context = context;
    }


    @Provides
    @ApplicationScope
    SharedPreferences provideTimeSharedPreferences() {
        return context.getSharedPreferences("cache_preference", Context.MODE_PRIVATE);
    }
}