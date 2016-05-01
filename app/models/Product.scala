package models

/**
  * Created by linoor on 4/17/16.
  */
case class Product(id: Long, name: String, description: String, price: Int)

object Product {

  def all(): List[Product] = Nil

  def create(name: String, description: String) {}

  def delete(id: Long) {}

}
