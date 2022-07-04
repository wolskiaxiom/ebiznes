package controllers

import models.{Order, OrderRepository, OrderDetailsRepository}

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import com.mohiva.play.silhouette.api.actions.SecuredRequest

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success
import scala.util.Failure

@Singleton
class OrderController @Inject()
(ordersRepository: OrderRepository,
 orderDetailsRepository: OrderDetailsRepository,
 scc: SilhouetteControllerComponents
)(implicit ec: ExecutionContext) extends SilhouetteController(scc) {

  val createOrderForm: Form[CreateOrderForm] = Form {
    mapping(
      "customer_nick" -> nonEmptyText,
      "customer_address1" -> nonEmptyText,
      "customer_city" -> nonEmptyText,
      "customer_zipcode" -> nonEmptyText,
      "total_price" -> number,
      "order_items" -> seq(
        mapping(
          "item_id" -> number,
          "item_cat" -> nonEmptyText
        )(CreateOrderDetailsForm.apply)(CreateOrderDetailsForm.unapply)
      )
    )(CreateOrderForm.apply)(CreateOrderForm.unapply)
  }
  case class CreateOrderModel(customerNick: String,
                              customerAddress1: String,
                              customerCity: String,
                              customerZipcode: String,
                              totalPrice: Int)
  implicit val createOrderFormat = Json.format[CreateOrderModel]

  def addOrder = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    createOrderForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      order => {
        val orderId = ordersRepository.create(request.identity.email.get, order.customerNick, order.customerAddress1, order.customerCity, order.customerZipcode, order.totalPrice)
        orderId.onComplete({
          case Success(a) => {
            for (createOrderDetailsForm <- order.orderItems) {
              orderDetailsRepository.create(a.id, createOrderDetailsForm.itemId, createOrderDetailsForm.itemCat)
            }
          }
          case Failure(exception) => {
            Future.successful(
              BadRequest
            )
          }
        })
        Future.successful(
          Ok
        )
      }
    )
  }

  def getOrders: Action[AnyContent] = Action.async { implicit request =>
    ordersRepository.list().map {
      order => Ok(Json.toJson(order))
    }
  }

  def getDetails: Action[AnyContent] = Action.async { implicit request =>
    orderDetailsRepository.list().map {
      orderDetails => Ok(Json.toJson(orderDetails))
    }
  }

}

case class CreateOrderForm(customerNick: String, customerAddress1: String, customerCity: String, customerZipcode: String, totalPrice: Int, orderItems: Seq[CreateOrderDetailsForm])
case class CreateOrderDetailsForm(itemId: Int, itemCat: String)