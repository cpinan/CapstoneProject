package com.carlospinan.lolguide;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;

import com.carlospinan.lolguide.activities.ChampionListActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Carlos Piñan
 * @source https://google.github.io/android-testing-support-library/docs/espresso/advanced/index.html
 * @source https://github.com/googlesamples/android-testing/blob/master/downloads/espresso-cheat-sheet-2.1.0.pdf
 * @source http://stackoverflow.com/questions/31614937/espresso-nestedscrollview?lq=1
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITesting {

    private static final long SLEEP_1SECONDS = 500L;
    private static final long SLEEP_3SECONDS = 1500L;
    private static final long SLEEP_5SECONDS = 5000L;

    private static final int TOTAL_CHAMPIONS = 129;

    @Rule
    public ActivityTestRule<ChampionListActivity> mActivityRule = new ActivityTestRule<>(
            ChampionListActivity.class);

    @Test
    public void championListTest() throws InterruptedException {
        Thread.sleep(SLEEP_5SECONDS); // Wait for sync with the server

        for (int i = 0; i < TOTAL_CHAMPIONS; i++) {

            onView(withId(R.id.championRecyclerView)).perform(
                    RecyclerViewActions.scrollToPosition(i)
            );

            Thread.sleep(SLEEP_1SECONDS);

            onView(withId(R.id.championRecyclerView)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(i, click()));

            Thread.sleep(SLEEP_3SECONDS);

            onView(withId(R.id.infoContainer)).perform(click());
            Thread.sleep(SLEEP_1SECONDS);
            pressBack();
            Thread.sleep(SLEEP_5SECONDS);

            pressBack();

        }
    }

    public static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with " + childPosition + " child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && view.equals(group.getChildAt(childPosition));
            }
        };

    }

}
