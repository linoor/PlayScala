package models

/**
  * Created by linoor on 4/17/16.
  */
case class Item(id: Long, name: String, description: String, price: Int)

object Items {

  var items: Seq[Item] = Seq()

  def add(user: Item): String = {
    items = items :+ user.copy(id = items.length)
    "Item successfully added"
  }

  def delete(id: Long): Option[Int] = {
    val originalSize = items.length
    items = items.filterNot(_.id == id)
    Some(originalSize - items.length)
  }

  def get(id: Long): Option[Item] = items.find(_.id == id)

  def listAll: Seq[Item] = items

}
