import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author irof
 */
public class Main {

    public static void main(String[] args) throws Exception {
        WebDriver driver = new FirefoxDriver();
        try (AutoCloseable ac = driver::quit) {
            driver.get(ClassLoader.getSystemResource("index.html").toString());

            System.out.println(driver.getTitle()); // "index page"

            driver.findElement(By.cssSelector("form input[type='submit']")).click();
            System.out.println(driver.getTitle()); // "target page"
        }
    }
}
