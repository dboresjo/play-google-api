package com.boresjo.play.api

import play.api.libs.json.*
import play.api.libs.ws.WSRequest

import scala.concurrent.Future

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
  override def apply(req: WSRequest): WSRequest = req.addHttpHeaders("Authorization" -> s"$token_type $access_token")

  private lazy val flatTokenScope: Seq[String] = scope.split("\\s+")

  def checkScope(requiredScope: String*): Boolean = {
    val flatRequired: Seq[String] = requiredScope.flatMap(_.split(" "))
    flatTokenScope.intersect(flatRequired).nonEmpty
  }
}

object OAuth2Token {
  given OFormat[OAuth2Token] = Json.format[OAuth2Token]
  
  def apply(token: String): OAuth2Token = Json.parse(token).as[OAuth2Token]
  def apply(token: JsValue): OAuth2Token = token.as[OAuth2Token]
}
