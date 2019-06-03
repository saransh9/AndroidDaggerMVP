package s.com.mvpdagger.ui.main;

import androidx.annotation.StringRes;

import java.util.ArrayList;

import s.com.mvpdagger.data.model.GithubModel;
import s.com.mvpdagger.ui.base.BaseViewContract;

public interface MainActivityViewContract extends BaseViewContract {

    void showLoader();

    void dismissLoader();

    void hideInternetError();

    void setApiResponse(ArrayList<GithubModel> githubModelArrayList);

    void setApiError(Throwable e);

    void setTimeText(@StringRes int text);

    void hideTimeText();
}
