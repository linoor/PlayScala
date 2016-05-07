package controllers

import models.{Item, Items}
import play.api.mvc.{Action, AnyContent, Controller}
import play.api.libs.json._
import play.api.libs.functional.syntax._

class ItemController extends Controller {

  implicit val itemWrites: Writes[Item] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "name").write[String] and
    (JsPath \ "description").write[String] and
    (JsPath \ "price").write[Int]
  )(unlift(Item.unapply))

  def index: Action[AnyContent] = Action { implicit request =>
    val items = Items.listAll
    Ok(views.html.index())
  }

  def delete(id: Long) = Action { implicit request =>
    if (Items.listAll.exists(_.id == id)) {
      Items.delete(id)
      Ok("The user has been removed")
    } else {
      Ok("There has been a problem processing your request")
    }
  }

  def add() = Action { implicit request =>
    request.body.asJson.map {
      json => {
        val name = (json \ "name").as[String]
        val description = (json \ "description").as[String]
        val price = (json \ "price").as[Int]
        val newItem = Item(0, name, description, price)
        Items.add(newItem)
        Ok("The item has been added")
      }
    }.getOrElse {
      BadRequest("Expecting application/json request body")
    }
  }

  def listItems() = Action { implicit request =>
    Ok(Json.toJson(Items.listAll))
  }

  def get(id: Long) = Action { implicit request =>
    if (Items.listAll.exists(_.id == id)) {
      Ok(Json.toJson(Items.get(id)))
    } else {
      Ok("This item does not exist")
    }
  }
}