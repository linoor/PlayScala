package dao

import javax.inject.Inject

import models.User
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

/**
  * Created by linoor on 5/14/16.
  */
class UserDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val Users = TableQuery[UsersTable]

  def all(): Future[Seq[User]] = db.run(Users.result)

  def insert(user: User): Future[Unit] = db.run(Users += user).map { _ => () }

  def delete(id: Long): Future[Unit] = db.run(Users.filter(_.id === id).delete).map {_ => ()}

  def findById(id: Long): Future[Option[User]] = db.run(Users.filter(_.id === id).result.headOption)

  def findByName(name: String): Future[Option[User]] = db.run(Users.filter(_.fullname === name).result.headOption)

  def update(id: Long, user: User): Future[Unit] = {
    val userToUpdate = user.copy(id)
    db.run(Users.filter(_.id === id).update(userToUpdate).map(_ => ()))
  }

  private class UsersTable(tag: Tag) extends Table[User](tag, "USER") {
    def id = column[Long]("ID", O.PrimaryKey)

    def email = column[String]("EMAIL")

    def password = column[String]("PASSWORD")

    def fullname = column[String]("FULLNAME")

    def isAdmin = column[Boolean]("ISADMIN")

    def * = (id, email, password, fullname, isAdmin) <> (User.tupled, User.unapply _)
  }
}
