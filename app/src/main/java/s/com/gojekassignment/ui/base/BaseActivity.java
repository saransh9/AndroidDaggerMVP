package s.com.gojekassignment.ui.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import s.com.gojekassignment.ApplicationClass;
import s.com.gojekassignment.R;
import s.com.gojekassignment.di.component.ActivityComponent;
import s.com.gojekassignment.di.component.DaggerActivityComponent;
import s.com.gojekassignment.di.module.ActivityModule;

public abstract class BaseActivity extends AppCompatActivity implements BaseViewContract{
    ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(ApplicationClass.get(this).getApplicationComponent())
                .build();

    }

    public ActivityComponent getActivityComponent()
    {
        return activityComponent;
    }


    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }
}
