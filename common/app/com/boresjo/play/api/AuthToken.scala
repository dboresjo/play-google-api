package com.boresjo.play.api

import play.api.libs.ws.WSRequest

/**
 * Trait representing an authentication token that can be applied to a WSRequest.
 */
trait AuthToken extends Function[WSRequest, WSRequest]
