package example

import geb.Browser

/**
 * The Book Of Geb
 * 1.4.2 Scripting with Page Objects
 *
 * http://www.gebish.org/manual/current/intro.html#full_examples
 */
Browser.drive {
    to GoogleHomePage
    assert at(GoogleHomePage)
    search.field.value("wikipedia")
    waitFor { at GoogleResultsPage }
    assert firstResultLink.text() == "Wikipedia"
    firstResultLink.click()
    waitFor { at WikipediaPage }
}.quit()