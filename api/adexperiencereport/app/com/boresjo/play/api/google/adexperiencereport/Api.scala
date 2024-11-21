package com.boresjo.play.api.google.adexperiencereport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://adexperiencereport.googleapis.com/"

	object sites {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SiteSummaryResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SiteSummaryResponse])
		}
		object get {
			def apply(sitesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/sites/${sitesId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.SiteSummaryResponse]] = (fun: get) => fun.apply()
		}
	}
	object violatingSites {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ViolatingSitesResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ViolatingSitesResponse])
		}
		object list {
			def apply()(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/violatingSites")).addQueryStringParameters())
			given Conversion[list, Future[Schema.ViolatingSitesResponse]] = (fun: list) => fun.apply()
		}
	}
}
