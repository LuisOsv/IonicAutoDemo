package com.ionic.tests;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static com.ionic.utils.AppiumUtilities.*;

/**
 * Created by luis.serna on 05/09/2017.
 */
public class IonicPreviewTests {

    AndroidDriver driver;

    By ShowGenericButton = By.cssSelector("div.scroll-content  >  button");
    By TitleActionMenu = By.cssSelector(".action-sheet-title");
    By CancelActionButton = By.cssSelector(".action-sheet-group >button:nth-child(1)");
    By DeleteActionButton = By.cssSelector(".action-sheet-group >button:nth-child(2)");
    By ShareActionButton = By.cssSelector(".action-sheet-group >button:nth-child(3)");
    By PlayActionButton = By.cssSelector(".action-sheet-group >button:nth-child(4)");
    By FavoriteActionButton = By.cssSelector(".action-sheet-group >button:nth-child(5)");

    By LeftNavButton = By.cssSelector(".app-root ion-fab:nth-child(1)");
    By RightNavButton = By.cssSelector(".app-root ion-fab:nth-child(2)");

    //form inputs
    By HeaderTitle = By.cssSelector("ion-navbar ion-title");
    By FirstName = By.cssSelector("input[formcontrolname=firstName]");
    By LastName = By.cssSelector("input[formcontrolname=lastName]");
    By CreateButton = By.xpath("//span[contains(text(), 'Create Account')]/parent::button");
    By OkButton = By.cssSelector(".alert-button");
    By AlertMsgText = By.cssSelector(".alert-message");


    //slides
    By slide1 = By.cssSelector(".swiper-wrapper > ion-slide:nth-child(1)");
    By title1 = By.cssSelector(".swiper-wrapper > ion-slide:nth-child(1) .slide-title");
    By slide2 = By.cssSelector(".swiper-wrapper > ion-slide:nth-child(2)");
    By title2 = By.cssSelector(".swiper-wrapper > ion-slide:nth-child(2) .slide-title");
    By slide3 = By.cssSelector(".swiper-wrapper > ion-slide:nth-child(3)");
    By title3 = By.cssSelector(".swiper-wrapper > ion-slide:nth-child(3) .slide-title");

    @BeforeSuite
    public void setUpSuite(){
        //Appium Cababilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("device","Android");
        capabilities.setCapability("deviceName","LGD80265209f9");
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("appPackage", "com.ionicframework.ionicpreviewapp458643");
        capabilities.setCapability("appActivity", "com.ionicframework.ionicpreviewapp458643.MainActivity");
        try {
            driver =  new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            WaitSomeSeconds(4);
            switchToWebView(driver);
            WaitForElement(driver, RightNavButton);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void tearDownSuite(){
        driver.quit();
    }

    @Test
    public void testFormInputs(){
        NavigateToPage("Inputs");
        driver.findElement(FirstName).sendKeys("Luis Osvaldo");
        driver.findElement(LastName).sendKeys("Serna Gomez");
        driver.findElement(CreateButton).click();

        String msgAlert = driver.findElement(AlertMsgText).getText();
        Assert.assertEquals(msgAlert, "Created Account for: Luis Osvaldo Serna Gomez");
        driver.findElement(OkButton).click();
    }

    public void NavigateToPage(String page){
        String headerTitle = driver.findElement(HeaderTitle).getText();
        while(!headerTitle.equals(page)){
            driver.findElement(RightNavButton).click();
            WaitSomeSeconds(2);
            headerTitle = driver.findElement(HeaderTitle).getText();
        }
        System.out.println(" Navigated to "+page);
    }

//    @Test
//    public void testSearch(){
//
//    }
//
//    @Test
//    public void testGestures(){
//
//    }
//
    @Test
    public void testSlides(){
        NavigateToPage("Slides");

        swipeSlide(driver, driver.findElement(slide1));
        Assert.assertEquals(driver.findElement(title1).getText(), "Welcome to the Docs!");

        swipeSlide(driver, driver.findElement(slide2));
        Assert.assertEquals(driver.findElement(title2).getText(), "What is Ionic?");

        swipeSlide(driver, driver.findElement(slide3));
        Assert.assertEquals(driver.findElement(title3).getText(), "What is Ionic Cloud?");
    }


}
