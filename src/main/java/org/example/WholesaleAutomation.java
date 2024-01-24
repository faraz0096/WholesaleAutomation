package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WholesaleAutomation {


    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub

        System.setProperty("chromedriver.exe", "C:/Users/GS/Downloads/ChromeDriver");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        String adminURL = "https://wordpress-1077016-3777704.cloudwaysapps.com/wp-admin/";
        String restoreURL = "https://wordpress-1077016-3777704.cloudwaysapps.com/";
        String truncateWholesale = "?reset_pricing=1";
        String truncateWholesalePrice = restoreURL + truncateWholesale;

        driver.get(adminURL);
        driver.findElement(By.id("user_login")).

                sendKeys("support@wpexperts.io");
        driver.findElement(By.id("user_pass")).sendKeys("faraz0096");
        driver.findElement(By.id("wp-submit")).click();
        Thread.sleep(1000);
        // It will truncate all the Wholesale Discount from database
        driver.get(truncateWholesalePrice);
        driver.navigate().back();
        WebElement hover = driver.findElement(By.xpath("//div[text()='Wholesale']"));
        new Actions(driver).moveToElement(hover).perform();

        driver.findElement(By.xpath("//a[@href='admin.php?page=wwp_wholesale_settings']")).click();
        driver.findElement(By.linkText("Wholesale Price Global")).click();


        Thread.sleep(2000);
        // div[@class='card']/button
        // div[@class='card'] //button[@class='btn btn-link collapsed']
        List<WebElement> cardsList = driver.findElements(By.xpath("//div[@class='card']/button"));

        for (WebElement e : cardsList)

        {
            if (e.getText().contains("Updated Role")) {

                e.click();

            }
        }

        Thread.sleep(2000);

        WebElement discountCheckbox = driver.findElement(By.xpath("//input[@id='role_26']"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;

        if (!discountCheckbox.isSelected()) {
            executor.executeScript("arguments[0].click();", discountCheckbox);
        }

        WebElement discountType = driver.findElement(By.name("discount_type_26"));
        Select el = new Select(discountType);
        el.selectByValue("percent");
        System.out.println(el.getFirstSelectedOption().getText());

        driver.findElement(By.cssSelector("input[name='wholesale_price_26']")).clear();
        driver.findElement(By.cssSelector("input[name='wholesale_price_26']")).sendKeys("50");
        float convertWholesaleDiscount = Float.parseFloat(
                driver.findElement(By.cssSelector("input[name='wholesale_price_26']")).getAttribute("value"));

        driver.findElement(By.cssSelector("input[name='min_quatity_26']")).clear();
        driver.findElement(By.cssSelector("input[name='min_quatity_26']")).sendKeys("12");

        int getMinQty = Integer
                .parseInt(driver.findElement(By.cssSelector("input[name='min_quatity_26']")).getAttribute("value"));
        driver.findElement(By.cssSelector("div[class='main-save-settings'] button[name='save-wwp_wholesale']")).click();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver.get("https://wordpress-1077016-3777704.cloudwaysapps.com/my-account/");
        driver.findElement(By.cssSelector("li[class*='customer-logout'] a")).click();
        driver.findElement(By.id("username")).sendKeys("tixixy@mailinator.com");
        driver.findElement(By.id("password")).sendKeys("faraz0096");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.linkText("Shop")).click();

        List<Float> actualPrices = getActualPrice(driver);
        Thread.sleep(2000);

        for (Float price : actualPrices)

        {
            System.out.println("Actual Prices: " + price);
        }

        List<Float> wholesalePrices = getWholesalePrice(driver);
        Thread.sleep(2000);

        for (Float price : wholesalePrices)

        {
            System.out.println("Wholesale Prices: " + price);

        }

        // Check Wholesale Global Discount working fine on Shop page

        for (float price : actualPrices)

        {

            float calculate = (float) ((price * convertWholesaleDiscount) / 100);

            for (float price1 : wholesalePrices) {

                if (calculate == price1) {

                    System.out.println("Wholesale price working fine: " + price1);
                    break;
                }
            }

        }

        Thread.sleep(2000);

        // Check Wholesale Global Discount on product Page

        List<WebElement> products = driver.findElements(By.cssSelector("li[class*=' type-product']"));

        for (WebElement product : products)

        {
            product.click();
            break;
        }

        // Get Wholesale Price from products and convert the price into double
        String wholesalePrice = driver
                .findElement(By.xpath("//div[@class='wwp-wholesale-pricing-details']/p[2]/span[2]")).getText();

        String WholesalepriceWithoutDollarSign = wholesalePrice.replace("$", "").trim();

        if (!WholesalepriceWithoutDollarSign.isEmpty()) {

            float convertWholesalePrice = Float.parseFloat(WholesalepriceWithoutDollarSign);
            System.out.println(convertWholesalePrice);

            // Get Actual Price from products and convert the price into double
            String actualPrice = driver.findElement(By.xpath("//div[@class='summary entry-summary'] //div[@class='wwp-wholesale-pricing-details']/p[1] //span[@class='woocommerce-Price-amount amount']"))
                    .getText();
            String ActualpriceWithoutDollarSign = actualPrice.replace("$", "").trim();
            float convertActualPrice = Float.parseFloat(ActualpriceWithoutDollarSign);
             System.out.println("Actual: " + convertActualPrice);

            // Calculate and compare
            float calculatePercentDiscount = (convertActualPrice * convertWholesaleDiscount) / 100;

            Assert.assertEquals(convertWholesalePrice, calculatePercentDiscount);
        }
        // Get Minimum Quantity Text and convert to integer

        String minQty = driver
                .findElement(By.xpath(
                        "//div[@class='summary entry-summary'] //div[@class='wwp-wholesale-pricing-details']/p[4]"))
                .getText();
        String[] splitText = minQty.split("of ");
        String[] splitText1 = splitText[1].split(" ");
        String trimText = splitText1[0].trim();
        int minQtyText = Integer.parseInt(String.valueOf(trimText));

        // Compare admin min qty text and product min qty

        Assert.assertEquals(minQtyText, getMinQty);

        // Empty cart
        String getCurrentUrl = driver.getCurrentUrl();
        String concatUrl = getCurrentUrl + "?empty_cart=1";
        driver.get(concatUrl);

        // enter wholesale min qty in products

        driver.findElement(By.cssSelector("input[class='input-text qty text']")).clear();
        driver.findElement(By.cssSelector("input[class='input-text qty text']")).sendKeys("12");
        driver.findElement(By.cssSelector("form[class='cart'] button[name='add-to-cart']")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("div[class='wc-block-components-notice-banner__content'] a")).click();

        //Get Wholesale discount from cart

       String discountText =  driver.findElement(By.cssSelector("ins[class='wc-block-components-product-price__value is-discounted']")).getText();

       String removeDollarWH = discountText.replace("$", "").trim();

       double convertWH = Double.parseDouble(removeDollarWH);


       //Get Regular Price from cart

        String regPriceText = driver.findElement(By.cssSelector("del[class='wc-block-components-product-price__regular']")).getText();
        String removeDollarRE = regPriceText.replace("$", "").trim();
        double convertREg = Double.parseDouble(removeDollarRE);

        //Calculate Wholesale Discount on Cart page

        double calculate = (convertREg * convertWholesaleDiscount) /100;

        //Compare and calculate
        Assert.assertEquals(calculate , convertWH);
        System.out.println("Wholesale Discount calculating fine on cart page" + convertWH);

    }

    // System.out.println("Calcuated Wholesale Discount: " +
    // calculateWholesaleDiscount);

/*
     * if (wholesalePrices == calculateWholesaleDiscount)
     *
     * { System.out.println("Global Discount working fine");
     *
     * }
*/

    public static List<Float> getWholesalePrice(WebDriver driver) {
        List<WebElement> getWholesaleGlobalDiscount = driver
                .findElements(By.xpath("//div[@class='wwp-wholesale-pricing-details']/p[2]/span[2]"));

        List<Float> wholesalePricesList = new ArrayList<>();

        for (WebElement getWholesale : getWholesaleGlobalDiscount) {
            String originalPrice = getWholesale.getText();
            String priceWithoutDollarSign = originalPrice.replace("$", "").trim();

            // Convert the amount to double
            if (!priceWithoutDollarSign.isEmpty()) {
                float doublePriceWholesale = Float.parseFloat(priceWithoutDollarSign);
                wholesalePricesList.add(doublePriceWholesale);

            }
        }

        return wholesalePricesList;
    }

    public static List<Float> getActualPrice(WebDriver driver) {

        List<WebElement> getretailPrice = driver.findElements(By.xpath(
                "//div[@class='wwp-wholesale-pricing-details']/p[1] //span[@class='woocommerce-Price-amount amount']"));
        // double doublePriceActual = 0.0;
        List<Float> actualPricesList = new ArrayList<>();

        for (WebElement iterateRetial : getretailPrice)

        {
            String originalPrice = iterateRetial.getText();
            String priceWithoutDollarSign = originalPrice.replace("$", "").trim();

            // convert the amount in double
            if (!priceWithoutDollarSign.isEmpty()) {
                float doublePriceActual = Float.parseFloat(priceWithoutDollarSign);
                actualPricesList.add(doublePriceActual);
            }

        }

        return actualPricesList;

    }

}
