package models

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CatRepository  @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  private class CatTable(tag: Tag) extends Table[Cat](tag, "cat") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def animalType = column[String]("animal_type")
    def age = column[Int]("age")
    def price = column[BigDecimal]("price")

    def * = (id, name, animalType, age, price) <> ((Cat.apply _).tupled, Cat.unapply)
  }

  private val cat = TableQuery[CatTable]
  def create(name: String, animalType: String, age: Int, price:BigDecimal): Future[Cat] = db.run {
    (cat.map(a => (a.name, a.animalType, a.age, a.price))
      returning cat.map(_.id)
      into {case ((name, animalType, age, price), id) => Cat(id, name, animalType, age, price)}
      ) += (name, animalType, age, price)
  }

  def list(): Future[Seq[Cat]] = db.run {
    cat.result
  }

  def delete(id: Long): Future[Unit] = db.run(cat.filter(_.id === id).delete).map(_ => ())

  def update(id: Long, newAnimal: Cat): Future[Unit] = {
    val animalToUpdate: Cat = newAnimal.copy(id)
    db.run(cat.filter(_.id === id).update(animalToUpdate)).map(_ => ())
  }

}
