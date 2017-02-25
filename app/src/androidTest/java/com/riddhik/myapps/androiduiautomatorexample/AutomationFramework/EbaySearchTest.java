package com.riddhik.myapps.androiduiautomatorexample.AutomationFramework;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import com.riddhik.myapps.androiduiautomatorexample.Utility.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.riddhik.myapps.androiduiautomatorexample.AutomationFramework.TestHelper.downloadAndInstallApp;
import static com.riddhik.myapps.androiduiautomatorexample.AutomationFramework.TestHelper.searchAndOpenApp;
import static com.riddhik.myapps.androiduiautomatorexample.AutomationFramework.TestHelper.searchProducteBay;
import static com.riddhik.myapps.androiduiautomatorexample.AutomationFramework.TestHelper.setCrashDialogWatcher;
import static com.riddhik.myapps.androiduiautomatorexample.AutomationFramework.TestHelper.uninstallApp;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class EbaySearchTest {

    private final String LOG_TAG = EbaySearchTest.class.getSimpleName();
    private UiDevice mUiDevice;

    @Before
    public void setUp() throws Exception {
        //Initialize UiDevice
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        assertThat(mUiDevice, notNullValue());

        if (!mUiDevice.isScreenOn()) {
            mUiDevice.wakeUp();
        }
        mUiDevice.pressHome();
        Thread.sleep(Constants.WAIT_TIME);
    }

    @Test
    public void Test_eBaySearch() throws Exception {
        boolean result = false;
        int count = 0;

        //Run application crash watcher
        setCrashDialogWatcher(mUiDevice);
        mUiDevice.getInstance().runWatchers();

        //Step1: UnInstall app if already installed
        result = uninstallApp(mUiDevice);
        //assertTrue("App is uninstalled", result);

        //Step2: Install app from play store
        result = downloadAndInstallApp(mUiDevice, Constants.EBAY_APP);
        assertTrue("App is downloaded and installed from Play Store", result);

        //Step3: Search and launch app from device
        result = searchAndOpenApp(mUiDevice, Constants.EBAY_APP);
        assertTrue("eBay app is launched", result);

        //Step4: Search a product in eBay app
        count = searchProducteBay(mUiDevice, "Sony TV");
        assertTrue("Found products", (count > 0));
    }
}
