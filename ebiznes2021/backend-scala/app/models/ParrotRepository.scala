package models

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ParrotRepository  @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  private class ParrotTable(tag: Tag) extends Table[Parrot](tag, "parrot") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def animalType = column[String]("animal_type")
    def age = column[Int]("age")
    def price = column[BigDecimal]("price")

    def * = (id, name, animalType, age, price) <> ((Parrot.apply _).tupled, Parrot.unapply)
  }

  private val parrot = TableQuery[ParrotTable]
  def create(name: String, animalType: String, age: Int, price:BigDecimal): Future[Parrot] = db.run {
    (parrot.map(a => (a.name, a.animalType, a.age, a.price))
      returning parrot.map(_.id)
      into {case ((name, animalType, age, price), id) => Parrot(id, name, animalType, age, price)}
      ) += (name, animalType, age, price)
  }

  def list(): Future[Seq[Parrot]] = db.run {
    parrot.result
  }

  def delete(id: Long): Future[Unit] = db.run(parrot.filter(_.id === id).delete).map(_ => ())

  def update(id: Long, new_animal: Parrot): Future[Unit] = {
    val animalToUpdate: Parrot = new_animal.copy(id)
    db.run(parrot.filter(_.id === id).update(animalToUpdate)).map(_ => ())
  }

}
