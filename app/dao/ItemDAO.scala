package dao

import javax.inject.Inject

import models.Item
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

/**
  * Created by linoor on 5/10/16.
  */
class ItemDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val Items = TableQuery[ItemsTable]

  def all(): Future[Seq[Item]] = db.run(Items.result)

  def insert(item: Item): Future[Unit] = db.run(Items += item).map { _ => () }

  def delete(name: String): Future[Unit] = db.run(Items.filter(_.name === name).delete).map(_ => ())

  def findById(name: String): Future[Option[Product]] = db.run(Items.filter(_.name === name).result.headOption)

  def update(name: String, item: Item): Future[Unit] = {
    val itemToUpdate: Item = item.copy(name)
    db.run(Items.filter(_.name === name).update(itemToUpdate)).map(_ => ())
  }

  private class ItemsTable(tag: Tag) extends Table[Item](tag, "ITEM") {
    def name = column[String]("NAME", O.PrimaryKey)
    def description = column[String]("DESCRIPTION")
    def price = column[Int]("PRICE")
    def category = column[String]("CATEGORY")

    def * = (name, description, price, category) <> (Item.tupled, Item.unapply _)
  }
}
