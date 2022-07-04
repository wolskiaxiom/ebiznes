package models

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SnakeRepository  @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  private class SnakeTable(tag: Tag) extends Table[Snake](tag, "snake") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def animalType = column[String]("animal_type")
    def age = column[Int]("age")
    def price = column[BigDecimal]("price")

    def * = (id, name, animalType, age, price) <> ((Snake.apply _).tupled, Snake.unapply)
  }

  private val snake = TableQuery[SnakeTable]
  def create(name: String, animalType: String, age: Int, price:BigDecimal): Future[Snake] = db.run {
    (snake.map(a => (a.name, a.animalType, a.age, a.price))
      returning snake.map(_.id)
      into {case ((name, animalType, age, price), id) => Snake(id, name, animalType, age, price)}
      ) += (name, animalType, age, price)
  }

  def list(): Future[Seq[Snake]] = db.run {
    snake.result
  }

  def delete(id: Long): Future[Unit] = db.run(snake.filter(_.id === id).delete).map(_ => ())

  def update(id: Long, newAnimal: Snake): Future[Unit] = {
    val animalToUpdate: Snake = newAnimal.copy(id)
    db.run(snake.filter(_.id === id).update(animalToUpdate)).map(_ => ())
  }

}
