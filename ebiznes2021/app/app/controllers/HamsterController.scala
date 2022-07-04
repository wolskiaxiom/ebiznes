package controllers

import models.{Hamster, HamsterRepository}

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HamstersController @Inject()(hamstersRepository: HamsterRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createHamsterForm: Form[CreateHamsterForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(CreateHamsterForm.apply)(CreateHamsterForm.unapply)
  }

  val updateHamsterForm: Form[UpdateHamsterForm] = Form {
    mapping(
      "id" -> longNumber,
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(UpdateHamsterForm.apply)(UpdateHamsterForm.unapply)
  }

  def addHamster: Action[AnyContent] = Action.async { implicit request =>
    createHamsterForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      hamster => {
        hamstersRepository.create(hamster.name, hamster.nickname, hamster.age, hamster.price).map { _ =>
          Redirect(routes.HamstersController.getHamsters).flashing("success" -> "product.created")
        }
      }
    )
  }

  def getHamsters: Action[AnyContent] = Action.async { implicit request =>
    hamstersRepository.list().map {
      hamster => Ok(Json.toJson(hamster))
    }
  }

  def updateHamster(id: Long): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    updateHamsterForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      hamster => {
        hamstersRepository.update(hamster.id, Hamster(hamster.id, hamster.name, hamster.nickname, hamster.age, hamster.price)).map { _ =>
          Redirect(routes.HamstersController.getHamsters).flashing("success" -> "hamster updated")
        }
      }
    )
  }

  def delete(id: Long): Action[AnyContent] = Action {
    hamstersRepository.delete(id)
    Redirect("/hamsters")
  }


}

case class CreateHamsterForm(name: String, nickname: String, age: Int, price: BigDecimal)
case class UpdateHamsterForm(id: Long, name: String, nickname: String, age: Int, price: BigDecimal)
