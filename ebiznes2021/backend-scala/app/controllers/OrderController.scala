package controllers

import models.{Order, OrderRepository}

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import com.mohiva.play.silhouette.api.actions.SecuredRequest

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderController @Inject()
(ordersRepository: OrderRepository,
 scc: SilhouetteControllerComponents
)(implicit ec: ExecutionContext) extends SilhouetteController(scc) {

  val createOrderForm: Form[CreateOrderForm] = Form {
    mapping(
      "customer_nick" -> nonEmptyText,
      "customer_address1" -> nonEmptyText,
      "customer_city" -> nonEmptyText,
      "customer_zipcode" -> nonEmptyText,
      "total_price" -> number,
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
        ordersRepository.create(request.identity.email.get, order.customerNick, order.customerAddress1, order.customerCity, order.customerZipcode, order.totalPrice).map { _ =>
          Ok
        }
      }
    )
  }

  def getOrders: Action[AnyContent] = Action.async { implicit request =>
    ordersRepository.list().map {
      order => Ok(Json.toJson(order))
    }
  }

}

case class CreateOrderForm(customerNick: String, customerAddress1: String, customerCity: String, customerZipcode: String, totalPrice: Int)
