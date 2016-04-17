package models

/**
  * Created by linoor on 4/17/16.
  */
case class Item(id: Long, shortName: String, description: String)

object Item {

  def all(): List[Item] = Nil

  def create(shortName: String, description: String) {}

  def delete(id: Long) {}

}
