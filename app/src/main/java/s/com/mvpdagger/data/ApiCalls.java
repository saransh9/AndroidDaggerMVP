package s.com.mvpdagger.data;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import s.com.mvpdagger.data.model.GithubModel;

public interface ApiCalls {

    @GET("repositories")
    Single<ArrayList<GithubModel>> fetchRepo(@Header("forcepull") Boolean forcePull);
}
