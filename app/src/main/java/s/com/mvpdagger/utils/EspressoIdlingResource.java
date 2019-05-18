package s.com.mvpdagger.utils;

import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.idling.CountingIdlingResource;

public class EspressoIdlingResource {

    private static EspressoIdlingResource instance;
    private String RESOURCE = "GLOBAL";
    private CountingIdlingResource mCountingIdlingResource = new CountingIdlingResource(RESOURCE);

    private EspressoIdlingResource() {

    }

    public static EspressoIdlingResource getInstance() {
        if (instance == null) {
            instance = new EspressoIdlingResource();
        }
        return instance;
    }

    public void increment() {
        mCountingIdlingResource.increment();
    }

    public void decrement() {
        mCountingIdlingResource.decrement();
    }

    public IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }
}
