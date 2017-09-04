/**
 * Created by LuisOsvaldo on 03/09/2017.
 */
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

public class AppiumTest {

    AndroidDriver driver;

    @BeforeMethod
    public void setUp(){
        //Appium Cababilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("device","Android");
        capabilities.setCapability("deviceName","LGD80265209f9");
        capabilities.setCapability("platformName","Android");
        // demo1
//        capabilities.setCapability("appPackage", "io.ionic.starter");
//        capabilities.setCapability("appActivity", "io.ionic.starter.MainActivity");
        // demo2
        capabilities.setCapability("appPackage", "com.ionicframework.ionicpreviewapp458643");
        capabilities.setCapability("appActivity", "com.ionicframework.ionicpreviewapp458643.MainActivity");
        try {
            driver =  new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown(){
        //close apppium session
        driver.quit();
    }

    @Test
    public void testClickTabs(){
        By HomeButton = By.cssSelector("ion-icon[ng-reflect-name~=\"home\"]");
        By AboutButton = By.cssSelector("ion-icon[ng-reflect-name~=\"information-circle\"]");
        By ContactsButton = By.cssSelector("ion-icon[ng-reflect-name~=\"contacts\"]");
        switchToWebView();
        WaitForElement(HomeButton);
        driver.findElement(HomeButton).click();
        driver.findElement(AboutButton).click();
        driver.findElement(ContactsButton).click();
    }

    @Test
    public void testSwipeHorizontal(){
        switchToWebView();
//        swipeJS();
//        swipeHorizontal();
        swipeWebElementLeft();
//        driver.executeScript("client:client.swipe(\"Left\", 0, 500)");
    }

    @Test
    public void testFillForm(){
        switchToWebView();
        By ShowGenericButton = By.cssSelector("div.scroll-content  >  button");
        By TitleActionMenu = By.cssSelector(".action-sheet-title");
        By CancelActionButton = By.cssSelector(".action-sheet-group >button:nth-child(1)");
        By DeleteActionButton = By.cssSelector(".action-sheet-group >button:nth-child(2)");
        By ShareActionButton = By.cssSelector(".action-sheet-group >button:nth-child(3)");
        By PlayActionButton = By.cssSelector(".action-sheet-group >button:nth-child(4)");
        By FavoriteActionButton = By.cssSelector(".action-sheet-group >button:nth-child(5)");

        By LeftNavButton = By.cssSelector(".app-root ion-fab:nth-child(1)");
        By RightNavButton = By.cssSelector(".app-root ion-fab:nth-child(2)");

        Assert.assertEquals(driver.findElement(ShowGenericButton).getText(),"SHOW ACTION SHEET");
        driver.findElement(RightNavButton).click();
        Assert.assertEquals(driver.findElement(ShowGenericButton).getText(), "SHOW BASIC ALERT");

    }

    public void switchToWebView(){
        Set contextNames = driver.getContextHandles();
        for (Object contextName: contextNames) {
            String cn = ((String)contextName);
            if (cn.contains("WEBVIEW")){
                driver.context(cn);
            }
        }
    }

    public void switchToNative(){
        Set contextNames = driver.getContextHandles();
        for (Object contextName: contextNames) {
            String cn = ((String)contextName);
            if (cn.contains("NATIVE_APP")){
                driver.context(cn);
            }
        }
    }

    public void WaitForElement(By by) {
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void swipeHorizontal(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int width = ((Long) js.executeScript("return window.innerWidth || document.body.clientWidth")).intValue();
        int height = ((Long) js.executeScript("return window.innerHeight || document.body.clientHeight")).intValue() ;
        Dimension size = new Dimension(width, height);
        int startX = (int) (size.getWidth() * 0.70);
        int endX = (int) (size.getWidth() * 0.30);
        int startY = size.getHeight() / 2; // get middle of screen on Y
        switchToNative();
        TouchAction touchAction = new TouchAction(driver);
        //Swipe from Right to left
//        touchAction.press(startX, startY).moveTo(endX, startY).release().perform();
        //Swipe from Left to Right
//        touchAction.press(endX, startY).moveTo(startX, startY).release().perform();
        touchAction.press(endX, startY).moveTo(startX, startY).release().perform();
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void swipeWebElementLeft(){
        WebElement slide = driver.findElement(By.cssSelector("ion-nav.menu-content"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int width = ((Long) js.executeScript("return window.innerWidth || document.body.clientWidth")).intValue();
        int startX = (int) (width * 0.70);
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(slide).moveTo(startX, 0).release().perform();
    }

    public void swipeJS() {
        switchToNative();
        JavascriptExecutor js = (JavascriptExecutor) driver;
//        int width = ((Long) js.executeScript("return window.innerWidth || document.body.clientWidth")).intValue();
//        int height = ((Long) js.executeScript("return window.innerHeight || document.body.clientHeight")).intValue() ;
//        int startX = (int) (width * 0.70);
//        int endX = (int) (width * 0.30);
//        int startY = height / 2; // get middle of screen on Y
        HashMap<String, Double> swipeElement = new HashMap<String, Double>();
        swipeElement.put("startX", 0.01);
        swipeElement.put("startY", 0.3);
        swipeElement.put("endX", 0.7);
        swipeElement.put("endY", 0.6);
        swipeElement.put("duration", 4.0);
        js.executeScript("mobile: swipe", swipeElement);
    }
}
