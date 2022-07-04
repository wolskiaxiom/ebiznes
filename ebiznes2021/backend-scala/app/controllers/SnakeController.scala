package controllers

import models.{Snake, SnakeRepository}

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SnakesController @Inject()(snakesRepository: SnakeRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createSnakeForm: Form[CreateSnakeForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(CreateSnakeForm.apply)(CreateSnakeForm.unapply)
  }

  val updateSnakeForm: Form[UpdateSnakeForm] = Form {
    mapping(
      "id" -> longNumber,
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(UpdateSnakeForm.apply)(UpdateSnakeForm.unapply)
  }

  def addSnake: Action[AnyContent] = Action.async { implicit request =>
    createSnakeForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      snake => {
        snakesRepository.create(snake.name, snake.nickname, snake.age, snake.price).map { _ =>
          Redirect(routes.SnakesController.getSnakes).flashing("success" -> "product.created")
        }
      }
    )
  }

  def getSnakes: Action[AnyContent] = Action.async { implicit request =>
    snakesRepository.list().map {
      snake => Ok(Json.toJson(snake))
    }
  }

  def updateSnake(id: Long): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    updateSnakeForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      snake => {
        snakesRepository.update(snake.id, Snake(snake.id, snake.name, snake.nickname, snake.age, snake.price)).map { _ =>
          Redirect(routes.SnakesController.getSnakes).flashing("success" -> "snake updated")
        }
      }
    )
  }

  def delete(id: Long): Action[AnyContent] = Action {
    snakesRepository.delete(id)
    Redirect("/snakes")
  }


}

case class CreateSnakeForm(name: String, nickname: String, age: Int, price: BigDecimal)
case class UpdateSnakeForm(id: Long, name: String, nickname: String, age: Int, price: BigDecimal)
