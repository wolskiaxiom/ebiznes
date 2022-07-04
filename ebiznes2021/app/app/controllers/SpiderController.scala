package controllers

import models.{Spider, SpiderRepository}

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SpidersController @Inject()(spidersRepository: SpiderRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createSpiderForm: Form[CreateSpiderForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(CreateSpiderForm.apply)(CreateSpiderForm.unapply)
  }

  val updateSpiderForm: Form[UpdateSpiderForm] = Form {
    mapping(
      "id" -> longNumber,
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(UpdateSpiderForm.apply)(UpdateSpiderForm.unapply)
  }

  def addSpider: Action[AnyContent] = Action.async { implicit request =>
    createSpiderForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      spider => {
        spidersRepository.create(spider.name, spider.nickname, spider.age, spider.price).map { _ =>
          Redirect(routes.SpidersController.getSpiders).flashing("success" -> "product.created")
        }
      }
    )
  }

  def getSpiders: Action[AnyContent] = Action.async { implicit request =>
    spidersRepository.list().map {
      spider => Ok(Json.toJson(spider))
    }
  }

  def updateSpider(id: Long): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    updateSpiderForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      spider => {
        spidersRepository.update(spider.id, Spider(spider.id, spider.name, spider.nickname, spider.age, spider.price)).map { _ =>
          Redirect(routes.SpidersController.getSpiders).flashing("success" -> "spider updated")
        }
      }
    )
  }

  def delete(id: Long): Action[AnyContent] = Action {
    spidersRepository.delete(id)
    Redirect("/spiders")
  }


}

case class CreateSpiderForm(name: String, nickname: String, age: Int, price: BigDecimal)
case class UpdateSpiderForm(id: Long, name: String, nickname: String, age: Int, price: BigDecimal)
