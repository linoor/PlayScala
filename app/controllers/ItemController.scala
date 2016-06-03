package controllers

//import models.{Item, Items}
import javax.inject.Inject

import dao.ItemDAO
import models.Item
import play.api.data.Form
import play.api.data.Forms.{mapping, single}
import play.api.data.Forms.text
import play.api.data.Forms.number
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

class ItemController @Inject() (itemDAO: ItemDAO) extends Controller {

  implicit val itemFormat = Json.format[Item]

 val itemForm  = Form(
   mapping(
     "name" -> text(),
     "description" -> text(),
     "price" -> number,
     "category" -> text()
   )(Item.apply)(Item.unapply)
 )

  def createitem = Action { implicit request => {
    Ok(views.html.createitem.render(request.session))
    }
  }

  def newitem = Action { implicit request =>
    val item: models.Item = itemForm.bindFromRequest().get
    itemDAO.insert(item)
    Ok(views.html.newitem.render(item, request.session))
  }

  def listItems = Action.async {
    itemDAO.all().map(items => Ok(Json.toJson(items)))
  }

  val itemRemoveForm = Form(
    single(
      "name" -> text()
    )
  )

  def removeitem = Action { implicit request => {
      Ok(views.html.removeitem.render(request.session))
    }
  }

  def itemremoved = Action { implicit request =>  {
      val item: String = itemRemoveForm.bindFromRequest().get
      itemDAO.delete(item)
      Ok(views.html.itemremoved(item)).withSession(request.session)
    }
  }

}
