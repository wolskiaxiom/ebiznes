package controllers

import models.{User, UserRepository}

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UsersController @Inject()(usersRepo: UserRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val userForm: Form[CreateUserForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "nickname" -> nonEmptyText,
      "age" -> number,
    )(CreateUserForm.apply)(CreateUserForm.unapply)
  }

  val updateUserForm: Form[UpdateUserForm] = Form {
    mapping(
      "id" -> longNumber,
      "name" -> nonEmptyText,
      "nickname" -> nonEmptyText,
      "age" -> number,
    )(UpdateUserForm.apply)(UpdateUserForm.unapply)
  }

  def addUser: Action[AnyContent] = Action.async { implicit request =>
    userForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      user => {
        usersRepo.create(user.name, user.nickname, user.age).map { _ =>
          Redirect(routes.UsersController.getUsers()).flashing("success" -> "product.created")
        }
      }
    )
  }

  def getUsers: Action[AnyContent] = Action.async { implicit request =>
    usersRepo.list().map {
      user => Ok(Json.toJson(user))
    }
  }

  def updateUser(id: Long): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    updateUserForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(
          BadRequest
        )
      },
      user => {
        usersRepo.update(user.id, User(user.id, user.name, user.nickname, user.age)).map { _ =>
          Redirect(routes.UsersController.getUsers()).flashing("success" -> "user updated")
        }
      }
    )
  }

  def delete(id: Long): Action[AnyContent] = Action {
    usersRepo.delete(id)
    Redirect("/users")
  }


}

case class CreateUserForm(name: String, nickname: String, age: Int)
case class UpdateUserForm(id: Long, name: String, nickname: String, age: Int)
