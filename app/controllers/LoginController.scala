package controllers

import javax.inject._
import play.api.mvc._

/**
  * Created by linoor on 5/24/16.
  */
@Singleton
class LoginController @Inject() extends Controller {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    Ok(views.html.login())
  }

}

