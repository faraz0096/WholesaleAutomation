package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.time.Duration;

public class WholesaleRegistration {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("chromedriver.exe", "C:/Users/GS/Downloads/ChromeDriver");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        String adminURL = "https://wordpress-1077016-3777704.cloudwaysapps.com/wp-admin/";
        String restoreURL = "https://wordpress-1077016-3777704.cloudwaysapps.com/";
        driver.get(adminURL);
        driver.findElement(By.id("user_login")).

                sendKeys("support@wpexperts.io");
        driver.findElement(By.id("user_pass")).sendKeys("faraz0096");
        driver.findElement(By.id("wp-submit")).click();
        Thread.sleep(1000);
        WebElement hover = driver.findElement(By.xpath("//div[text()='Wholesale']"));
        new Actions(driver).moveToElement(hover).perform();
        driver.findElement(By.xpath("//a[@href='admin.php?page=wwp-registration-setting']")).click();

        Thread.sleep(1000);
        driver.findElement(By.linkText("General Settings")).click();

        Thread.sleep(1000);
      //Turn of Customer Billing toggle if not enabled
      if(!driver.findElement(By.id("custommer_billing_address")).isSelected()) {
          driver.findElement(By.cssSelector("label[for='custommer_billing_address']")).click();
      }

        Assert.assertTrue(driver.findElement(By.id("custommer_billing_address")).isSelected());

      //Turn of Customer Shipping toggle if not enabled

        if(!driver.findElement(By.id("custommer_shipping_address")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='custommer_shipping_address']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("custommer_shipping_address")).isSelected());

        //Click on Save Changes
         Thread.sleep(2000);
        driver.findElement(By.cssSelector("button[name='save_wwp_registration_setting']")).click();

        //Go to Default Fields
        driver.findElement(By.linkText("Default Fields")).click();

        driver.findElement(By.cssSelector("div[class='card'] button[aria-controls='collapse_billing']")).click();

        //Fill Billing Address fields

        driver.findElement(By.id("billing_first_name")).clear();
        driver.findElement(By.id("billing_first_name")).sendKeys("Your Name");
        driver.findElement(By.id("billing_first_name")).click();

        if(!driver.findElement(By.id("enable_billing_first_name")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='enable_billing_first_name']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("enable_billing_first_name")).isSelected());

        if(!driver.findElement(By.id("required_billing_first_name")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='required_billing_first_name']")).click();
        }

        boolean requiredFirstName = driver.findElement(By.id("required_billing_first_name")).isSelected();

        Assert.assertTrue(driver.findElement(By.id("required_billing_first_name")).isSelected());

        driver.findElement(By.id("billing_last_name")).clear();
        driver.findElement(By.id("billing_last_name")).sendKeys("Your Last Name");

        if(!driver.findElement(By.id("enable_billing_last_name")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='enable_billing_last_name']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("enable_billing_last_name")).isSelected());

        if(!driver.findElement(By.id("required_billing_last_name")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='required_billing_last_name']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("required_billing_last_name")).isSelected());
        boolean requiredLastName = driver.findElement(By.id("required_billing_last_name")).isSelected();

        driver.findElement(By.id("billing_company")).clear();
        driver.findElement(By.id("billing_company")).sendKeys("Enter Company name");

        if(!driver.findElement(By.id("enable_billing_company")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='enable_billing_company']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("enable_billing_company")).isSelected());

      /* if(!driver.findElement(By.id("required_billing_company")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='required_billing_company']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("required_billing_company")).isSelected());*/

        boolean requiredCompany = driver.findElement(By.id("required_billing_company")).isSelected();

        driver.findElement(By.id("billing_address_1")).clear();
        driver.findElement(By.id("billing_address_1")).sendKeys("Billing home address");

        if(!driver.findElement(By.id("enable_billing_address_1")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='enable_billing_address_1']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("enable_billing_address_1")).isSelected());

        if(!driver.findElement(By.id("required_billing_address_1")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='required_billing_address_1']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("required_billing_address_1")).isSelected());

        driver.findElement(By.id("billing_address_2")).clear();
        driver.findElement(By.id("billing_address_2")).sendKeys("Billing Office address");

        if(!driver.findElement(By.id("enable_billing_address_2")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='enable_billing_address_2']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("enable_billing_address_2")).isSelected());

        if(!driver.findElement(By.id("required_billing_address_2")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='required_billing_address_2']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("required_billing_address_1")).isSelected());

        driver.findElement(By.id("billing_city")).clear();
        driver.findElement(By.id("billing_city")).sendKeys("City/Town");

        if(!driver.findElement(By.id("enable_billing_city")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='enable_billing_city']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("enable_billing_city")).isSelected());

        if(!driver.findElement(By.id("required_billing_city")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='required_billing_city']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("required_billing_city")).isSelected());

        driver.findElement(By.id("billing_post_code")).clear();
        driver.findElement(By.id("billing_post_code")).sendKeys("City/Town");

        if(!driver.findElement(By.id("enable_billing_post_code")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='enable_billing_post_code']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("enable_billing_post_code")).isSelected());

        if(!driver.findElement(By.id("required_billing_post_code")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='required_billing_post_code']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("required_billing_post_code")).isSelected());

        driver.findElement(By.id("billing_countries")).clear();
        driver.findElement(By.id("billing_countries")).sendKeys("Billing Country");

        if(!driver.findElement(By.id("enable_billing_country")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='enable_billing_country']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("enable_billing_country")).isSelected());

        if(!driver.findElement(By.id("required_billing_country")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='required_billing_country']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("required_billing_country")).isSelected());

        driver.findElement(By.id("billing_state")).clear();
        driver.findElement(By.id("billing_state")).sendKeys("State");

        if(!driver.findElement(By.id("enable_billing_state")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='enable_billing_state']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("enable_billing_state")).isSelected());

        if(!driver.findElement(By.id("required_billing_state")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='required_billing_state']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("required_billing_state")).isSelected());

        //Phone

        driver.findElement(By.id("billing_phone")).clear();
        driver.findElement(By.id("billing_phone")).sendKeys("Phone Number");

        if(!driver.findElement(By.id("enable_billing_phone")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='enable_billing_phone']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("enable_billing_phone")).isSelected());

        if(!driver.findElement(By.id("required_billing_phone")).isSelected())
        {
            driver.findElement(By.cssSelector("label[for='required_billing_phone']")).click();
        }

        Assert.assertTrue(driver.findElement(By.id("required_billing_phone")).isSelected());

        //Save Registration Settings

        driver.findElement(By.cssSelector("button[name='save_wwp_registration_setting']")).click();

        //Logout from admin and open browser incognito mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver.get("https://wordpress-1077016-3777704.cloudwaysapps.com/my-account/");
        driver.findElement(By.cssSelector("li[class*='customer-logout'] a")).click();

        Thread.sleep(2000);
        //Go to Registration page   //prerequisite (Wholesale registration should created with wholesale registration shortcode)

        //ReCapthca should disable reCaptcha can't automate
        driver.findElement(By.linkText("Wholesale Registration")).click();
        //QueryString to remove cache
        String queryString = "/?=54545";
        driver.get(driver.getCurrentUrl()+queryString);

        driver.findElement(By.id("wwp_wholesaler_username")).sendKeys("automation.testser");
        driver.findElement(By.id("wwp_wholesaler_email")).sendKeys("automation.tester@gmail.com");
        driver.findElement(By.id("wwp_wholesaler_password")).sendKeys("faraz0096");
        driver.findElement(By.id("reg_password2")).sendKeys("faraz4555");
        //Check FirstName required validation applied correctly on Billing firstName or not
       WebElement firstNameRequired = driver.findElement(By.id("wwp_wholesaler_fname"));
        String requiredAttribute = firstNameRequired.getAttribute("required");

        if(requiredAttribute!=null)
        {
            System.out.println("Billing FirstName is required");
        }else
        {
            System.out.println("Billing FirstName is not required");
        }

        //If firstname required is enabled from backend and not applying on front-end then below assertion failed
        Assert.assertTrue(requiredFirstName , driver.findElement(By.id("wwp_wholesaler_fname")).getAttribute("required"));
        //If found not required (null) then this assertion failed
        Assert.assertNotNull(requiredAttribute, "FirstName should required.");



        //Check LastName required validation applied correctly on Billing LastName or not
        WebElement lastNameRequired = driver.findElement(By.id("wwp_wholesaler_lname"));
        String lastNameRequiredAttribute = lastNameRequired.getAttribute("required");

        if(lastNameRequiredAttribute!=null)
        {
            System.out.println("Billing LastName is required");
        }else
        {
            System.out.println("Billing LastName is not required");
        }

        //If firstname required is enabled from backend and not applying on front-end then below assertion failed
        Assert.assertTrue(requiredLastName , driver.findElement(By.id("wwp_wholesaler_lname")).getAttribute("required"));
        //If found not required (null) then this assertion failed
        Assert.assertNotNull(lastNameRequiredAttribute, "LastName should required.");

        //Check Company required validation applied correctly on Company or not

        WebElement companyRequired = driver.findElement(By.id("wwp_wholesaler_company"));
        String companyRequiredAttribute = companyRequired.getAttribute("required");

        if(companyRequiredAttribute!= null)
        {
            System.out.println("Company field is required");
        }else {

            System.out.println("Company field is not required");
        }

        //If company required is enabled from backend and not applying on front-end then below assertion failed
        Assert.assertTrue(requiredCompany, driver.findElement(By.id("wwp_wholesaler_company")).getAttribute("required"));

        //If found not required (null) then this assertion failed

        Assert.assertNotNull(companyRequiredAttribute , "Company field should requird");


    }
}