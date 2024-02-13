package org.example;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import java.time.Duration;

public class VerifyProductDiscountFixed {

    public static void main(String[] args) throws InterruptedException {

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

        Actions action = new Actions(driver);
        WebElement hover = driver.findElement(By.xpath("//div[text()='Products']"));
        //Hover to WooCommerce Products
        action.moveToElement(hover).perform();
        //Click on All products
        driver.findElement(By.linkText("All Products")).click();

        //Search automation product "Automation Products Fixed Discount"
        driver.findElement(By.id("post-search-input")).sendKeys(products);
        driver.findElement(By.id("search-submit")).click();
        driver.findElement(By.linkText("Automation Products Fixed Discount")).click();
        Thread.sleep(3000);

        try {
            //wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@class='card']/button"))));
            List<WebElement> wholesaleRole = driver.findElements(By.xpath("//div[@class='card']/button"));
            for (int i = 0; i < wholesaleRole.size(); i++) {

                System.out.println(wholesaleRole.get(i).getText());

                if (wholesaleRole.get(i).getText().contains("Updated Role")) {

                    wholesaleRole.get(i).click();
                }
            }

        } catch (org.openqa.selenium.StaleElementReferenceException ex) {

            //wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@class='card']/button"))));
            List<WebElement> wholesaleRole = driver.findElements(By.xpath("//div[@class='card']/button"));
            for (int i = 0; i < wholesaleRole.size(); i++) {

                //    System.out.println(wholesaleRole.get(i).getText());

                if (wholesaleRole.get(i).getText().contains("Updated Role")) {

                    wholesaleRole.get(i).click();


                }
            }
        }

        Thread.sleep(3000);

        WebElement discountCheck = driver.findElement(By.cssSelector("input[id='role_16']"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;

        if (!discountCheck.isSelected()) {
            executor.executeScript("arguments[0].click();", discountCheck);
        }

        WebElement discountDropDown = driver.findElement(By.cssSelector("select[name='discount_type_16']"));
        Select discountType = new Select(discountDropDown);
        discountType.selectByValue("fixed");

        driver.findElement(By.cssSelector("input[name='wholesale_price_16']")).clear();
        driver.findElement(By.cssSelector("input[name='wholesale_price_16']")).sendKeys("50");
        Integer wholesalePrice = Integer.parseInt(driver.findElement(By.cssSelector("input[name='wholesale_price_16']")).getAttribute("value"));
        driver.findElement(By.cssSelector("input[name='min_quatity_16']")).clear();
        driver.findElement(By.cssSelector("input[name='min_quatity_16']")).sendKeys("12");
        Integer minQty = Integer.parseInt(driver.findElement(By.cssSelector("input[name='min_quatity_16']")).getAttribute("value"));
        WebElement updateButton = driver.findElement(By.cssSelector("input[id='publish']"));
        executor.executeScript("arguments[0].click();", updateButton);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver.get("https://wordpress-1077016-3777704.cloudwaysapps.com/my-account/");
        driver.findElement(By.cssSelector("li[class*='customer-logout'] a")).click();
        driver.findElement(By.id("username")).sendKeys("wholesale.automation@gmail.com");
        driver.findElement(By.id("password")).sendKeys("faraz0096");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.linkText("Shop")).click();

        List<WebElement> loopProducts = driver.findElements(By.cssSelector("h2.woocommerce-loop-product__title"));
        WebElement loopWholesaleDiscount = driver.findElement(By.xpath("//div[@class='wwp-wholesale-pricing-details']/p[2]/span[2]"));

        // Iterate through each product title
        for (int i = 0; i < loopProducts.size(); i++) {
            WebElement productTitleElement = loopProducts.get(i);
            String productTitle = productTitleElement.getText();
            //System.out.println(productTitle);

            // Check if the current product title contains the desired product
            if (productTitle.equals(products)) {
                // Use the index 'i' to get the corresponding wholesale discount element
              //  WebElement wholesaleDiscountElement = loopWholesaleDiscount.get(i);
                String priceWithSymbol = loopWholesaleDiscount.getText();
                String priceWithoutSymbol = priceWithSymbol.replace("$", "").trim();

                if (!priceWithoutSymbol.isEmpty()) {
                    double wholesaleDiscount = Double.parseDouble(priceWithoutSymbol);

                    //Verify the wholesale discount applying on shop page correctly
                    Assert.assertEquals(wholesaleDiscount , (int)wholesalePrice);
                }

            }
        }

            Actions a = new Actions(driver);
            driver.findElement(By.id("woocommerce-product-search-field-0")).sendKeys(products);
            a.moveToElement(driver.findElement(By.id("woocommerce-product-search-field-0"))).keyDown(Keys.ENTER).build().perform();

    }

}


