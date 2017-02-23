package com.riddhik.myapps.androiduiautomatorexample;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class EbaySearchTest {

    private static final String LOG_TAG = EbaySearchTest.class.getSimpleName();

    private UiDevice mUiDevice;

    @Before
    public void setUp() throws Exception {
        //Initialize UiDevice
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mUiDevice.pressHome();
    }

    @Test
    public void calcAdditionTest() throws Exception {

        //Search and open eBay app on phone
        TestHelper.searchApp(mUiDevice, Constants.APP_NAMES.eBay.toString());

    }
}
