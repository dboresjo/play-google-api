package com.boresjo.play.api

import play.api.libs.ws.WSRequest

trait AuthToken extends Function[WSRequest, WSRequest]
