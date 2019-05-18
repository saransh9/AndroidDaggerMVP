package s.com.mvpdagger.utils;

import android.content.res.Resources;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.google.common.collect.Ordering;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.List;

import s.com.mvpdagger.data.model.GithubModel;
import s.com.mvpdagger.ui.main.Adapter;

public class RecyclerViewMatcher {
    private final int recyclerViewId;

    public RecyclerViewMatcher(int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }

    public Matcher<View> atPosition(final int position) {
        return atPositionOnView(position, -1);
    }

    public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            View childView;

            public void describeTo(Description description) {
                String idDescription = Integer.toString(recyclerViewId);
                if (this.resources != null) {
                    try {
                        idDescription = this.resources.getResourceName(recyclerViewId);
                    } catch (Resources.NotFoundException var4) {
                        idDescription = String.format("%s (resource name not found)",
                                Integer.valueOf
                                        (recyclerViewId));
                    }
                }

                description.appendText("with id: " + idDescription);
            }

            public boolean matchesSafely(View view) {

                this.resources = view.getResources();

                if (childView == null) {
                    RecyclerView recyclerView =
                            view.getRootView().findViewById(recyclerViewId);
                    if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                        childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                    } else {
                        return false;
                    }
                }

                if (targetViewId == -1) {
                    return view == childView;
                } else {
                    View targetView = childView.findViewById(targetViewId);
                    return view == targetView;
                }

            }
        };
    }

    public Matcher<View> isSortedByName() {
        return new TypeSafeMatcher<View>() {

            private final List<String> gitRepoNames = new ArrayList<>();

            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                Adapter rvAdapter = (Adapter) recyclerView.getAdapter();
                gitRepoNames.clear();
                gitRepoNames.addAll(extractTeamNames(rvAdapter.getmGithubModelArrayList()));
                return Ordering.natural().isOrdered(gitRepoNames);
            }

            private List<String> extractTeamNames(List<GithubModel> githubModels) {
                List<String> gitRepoNames = new ArrayList<>();
                for (GithubModel model : githubModels) {
                    gitRepoNames.add(model.getName().toLowerCase());
                }
                return gitRepoNames;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has items sorted names: " + gitRepoNames);
            }
        };
    }

    public Matcher<View> isSortedByStars() {
        return new TypeSafeMatcher<View>() {

            private final List<Integer> gitRepoNames = new ArrayList<>();

            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                Adapter rvAdapter = (Adapter) recyclerView.getAdapter();
                gitRepoNames.clear();
                gitRepoNames.addAll(extractTeamNames(rvAdapter.getmGithubModelArrayList()));
                return Ordering.natural().isOrdered(gitRepoNames);
            }

            private List<Integer> extractTeamNames(List<GithubModel> githubModels) {
                List<Integer> gitRepoNames = new ArrayList<>();
                for (GithubModel model : githubModels) {
                    gitRepoNames.add(Integer.valueOf(model.getStars()));
                }
                return gitRepoNames;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has items sorted by stars: " + gitRepoNames);
            }
        };
    }
}