package controllers

import models.{Parrot, ParrotRepository}

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ParrotsController @Inject()(parrotsRepository: ParrotRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createParrotForm: Form[CreateParrotForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(CreateParrotForm.apply)(CreateParrotForm.unapply)
  }

  val updateParrotForm: Form[UpdateParrotForm] = Form {
    mapping(
      "id" -> longNumber,
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(UpdateParrotForm.apply)(UpdateParrotForm.unapply)
  }

  def addParrot: Action[AnyContent] = Action.async { implicit request =>
    createParrotForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      parrot => {
        parrotsRepository.create(parrot.name, parrot.nickname, parrot.age, parrot.price).map { _ =>
          Redirect(routes.ParrotsController.getParrots).flashing("success" -> "product.created")
        }
      }
    )
  }

  def getParrots: Action[AnyContent] = Action.async { implicit request =>
    parrotsRepository.list().map {
      parrot => Ok(Json.toJson(parrot))
    }
  }

  def updateParrot(id: Long): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    updateParrotForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      parrot => {
        parrotsRepository.update(parrot.id, Parrot(parrot.id, parrot.name, parrot.nickname, parrot.age, parrot.price)).map { _ =>
          Redirect(routes.ParrotsController.getParrots).flashing("success" -> "parrot updated")
        }
      }
    )
  }

  def delete(id: Long): Action[AnyContent] = Action {
    parrotsRepository.delete(id)
    Redirect("/parrots")
  }


}

case class CreateParrotForm(name: String, nickname: String, age: Int, price: BigDecimal)
case class UpdateParrotForm(id: Long, name: String, nickname: String, age: Int, price: BigDecimal)
