package models

/**
  * Created by linoor on 5/1/16.
  */
case class User(id: Long, email: String, password: String, fullName: String, isAdmin: Boolean)

object User {

  def all(): List[User] = Nil

  def create(email: String, password: String, fullName: String, isAdmin: Boolean): Unit = {}

  def delete(id: Long): Unit = {

  }
}
