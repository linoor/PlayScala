package controllers

import javax.inject.Inject

import dao.CartEntryDAO
import models.CartEntry
import play.api.mvc.{Action, Controller}

import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
  * Created by linoor on 5/18/16.
  */
class CartEntryController @Inject()(cartEntryDAO: CartEntryDAO) extends Controller {
  def addToCart(userId: Long, itemName: String) = Action.async {
    cartEntryDAO.insert(CartEntry(0, userId, itemName, paid=false))
      .map(i => Ok("the item has been added to the cart"))
  }
}
