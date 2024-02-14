package org.example;

import java.text.NumberFormat;
import java.text.ParseException;
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
        double wholesalePrice = Double.parseDouble(driver.findElement(By.cssSelector("input[name='wholesale_price_16']")).getAttribute("value"));
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
        WebElement minQtyTextFromShopPage = driver.findElement(By.xpath("//div[@class='wwp-wholesale-pricing-details']/p[4]"));

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
                    double wholesaleDiscountShopPage = Double.parseDouble(priceWithoutSymbol);
                    System.out.println(wholesaleDiscountShopPage);

                    //Verify the wholesale discount applying on shop page correctly
                    Assert.assertEquals(wholesaleDiscountShopPage, wholesalePrice);

                    int splitMinQtytext = Integer.parseInt(minQtyTextFromShopPage.getText().split("of")[1].split("products")[0].trim());

                    //Verify the minimum quantity text on shop page
                    Assert.assertEquals(splitMinQtytext, minQty);
                    System.out.println(splitMinQtytext);
                }

            }
        }

        Actions a = new Actions(driver);
        driver.findElement(By.id("woocommerce-product-search-field-0")).sendKeys(products);
        a.moveToElement(driver.findElement(By.id("woocommerce-product-search-field-0"))).keyDown(Keys.ENTER).build().perform();

        Thread.sleep(2000);
        //Get Wholesale Discount from product page and convert to float or double
        double wholesaleDiscount = Double.parseDouble(driver.findElement(By.xpath("//div[@class='summary entry-summary'] //div[@class='wwp-wholesale-pricing-details']/p[2]/span[2]")).getText().replace("$", "").trim());

        //Verify the Wholesale discount correctly on product page
        Assert.assertEquals(wholesaleDiscount, wholesalePrice);

        //Verify the minimum quantity discount on product page
        int minQtyProductPage = Integer.parseInt(driver.findElement(By.xpath("//div[@class='summary entry-summary'] //div[@class='wwp-wholesale-pricing-details']/p[4]")).getText().split("of")[1].split("products")[0].trim());
        Assert.assertEquals(minQtyProductPage, minQty);
        System.out.println("The minimum quantity on product page is correct " + minQtyProductPage);

        //send the quantity to products then add to cart the product
        //  int getqtyfromProduct = Integer.parseInt(driver.findElement(By.cssSelector("form[class='cart'] button[name='add-to-cart']")).getAttribute("value"));
        //   System.out.println(getqtyfromProduct);
        driver.findElement(By.cssSelector("input[aria-label='Product quantity']")).clear();
        driver.findElement(By.cssSelector("input[aria-label='Product quantity']")).sendKeys("12");
        driver.findElement(By.cssSelector("form[class='cart'] button[name='add-to-cart']")).click();
        driver.findElement(By.linkText("View cart")).click();

        //Get wholesale discount from cart and verify
        double cartWholesale = Double.parseDouble(driver.findElement(By.xpath("//div[@class='wc-block-cart-item__prices'] //ins[@class='wc-block-components-product-price__value is-discounted']")).getText().replace("$", "").trim());
        Assert.assertEquals(cartWholesale, wholesalePrice);
        System.out.println("The wholesale fixed discount on cart page is correct " + cartWholesale);

        //Get product quantity from cart page
        int cartQuantity = Integer.parseInt(driver.findElement(By.xpath("//div[@class='wc-block-cart-item__quantity']/div //input[@class='wc-block-components-quantity-selector__input']")).getAttribute("value"));

        //multiply the quantity with per product price
        double multiplyQuantity = cartWholesale * cartQuantity;

        //Get total amount from product cart section
        String productCartTotalText = driver.findElement(By.xpath("//div[@class='wc-block-cart-item__total-price-and-sale-badge-wrapper'] //span[@class='wc-block-formatted-money-amount wc-block-components-formatted-money-amount wc-block-components-product-price__value']")).getText();
        double convertproductTotalText = NumberFormat.getInstance().parse(productCartTotalText.substring(1)).doubleValue();
        System.out.println("total cart " + convertproductTotalText);

        //Verify the per product wholesale discount calculating with product quantity
        Assert.assertEquals(convertproductTotalText, multiplyQuantity);

        //Get Sub-total from cart totals
        String cartSubTotalText = driver.findElement(By.xpath("//div[@class='wc-block-components-totals-item']/span[2]")).getText();
        double cartSubTotal = NumberFormat.getInstance().parse(cartSubTotalText.substring(1)).doubleValue();

        //Compare sub-total in product cart section and cart total section
        Assert.assertEquals(cartSubTotal, convertproductTotalText);
        //  System.out.println("");


        int reduceQty = Integer.parseInt(driver.findElement(By.xpath("//div[@class='wc-block-cart-item__quantity']/div //input[@class='wc-block-components-quantity-selector__input']")).getAttribute("value"));
      //  System.out.println(reduceQty);

        while (reduceQty > 11) {
            WebElement decreaseButton = driver.findElement(By.xpath("//button[@class='wc-block-components-quantity-selector__button wc-block-components-quantity-selector__button--minus']"));
            decreaseButton.click();

            // Wait for the quantity to update before checking again
            Thread.sleep(5000);

            // Update reduceQty after clicking the decrease button
            reduceQty = Integer.parseInt(driver.findElement(By.xpath("//div[@class='wc-block-cart-item__quantity']/div //input[@class='wc-block-components-quantity-selector__input']")).getAttribute("value"));
        }

        String getPriceAfterDecreaseQtyText = driver.findElement(By.xpath("//div[@class='wc-block-cart-item__prices'] //span[@class='wc-block-formatted-money-amount wc-block-components-formatted-money-amount wc-block-components-product-price__value']")).getText();
        double getPriceAfterDecreaseQty = NumberFormat.getInstance().parse(getPriceAfterDecreaseQtyText.substring(1)).doubleValue();
        System.out.println(getPriceAfterDecreaseQty);


    }

}


