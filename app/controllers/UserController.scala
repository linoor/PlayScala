package controllers

import javax.inject.Inject

import dao.UserDAO
import models.User
import play.api.libs.json.{Json, JsError}
import play.api.mvc.{Result, Action, AnyContent, Controller}
import scala.concurrent.ExecutionContext.Implicits.global


class UserController @Inject()(userDAO: UserDAO) extends Controller {

  def deleteUser(id: Long) = Action { implicit request =>
    Ok("asd")
  }

  def addUser() = Action { implicit request =>
    request.body.asJson.map {
      json => {
        val email = (json \ "email").as[String]
        val fullName = (json \ "fullname").as[String]
        val password = (json \ "password").as[String]
        var result: Result = Ok("")
        userDAO.all().onComplete(
          users => {
            if (users.get.exists(_.email == email)) {
              result = Ok("This email address already exists")
            } else {
              val newUser = User(users.get.length, email, password, fullName, isAdmin = false)
              userDAO.insert(newUser)
              result = Ok("User has been created")
            }
          }
        )
        result
      }
    }.getOrElse {
      BadRequest("Expecting application/json request body: ")
    }
  }

  def listUsers() = Action { implicit request =>
    var result = Ok("Error")
    userDAO.all().onComplete({
      users => {
        result = Ok(Json.toJson(users.get.map { u =>
            (u.id.toString, u.email)
          } toMap))
      }
    })
    result
  }

  def get(id: Long) = Action { implicit request =>
    var result = Ok("")
    userDAO.all().onComplete(users => {
      if (users.get.exists(_.id == id)) {
       result = Ok(Json.toJson(users.get.filter(_.id == id).map { u =>
           (u.id.toString, u.email)
         } toMap))
      } else {
        result = Ok("This user does not exist")
      }
    })
    result
  }
}
