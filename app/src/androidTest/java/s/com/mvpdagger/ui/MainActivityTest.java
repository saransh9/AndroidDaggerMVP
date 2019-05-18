package s.com.mvpdagger.ui;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import s.com.mvpdagger.R;
import s.com.mvpdagger.ui.main.MainActivity;
import s.com.mvpdagger.utils.EspressoIdlingResource;
import s.com.mvpdagger.utils.RecyclerViewMatcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void initialiseLoginTest() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getInstance().getIdlingResource());
    }

    @Test
    public void testStart() {
        onView(withId(R.id.v_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.v_divider)).check(matches(isDisplayed()));
        onView(withId(R.id.srl_swipe_refresh_list)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.l_internet_not_found)).check(matches(not(isDisplayed())));

        onView(withId(R.id.rv_recycler)).perform(swipeUp());

    }

    @Test
    public void swipetorefreshtest() {
        onView(withId(R.id.srl_swipe_refresh_list)).check(matches(isDisplayed()));

        onView(withId(R.id.rv_recycler)).perform(swipeDown());

    }

    @Test
    public void test_recyclerview_items_nonzero() {
        onView(withId(R.id.rv_recycler)).check(matches(hasMinimumChildCount(4)));
    }

    @Test
    public void testClickToolbarItem() {

        onView(withId(R.id.iv_overflow)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_overflow))
                .perform(click());

        onView(withId(R.id.tv_sort_by_name)).check(matches(withText(R.string.sort_by_name)));
        onView(withId(R.id.tv_sort_by_name)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_sort_by_stars)).check(matches(withText(R.string.sort_by_stars)));
        onView(withId(R.id.tv_sort_by_stars)).check(matches(isDisplayed()));


    }

    @Test
    public void testrecycler() {
        onView(withId(R.id.rv_recycler)).perform(RecyclerViewActions.scrollToPosition(4));
        onView(withId(R.id.rv_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        viewDisplayed(4);

        onView(withId(R.id.rv_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(new RecyclerViewMatcher(R.id.rv_recycler)
                .atPositionOnView(4, R.id.iv_circle))
                .check(matches(not(isDisplayed())));
        onView(new RecyclerViewMatcher(R.id.rv_recycler)
                .atPositionOnView(4, R.id.tv_forks))
                .check(matches(not(isDisplayed())));
        onView(new RecyclerViewMatcher(R.id.rv_recycler)
                .atPositionOnView(4, R.id.tv_lang_name))
                .check(matches(not(isDisplayed())));
        onView(new RecyclerViewMatcher(R.id.rv_recycler)
                .atPositionOnView(4, R.id.tv_stars))
                .check(matches(not(isDisplayed())));
        onView(new RecyclerViewMatcher(R.id.rv_recycler)
                .atPositionOnView(4, R.id.iv_stars))
                .check(matches(not(isDisplayed())));
        onView(new RecyclerViewMatcher(R.id.rv_recycler)
                .atPositionOnView(4, R.id.iv_fork))
                .check(matches(not(isDisplayed())));

        viewDisplayed(3);


    }

    private void viewDisplayed(int position) {
        onView(new RecyclerViewMatcher(R.id.rv_recycler)
                .atPositionOnView(position, R.id.iv_circle))
                .check(matches(isDisplayed()));
        onView(new RecyclerViewMatcher(R.id.rv_recycler)
                .atPositionOnView(position, R.id.tv_forks))
                .check(matches(isDisplayed()));
        onView(new RecyclerViewMatcher(R.id.rv_recycler)
                .atPositionOnView(position, R.id.tv_lang_name))
                .check(matches(isDisplayed()));
        onView(new RecyclerViewMatcher(R.id.rv_recycler)
                .atPositionOnView(position, R.id.tv_stars))
                .check(matches(isDisplayed()));
        onView(new RecyclerViewMatcher(R.id.rv_recycler)
                .atPositionOnView(position, R.id.iv_stars))
                .check(matches(isDisplayed()));
        onView(new RecyclerViewMatcher(R.id.rv_recycler)
                .atPositionOnView(position, R.id.iv_fork))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickSortByNameItem() {
        onView(withId(R.id.iv_overflow))
                .perform(click());

        onView(withId(R.id.tv_sort_by_name)).perform(click());
        onView(withId(R.id.rv_recycler)).check(matches((new RecyclerViewMatcher(R.id.rv_recycler)).isSortedByName()));
    }

    @Test
    public void testClickSortByStarItem() {
        onView(withId(R.id.iv_overflow))
                .perform(click());

        onView(withId(R.id.tv_sort_by_stars)).perform(click());
        onView(withId(R.id.rv_recycler)).check(matches(new RecyclerViewMatcher(R.id.rv_recycler).isSortedByStars()));
    }

    @After
    public void detachLoginTest() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getInstance().getIdlingResource());
    }


}
