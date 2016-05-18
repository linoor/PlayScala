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

  def findById(id: Long): Future[Option[Product]] = db.run(CartEntries.filter(_.id === id).result.headOption)

  def update(id: Long, cartEntry: CartEntry): Future[Unit] = {
    val cartEntryToUpdate: CartEntry = cartEntry.copy(id)
    db.run(CartEntries.filter(_.id === id).update(cartEntryToUpdate)).map(_ => ())
  }

  def cartEntriesForUser(user: User): Future[Seq[CartEntry]] = db.run(CartEntries.result)

  private class CartsTable(tag: Tag) extends Table[CartEntry](tag, "CART") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def userId = column[Long]("USER_ID")
    def itemName = column[String]("ITEM_ID")
    def paid = column[Boolean]("PAID")

    def * = (id, userId, itemName, paid) <> (CartEntry.tupled, CartEntry.unapply _)
  }
}

