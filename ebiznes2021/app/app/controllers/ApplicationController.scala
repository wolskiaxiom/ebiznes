package controllers

import com.mohiva.play.silhouette.api.LogoutEvent
import com.mohiva.play.silhouette.api.actions._

import javax.inject.Inject
import play.api.mvc._
import play.api.http.HttpErrorHandler
import utils.route.Calls

import scala.concurrent.{ExecutionContext, Future}

/**
 * The basic application controller.
 */
class ApplicationController @Inject() (
  scc: SilhouetteControllerComponents,
  errorHandler: HttpErrorHandler,
  assets: Assets
)(implicit ex: ExecutionContext) extends SilhouetteController(scc) {

  /**
   * Handles the index action.
   *
   * @return The result to display.
   */
  def index: Action[AnyContent] = assets.at("index.html")

  def assetOrDefault(resource: String): Action[AnyContent] =
    if (resource.startsWith("api")) {
      Action.async(r => errorHandler.onClientError(r, NOT_FOUND, "Not found"))
    } else {
      if (resource.contains(".")) assets.at(resource) else index
    }

  /**
   * Handles the Sign Out action.
   *
   * @return The result to display.
   */
  def signOut = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    val result = Redirect(Calls.home)
      .discardingCookies(DiscardingCookie("email"))
    eventBus.publish(LogoutEvent(request.identity, request))
    authenticatorService.discard(request.authenticator, result)
  }
}
