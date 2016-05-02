package models

/**
  * Created by linoor on 5/1/16.
  */
case class User(id: Long, email: String, password: String, fullName: String, isAdmin: Boolean)

object Users {

  var users: Seq[User] = Seq()

  def add(user: User): String = {
    users = users :+ user.copy(id = users.length)
    "User successfully added"
  }

  def delete(id: Long): Option[Int] = {
    val originalSize = users.length
    users = users.filterNot(_.id == id)
    Some(originalSize - users.length)
  }

  def get(id: Long): Option[User] = users.find(_.id == id)

  def listAll: Seq[User] = users
}
