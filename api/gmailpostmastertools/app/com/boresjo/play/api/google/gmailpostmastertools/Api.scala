package com.boresjo.play.api.google.gmailpostmastertools

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://gmailpostmastertools.googleapis.com/"

	object domains {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDomainsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDomainsResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/domains").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListDomainsResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Domain]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Domain])
		}
		object get {
			def apply(domainsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/domains/${domainsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Domain]] = (fun: get) => fun.apply()
		}
		object trafficStats {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTrafficStatsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTrafficStatsResponse])
			}
			object list {
				def apply(domainsId :PlayApi, pageSize: Int, endDateDay: Int, startDateYear: Int, endDateYear: Int, pageToken: String, parent: String, endDateMonth: Int, startDateMonth: Int, startDateDay: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/domains/${domainsId}/trafficStats").addQueryStringParameters("pageSize" -> pageSize.toString, "endDate.day" -> endDateDay.toString, "startDate.year" -> startDateYear.toString, "endDate.year" -> endDateYear.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "endDate.month" -> endDateMonth.toString, "startDate.month" -> startDateMonth.toString, "startDate.day" -> startDateDay.toString))
				given Conversion[list, Future[Schema.ListTrafficStatsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TrafficStats]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TrafficStats])
			}
			object get {
				def apply(domainsId :PlayApi, trafficStatsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/domains/${domainsId}/trafficStats/${trafficStatsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.TrafficStats]] = (fun: get) => fun.apply()
			}
		}
	}
}
