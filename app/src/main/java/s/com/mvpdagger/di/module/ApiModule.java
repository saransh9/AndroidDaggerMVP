package s.com.mvpdagger.di.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import s.com.mvpdagger.BuildConfig;
import s.com.mvpdagger.data.ApiCalls;
import s.com.mvpdagger.di.Scope.ApplicationScope;


@Module(includes = {Network.class})
public class ApiModule {

    @Provides
     @ApplicationScope
     public ApiCalls apiCalls(Retrofit retrofit)
     {
         return retrofit.create(ApiCalls.class);
     }


    @Provides
    @ApplicationScope
    public Retrofit retrofitClient(OkHttpClient client,Converter.Factory gsonConverterFactory,CallAdapter.Factory rxCallAdapter,@Named("baseurl") String baseUrl) {
        return new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxCallAdapter)
                .client(client)
                .baseUrl(baseUrl)
                .build();
    }

    @Provides
    @ApplicationScope
    public Converter.Factory getGsonConvertorFactory(){
        return GsonConverterFactory.create();
    }

    @Provides
    @ApplicationScope
    public CallAdapter.Factory getRxCallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }

    @Named("baseurl")
    @Provides
    @ApplicationScope
    public String getBaseUrl(){
        return BuildConfig.HOST;
    }

}

