package s.com.gojekassignment.ui.base;

import androidx.annotation.StringRes;

public interface BaseViewContract  {

    void showMessage(String message);

    void showMessage(@StringRes int resId);
}
