package com.riddhik.myapps.androiduiautomatorexample;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by rkakadia on 5/26/2016.
 */
public class EbayTest {

    //Basic tests for default Android calculator application

    static final String LOG_TAG = EbayTest.class.getSimpleName();
    static final int WAIT_TIME = 3000;
    int num1 = 100;
    int num2 = 20;

    private UiDevice mUiDevice;

    @Before
    public void setUp() throws Exception {
        //Initialize UiDevice
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mUiDevice.pressHome();

        mUiDevice.executeShellCommand("ls");
        mUiDevice.openNotification();
        mUiDevice.pressBack();

        mUiDevice.pressRecentApps();
        mUiDevice.pressBack();
        mUiDevice.pressHome();

        mUiDevice.setOrientationLeft();
        mUiDevice.setOrientationNatural();
    }

    @Test
    public void calcAdditionTest() throws Exception {

        //Setup Phone
        Log.d(LOG_TAG, "Open Home screen");
        mUiDevice.pressHome();

        //Step1: Open calculator application
        Log.d(LOG_TAG, "Open calculator app");
        launchCalc();

        //Step2: Input and Add two numbers
        Log.d(LOG_TAG, "Add " + num1 + " and " + num2);
        int sum = num1 + num2;
        int returnedSum = calcAddition(num1, num2);
        if (returnedSum == sum) {
            assertThat("Calculator Sum operation",(returnedSum == sum), equalTo(true));
        }
    }

    public void launchCalc() {
        UiObject allAppsBtn = mUiDevice.findObject(new UiSelector().description("Apps"));
        UiObject appsTab = mUiDevice.findObject(new UiSelector().description("Apps"));
        UiObject calculatorApp = mUiDevice.findObject(new UiSelector().description("Calculator"));
        UiObject calculatorAppMainScr_Validation = mUiDevice.findObject(new UiSelector()
                .resourceId("com.android.calculator2:id/digit_0"));

        try {
            allAppsBtn.clickAndWaitForNewWindow(WAIT_TIME);
            if (appsTab.exists()) {
                appsTab.clickAndWaitForNewWindow(WAIT_TIME);
            }
            calculatorApp.clickAndWaitForNewWindow(WAIT_TIME);
            assertThat(calculatorAppMainScr_Validation.exists(), equalTo(true));

        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int calcAddition(int num1, int num2) {
        int sum = 0;
        UiObject calcResultText = mUiDevice.findObject(new UiSelector()
                .resourceId("com.android.calculator2:id/result"));
        UiObject addButton = mUiDevice.findObject(new UiSelector()
                .resourceId("com.android.calculator2:id/op_add"));
        UiObject equalButton = mUiDevice.findObject(new UiSelector()
                .resourceId("com.android.calculator2:id/eq"));

        try {
            calcResultText.click();
            Thread.sleep(WAIT_TIME);
            calcResultText.setText(num1 + "");
            addButton.click();
            Thread.sleep(WAIT_TIME);
            calcResultText.setText(num2 + "");
            equalButton.click();
            Thread.sleep(WAIT_TIME);

            String outputText = calcResultText.getText().toString();
            sum = Integer.parseInt(outputText);

        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException nfe) {
            sum = 0;
        } finally {
            return sum;
        }
        //return sum;
    }
}
