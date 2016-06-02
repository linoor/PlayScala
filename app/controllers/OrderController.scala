package controllers

import javax.inject.Inject

import dao.{OrderDAO, CartEntryDAO}
import models.{User, Order, CartEntry}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class OrderController @Inject()(orderDAO: OrderDAO) extends Controller {

  def index = Action { implicit request => {
    Ok(views.html.order()).withSession(request.session)
  }
  }

  implicit val orderFormat = Json.format[Order]

  def addOrder() = Action { request => {
    request.body.asJson.map {
      json => {
        val userId = (json \ "userId").as[Long]
        val itemName = (json \ "itemname").as[String]
        val name = (json \ "name").as[String]
        val address = (json \ "address").as[String]
        val postcode = (json \ "postcode").as[String]
        val comments = (json \ "comments").as[String]

        val orders = Await.result(orderDAO.all(), Duration.Inf)
        orderDAO.insert(Order(orders.size, userId, itemName, name, address, postcode, comments))
        Ok("Order has been created")
      }
    }.getOrElse {
      BadRequest("Expecting application/json request body: ")
    }
    }
  }

//  def addToCart(userId: Long, itemName: String) = Action.async {
//    cartEntryDAO.all().map(entries => cartEntryDAO.insert(CartEntry(entries.size, userId, itemName, paid=false)))
//      .map(i => Ok("the item has been added to the cart")).recover {
//      case ex: Throwable => Ok("Error WTF!?")
//    }
//  }
//
//  def removeFromCart(userId: Long, itemName: String) = Action.async {
//    cartEntryDAO.delete(userId, itemName).map(i => Ok("The item has been removed")).recover {
//      case ex: Throwable => Ok("Error WTF!?")
//    }
//  }
//
  def getAllOrders = Action.async {
    orderDAO.all().map(orders => Ok(Json.toJson(orders)))
  }
}
