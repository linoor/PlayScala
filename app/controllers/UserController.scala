package controllers

import models.{User, Users}
import play.api.libs.json.{Json, JsError}
import play.api.mvc.{Action, AnyContent, Controller}


class UserController extends Controller {

  def index: Action[AnyContent] = Action { implicit request =>
    val users = Users.listAll
    Ok(views.html.index())
  }

  def addUser() = Action { implicit request =>
    request.body.asJson.map {
      json => {
        val email = (json \ "email").as[String]
        val fullName = (json \ "fullname").as[String]
        val password = (json \ "password").as[String]
        val newUser = User(0, email, password, fullName, isAdmin = false)
        Users.add(newUser)
        Ok("User has been created")
      }
    }.getOrElse {
      BadRequest("Expecting application/json request body")
    }
  }

  def listUsers() = Action { implicit request =>
    Ok(Json.toJson(Users.listAll.map({ u =>
      (u.email, u.fullName)
    }).toMap))
  }
}