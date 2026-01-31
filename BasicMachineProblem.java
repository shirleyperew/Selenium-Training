package com.bootcamp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasicMachineProblem {
    
        WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void getPageInfo() throws InterruptedException {
        // get info from url
        driver.get("https://the-internet.herokuapp.com");
        Thread.sleep(2000);

        //1. get page title
        String pageTitle = driver.getTitle();
        System.err.println("Page Title: "+ pageTitle);
        Assert.assertEquals(pageTitle,"The Internet");


        //2. interaction and locators

        //a. locate Form Authentication Link
        driver.findElement(By.linkText("Form Authentication")).click();
        Thread.sleep(2000);
        
        //b. locate username input field
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("tomsmith");

        //c. locate password input field
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("SuperSecretPassword!");
        Thread.sleep(2000);

        //d. locate login button
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        Thread.sleep(2000);
    
        //e. get result - login successful
        WebElement successMessage = driver.findElement(By.id("flash"));
        Assert.assertTrue(successMessage.isDisplayed()," You logged into a secure area!");
        System.out.println("Login Success Message: 'You logged into a secure area!' is displayed.");
        Thread.sleep(2000);

        //f. locate logout button
        WebElement logoutButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/a"));
        logoutButton.click();
        Thread.sleep(5000);

        //g. go back to main page
        driver.navigate().to("https://the-internet.herokuapp.com");
        Thread.sleep(2000);


        //3. Dropdown
        //a. locate Dropdown Link
        driver.findElement(By.linkText("Dropdown")).click();
        Thread.sleep(2000);        

        //b. option 2 dropdown
        //locate dropdown element
        WebElement dropdownElement = driver.findElement(By.id("dropdown"));

        //create select object
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("2");
        Assert.assertEquals(dropdown.getFirstSelectedOption().getText(), "Option 2"); 
        System.out.println("Dropdown Selection: Option 2 Dropdown is selected as expected.");
        Thread.sleep(2000);
        
        //c. go back to main page
        driver.navigate().back();
        Thread.sleep(2000);


        //4. Checkboxes
         //a. locate Checkboxes Link
        driver.findElement(By.linkText("Checkboxes")).click();
        Thread.sleep(2000);

        //b. check if checkbox 1 is checked
        WebElement checkbox1 = driver.findElement(By.xpath("//input[@type='checkbox'][1]"));
        Assert.assertFalse(checkbox1.isSelected(), "Checkbox 1 should be unselected by default");
        checkbox1.click();
        Thread.sleep(1000);
        Assert.assertTrue(checkbox1.isSelected(), "Checkbox 1 should be selected after clicking");
        System.out.println("Checkbox Selection: Checkbox 1 is selected as expected.");

        //c. check if checkbox 2 is selected
        WebElement checkbox2 = driver.findElement(By.xpath("//input[@type='checkbox'][2]"));
        Assert.assertTrue(checkbox2.isSelected(), "Checkbox 2 should be selected by default");
        System.out.println("Checkbox Selection: Checkbox 2 is selected as expected.");
        Thread.sleep(2000); 

        //d. go back to main page
        driver.navigate().back();
        Thread.sleep(2000);


        //5. Alerts
        //a. locate JavaScript alerts Link
        driver.findElement(By.linkText("JavaScript Alerts")).click();
        Thread.sleep(2000);         

        //b. click JS Alert button
        WebElement jsalertButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/ul/li[1]/button"));
        jsalertButton.click();
        Thread.sleep(2000);

        //c. Click OK
        driver.switchTo().alert().accept();
        Thread.sleep(2000);

        //d. Assert alert is click
        WebElement successalertMessage = driver.findElement(By.xpath("/html/body/div[2]/div/div/p[2]"));
        Assert.assertTrue(successalertMessage.isDisplayed(),"You successfully clicked an alert");
        System.out.println("Alert Success Message: 'You successfully clicked an alert' is displayed.");
        Thread.sleep(2000);
   
        //e. go back to main page
        driver.navigate().back();
        Thread.sleep(2000);


        //6. Explicit waits
         //a. locate Dynamic Loading Link
        driver.findElement(By.linkText("Dynamic Loading")).click();
        Thread.sleep(2000);          

        //b. Click Example 1
        driver.findElement(By.linkText("Example 1: Element on page that is hidden")).click();
        Thread.sleep(2000);        

        //c. Click Start
        WebElement startButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[1]/button"));
        startButton.click();

        //d. Wait for Hello World!
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            
            WebElement successwaitMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div/div[3]")));

            // If the element becomes visible within 10 seconds, the code proceeds
            Assert.assertTrue(successwaitMessage.isDisplayed(),"Hello World!"); 

            } finally {
            System.out.println("Dynamic Loading Message: 'Hello World!' is displayed.");
            Thread.sleep(2000);   
            }
        }

        @AfterMethod
        public void tearDown() {
            if (driver != null) {
                driver.quit();
        }
    }

}