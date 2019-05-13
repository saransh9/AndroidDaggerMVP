package s.com.gojekassignment.ui.main;

import java.util.ArrayList;

import s.com.gojekassignment.data.model.GithubModel;
import s.com.gojekassignment.ui.base.BaseViewContract;

public interface MainActivityViewContract extends BaseViewContract {

    void showLoader();

    void dismissLoader();

    void hideInternetError();

    void setApiResponse(ArrayList<GithubModel> githubModelArrayList);

    void setApiError(Throwable e);
}
