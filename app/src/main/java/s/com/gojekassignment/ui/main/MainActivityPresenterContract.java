package s.com.gojekassignment.ui.main;

import s.com.gojekassignment.ui.base.BasePresneterContract;

public interface MainActivityPresenterContract <V extends MainActivityViewContract>extends BasePresneterContract<V> {


    void fetchData();

}
