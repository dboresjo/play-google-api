package com.boresjo.play.api

import play.api.libs.ws.WSRequest

import scala.concurrent.Future.successful
import scala.concurrent.{ExecutionContext, Future}

trait RequestSigner {
  def exec(scope: String*)(req: WSRequest, execFun: WSRequest => Future[WSRequest#Response])(using ExecutionContext): Future[WSRequest#Response]
}

trait TokenRepository[A >: AuthToken] {
  def getCurrent(scope: String*): Future[A]
  def getNext(oldToken: A): Future[A]
}

private [api] class DefaultRequestSigner(tokenRepository: TokenRepository[AuthToken]) extends RequestSigner {
  def exec(scope: String*)(req: WSRequest, execFun: WSRequest => Future[WSRequest#Response])(using ExecutionContext): Future[WSRequest#Response] = {
    for (
      currentToken <- tokenRepository.getCurrent(scope:_*);
      response <- execFun(currentToken(req))
    ) yield {
      if (response.status == 401) {
        for (
          refreshedToken <- tokenRepository.getNext(currentToken);
          secondResponse <- execFun(refreshedToken(req))
        ) yield secondResponse
      } else {
        successful(response)
      }
    }
  }.flatten
}
