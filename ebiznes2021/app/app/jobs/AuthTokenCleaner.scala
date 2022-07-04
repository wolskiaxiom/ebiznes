package jobs

import javax.inject.Inject
import akka.actor._
import com.mohiva.play.silhouette.api.util.Clock
import jobs.AuthTokenCleaner.Clean
import models.services.AuthTokenService
import play.api.Logging

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * A job which cleanup invalid auth tokens.
 *
 * @param service The auth token service implementation.
 * @param clock The clock implementation.
 */
class AuthTokenCleaner @Inject() (
  service: AuthTokenService,
  clock: Clock)
  extends Actor with Logging {

  /**
   * Process the received messages.
   */
  val MSG_SEPARATOR = "=================================\n"
  def receive: Receive = {
    case Clean =>
      val start = clock.now.getMillis
      val msg = new StringBuffer("\n")
      msg.append(MSG_SEPARATOR)
      msg.append("Start to cleanup auth tokens\n")
      msg.append(MSG_SEPARATOR)
      service.clean.map { deleted =>
        val seconds = (clock.now.getMillis - start) / 1000
        msg.append("Total of %s auth tokens(s) were deleted in %s seconds".format(deleted.length, seconds)).append("\n")
        msg.append(MSG_SEPARATOR)

        msg.append(MSG_SEPARATOR)
        logger.info(msg.toString)
      }.recover {
        case e =>
          msg.append("Couldn't cleanup auth tokens because of unexpected error\n")
          msg.append(MSG_SEPARATOR)
          logger.error(msg.toString, e)
      }
  }
}

/**
 * The companion object.
 */
object AuthTokenCleaner {
  case object Clean
}
