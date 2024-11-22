package com.boresjo.play.api.google.driveactivity

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://driveactivity.googleapis.com/"

	object activity {
		class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withQueryDriveActivityRequest(body: Schema.QueryDriveActivityRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.QueryDriveActivityResponse])
		}
		object query {
			def apply()(using auth: AuthToken, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"v2/activity:query").addQueryStringParameters())
		}
	}
}
