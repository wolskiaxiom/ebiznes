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

    def * = (id, customerEmail, customerNick, customerAddress1, customerAddress2, customerCity, customerZipcode, totalPrice, comments) <> ((Order.apply _).tupled, Order.unapply)
  }

  private val order = TableQuery[OrderTable]
  def create(customerEmail: String,
             customerNick: String,
             customerAddress1: String,
             customerAddress2: String,
             customerCity: String,
             customerZipcode: String,
             totalPrice:Int,
             comments: String): Future[Order] = db.run {
    (order.map(a => (a.customerEmail, a.customerNick, a.customerAddress1, a.customerAddress2, a.customerCity, a.customerZipcode, a.totalPrice, a.comments))
      returning order.map(_.id)
      into {case ((customerEmail, customerNick, customerAddress1, customerAddress2, customerCity, customerZipcode, totalPrice, comments), id) => Order(id, customerEmail, customerNick, customerAddress1, customerAddress2, customerCity, customerZipcode, totalPrice, comments)}
      ) += (customerEmail, customerNick, customerAddress1, customerAddress2, customerCity, customerZipcode, totalPrice, comments)
  }

  def list(): Future[Seq[Order]] = db.run {
    order.result
  }
}
