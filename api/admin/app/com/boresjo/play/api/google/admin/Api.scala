package com.boresjo.play.api.google.admin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://admin.googleapis.com/"

	object activities {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Activities]) {
			/** Determines how many activity records are shown on each response page. For example, if the request sets `maxResults=1` and the report has two activities, the report has two pages. The response's `nextPageToken` property has the token to the second page. The `maxResults` query string is optional in the request. The default value is 1000.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Activities])
		}
		object list {
			def apply(userKey: String, applicationName: String, actorIpAddress: String, customerId: String, endTime: String, eventName: String, filters: String, orgUnitID: String, pageToken: String, startTime: String, groupIdFilter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"admin/reports/v1/activity/users/${userKey}/applications/${applicationName}").addQueryStringParameters("actorIpAddress" -> actorIpAddress.toString, "customerId" -> customerId.toString, "endTime" -> endTime.toString, "eventName" -> eventName.toString, "filters" -> filters.toString, "orgUnitID" -> orgUnitID.toString, "pageToken" -> pageToken.toString, "startTime" -> startTime.toString, "groupIdFilter" -> groupIdFilter.toString))
			given Conversion[list, Future[Schema.Activities]] = (fun: list) => fun.apply()
		}
		class watch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Determines how many activity records are shown on each response page. For example, if the request sets `maxResults=1` and the report has two activities, the report has two pages. The response's `nextPageToken` property has the token to the second page. The `maxResults` query string is optional in the request. The default value is 1000.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new watch(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			def withChannel(body: Schema.Channel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
		}
		object watch {
			def apply(userKey: String, applicationName: String, actorIpAddress: String, customerId: String, endTime: String, eventName: String, filters: String, orgUnitID: String, pageToken: String, startTime: String, groupIdFilter: String)(using auth: AuthToken, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"admin/reports/v1/activity/users/${userKey}/applications/${applicationName}/watch").addQueryStringParameters("actorIpAddress" -> actorIpAddress.toString, "customerId" -> customerId.toString, "endTime" -> endTime.toString, "eventName" -> eventName.toString, "filters" -> filters.toString, "orgUnitID" -> orgUnitID.toString, "pageToken" -> pageToken.toString, "startTime" -> startTime.toString, "groupIdFilter" -> groupIdFilter.toString))
		}
	}
	object channels {
		class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withChannel(body: Schema.Channel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object stop {
			def apply()(using auth: AuthToken, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"admin/reports_v1/channels/stop").addQueryStringParameters())
		}
	}
	object customerUsageReports {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UsageReports]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.UsageReports])
		}
		object get {
			def apply(date: String, customerId: String, pageToken: String, parameters: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"admin/reports/v1/usage/dates/${date}").addQueryStringParameters("customerId" -> customerId.toString, "pageToken" -> pageToken.toString, "parameters" -> parameters.toString))
			given Conversion[get, Future[Schema.UsageReports]] = (fun: get) => fun.apply()
		}
	}
	object entityUsageReports {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UsageReports]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.UsageReports])
		}
		object get {
			def apply(entityType: String, entityKey: String, date: String, customerId: String, filters: String, maxResults: Int, pageToken: String, parameters: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"admin/reports/v1/usage/${entityType}/${entityKey}/dates/${date}").addQueryStringParameters("customerId" -> customerId.toString, "filters" -> filters.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "parameters" -> parameters.toString))
			given Conversion[get, Future[Schema.UsageReports]] = (fun: get) => fun.apply()
		}
	}
	object userUsageReport {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UsageReports]) {
			/** Determines how many activity records are shown on each response page. For example, if the request sets `maxResults=1` and the report has two activities, the report has two pages. The response's `nextPageToken` property has the token to the second page. The `maxResults` query string is optional.<br>Format: uint32 */
			def withMaxResults(maxResults: Int) = new get(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.UsageReports])
		}
		object get {
			def apply(userKey: String, date: String, customerId: String, filters: String, orgUnitID: String, pageToken: String, parameters: String, groupIdFilter: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"admin/reports/v1/usage/users/${userKey}/dates/${date}").addQueryStringParameters("customerId" -> customerId.toString, "filters" -> filters.toString, "orgUnitID" -> orgUnitID.toString, "pageToken" -> pageToken.toString, "parameters" -> parameters.toString, "groupIdFilter" -> groupIdFilter.toString))
			given Conversion[get, Future[Schema.UsageReports]] = (fun: get) => fun.apply()
		}
	}
}
