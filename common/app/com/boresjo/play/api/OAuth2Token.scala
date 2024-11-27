package com.boresjo.play.api

import play.api.libs.json.*
import play.api.libs.ws.WSRequest

/**
 * @example {
 * "access_token": "ya29.a0AeDClZAYuv5RWM6hNiAxWtAfT0JM-HPNPR5BsFrXJVtAqFFtjX0ARjL4lnJW3Oeti9gsXu0qK0UJvk2aT5Ms3ToK_yWaXwPBXPO9T89Z4ajYlAEqhVNwBl06vH0wXNyBTkEtPAD5HJMcH1RY4nufhmOzaVK6Wwo4PAhkavRUaCgYKAYESARMSFQHGX2Mi5czJsTenYa9ItNzipOXF7A0176",
 * "expires_in": 3599,
 * "refresh_token": "1//03tdysddOZS3vCgYIARAAGAMSNwF-L9IrhbGiqbn60Zl_InOXOxdcgsopPdROg4jihQLgXp6wRKDQYIrlUtHZkX0w3krUBrTte1N",
 * "scope": "https://www.googleapis.com/auth/tasks.readonly https://www.googleapis.com/auth/calendar.readonly",
 * "token_type": "Bearer"
 * }
 */
case class OAuth2Token (
                         access_token: String,
                         expires_in: Int,
                         refresh_token: String,
                         scope: String,
                         token_type: String
                       ) extends AuthToken {
  /**
   * Adds the authorization header to the WSRequest.
   *
   * @param req The WSRequest to which the authorization header will be added.
   * @return The WSRequest with the authorization header added.
   */
  override def apply(req: WSRequest): WSRequest = req.addHttpHeaders("Authorization" -> s"$token_type $access_token")

  /**
   * Splits the scope string into a sequence of individual scopes.
   */
  private lazy val flatTokenScope: Seq[String] = scope.split("\\s+")

  /**
   * Checks if the token has the required scopes.
   *
   * @param requiredScope The required scopes to check.
   * @return True if the token has at least one of the required scopes, false otherwise.
   */
  def checkScope(requiredScope: String*): Boolean = {
    val flatRequired: Seq[String] = requiredScope.flatMap(_.split(" "))
    flatTokenScope.intersect(flatRequired).nonEmpty
  }
}

object OAuth2Token {
  /**
   * Implicit format for serializing and deserializing OAuth2Token instances.
   */
  given OFormat[OAuth2Token] = Json.format[OAuth2Token]

  /**
   * Creates an OAuth2Token from a JSON string.
   *
   * @param token The JSON string representing the token.
   * @return The OAuth2Token instance.
   */
  def apply(token: String): OAuth2Token = Json.parse(token).as[OAuth2Token]

  /**
   * Creates an OAuth2Token from a JsValue.
   *
   * @param token The JsValue representing the token.
   * @return The OAuth2Token instance.
   */
  def apply(token: JsValue): OAuth2Token = token.as[OAuth2Token]
}