package com.boresjo.play.api

import scala.concurrent.Future

trait TokenRepository[A <: AuthToken] {
  def getCurrent(scope: String*): Future[A]

  def getNext(oldToken: A): Future[A]
}
