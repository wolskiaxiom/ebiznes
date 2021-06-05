package controllers

import models.{Pig, PigRepository}

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PigsController @Inject()(pigsRepository: PigRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createPigForm: Form[CreatePigForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(CreatePigForm.apply)(CreatePigForm.unapply)
  }

  val updatePigForm: Form[UpdatePigForm] = Form {
    mapping(
      "id" -> longNumber,
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(UpdatePigForm.apply)(UpdatePigForm.unapply)
  }

  def addPig: Action[AnyContent] = Action.async { implicit request =>
    createPigForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      pig => {
        pigsRepository.create(pig.name, pig.nickname, pig.age, pig.price).map { _ =>
          Redirect(routes.PigsController.getPigs()).flashing("success" -> "product.created")
        }
      }
    )
  }

  def getPigs: Action[AnyContent] = Action.async { implicit request =>
    pigsRepository.list().map {
      pig => Ok(Json.toJson(pig))
    }
  }

  def updatePig(id: Long): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    updatePigForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      pig => {
        pigsRepository.update(pig.id, Pig(pig.id, pig.name, pig.nickname, pig.age, pig.price)).map { _ =>
          Redirect(routes.PigsController.getPigs()).flashing("success" -> "pig updated")
        }
      }
    )
  }

  def delete(id: Long): Action[AnyContent] = Action {
    pigsRepository.delete(id)
    Redirect("/pigs")
  }


}

case class CreatePigForm(name: String, nickname: String, age: Int, price: BigDecimal)
case class UpdatePigForm(id: Long, name: String, nickname: String, age: Int, price: BigDecimal)
