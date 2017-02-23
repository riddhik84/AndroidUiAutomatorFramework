package com.riddhik.myapps.androiduiautomatorexample;

import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

/**
 * Created by RKs on 2/23/2017.
 */

public class TestHelper {

    public static void searchApp(UiDevice uiDevice, String appname) {
        UiObject allAppsBtn = uiDevice.findObject(new UiSelector().description("Apps"));

        UiScrollable allAppsView = new UiScrollable(new UiSelector().scrollable(true));
        allAppsView.setAsHorizontalList();

        UiObject app_name = null;
        try {
            allAppsBtn.click();
            Thread.sleep(3000);

            app_name = allAppsView.getChildByText(new UiSelector()
                    .className(android.widget.TextView.class.getName()), appname);
            Thread.sleep(3000);

            app_name.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
