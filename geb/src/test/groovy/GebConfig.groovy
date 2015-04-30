import org.openqa.selenium.htmlunit.HtmlUnitDriver

driver = { new HtmlUnitDriver(true) }

environments {
    'firefox' {
        driver = "firefox"
    }
}