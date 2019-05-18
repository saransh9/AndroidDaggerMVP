package s.com.mvpdagger.ui.main;

import s.com.mvpdagger.ui.base.BasePresneterContract;

public interface MainActivityPresenterContract <V extends MainActivityViewContract>extends BasePresneterContract<V> {


    void fetchData(boolean showLoader, boolean forceRefresh);

}
