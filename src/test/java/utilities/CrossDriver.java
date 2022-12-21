package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class CrossDriver {
    protected CrossDriver() {
    }

    public static WebDriver driver;

    public static WebDriver getDriver(String browser) {


        browser = browser == null ? ConfigFiles.getProperty("browser") : browser;

        if (driver == null) {

            switch (browser) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    FirefoxOptions options=new FirefoxOptions();
                    options.addArguments("--disable-blink-features=AutomationControlled");
                    options.addArguments("--disable-extensions");
                    options.addArguments("--incognito");
                    driver = new FirefoxDriver(options);
                    driver.manage().deleteAllCookies();
                    break;
            }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return driver;
    }

    public static void closeDriver() {
        try {
            if (driver != null) {
                driver.quit();
                driver = null;
            } else {
                System.out.println("Driver kapalı zaten.");
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }
}
