package s.com.mvpdagger.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import s.com.mvpdagger.di.Scope.ApplicationContext;
import s.com.mvpdagger.di.Scope.ApplicationScope;
import s.com.mvpdagger.utils.TLSSocketFactory;


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
    public OkHttpClient okHttpClient(@ApplicationContext Context context, HttpLoggingInterceptor loggerInterceptor, SharedPreferences sharedPreferences) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(new File(context.getCacheDir(), "responses"), (5 * 1024 * 1024)))
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Boolean value = Boolean.valueOf(request.headers().get("forcepull"));
                    request = request.newBuilder().removeHeader("forcepull").build();
                    if (!value) {
                        if (!isNetworkAvailable(context)) {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 2 * 60 * 60).build();
                            return chain.proceed(request);
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build();
                            return chain.proceed(request);
                        }
                    } else {
                        request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
                        return chain.proceed(request);
                    }


                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        if (response.networkResponse() != null) {
                            sharedPreferences.edit().putBoolean("from_cache", false).apply();
                            //Log.v(TAG, "Response from networkResponse(): " + response.networkResponse());
                        } else if (response.cacheResponse() != null) {
                            sharedPreferences.edit().putBoolean("from_cache", true).apply();
                        }
                        return response;
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
