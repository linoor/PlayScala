import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

  "HomeController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Allugro")
    }

  }

  "CartController" should {

    "render the cart page" in {
      val home = route(app, FakeRequest(GET, "/cart")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Pay now")
    }

  }

  "CartController" should {

    "render the add new item page" in {
      val home = route(app, FakeRequest(GET, "/createitem")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Product name")
      contentAsString(home) must include ("Category")
    }

  }

  "CartController" should {

    "render the remove item page" in {
      val home = route(app, FakeRequest(GET, "/removeitem")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Product name")
      contentAsString(home) must include ("Remove")
    }

  }
}
