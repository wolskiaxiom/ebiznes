package controllers

import com.mohiva.play.silhouette.api.LogoutEvent
import com.mohiva.play.silhouette.api.actions._
import com.mohiva.play.silhouette.impl.providers.GoogleTotpInfo
import javax.inject.Inject
import play.api.mvc._
import utils.route.Calls

import scala.concurrent.ExecutionContext

/**
 * The basic application controller.
 */
class ApplicationController @Inject() (
  scc: SilhouetteControllerComponents
)(implicit ex: ExecutionContext) extends SilhouetteController(scc) {

  /**
   * Handles the index action.
   *
   * @return The result to display.
   */
  def index = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    authInfoRepository.find[GoogleTotpInfo](request.identity.loginInfo).map { totpInfoOpt =>
      Redirect(Calls.home)
    }
  }

  /**
   * Handles the Sign Out action.
   *
   * @return The result to display.
   */
  def signOut = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    val result = Redirect(Calls.home)
    eventBus.publish(LogoutEvent(request.identity, request))
    authenticatorService.discard(request.authenticator, result)
  }
}
