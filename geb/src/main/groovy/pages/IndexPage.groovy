package pages

import geb.Module
import geb.Page

/**
 * @author irof
 */
class IndexPage extends Page {

    static at = {
        title == "JettyServerRuleIndexPage"
    }

    static content = {
        管理メニュー { module AdminModule }
    }

    def 管理メニューリンクをクリック() {
        管理メニュー.リンク.click()
    }
}

class AdminModule extends Module {
    static content = {
        リンク(to: AdminPage) { $("a", text: "管理メニュー") }
    }
}