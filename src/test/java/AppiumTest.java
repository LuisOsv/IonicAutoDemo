/**
 * Created by LuisOsvaldo on 03/09/2017.
 */
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.tools.ant.taskdefs.WaitFor;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
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
//        capabilities.setCapability("platformVersion", "5.1");
//        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "io.ionic.starter");
        capabilities.setCapability("appActivity", "io.ionic.starter.MainActivity");
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
        swipeHorizontal();
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

    public void WaitForElement(By by) {
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void swipeHorizontal(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int width = ((Long) js.executeScript("return window.innerWidth || document.body.clientWidth")).intValue();
        int height = ((Long) js.executeScript("return window.innerHeight || document.body.clientHeight")).intValue() ;
        int startX = (int) (width * 0.70);
        int endX = (int) (width * 0.30);
        int startY = height / 2; // get middle of screen on Y
        TouchAction touchAction = new TouchAction((MobileDriver)driver);
        //Swipe from Right to left
//        touchAction.press(startX, startY).moveTo(endX, startY).release().perform();
        //Swipe from Left to Right
//        touchAction.press(endX, startY).moveTo(startX, startY).release().perform();
        WebElement element = driver.findElement(By.tagName("ion-navbar"));
        touchAction.longPress(element).moveTo(endX,580).release().perform();
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
