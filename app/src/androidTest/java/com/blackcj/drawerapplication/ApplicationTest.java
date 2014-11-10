package com.blackcj.drawerapplication;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2 {
    private MainActivity mActivity; // MyActivity is the class name of the app under test
    private NavigationDrawerFragment mDrawerFragment;
    private Instrumentation mInstrumentation;
    private int selectedItem = 1;
    private PowerManager.WakeLock wl;

    public ApplicationTest() {
        super(MainActivity.class);
    }


    protected void setUp() throws Exception {
        super.setUp();
        // Clear saved preferences
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        sp.edit().putBoolean(NavigationDrawerFragment.PREF_USER_LEARNED_DRAWER, false).apply();

        // Get a references to the app under test
        mActivity = (MainActivity)getActivity();
        mInstrumentation = getInstrumentation();
        mDrawerFragment = (NavigationDrawerFragment) mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
    }

    public void testFirstRun() throws Exception {

        assertEquals(true, mDrawerFragment.isDrawerOpen());
    }

    public void testDrawerClosed() throws InterruptedException {
        // Run on UI thread
        Runnable r = new Runnable() {
            public void run() {
                mDrawerFragment.closeDrawer();
            }
        };
        mActivity.runOnUiThread(r);
        Thread.sleep(500);

        // Stop the activity
        mActivity.finish();
        // Re-start the Activity
        mActivity = (MainActivity)getActivity();
        assertEquals(false, mDrawerFragment.isDrawerOpen());
    }

    /**
     * Test to validate the menu drawer maintains state after Activity is destroyed and restarted
     */
    public void testDrawerOpen() throws InterruptedException {
        // Run on UI thread
        Runnable r = new Runnable() {
            public void run() {
                mDrawerFragment.openDrawer();
            }
        };
        mActivity.runOnUiThread(r);
        // Wait for drawer animation to complete
        Thread.sleep(500);

        // Stop the activity
        mActivity.finish();
        // Re-start the Activity
        mActivity = (MainActivity)getActivity();
        assertEquals(true, mDrawerFragment.isDrawerOpen());
    }

    /**
     * Test to validate the menu drawer maintains state after Activity is destroyed and restarted
     */
    public void testDrawerSelection() throws InterruptedException {
        // Run on UI thread
        Runnable r = new Runnable() {
            public void run() {
                mDrawerFragment.selectItem(selectedItem);
            }
        };
        mActivity.runOnUiThread(r);
        // Wait for drawer animation to complete
        Thread.sleep(500);

        // Stop the activity
        mActivity.finish();
        // Re-start the Activity
        mActivity = (MainActivity)getActivity();
        assertEquals(selectedItem, mDrawerFragment.getSelectedItem());
    }

    protected void tearDown() throws Exception {
        super.tearDown();

    }
}