package com.boresjo.play.api

import play.api.libs.ws.WSRequest

import scala.concurrent.Future

trait AuthToken {
  def exec(req: WSRequest, execFun: WSRequest => Future[WSRequest#Response]): Future[WSRequest#Response]
}
