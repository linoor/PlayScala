package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class CartController @Inject() extends Controller {
  def index()  = Action {
    Ok(views.html.cart())
  }
}