package com.boresjo.play.api

import scala.concurrent.Future

/**
 * Trait representing a repository for managing authentication tokens.
 *
 * @tparam A The type of authentication token.
 */
trait TokenRepository[A <: AuthToken] {
  /**
   * Retrieves the current authentication token for the given scopes.
   *
   * @param scope The scopes for which the token is required.
   * @return A Future containing the current authentication token.
   */
  def getCurrent(scope: String*): Future[A]

  /**
   * Retrieves a refreshed authentication token, replacing the old token.
   *
   * @param oldToken The old authentication token to be replaced.
   * @return A Future containing the refreshed authentication token.
   */
  def getNext(oldToken: A): Future[A]
}