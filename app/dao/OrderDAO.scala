package dao

import javax.inject.Inject

import models.{Order, User}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.Future

/**
  * Created by linoor on 5/15/16.
  */
class OrderDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val Orders = TableQuery[OrderTable]

  def all(): Future[Seq[Order]] = db.run(Orders.result)

  def insert(order: Order): Future[Unit] = db.run(Orders += order).map { _ => () }

  def delete(id: Long): Future[Unit] = db.run(Orders.filter(_.id === id).delete).map(_ => ())

  def findById(id: Long): Future[Option[Order]] = db.run(Orders.filter(_.id === id).result.headOption)

  def getAllForUser(userId: Long): Future[Seq[Order]] = db.run(Orders.filter(_.userId === userId).result)

  def delete(userId: Long, itemName: String): Future[Unit] = db.run(Orders.filter(_.userId === userId).filter(_.itemName === itemName).delete).map(_ => ())

  def update(id: Long, order: Order): Future[Unit] = {
    val orderToUpdate: Order = order.copy(id)
    db.run(Orders.filter(_.id === id).update(orderToUpdate)).map(_ => ())
  }

  def cartEntriesForUser(user: User): Future[Seq[Order]] = db.run(Orders.result)

  private class OrderTable(tag: Tag) extends Table[Order](tag, "ORDER") {
    def id = column[Long]("id", O.PrimaryKey)
    def userId = column[Long]("userId")
    def itemName = column[String]("itemName")
    def name = column[String]("name")
    def address = column[String]("address")
    def postcode = column[String]("postcode")
    def comments = column[String]("comments")

    def * = (id, userId, itemName, name, address, postcode, comments) <> (Order.tupled, Order.unapply _)
  }
}

