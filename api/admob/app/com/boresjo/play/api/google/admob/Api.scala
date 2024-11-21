package com.boresjo.play.api.google.admob

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://admob.googleapis.com/"

	object accounts {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PublisherAccount]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.PublisherAccount])
		}
		object get {
			def apply(accountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/accounts/${accountsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.PublisherAccount]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPublisherAccountsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ListPublisherAccountsResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/accounts")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListPublisherAccountsResponse]] = (fun: list) => fun.apply()
		}
		object mediationReport {
			class generate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGenerateMediationReportRequest(body: Schema.GenerateMediationReportRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GenerateMediationReportResponse])
			}
			object generate {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): generate = new generate(auth(ws.url(BASE_URL + s"v1/accounts/${accountsId}/mediationReport:generate")).addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object adUnits {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAdUnitsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListAdUnitsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/accounts/${accountsId}/adUnits")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAdUnitsResponse]] = (fun: list) => fun.apply()
			}
		}
		object networkReport {
			class generate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGenerateNetworkReportRequest(body: Schema.GenerateNetworkReportRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GenerateNetworkReportResponse])
			}
			object generate {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): generate = new generate(auth(ws.url(BASE_URL + s"v1/accounts/${accountsId}/networkReport:generate")).addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object apps {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAppsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListAppsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/accounts/${accountsId}/apps")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAppsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
