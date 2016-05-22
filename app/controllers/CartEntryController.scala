package controllers

import javax.inject.Inject

import dao.CartEntryDAO
import models.CartEntry
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
  * Created by linoor on 5/18/16.
  */
class CartEntryController @Inject()(cartEntryDAO: CartEntryDAO) extends Controller {

  implicit val cartEntryFormat = Json.format[CartEntry]

  def addToCart(userId: Long, itemName: String) = Action.async {
    cartEntryDAO.all().map(entries => cartEntryDAO.insert(CartEntry(entries.size, userId, itemName, paid=false)))
      .map(i => Ok("the item has been added to the cart")).recover {
      case ex: Throwable => Ok("Error WTF!?")
    }
  }

  def removeFromCart(userId: Long, itemName: String) = Action.async {
    cartEntryDAO.delete(userId, itemName).map(i => Ok("The item has been removed")).recover {
      case ex: Throwable => Ok("Error WTF!?")
    }
  }

  def getAllForUser(userId: Long) = Action.async {
    cartEntryDAO.getAllForUser(userId).map(entries => Ok(Json.toJson(entries)))
  }
}
