package models

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PigRepository  @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  private class PigTable(tag: Tag) extends Table[Pig](tag, "pig") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def animalType = column[String]("animal_type")
    def age = column[Int]("age")
    def price = column[BigDecimal]("price")

    def * = (id, name, animalType, age, price) <> ((Pig.apply _).tupled, Pig.unapply)
  }

  private val pig = TableQuery[PigTable]
  def create(name: String, animalType: String, age: Int, price:BigDecimal): Future[Pig] = db.run {
    (pig.map(a => (a.name, a.animalType, a.age, a.price))
      returning pig.map(_.id)
      into {case ((name, animalType, age, price), id) => Pig(id, name, animalType, age, price)}
      ) += (name, animalType, age, price)
  }

  def list(): Future[Seq[Pig]] = db.run {
    pig.result
  }

  def delete(id: Long): Future[Unit] = db.run(pig.filter(_.id === id).delete).map(_ => ())

  def update(id: Long, new_animal: Pig): Future[Unit] = {
    val animalToUpdate: Pig = new_animal.copy(id)
    db.run(pig.filter(_.id === id).update(animalToUpdate)).map(_ => ())
  }

}
