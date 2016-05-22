package dao

import javax.inject.Inject

import models.{Item, User, CartEntry}
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

/**
  * Created by linoor on 5/15/16.
  */
class CartEntryDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val CartEntries = TableQuery[CartsTable]

  def all(): Future[Seq[CartEntry]] = db.run(CartEntries.result)

  def insert(cartEntry: CartEntry): Future[Unit] = db.run(CartEntries += cartEntry).map { _ => () }

  def delete(id: Long): Future[Unit] = db.run(CartEntries.filter(_.id === id).delete).map(_ => ())

  def findById(id: Long): Future[Option[CartEntry]] = db.run(CartEntries.filter(_.id === id).result.headOption)

  def getAllForUser(userId: Long): Future[Seq[CartEntry]] = db.run(CartEntries.filter(_.userId === userId).result)

  def delete(userId: Long, itemName: String): Future[Unit] = db.run(CartEntries.filter(_.userId === userId).filter(_.itemName === itemName).delete).map(_ => ())

  def update(id: Long, cartEntry: CartEntry): Future[Unit] = {
    val cartEntryToUpdate: CartEntry = cartEntry.copy(id)
    db.run(CartEntries.filter(_.id === id).update(cartEntryToUpdate)).map(_ => ())
  }

  def cartEntriesForUser(user: User): Future[Seq[CartEntry]] = db.run(CartEntries.result)

  private class CartsTable(tag: Tag) extends Table[CartEntry](tag, "CART_ENTRY") {
    def id = column[Long]("id", O.PrimaryKey)
    def userId = column[Long]("userId")
    def itemName = column[String]("itemName")
    def paid = column[Boolean]("paid")

    def * = (id, userId, itemName, paid) <> (CartEntry.tupled, CartEntry.unapply _)
  }
}

