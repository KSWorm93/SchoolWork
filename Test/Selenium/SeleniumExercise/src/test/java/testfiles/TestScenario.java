/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testfiles;

import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import org.junit.AfterClass;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author kasper
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestScenario {

    static WebDriver driver;
    WebElement element;
    final static int WAIT = 5;

    public TestScenario() {
    }

    @BeforeClass
    public static void setup() {
//        System.setProperty("webdriver.gecko.driver", "/home/kasper/Documents/Java/Lib/Drivers/geckodriver");
//        WebDriver driver = new FirefoxDriver()1;

        //setup the driver for Chrome browser
        System.setProperty("webdriver.chrome.driver", "/home/kasper/Documents/Java/Lib/Drivers/chromedriver");
        driver = new ChromeDriver();

        //Reset data before we start in case something was not as it should be
        com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");

        //Navigate to site
        driver.get("http://localhost:3000/");
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    /**
     * Testing the basic layout and verifies that there is 5 rows at the start
     */
    @Test
    public void test1_FiveRowsInTable() {
        (new WebDriverWait(driver, WAIT)).until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                int size = driver.findElement(By.id("tbodycars")).findElements(By.cssSelector("tr")).size();
                assertThat(size, is(5));
                return true;
            }
        });
    }

    /**
     * Testing the filter functionality by inputted "2002" in the filter field
     */
    @Test
    public void test2_Filter2002() {
        (new WebDriverWait(driver, WAIT)).until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                element = driver.findElement(By.id("filter"));
                element.sendKeys("2002");

                //Check if size is 2
                int size = driver.findElement(By.id("tbodycars")).findElements(By.cssSelector("tr")).size();
                assertThat(size, is(2));
                return true;
            }
        });
    }

    /**
     * Clearing the data inputted in the previous test and verifies that the
     * size is still 5
     */
    @Test
    public void test3_ClearFilter() {
        (new WebDriverWait(driver, WAIT)).until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                element = driver.findElement(By.id("filter"));

                //Mark all and delete
                element.sendKeys(Keys.CONTROL + "a");
                element.sendKeys(Keys.DELETE);

                //Same as test1
                int size = driver.findElement(By.id("tbodycars")).findElements(By.cssSelector("tr")).size();
                assertThat(size, is(5));
                return true;
            }
        });
    }

    /**
     * Testing the sort functionality when clicking sort on the table. Verifies
     * that the cars are sorted by looking at first and last car
     */
    @Test
    public void test4_SortYearFirstAndLastCar() {
        WebElement sortLink = driver.findElement(By.id("h_year"));
        sortLink.click();
        (new WebDriverWait(driver, WAIT)).until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                //Get rows
                WebElement tbody = driver.findElement(By.id("tbodycars"));
                List<WebElement> trows = tbody.findElements(By.tagName("tr"));
                WebElement row1 = trows.get(0).findElements(By.tagName("td")).get(0);
                WebElement row5 = trows.get(4).findElements(By.tagName("td")).get(0);

                //Check rows
                assertThat(row1.getText(), is("938"));
                assertThat(row5.getText(), is("940"));
                return true;
            }
        });
    }

    /**
     * Testing the functionality of changing the description of a car
     */
    @Test
    public void test5_ChangeDescription() {
        List<WebElement> tds = driver.findElement(By.id("tbodycars"))
                .findElements(By.cssSelector("tr"))
                .get(0).findElements(By.tagName("td"));

        tds.get(7).findElements(By.tagName("a")).get(0).click();

        //Find "description", clear, input data
        element = driver.findElement(By.id("description"));
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys("Cool car");
        //Click button
        driver.findElement(By.id("save")).click();

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                WebElement e = d.findElement(By.tagName("tbody"));
                List<WebElement> rows = e.findElements(By.tagName("tr"));
                String result = null;
                //Loop through rows
                for (int i = 0; i < rows.size(); i++) {
                    //If id is "938" set result and break loop
                    if (rows.get(i).findElements(By.tagName("td")).get(0).getText().equalsIgnoreCase("938")) {
                        result = rows.get(i).findElements(By.tagName("td")).get(5).getText();
                        break;
                    }
                }
                //check description
                assertThat(result, is("Cool car"));
                return true;
            }
        });
    }

    /**
     * Testing if we get an error when not inputting the required data
     */
    @Test
    public void test6_CreateError_AllFieldsRequired() {
        driver.findElement(By.id("new")).click();
        driver.findElement(By.id("save")).click();

        (new WebDriverWait(driver, WAIT)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                //Varify error when button pressed
                String result = driver.findElement(By.id("submiterr")).getText();
                assertThat(result, is("All fields are required"));

                //Varify size
                int size = driver.findElement(By.id("tbodycars")).findElements(By.cssSelector("tr")).size();
                assertThat(size, is(5));
                return true;
            }
        });
    }

    /**
     * Testing the add car functionality
     */
    @Test
    public void test7_AddCar_VerifySize6() {
        //press button
        driver.findElement(By.id("new")).click();
        //insert data
        driver.findElement(By.id("year")).sendKeys("2008");
        driver.findElement(By.id("registered")).sendKeys("2002-05-05");
        driver.findElement(By.id("make")).sendKeys("Kia");
        driver.findElement(By.id("model")).sendKeys("Rio");
        driver.findElement(By.id("description")).sendKeys("As new");
        driver.findElement(By.id("price")).sendKeys("31000");

        //press button
        driver.findElement(By.id("save")).click();

        (new WebDriverWait(driver, WAIT)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement e = d.findElement(By.tagName("tbody"));
                List<WebElement> rows = e.findElements(By.tagName("tr"));
                //Size should be 6 now
                assertThat(rows.size(), is(6));
                return true;
            }
        });
    }

    /**
     * ******
     * Bonus tests :) 
     * ******
     */
    /**
     * Testing the remove button on the table. removing the first row
     */
    @Test
    public void test8_BONUS_RemoveCar() {
        (new WebDriverWait(driver, WAIT)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                //find and press delete button
                List<WebElement> tds = driver.findElement(By.id("tbodycars"))
                        .findElements(By.cssSelector("tr"))
                        .get(0).findElements(By.tagName("td"));
                tds.get(7).findElements(By.tagName("a")).get(1).click();

                WebElement e = d.findElement(By.tagName("tbody"));
                List<WebElement> rows = e.findElements(By.tagName("tr"));
                //Size should be 5 now
                assertThat(rows.size(), is(5));
                return true;
            }
        });
    }
}
