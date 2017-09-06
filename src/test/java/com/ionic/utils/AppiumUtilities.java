package com.ionic.utils;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Set;

/**
 * Created by luis.serna on 05/09/2017.
 */
public class AppiumUtilities {

    static int TimeOutSeconds = 25;

    public static void switchToWebView(AndroidDriver driver){
        Set contextNames = driver.getContextHandles();
        for (Object contextName: contextNames) {
            String cn = ((String)contextName);
            if (cn.contains("WEBVIEW")){
                driver.context(cn);
            }
        }
    }

    public static void switchToNative(AndroidDriver driver){
        Set contextNames = driver.getContextHandles();
        for (Object contextName: contextNames) {
            String cn = ((String)contextName);
            if (cn.contains("NATIVE_APP")){
                driver.context(cn);
            }
        }
    }

    public static void WaitForElement(AndroidDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, TimeOutSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void WaitSomeSeconds(int milliseconds){
        try {
            Thread.sleep(milliseconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void swipeHorizontal(AndroidDriver driver){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int width = ((Long) js.executeScript("return window.innerWidth || document.body.clientWidth")).intValue();
        int height = ((Long) js.executeScript("return window.innerHeight || document.body.clientHeight")).intValue() ;
        Dimension size = new Dimension(width, height);
        int startX = (int) (size.getWidth() * 0.70);
        int endX = (int) (size.getWidth() * 0.30);
        int startY = size.getHeight() / 2; // get middle of screen on Y
        switchToNative(driver);
        TouchAction touchAction = new TouchAction(driver);
        //Swipe from Right to left
//        touchAction.press(startX, startY).moveTo(endX, startY).release().perform();
        touchAction.press(startX, startY).moveTo(endX, startY).release().perform();

        //Swipe from Left to Right
//        touchAction.press(endX, startY).moveTo(startX, startY).release().perform();
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void swipeSlide(AndroidDriver driver, WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int width = ((Long) js.executeScript("return window.innerWidth || document.body.clientWidth")).intValue();
        js.executeScript("arguments[0].style.cssText = \"position: absolute; left: -"+ width+"px;\";", element);
    }

}
