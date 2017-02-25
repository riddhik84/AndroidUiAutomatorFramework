package com.riddhik.myapps.androiduiautomatorexample.AutomationFramework;

import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.util.Log;

import com.riddhik.myapps.androiduiautomatorexample.Utility.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A helper class where script logic is written
 */

public class TestHelper {

    private static final String LOG_TAG = TestHelper.class.getSimpleName();

    public static boolean uninstallApp(UiDevice uiDevice) {
        boolean result = false;
        try {
            String output = uiDevice.executeShellCommand("pm uninstall " + Constants.EBAY_PACKAGE);
            //Log.d(LOG_TAG, "Shell output : " + output);
            if (output.contains("success")) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Download an Install an app from play store
     *
     * @param uiDevice
     * @param appName
     */
    public static boolean downloadAndInstallApp(UiDevice uiDevice, String appName) {
        boolean result = false;

        UiObject searchBoxImageView = uiDevice.findObject(new UiSelector().resourceId("com.android.vending:id/text_container"));
        UiObject searchEditBox = uiDevice.findObject(new UiSelector().resourceId("com.android.vending:id/search_box_text_input"));
        UiObject firstSearchResult = uiDevice.findObject(new UiSelector()
                .resourceId("com.android.vending:id/bucket_items")
                .index(0));
        UiObject installButton = uiDevice.findObject(new UiSelector()
                .resourceId("com.android.vending:id/buy_button")
                .text("INSTALL"));
        UiObject openButton = uiDevice.findObject(new UiSelector()
                .resourceId("com.android.vending:id/launch_button")
                .text("OPEN"));
        UiObject ebayNotification = uiDevice.findObject(new UiSelector()
                .resourceId("android:id/title")
                .packageName("com.android.providers.downloads"));

        try {
            //Search and open play store
            searchAndOpenApp(uiDevice, Constants.PLAY_STORE_APP);
            Thread.sleep(Constants.WAIT_TIME);
            //Search eBay app
            searchBoxImageView.click();
            Thread.sleep(Constants.WAIT_TIME);
            searchEditBox.setText(appName);
            Thread.sleep(Constants.WAIT_TIME);
            uiDevice.pressEnter();
            Thread.sleep(Constants.WAIT_TIME);
            //Select search result
            firstSearchResult.click();
            Thread.sleep(Constants.WAIT_TIME);
            //Install the app
            if (installButton.exists()) {
                installButton.click();
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(5000);
                }

                //Check if app installed
                if (openButton.exists()) {
                    Log.d(LOG_TAG, "App is installed from play store");
                    result = true;
                    for (int i = 0; i < 4; i++) {
                        uiDevice.pressBack();
                    }
                    uiDevice.pressHome();
                }
            } else {
                //app is already installed
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        allAppsView.setAsHorizontalList();
        //allAppsView.setAsVerticalList();
        UiObject appToSearch = uiDevice.findObject(new UiSelector().description(appName));

        try {
            uiDevice.pressHome();
            Thread.sleep(Constants.WAIT_TIME);
            allAppsBtn.clickAndWaitForNewWindow();
            UiObject appToBeLaunched = allAppsView.getChildByText(new UiSelector()
                            .className(android.widget.TextView.class.getName()),
                    appName);
            isAppFound = appToBeLaunched.clickAndWaitForNewWindow(5000);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAppFound;
    }

    /**
     * Search a product in eBay search box
     *
     * @param uiDevice    Device under test
     * @param productName product to search
     * @return true if product is found
     */
    public static int searchProducteBay(UiDevice uiDevice, String productName) {
        int productCount = 0;

        UiObject eBaySearchBox = uiDevice.findObject(new UiSelector().resourceId("com.ebay.mobile:id/search_box")
                .text("Search eBay"));
        UiObject eBaySearchEditBox = uiDevice.findObject(new UiSelector().resourceId("com.ebay.mobile:id/search_src_text"));
        UiObject productResultText = uiDevice.findObject(new UiSelector().resourceId("com.ebay.mobile:id/textview_item_count"));

        try {
            if (eBaySearchBox.exists()) {
                eBaySearchBox.clickAndWaitForNewWindow(Constants.WAIT_TIME);
                eBaySearchEditBox.clearTextField();
                eBaySearchEditBox.setText(productName);
                Thread.sleep(Constants.WAIT_TIME);
                uiDevice.pressEnter();

                if (productResultText.waitForExists(5000)) {
                    String info[] = productResultText.getText().split(" ");
                    productCount = Integer.parseInt(info[0].trim());
                    Log.d(LOG_TAG, "Product Search result count: " + productCount);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productCount;
    }

    /**
     * @return
     */
    public static ArrayList<String> getSearchProducts() {
        ArrayList<String> products = new ArrayList<>();
        final String file = "products.txt";
        InputStream is = null;
        String line = "";
        int i = 0;

        try {
            is = InstrumentationRegistry.getInstrumentation().getContext().getResources()
                    .getAssets().open(file);
            //is = new FileInputStream(file);
            while ((i = is.read()) != -1) {
                line = line + i + "";
            }
            Log.d(LOG_TAG, "products to search: " + line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return products;
    }

    /**
     * Watcher for crash
     *
     * @param uiDevice
     */
    public static void setCrashDialogWatcher(UiDevice uiDevice) {
        UiWatcher crashWatcher = new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject okCancelDialog = new UiObject(new UiSelector().textStartsWith("Unfortunately"));
                if (okCancelDialog.exists()) {
                    Log.w(LOG_TAG, "Application crash found");
                    UiObject okButton = new UiObject(new UiSelector().className("android.widget.Button").text("OK"));
                    try {
                        okButton.click();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    return (okCancelDialog.waitUntilGone(25000));
                }
                return false;
            }
        };

        uiDevice.getInstance().registerWatcher("CrashDialog", crashWatcher);
    }

//    public static void openApp(String appPackageName) {
//        Context context = InstrumentationRegistry.getInstrumentation().getContext();
//        Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
//    }
}
