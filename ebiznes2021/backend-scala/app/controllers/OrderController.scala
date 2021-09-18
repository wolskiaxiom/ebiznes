package controllers

import models.{Order, OrderRepository}

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderController @Inject()(ordersRepository: OrderRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createOrderForm: Form[CreateOrderForm] = Form {
    mapping(
      "customerEmail" -> nonEmptyText,
      "customerNick" -> nonEmptyText,
      "customerAddress1" -> nonEmptyText,
      "customerAddress2" -> nonEmptyText,
      "customerCity" -> nonEmptyText,
      "customerZipcode" -> nonEmptyText,
      "totalPrice" -> number,
      "comments" -> nonEmptyText
    )(CreateOrderForm.apply)(CreateOrderForm.unapply)
  }

  def addOrder: Action[AnyContent] = Action.async { implicit request =>
    createOrderForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      order => {
        ordersRepository.create(order.customerEmail, order.customerNick, order.customerAddress1, order.customerAddress2, order.customerCity, order.customerZipcode, order.totalPrice, order.comments).map { _ =>
          Redirect(routes.OrderController.getOrders()).flashing("success" -> "product.created")
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

case class CreateOrderForm(customerEmail: String, customerNick: String, customerAddress1: String, customerAddress2: String, customerCity: String, customerZipcode: String, totalPrice: Int, comments: String)
//case class UpdateDogForm(id: Long, name: String, nickname: String, age: Int, price: BigDecimal)
