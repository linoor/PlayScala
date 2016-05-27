package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class CartController @Inject() extends Controller {
  def index()  = Action { implicit request => {
    Ok(views.html.cart()).withSession(request.session)
  }
  }
}