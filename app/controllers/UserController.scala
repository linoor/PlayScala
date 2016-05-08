package controllers

import models.{User, Users}
import play.Logger
import play.api.libs.json.{Json, JsError}
import play.api.mvc.{Action, AnyContent, Controller}


class UserController extends Controller {

  def index: Action[AnyContent] = Action { implicit request =>
    val users = Users.listAll
    Ok(views.html.index())
  }

  def deleteUser(id: Long) = Action { implicit request =>
    if (Users.listAll.exists(_.id == id)) {
      Users.delete(id)
      Ok("The user has been removed")
    } else {
      Ok("There has been a problem processing your request")
    }
  }

  def addUser() = Action { implicit request =>
    request.body.asJson.map {
      json => {
        val email = (json \ "email").as[String]
        val fullName = (json \ "fullname").as[String]
        val password = (json \ "password").as[String]
        val newUser = User(0, email, password, fullName, isAdmin = false)
        if (Users.listAll.exists(_.email == email)) {
          Ok("This email address already exists")
        } else {
          Users.add(newUser)
          Ok("User has been created")
        }
      }
    }.getOrElse {
      BadRequest("Expecting application/json request body: ")
    }
  }

  def listUsers() = Action { implicit request =>
    Ok(Json.toJson(Users.listAll.map { u =>
      (u.id.toString, u.email)
    } toMap))
  }

  def get(id: Long) = Action { implicit request =>
    if (Users.listAll.exists(_.id == id)) {
      Ok(Json.toJson(Users.listAll.filter(_.id == id).map { u =>
        (u.id.toString, u.email)
      } toMap))
    } else {
      Ok("This user does not exist")
    }
  }
}
