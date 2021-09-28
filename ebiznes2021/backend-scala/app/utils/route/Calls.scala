package utils.route

import play.api.mvc.Call

/**
 * Defines some common redirect calls used in authentication flow.
 */
object Calls {
  /** @return The URL to redirect to when an authentication succeeds. */
  def home: Call = Call("GET", "http://localhost:3000/")

  /** @return The URL to redirect to when an authentication fails. */
  def authenticationFailed: Call = Call("GET", "http://localhost:3000/authenticionFailed")
  /** @return The URL to redirect to when an authentication fails. */
  def notAuthenticated: Call = Call("GET", "http://localhost:3000/notAuthenticated")
  /** @return The URL to redirect to when an authentication fails. */
  def notAuthorized: Call = Call("GET", "http://localhost:3000/notAuthorized")
}
