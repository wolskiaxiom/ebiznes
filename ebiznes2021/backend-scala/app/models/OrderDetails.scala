package models

import play.api.libs.json.Json

case class OrderDetails
(id: Long,
 orderId: Long,
 itemId: Int,
 itemCategory: String
)

object OrderDetails {
  implicit val orderFormat = Json.format[OrderDetails]
}