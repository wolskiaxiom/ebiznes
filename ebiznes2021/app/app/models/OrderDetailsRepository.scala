package models

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderDetailsRepository  @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  private class OrderDetailsTable(tag: Tag) extends Table[OrderDetails](tag, "order_details") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def orderId = column[Long]("orderId")
    def itemId = column[Int]("itemId")
    def itemCategory = column[String]("itemCategory")

    def * = (id, orderId, itemId, itemCategory) <> ((OrderDetails.apply _).tupled, OrderDetails.unapply)
  }

  private val orderDetails = TableQuery[OrderDetailsTable]
  def create(orderId: Long,
             itemId: Int,
             itemCategory: String): Future[OrderDetails] = db.run {
    (orderDetails.map(a => (a.orderId, a.itemId, a.itemCategory))
      returning orderDetails.map(_.id)
      into {case ((orderId, itemId, itemCategory), id) => OrderDetails(id, orderId, itemId, itemCategory)}
      ) += (orderId, itemId, itemCategory)
  }

  def list(): Future[Seq[OrderDetails]] = db.run {
    orderDetails.result
  }
}
