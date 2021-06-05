package controllers

import models.{Dog, DogRepository}

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DogsController @Inject()(dogsRepository: DogRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val createDogForm: Form[CreateDogForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(CreateDogForm.apply)(CreateDogForm.unapply)
  }

  val updateDogForm: Form[UpdateDogForm] = Form {
    mapping(
      "id" -> longNumber,
      "name" -> nonEmptyText,
      "animalType" -> nonEmptyText,
      "age" -> number,
      "price" -> bigDecimal,
    )(UpdateDogForm.apply)(UpdateDogForm.unapply)
  }

  def addDog: Action[AnyContent] = Action.async { implicit request =>
    createDogForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      dog => {
        dogsRepository.create(dog.name, dog.nickname, dog.age, dog.price).map { _ =>
          Redirect(routes.DogsController.getDogs()).flashing("success" -> "product.created")
        }
      }
    )
  }

  def getDogs: Action[AnyContent] = Action.async { implicit request =>
    dogsRepository.list().map {
      dog => Ok(Json.toJson(dog))
    }
  }

  def updateDog(id: Long): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    updateDogForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      dog => {
        dogsRepository.update(dog.id, Dog(dog.id, dog.name, dog.nickname, dog.age, dog.price)).map { _ =>
          Redirect(routes.DogsController.getDogs()).flashing("success" -> "dog updated")
        }
      }
    )
  }

  def delete(id: Long): Action[AnyContent] = Action {
    dogsRepository.delete(id)
    Redirect("/dogs")
  }


}

case class CreateDogForm(name: String, nickname: String, age: Int, price: BigDecimal)
case class UpdateDogForm(id: Long, name: String, nickname: String, age: Int, price: BigDecimal)
