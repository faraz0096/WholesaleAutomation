package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.text.ParseException;
import java.time.Duration;

public class EnforceMinQtyRules {

    public static void main(String[] args) throws InterruptedException, ParseException {

        WebDriver driver = new ChromeDriver();
        //WebDriverManager.chromedriver().setup();
        System.setProperty("chromedriver.exe", "C:/Users/GS/Downloads/ChromeDriver");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String products = "Automation Products Fixed Discount";

        driver.manage().window().maximize();

        String adminURL = "https://wordpress-1077016-3777704.cloudwaysapps.com/wp-admin/";
        String restoreURL = "https://wordpress-1077016-3777704.cloudwaysapps.com/";
        String truncateWholesale = "?reset_pricing=1";
        String truncateWholesalePrice = restoreURL + truncateWholesale;

        driver.get(adminURL);
        driver.findElement(By.id("user_login")).

                sendKeys("support@wpexperts.io");
        driver.findElement(By.id("user_pass")).sendKeys("faraz0096");
        driver.findElement(By.id("wp-submit")).click();

        // It will truncate all the Wholesale Discount from database
        driver.get(truncateWholesalePrice);
        driver.navigate().back();
        WebElement hover = driver.findElement(By.xpath("//div[text()='Wholesale']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(hover).build().perform();
        driver.findElement(By.xpath("//a[@href='admin.php?page=wwp_wholesale_settings']")).click();
        driver.findElement(By.cssSelector("div[class='col-md-3 col-sm-12'] li:nth-child(7)")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='form-table wwp-main-settings']/tbody/tr/th/label[contains(text(),'Enforce minimum quantity rules')]")));
        String printEnforceMinQty = driver.findElement(By.xpath("//table[@class='form-table wwp-main-settings']/tbody/tr/th/label[contains(text(),'Enforce minimum quantity rules')]")).getText();
        System.out.println(printEnforceMinQty);
        WebElement enforceMinQtyLabel = driver.findElement(By.cssSelector("label[for='wholesaler_allow_minimum_qty']"));
        WebElement enforceMinQtyChkBox = driver.findElement(By.id("wholesaler_allow_minimum_qty"));
        if (!enforceMinQtyChkBox.isSelected()) {
            enforceMinQtyLabel.click();
        }
        //Verify Enforce minimum quantity rules is selected or not
        Assert.assertTrue(enforceMinQtyChkBox.isSelected());
        WebElement hoverProduct = driver.findElement(By.xpath("//div[text()='Products']"));
        //Hover to WooCommerce Products
        actions.moveToElement(hoverProduct).build().perform();
        //Click on All products
        driver.findElement(By.linkText("All Products")).click();

        Thread.sleep(3000);
        driver.close();
    }
}