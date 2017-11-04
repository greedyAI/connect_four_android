package comgreedyai.github.connectfour;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import comgreedyai.github.connectfour.GameActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

/**
 * Created by Waley on 2017/10/22.
 */

@RunWith(AndroidJUnit4.class)
public class GameTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(GameActivity.class);

    @Test
    public void canEditNamesTest() {
        onView(withId(R.id.red_name)).perform(click());
        onView(withId(R.id.red_name)).check(matches(withText("Edit Name")));
    }

    @Test
    public void changeNamesTest() {
        onView(withId(R.id.red_name)).perform(click());
        onView(withId(R.id.red_name)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.red_name)).perform(click());
        onView(withId(R.id.red_name)).perform(pressKey(KeyEvent.KEYCODE_A), closeSoftKeyboard());
        onView(withId(R.id.red_name)).perform(click());
        onView(withId(R.id.red_name)).check(matches(withText("a")));
    }

    @Test
    public void deleteNamesTest() {
        onView(withId(R.id.red_name)).perform(click());
        onView(withId(R.id.red_name)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.yellow_name)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.red_name)).check(matches(withText("")));
    }

    @Test
    public void banLeaderboardTest() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Leaderboard")).perform(click());
        onView(withText(R.string.ban_title)).check(matches(isDisplayed()));
    }

    @Test
    public void newGameTest() {
        onView(withId(R.id.red_name)).perform(click());
        onView(withId(R.id.red_name)).perform(clearText(), closeSoftKeyboard());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("New Game")).perform(click());
        onView(withId(R.id.red_name)).perform(click());
        onView(withId(R.id.red_name)).check(matches(withText("Edit Name")));
    }

    @Test
    public void verticalWinTest() {
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_2)))).perform(click());
        onView(withText(R.string.reset_title)).check(matches(isDisplayed()));
    }

    @Test
    public void horizontalWinTest() {
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_5), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_5), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_6), withParent(withId(R.id.row_2)))).perform(click());
        onView(withText(R.string.reset_title)).check(matches(isDisplayed()));
    }

    @Test
    public void diagonalWinTest() {
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_5), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_5), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_5), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_6), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_6), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_6), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_6), withParent(withId(R.id.row_1)))).perform(click());
        onView(withText(R.string.reset_title)).check(matches(isDisplayed()));
    }

    @Test
    public void leaderboardCorrectTest() {
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        onView(withText(R.string.reset_title)).perform(click());
        onView(withText(R.string.reset_confirm)).perform(click());
        pressBack();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("New Game")).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_2)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_2)))).perform(click());
        onView(withId(R.id.result)).check(matches(withText("Edit Name: W1 | L1 | D0")));
    }

    @Test
    public void persistentLeaderboardTest() {
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        onView(withText(R.string.reset_title)).perform(click());
        onView(withText(R.string.reset_confirm)).perform(click());
        pressBack();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("New Game")).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_1)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_1)))).perform(click());
        pressBack();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("New Game")).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_3)))).perform(click());
        onView(allOf(withId(R.id.col_3), withParent(withId(R.id.row_3)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_3)))).perform(click());
        onView(allOf(withId(R.id.col_4), withParent(withId(R.id.row_3)))).perform(click());
        onView(allOf(withId(R.id.col_5), withParent(withId(R.id.row_3)))).perform(click());
        onView(allOf(withId(R.id.col_5), withParent(withId(R.id.row_3)))).perform(click());
        onView(allOf(withId(R.id.col_6), withParent(withId(R.id.row_3)))).perform(click());
        onView(withId(R.id.result)).check(matches(withText("Edit Name: W2 | L2 | D0")));
    }

}