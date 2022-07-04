package controllers

import models.{Cat, CatRepository}

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CatsController @Inject()(catsRepository: CatRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createCatForm: Form[CreateCatForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(CreateCatForm.apply)(CreateCatForm.unapply)
  }

  val updateCatForm: Form[UpdateCatForm] = Form {
    mapping(
      "id" -> longNumber,
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(UpdateCatForm.apply)(UpdateCatForm.unapply)
  }

  def addCat: Action[AnyContent] = Action.async { implicit request =>
    createCatForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      cat => {
        catsRepository.create(cat.name, cat.nickname, cat.age, cat.price).map { _ =>
          Redirect(routes.CatsController.getCats).flashing("success" -> "product.created")
        }
      }
    )
  }

  def getCats: Action[AnyContent] = Action.async { implicit request =>
    catsRepository.list().map {
      cat => Ok(Json.toJson(cat))
    }
  }

  def updateCat(id: Long): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    updateCatForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      cat => {
        catsRepository.update(cat.id, Cat(cat.id, cat.name, cat.nickname, cat.age, cat.price)).map { _ =>
          Redirect(routes.CatsController.getCats).flashing("success" -> "cat updated")
        }
      }
    )
  }

  def delete(id: Long): Action[AnyContent] = Action {
    catsRepository.delete(id)
    Redirect("/cats")
  }


}

case class CreateCatForm(name: String, nickname: String, age: Int, price: BigDecimal)
case class UpdateCatForm(id: Long, name: String, nickname: String, age: Int, price: BigDecimal)
