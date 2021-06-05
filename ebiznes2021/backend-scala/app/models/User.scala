package models

import play.api.libs.json.Json

case class User (id: Long, name: String, nickname: String, age:Int)

object User {
  implicit val userFormat = Json.format[User]
}