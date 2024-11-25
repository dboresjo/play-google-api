package com.boresjo.play.api

import java.util.concurrent.atomic.AtomicReference
import scala.concurrent.Future
import scala.concurrent.Future.successful

trait TokenRepository[A <: AuthToken] {
  def getCurrent(scope: String*): Future[A]

  def getNext(oldToken: A): Future[A]
}
