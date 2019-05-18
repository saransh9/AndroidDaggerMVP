package s.com.mvpdagger.ui.base;

import androidx.annotation.StringRes;

public interface BaseViewContract  {

    void showMessage(String message);

    void showMessage(@StringRes int resId);
}
