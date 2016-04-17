package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class CartController @Inject() extends Controller {

  def show(id: Long) = Action {
    Ok(views.html.cart())
  }

  def add(id: Long) = play.mvc.Results.TODO

  def get(id: Long) = play.mvc.Results.TODO

  def change(id: Long) = play.mvc.Results.TODO
}