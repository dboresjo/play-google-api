package com.boresjo.play.api.google.groupssettings

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://www.googleapis.com/groups/v1/groups/"

	object groups {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Groups]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Groups])
		}
		object get {
			def apply(groupUniqueId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"${groupUniqueId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.Groups]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGroups(body: Schema.Groups) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Groups])
		}
		object patch {
			def apply(groupUniqueId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"${groupUniqueId}")).addQueryStringParameters())
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGroups(body: Schema.Groups) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Groups])
		}
		object update {
			def apply(groupUniqueId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"${groupUniqueId}")).addQueryStringParameters())
		}
	}
}
