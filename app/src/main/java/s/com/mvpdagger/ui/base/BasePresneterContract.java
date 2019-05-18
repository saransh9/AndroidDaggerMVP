package s.com.mvpdagger.ui.base;

public interface BasePresneterContract <V extends BaseViewContract>{

    void onAttach(V mvpView);

    void onDetach();

}
