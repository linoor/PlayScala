package controllers

//import models.{Item, Items}
import javax.inject.Inject

import dao.ItemDAO
import models.Item
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms.text
import play.api.data.Forms.number
import play.api.mvc._

class ItemController @Inject() (itemDAO: ItemDAO) extends Controller {
 val itemForm  = Form(
   mapping(
     "name" -> text(),
     "description" -> text(),
     "price" -> number
   )(Item.apply)(Item.unapply)
 )

  def createitem = Action {
    Ok(views.html.createitem.render())
  }

  def newitem = Action { implicit request =>
    val item: models.Item = itemForm.bindFromRequest().get
    itemDAO.insert(item)
    Ok(views.html.newitem.render(item))
  }

}
