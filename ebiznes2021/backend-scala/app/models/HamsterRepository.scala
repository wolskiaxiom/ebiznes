package models

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HamsterRepository  @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  private class HamsterTable(tag: Tag) extends Table[Hamster](tag, "hamster") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def animalType = column[String]("animal_type")
    def age = column[Int]("age")
    def price = column[BigDecimal]("price")

    def * = (id, name, animalType, age, price) <> ((Hamster.apply _).tupled, Hamster.unapply)
  }

  private val hamster = TableQuery[HamsterTable]
  def create(name: String, animalType: String, age: Int, price:BigDecimal): Future[Hamster] = db.run {
    (hamster.map(a => (a.name, a.animalType, a.age, a.price))
      returning hamster.map(_.id)
      into {case ((name, animalType, age, price), id) => Hamster(id, name, animalType, age, price)}
      ) += (name, animalType, age, price)
  }

  def list(): Future[Seq[Hamster]] = db.run {
    hamster.result
  }

  def delete(id: Long): Future[Unit] = db.run(hamster.filter(_.id === id).delete).map(_ => ())

  def update(id: Long, new_animal: Hamster): Future[Unit] = {
    val animalToUpdate: Hamster = new_animal.copy(id)
    db.run(hamster.filter(_.id === id).update(animalToUpdate)).map(_ => ())
  }

}
