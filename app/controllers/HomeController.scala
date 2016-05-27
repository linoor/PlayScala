package controllers

import java.util.UUID
import javax.inject._
import dao.ItemDAO
import play.api.libs.ws.WSClient
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (itemDAO: ItemDAO, configuration: play.api.Configuration, ws: WSClient) extends Controller {

  def index = Action { implicit request =>
    val oauth2 = new OAuth2(configuration, ws)
    val callbackUrl = routes.OAuth2.callback(None, None).absoluteURL()
    val scope = "repo"   // github scope - request repo access
    val state = UUID.randomUUID().toString  // random confirmation string
    val redirectUrl = oauth2.getAuthorizationUrl(callbackUrl, scope, state)
    Ok(views.html.index(redirectUrl)).
      withSession("oauth-state" -> state)
  }

}
