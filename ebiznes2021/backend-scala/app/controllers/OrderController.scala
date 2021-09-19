package controllers

import models.{Order, OrderRepository}

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import com.mohiva.play.silhouette.api.actions.SecuredRequest
import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.api.util.Credentials
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import javax.inject.Inject
import play.api.i18n.Lang
import play.api.libs.json.{ JsString, Json }
import play.api.mvc._
import utils.auth.{ JWTEnvironment, WithProvider }

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderController @Inject()
(ordersRepository: OrderRepository,
 scc: SilhouetteControllerComponents
)(implicit ec: ExecutionContext) extends SilhouetteController(scc) {

  val createOrderForm: Form[CreateOrderForm] = Form {
    mapping(
      "customer_email" -> nonEmptyText,
      "customer_nick" -> nonEmptyText,
      "customer_address1" -> nonEmptyText,
      "customer_address2" -> nonEmptyText,
      "customer_city" -> nonEmptyText,
      "customer_zipcode" -> nonEmptyText,
      "total_price" -> number,
      "comments" -> nonEmptyText
    )(CreateOrderForm.apply)(CreateOrderForm.unapply)
  }
  case class CreateOrderModel(customerEmail: String,
                              customerNick: String,
                              customerAddress1: String,
                              customerAddress2: String,
                              customerCity: String,
                              customerZipcode: String,
                              totalPrice: Int,
                              comments: String)
  implicit val createOrderFormat = Json.format[CreateOrderModel]

  def addOrder = securedAction(WithProvider[AuthType](CredentialsProvider.ID)).async {
    implicit request: SecuredRequest[JWTEnvironment, AnyContent] =>
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
