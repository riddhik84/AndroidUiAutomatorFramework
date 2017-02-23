package com.riddhik.myapps.androiduiautomatorexample;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A helper class where script logic is written
 */

public class TestHelper {

    public static boolean uninstallApp() {
        boolean result = false;
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("adb uninstall " + Constants.EBAY_PACKAGE);
            pr.waitFor();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        return result;
    }

    /**
     * Search for an app by scrolling the all apps view and open the app if found
     *
     * @param uiDevice Device under test
     * @param appName  App to search and open
     * @return if app is found
     */
    public static boolean searchAndOpenApp(UiDevice uiDevice, String appName) {
        boolean isAppFound = true;

        UiObject allAppsBtn = uiDevice.findObject(new UiSelector().description("Apps"));
        UiScrollable allAppsView = new UiScrollable(new UiSelector().scrollable(true));
        //allAppsView.setAsHorizontalList();
        allAppsView.setAsVerticalList();
        UiObject appToSearch = uiDevice.findObject(new UiSelector().description(appName));

        try {
            allAppsBtn.clickAndWaitForNewWindow();
            if (appToSearch.exists()) {
                appToSearch.clickAndWaitForNewWindow();
            } else {
                UiObject appToBeLaunched = allAppsView.getChildByText(new UiSelector()
                                .className(android.widget.TextView.class.getName()),
                        appName);
                isAppFound = appToBeLaunched.clickAndWaitForNewWindow();
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAppFound;
    }

    public static void openApp() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(Constants.EBAY_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    /**
     * Search a product in eBay search box
     *
     * @param uiDevice    Device under test
     * @param productName product to search
     * @return true if product is found
     */
    public static int searchProduct(UiDevice uiDevice, String productName) {
        int productCount = 0;

        UiObject eBaySearchBox = uiDevice.findObject(new UiSelector().resourceId("com.ebay.mobile:id/search_box")
                .text("Search eBay"));
        UiObject eBaySearchEditBox = uiDevice.findObject(new UiSelector().resourceId("com.ebay.mobile:id/search_src_text"));
        UiObject productResult = uiDevice.findObject(new UiSelector().resourceId("com.ebay.mobile:id/textview_item_count"));

        try {
            if (eBaySearchBox.exists()) {
                eBaySearchBox.clickAndWaitForNewWindow(3000);
                eBaySearchEditBox.clearTextField();
                eBaySearchEditBox.setText(productName);
                Thread.sleep(3000);
                uiDevice.pressEnter();

                if (productResult.waitForExists(5000)) {
                    String info[] = productResult.getText().split(" ");
                    productCount = Integer.parseInt(info[0]);
                }
            }
        } catch (Exception e) {

        }
        return productCount;
    }

    public ArrayList<String> getSearchProducts() {
        BufferedReader reader = null;
        ArrayList<String> products = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(new File("/assets/products.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                products.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return products;
    }
}
