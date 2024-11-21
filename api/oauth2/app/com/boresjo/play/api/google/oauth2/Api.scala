package com.boresjo.play.api.google.oauth2

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://www.googleapis.com/"

	object userinfo {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Userinfo]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Userinfo])
		}
		object get {
			def apply()(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"oauth2/v2/userinfo")).addQueryStringParameters())
			given Conversion[get, Future[Schema.Userinfo]] = (fun: get) => fun.apply()
		}
		object v2 {
			object me {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Userinfo]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Userinfo])
				}
				object get {
					def apply()(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"userinfo/v2/me")).addQueryStringParameters())
					given Conversion[get, Future[Schema.Userinfo]] = (fun: get) => fun.apply()
				}
			}
		}
	}
}
