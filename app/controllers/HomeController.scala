package controllers

import javax.inject._
import dao.ItemDAO
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (itemDAO: ItemDAO) extends Controller {

  def index = Action.async {
    itemDAO.all().map(items => Ok(views.html.index(items)))
  }
}
