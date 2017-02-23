package com.riddhik.myapps.androiduiautomatorexample;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.widget.TextView;

import com.squareup.spoon.Spoon;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class EbaySearchTest {

    private UiDevice mUiDevice;

    @Before
    public void setUp() throws Exception {
        //Uninstall ebay app
        TestHelper.uninstallApp();

        //Initialize UiDevice
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        assertThat(mUiDevice, notNullValue());

        mUiDevice.wakeUp();
        mUiDevice.pressHome();
        Thread.sleep(Constants.WAIT_TIME);
    }

    @Test
    public void Test() throws Exception {
        TestHelper.openApp();
    }

//    @Test
//    public void eBaySearchTest() throws Exception {
//        boolean isAppFound;
//
//        //Search and open eBay app on phone
//        isAppFound = TestHelper.searchAndOpenApp(mUiDevice, Constants.APP_NAMES.eBay.toString());
//        if (isAppFound == true) {
//            //Wait till app loads info
//            Thread.sleep(5000);
//            //Search a product
//            TestHelper.searchProduct(mUiDevice, "Sony TV");
//        } else {
//
//        }
//    }
}
