package s.com.gojekassignment.data;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.GET;
import s.com.gojekassignment.data.model.GithubModel;

public interface ApiCalls {

    @GET("repositories")
    Single<ArrayList<GithubModel>> fetchRepo();
}
