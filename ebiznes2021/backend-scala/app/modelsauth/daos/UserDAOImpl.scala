package modelsauth.daos

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import modelsauth.User
import modelsauth.tables.UserTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
//import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ ExecutionContext, Future }

/**
 * Gives access to the user storage.
 */
class UserDAOImpl @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends UserDAO {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private val users = TableQuery[UserTable]

  /**
   * Finds a user by its login info.
   *
   * @param loginInfo The login info of the user to find.
   * @return The found user or None if no user for the given login info could be found.
   */
  override def find(loginInfo: LoginInfo): Future[Option[User]] = db.run {
    users.filter(_.email === loginInfo.providerKey).result.headOption
  }

  /**
   * Saves a user.
   *
   * @param user The user to save.
   * @return The saved user.
   */

    override def save(user: User): Future[User] =
    db.run(users.insertOrUpdate(user)).map(_ => user)

  /**
   * Updates a user.
   *
   * @param user The user to update.
   * @return The saved user.
   */
  override def update(user: User): Future[User] = db.run {
    users.filter(_.email === user.email).update(user).map(_ => user)
  }
}
