package models

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DogRepository  @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  private class DogTable(tag: Tag) extends Table[Dog](tag, "dog") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def animalType = column[String]("animal_type")
    def age = column[Int]("age")
    def price = column[BigDecimal]("price")

    def * = (id, name, animalType, age, price) <> ((Dog.apply _).tupled, Dog.unapply)
  }

  private val dog = TableQuery[DogTable]
  def create(name: String, animalType: String, age: Int, price:BigDecimal): Future[Dog] = db.run {
    (dog.map(a => (a.name, a.animalType, a.age, a.price))
      returning dog.map(_.id)
      into {case ((name, animalType, age, price), id) => Dog(id, name, animalType, age, price)}
      ) += (name, animalType, age, price)
  }

  def list(): Future[Seq[Dog]] = db.run {
    dog.result
  }

  def delete(id: Long): Future[Unit] = db.run(dog.filter(_.id === id).delete).map(_ => ())

  def update(id: Long, new_animal: Dog): Future[Unit] = {
    val animalToUpdate: Dog = new_animal.copy(id)
    db.run(dog.filter(_.id === id).update(animalToUpdate)).map(_ => ())
  }

}
