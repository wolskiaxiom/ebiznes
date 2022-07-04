package models

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderRepository  @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  private class OrderTable(tag: Tag) extends Table[Order](tag, "orders") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def customerEmail = column[String]("customerEmail")
    def customerNick = column[String]("customerNick")
    def customerAddress1 = column[String]("customerAddress1")
    def customerAddress2 = column[String]("customerAddress2")
    def customerCity = column[String]("customerCity")
    def customerZipcode = column[String]("customerZipcode")
    def totalPrice = column[Int]("totalPrice")
    def comments = column[String]("comments")

    def * = (id, customerEmail, customerNick, customerAddress1, customerCity, customerZipcode, totalPrice) <> ((Order.apply _).tupled, Order.unapply)
  }

  private val order = TableQuery[OrderTable]
  def create(customerEmail: String,
             customerNick: String,
             customerAddress1: String,
             customerCity: String,
             customerZipcode: String,
             totalPrice:Int): Future[Order] = db.run {
    (order.map(a => (a.customerEmail, a.customerNick, a.customerAddress1, a.customerCity, a.customerZipcode, a.totalPrice))
      returning order.map(_.id)
      into {case ((customerEmail, customerNick, customerAddress1, customerCity, customerZipcode, totalPrice), id) => Order(id, customerEmail, customerNick, customerAddress1, customerCity, customerZipcode, totalPrice)}
      ) += (customerEmail, customerNick, customerAddress1, customerCity, customerZipcode, totalPrice)
  }

  def list(): Future[Seq[Order]] = db.run {
    order.result
  }
}
