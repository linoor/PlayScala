import org.scalatestplus.play._
import play.test.WithBrowser

import play.api.test._
import play.api.test.Helpers._
import pom.Products

import scala.language.postfixOps

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
class IntegrationSpec extends PlaySpec with OneServerPerTest with OneBrowserPerTest with HtmlUnitFactory with Products {

  "Application" should {

    "work from within a browser" in {

      go to ("http://localhost:" + port)

      pageSource must include ("Allugro")
    }
  }

  "Application" should {

    "render some items" in new WithBrowser {

      browser.goTo("http://localhost:" + port)
      val productsSize = browser.$(".item").size()
      productsSize must equal(4)
    }
  }

  "Application" should {

    "click on add items" in new WithBrowser {

      browser.goTo("http://localhost:" + port)
      val products = browser.$(".item").click()
    }
  }
  "Application" should {

    "click on add items" in new WithBrowser {

      browser.goTo("http://localhost:" + port)
      val products = browser.$(".item").click()
    }
  }
  "Application" should {

    "check if the cart is working" in new WithBrowser {

      browser.goTo("http://localhost:" + port + "/cart")
      val products = browser.$(".item").size()
      products must equal(0)
    }
  }
  "Application" should {

    "click on add item" in new WithBrowser {

      browser.goTo("http://localhost:" + port + "/newitem")
    }
  }
  "Application" should {

    "remove item" in new WithBrowser {

      browser.goTo("http://localhost:" + port)
      val products = browser.$(".item").click()
    }
  }
}
