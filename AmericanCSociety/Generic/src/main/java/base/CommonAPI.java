package base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utlity.TestUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CommonAPI {
    FileInputStream fis;
    public static Properties prop;
    public static WebDriver driver;

    public CommonAPI() {
        try {
            fis = new FileInputStream("/Users/abc/IdeaProject/AmericanCS/Generic/src/main/java/config/config.properties");
            prop = new Properties();
            try {
                prop.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initialization() {
        String browserName = prop.getProperty("browser");
        if (browserName.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver",prop.getProperty("chromedriver"));
            driver = new ChromeDriver();
        } else if (browserName.equals("FireFox")) {
            System.setProperty("webdriver.gecko.driver",prop.getProperty("firefoxdriver"));
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(TestUtil.implicit_wait,TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(TestUtil.page_load_timeout, TimeUnit.SECONDS);
        driver.get(prop.getProperty("urlACS"));

    }

    public void failedTest(String testMethodName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File("/Users/abc/IdeaProject/AmericanCS/" +
                    "Generic/src/main/java/ScreenshotFailedTests/" + testMethodName + "_" + ".jpg"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
