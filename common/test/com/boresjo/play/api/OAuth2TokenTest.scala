package com.boresjo.play.api

import org.mockito.Mockito.*
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import play.api.libs.ws.WSRequest

class OAuth2TokenTest extends AnyFunSuiteLike with Matchers with MockitoSugar {

  test("checkScope returns true if required scope is present") {
    val token = OAuth2Token(
      access_token = "access_token",
      expires_in = 3600,
      refresh_token = "refresh_token",
      scope = "scope1 scope2",
      token_type = "Bearer"
    )
    assert(token.checkScope("scope1"))
  }

  test("checkScope returns false if required scope is not present") {
    val token = OAuth2Token(
      access_token = "access_token",
      expires_in = 3600,
      refresh_token = "refresh_token",
      scope = "scope1 scope2",
      token_type = "Bearer"
    )
    assert(!token.checkScope("scope3"))
  }

  test("checkScope returns true if at least one required scope is present") {
    val token = OAuth2Token(
      access_token = "access_token",
      expires_in = 3600,
      refresh_token = "refresh_token",
      scope = "scope1 scope2",
      token_type = "Bearer"
    )
    assert(token.checkScope("scope3", "scope1"))
  }

  test("checkScope returns false if none of the required scopes are present") {
    val token = OAuth2Token(
      access_token = "access_token",
      expires_in = 3600,
      refresh_token = "refresh_token",
      scope = "scope1 scope2",
      token_type = "Bearer"
    )
    assert(!token.checkScope("scope3", "scope4"))
  }

  test("apply adds authorization header to request") {
    val token = OAuth2Token(
      access_token = "access_token",
      expires_in = 3600,
      refresh_token = "refresh_token",
      scope = "scope1 scope2",
      token_type = "Bearer"
    )
    val wsRequest = mock[WSRequest]
    when(wsRequest.addHttpHeaders("Authorization" -> "Bearer access_token")).thenReturn(wsRequest)
    assert(token.apply(wsRequest) == wsRequest)
  }
}
