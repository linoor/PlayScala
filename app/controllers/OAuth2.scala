package controllers

import javax.inject.Inject

import play.api.http.{HeaderNames, MimeTypes}
import play.api.libs.ws.WS
import play.api.mvc.{Action, Controller, Results}
import play.api.{Application, Play}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class OAuth2 @Inject() (configuration: play.api.Configuration) extends Controller {
  lazy val githubAuthId = configuration.getString("github.client.id").get
  lazy val githubAuthSecret = configuration.getString("github.client.secret").get
  lazy val oauth2 = new OAuth2(configuration)

  def getAuthorizationUrl(redirectUri: String, scope: String, state: String): String = {
    val baseUrl = configuration.getString("github.redirect.url").get
    baseUrl.format(githubAuthId, redirectUri, scope, state)
  }

  def getToken(code: String): Future[String] = {
    val tokenResponse = WS.url("https://github.com/login/oauth/access_token")(Play.current).
      withQueryString("client_id" -> githubAuthId,
        "client_secret" -> githubAuthSecret,
        "code" -> code).
      withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON).
      post(Results.EmptyContent())

    tokenResponse.flatMap { response =>
      (response.json \ "access_token").asOpt[String].fold(Future.failed[String](new IllegalStateException("Sod off!"))) { accessToken =>
        Future.successful(accessToken)
      }
    }
  }

  def callback(codeOpt: Option[String] = None, stateOpt: Option[String] = None) = Action.async { implicit request =>
    (for {
      code <- codeOpt
      state <- stateOpt
      oauthState <- request.session.get("oauth-state")
    } yield {
      if (state == oauthState) {
        oauth2.getToken(code).map { accessToken =>
          Redirect(routes.OAuth2.success()).withSession("oauth-token" -> accessToken)
        }.recover {
          case ex: IllegalStateException => Unauthorized(ex.getMessage)
        }
      }
      else {
        Future.successful(BadRequest("Invalid github login"))
      }
    }).getOrElse(Future.successful(BadRequest("No parameters supplied")))
  }

  def success() = Action.async { request =>
    implicit val app = Play.current
    request.session.get("oauth-token").fold(Future.successful(Unauthorized("No way Jose"))) { authToken =>
      WS.url("https://api.github.com/user/repos").
        withHeaders(HeaderNames.AUTHORIZATION -> s"token $authToken").
        get().map { response =>
        Ok(response.json)
      }
    }
  }
}

object OAuth2 extends Controller {

}