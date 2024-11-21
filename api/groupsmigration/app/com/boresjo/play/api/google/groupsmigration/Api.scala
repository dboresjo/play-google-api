package com.boresjo.play.api.google.groupsmigration

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://groupsmigration.googleapis.com/"

	object archive {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Groups]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.Groups])
		}
		object insert {
			def apply(groupId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"groups/v1/groups/${groupId}/archive")).addQueryStringParameters())
			given Conversion[insert, Future[Schema.Groups]] = (fun: insert) => fun.apply()
		}
	}
}
