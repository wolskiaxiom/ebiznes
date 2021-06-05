package models

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SpiderRepository  @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  private class SpiderTable(tag: Tag) extends Table[Spider](tag, "spider") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def animalType = column[String]("animal_type")
    def age = column[Int]("age")
    def price = column[BigDecimal]("price")

    def * = (id, name, animalType, age, price) <> ((Spider.apply _).tupled, Spider.unapply)
  }

  private val spider = TableQuery[SpiderTable]
  def create(name: String, animalType: String, age: Int, price:BigDecimal): Future[Spider] = db.run {
    (spider.map(a => (a.name, a.animalType, a.age, a.price))
      returning spider.map(_.id)
      into {case ((name, animalType, age, price), id) => Spider(id, name, animalType, age, price)}
      ) += (name, animalType, age, price)
  }

  def list(): Future[Seq[Spider]] = db.run {
    spider.result
  }

  def delete(id: Long): Future[Unit] = db.run(spider.filter(_.id === id).delete).map(_ => ())

  def update(id: Long, new_animal: Spider): Future[Unit] = {
    val animalToUpdate: Spider = new_animal.copy(id)
    db.run(spider.filter(_.id === id).update(animalToUpdate)).map(_ => ())
  }

}
