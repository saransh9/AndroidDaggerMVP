package s.com.gojekassignment.di.module;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import s.com.gojekassignment.di.Scope.ApplicationContext;
import s.com.gojekassignment.di.Scope.ApplicationScope;
import s.com.gojekassignment.utils.TLSSocketFactory;


@Module
public class Network {

    @Provides
    @ApplicationScope
    HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("dagger2", "Log: " + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;

    }
    @Provides
    @ApplicationScope
    public OkHttpClient okHttpClient(@ApplicationContext Context context, HttpLoggingInterceptor loggerInterceptor) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(new File(context.getCacheDir(), "responses"), (5 * 1024 * 1024)))
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    if (!isNetworkAvailable(context)) {
                        request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 2 * 60 * 60).build();
                        return chain.proceed(request);
                    } else {
                        request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build();
                        return chain.proceed(request);
                    }

                });

        try {
            okHttpClient.sslSocketFactory(new TLSSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return okHttpClient.build();

    }

    private boolean isNetworkAvailable(@ApplicationContext Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
