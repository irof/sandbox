import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.URL;
import java.nio.file.Paths;

/**
 * @author irof
 */
public class Main {

    public static void main(String[] args) throws Exception {
        WebDriver driver = new FirefoxDriver();
        try (AutoCloseable ac = driver::quit) {
            URL url = Paths.get("./html/index.html").toUri().toURL();
            driver.get(url.toString());

            System.out.println(driver.getTitle()); // "index page"

            driver.findElement(By.cssSelector("form input[type='submit']")).click();
            System.out.println(driver.getTitle()); // "target page"
        }
    }
}
