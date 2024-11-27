package com.boresjo.play.api

import play.api.http.Status.UNAUTHORIZED
import play.api.libs.ws.WSRequest

import scala.concurrent.Future.successful
import scala.concurrent.{ExecutionContext, Future}

/**
 * Trait representing a request signer that can execute a request with a given scope.
 */
trait RequestSigner {
  /**
   * Executes a request with the given scope.
   *
   * @param scope The scope for the request.
   * @param req The WSRequest to be executed.
   * @param execFun The function to execute the request.
   * @param ec The execution context.
   * @return A Future containing the response of the request.
   */
  def exec(scope: String*)(req: WSRequest, execFun: WSRequest => Future[WSRequest#Response])(using ExecutionContext): Future[WSRequest#Response]
}

/**
 * Exception thrown when an unauthorized request is encountered.
 */
class UnauthorizedException() extends RuntimeException

/**
 * Default implementation of the RequestSigner trait.
 *
 * @param tokenRepository The token repository to manage authentication tokens.
 * @tparam A The type of authentication token.
 */
class DefaultRequestSigner[A <: AuthToken](tokenRepository: TokenRepository[A]) extends RequestSigner {
  /**
   * Executes a request with the given scope, refreshing the token if necessary.
   *
   * @param scope The scope for the request.
   * @param req The WSRequest to be executed.
   * @param execFun The function to execute the request.
   * @param ec The execution context.
   * @return A Future containing the response of the request.
   */
  def exec(scope: String*)(req: WSRequest, execFun: WSRequest => Future[WSRequest#Response])(using ExecutionContext): Future[WSRequest#Response] = {
    for (
      currentToken <- tokenRepository.getCurrent(scope:_*);
      response <- execFun(currentToken(req))
    ) yield {
      if (response.status == UNAUTHORIZED) {
        for (
          refreshedToken <- tokenRepository.getNext(currentToken);
          secondResponse <- execFun(refreshedToken(req))
        ) yield {
          if (secondResponse.status == UNAUTHORIZED) {
            secondResponse
          } else {
            throw new UnauthorizedException()
          }
        }
      } else {
        successful(response)
      }
    }
  }.flatten
}