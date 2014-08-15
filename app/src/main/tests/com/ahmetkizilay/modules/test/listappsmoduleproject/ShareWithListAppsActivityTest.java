package com.ahmetkizilay.modules.test.listappsmoduleproject;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.ahmetkizilay.modules.listapps.AppListerViewGroup;

import java.util.List;

/**
 * Created by ahmetkizilay on 15.08.2014.
 */
public class ShareWithListAppsActivityTest extends ActivityInstrumentationTestCase2<ShareWithListAppsActivity> {
    public ShareWithListAppsActivityTest() {
        super(ShareWithListAppsActivity.class);
    }

    public void testTheExistenceOfToggleButton() {
        Activity activity = getActivity();

        View viewBtnToggle = activity.findViewById(R.id.btnToggleBottom);
        assertNotNull("btnToggle should exist", viewBtnToggle);
        assertTrue("btnToggle should be of ImageButton type", viewBtnToggle instanceof ImageButton);
        assertTrue("btnToggle should be visible", viewBtnToggle.isShown());
    }

    public void testTheExistenceOfAppLister() {
        Activity activity = getActivity();

        View viewAppLister = activity.findViewById(R.id.wgAppListerBottom);
        assertNotNull("appLister should exist", viewAppLister);
        assertTrue("appLister should be of AppListerViewGroup type", viewAppLister instanceof com.ahmetkizilay.modules.listapps.AppListerViewGroup);
        assertFalse("appLister should be invisible", viewAppLister.isShown());
    }

    public void testButtonTogglingAppLister() {
        Activity activity = getActivity();
        Instrumentation instrumentation = getInstrumentation();

        final ImageButton btnToggle = (ImageButton) activity.findViewById(R.id.btnToggleBottom);
        final AppListerViewGroup vgAppLister = (AppListerViewGroup) activity.findViewById(R.id.wgAppListerBottom);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                btnToggle.performClick();
            }
        });

        instrumentation.waitForIdleSync();
        assertTrue("vgAppLister should be visible", vgAppLister.isShown());

        // clicking the button for the second time to hide the applister
        activity.runOnUiThread(new Runnable() {
            public void run() {
                btnToggle.performClick();
            }
        });

        instrumentation.waitForIdleSync();
        assertFalse("vgAppLister should be invisible", vgAppLister.isShown());
    }

    public void testTheExistenceOfAppListComponents() {
        Activity activity = getActivity();
        Instrumentation instrumentation = getInstrumentation();

        final ImageButton btnToggle = (ImageButton) activity.findViewById(R.id.btnToggleBottom);
        final AppListerViewGroup vgAppLister = (AppListerViewGroup) activity.findViewById(R.id.wgAppListerBottom);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                btnToggle.performClick();
            }
        });

        instrumentation.waitForIdleSync();

        View lwListedApps = vgAppLister.findViewById(com.ahmetkizilay.modules.listapps.R.id.lwAppSharing);
        View txtShowMore = vgAppLister.findViewById(com.ahmetkizilay.modules.listapps.R.id.txtShowMore);

        assertNotNull("lwAppSharing should exist", lwListedApps);
        assertTrue("lwAppSharing should be of ListView type", lwListedApps instanceof ListView);

        assertNotNull("txtShowMore should exist", txtShowMore);
        assertTrue("txtShowMore should be of TextView type", txtShowMore instanceof TextView);
    }

    public void testInitialVisibleAppCount() {
        Activity activity = getActivity();
        Instrumentation instrumentation = getInstrumentation();

        final ImageButton btnToggle = (ImageButton) activity.findViewById(R.id.btnToggleBottom);
        final AppListerViewGroup vgAppLister = (AppListerViewGroup) activity.findViewById(R.id.wgAppListerBottom);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                btnToggle.performClick();
            }
        });
        instrumentation.waitForIdleSync();

        // setting displayed item number
        final int maxItemCountToDisplay = 2;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                vgAppLister.setMaxInitialDisplayLength(maxItemCountToDisplay);
                vgAppLister.setMinimalMode();
            }
        });
        instrumentation.waitForIdleSync();

        ListView lwListedApps = (ListView) vgAppLister.findViewById(com.ahmetkizilay.modules.listapps.R.id.lwAppSharing);
        assertTrue("appLister should list maximum " + maxItemCountToDisplay + " apps", lwListedApps.getCount() <= maxItemCountToDisplay);
    }

    public void testAppListerShowAllActivitiesForTheIntent() {
        Activity activity = getActivity();
        Instrumentation instrumentation = getInstrumentation();

        PackageManager pm = activity.getPackageManager();
        Intent shareIntent = new Intent("android.intent.action.SEND", null);
        shareIntent.setType("image/*");
        List<ResolveInfo> shareApps = pm.queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
        int shareAppsCount = shareApps.size();

        final ImageButton btnToggle = (ImageButton) activity.findViewById(R.id.btnToggleBottom);
        final AppListerViewGroup vgAppLister = (AppListerViewGroup) activity.findViewById(R.id.wgAppListerBottom);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                btnToggle.performClick();
            }
        });
        instrumentation.waitForIdleSync();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                vgAppLister.setExpandedMode();
            }
        });
        instrumentation.waitForIdleSync();


        ListView lwListedApps = (ListView) vgAppLister.findViewById(com.ahmetkizilay.modules.listapps.R.id.lwAppSharing);
        assertEquals("appLister should list " + shareAppsCount + " apps", shareAppsCount, lwListedApps.getCount());
    }

    public void testAppListerShouldShowAllAppsIfLessThanMaxInitial() {
        Activity activity = getActivity();
        Instrumentation instrumentation = getInstrumentation();

        PackageManager pm = activity.getPackageManager();
        Intent shareIntent = new Intent("android.intent.action.SEND", null);
        shareIntent.setType("text/html");
        List<ResolveInfo> shareApps = pm.queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
        int shareAppsCount = shareApps.size();

        final ImageButton btnToggle = (ImageButton) activity.findViewById(R.id.btnToggleBottom);
        final AppListerViewGroup vgAppLister = (AppListerViewGroup) activity.findViewById(R.id.wgAppListerBottom);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                btnToggle.performClick();
            }
        });
        instrumentation.waitForIdleSync();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                vgAppLister.setIntentNameAndType("android.intent.action.SEND", "text/html");
                vgAppLister.setMinimalMode();
            }
        });
        instrumentation.waitForIdleSync();

        assertTrue("appLister has less apps than maxInitialAppCount", shareAppsCount < vgAppLister.getMaxInitialDisplayLength());

        ListView lwListedApps = (ListView) vgAppLister.findViewById(com.ahmetkizilay.modules.listapps.R.id.lwAppSharing);
        View txtShowMore = vgAppLister.findViewById(com.ahmetkizilay.modules.listapps.R.id.txtShowMore);

        assertEquals("appLister should list " + shareAppsCount + " apps", shareAppsCount, lwListedApps.getCount());

        assertFalse("txtShowMore should be hidden", txtShowMore.isShown());
    }

    public void testShowMoreTextShouldOnlyBeShownInMinimalMode() {
        Activity activity = getActivity();
        Instrumentation instrumentation = getInstrumentation();

        final ImageButton btnToggle = (ImageButton) activity.findViewById(R.id.btnToggleBottom);
        final AppListerViewGroup vgAppLister = (AppListerViewGroup) activity.findViewById(R.id.wgAppListerBottom);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                btnToggle.performClick();
            }
        });
        instrumentation.waitForIdleSync();

        // setting displayed item number
        final int maxItemCountToDisplay = 2;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                vgAppLister.setMaxInitialDisplayLength(maxItemCountToDisplay);
                vgAppLister.setMinimalMode();
            }
        });
        instrumentation.waitForIdleSync();

        TextView txtShowMore = (TextView) vgAppLister.findViewById(com.ahmetkizilay.modules.listapps.R.id.txtShowMore);
        assertTrue("txtShowMore should be visible", txtShowMore.isShown());

        activity.runOnUiThread(new Runnable() {
            public void run() {
                vgAppLister.setExpandedMode();
            }
        });
        instrumentation.waitForIdleSync();

        assertFalse("txtShowMore should be hidden", txtShowMore.isShown());
    }

    public void testClickingShowMoreShouldTriggerExpandedMode() {
        Activity activity = getActivity();
        Instrumentation instrumentation = getInstrumentation();

        PackageManager pm = activity.getPackageManager();
        Intent shareIntent = new Intent("android.intent.action.SEND", null);
        shareIntent.setType("image/*");
        List<ResolveInfo> shareApps = pm.queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
        int shareAppsCount = shareApps.size();


        final ImageButton btnToggle = (ImageButton) activity.findViewById(R.id.btnToggleBottom);
        final AppListerViewGroup vgAppLister = (AppListerViewGroup) activity.findViewById(R.id.wgAppListerBottom);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                btnToggle.performClick();
            }
        });
        instrumentation.waitForIdleSync();

        final ListView lwListedApps = (ListView) vgAppLister.findViewById(com.ahmetkizilay.modules.listapps.R.id.lwAppSharing);
        final TextView txtShowMore = (TextView) vgAppLister.findViewById(com.ahmetkizilay.modules.listapps.R.id.txtShowMore);

        assertTrue("txtShowMore should be visible", txtShowMore.isShown());
        assertEquals("lwListedApps should show MaxInitialDisplayLength items", vgAppLister.getMaxInitialDisplayLength(), lwListedApps.getCount());

        activity.runOnUiThread(new Runnable() {
            public void run() {
                txtShowMore.performClick();
            }
        });
        instrumentation.waitForIdleSync();

        assertFalse("txtShowMore should be invisible", txtShowMore.isShown());
        assertEquals("lwListedApps should show all items", shareAppsCount, lwListedApps.getCount());
    }
}
