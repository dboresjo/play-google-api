package com.boresjo.play.api.google.admin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/admin.reports.audit.readonly""" /* View audit reports for your G Suite domain */,
		"""https://www.googleapis.com/auth/admin.reports.usage.readonly""" /* View usage reports for your G Suite domain */
	)

	private val BASE_URL = "https://admin.googleapis.com/"

	object activities {
		/** Retrieves a list of activities for a specific customer's account and application such as the Admin console application or the Google Drive application. For more information, see the guides for administrator and Google Drive activity reports. For more information about the activity report's parameters, see the activity parameters reference guides.  */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Activities]) {
			val scopes = Seq("""https://www.googleapis.com/auth/admin.reports.audit.readonly""")
			/** Determines how many activity records are shown on each response page. For example, if the request sets `maxResults=1` and the report has two activities, the report has two pages. The response's `nextPageToken` property has the token to the second page. The `maxResults` query string is optional in the request. The default value is 1000.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Activities])
		}
		object list {
			def apply(userKey: String, applicationName: String, actorIpAddress: String, customerId: String, endTime: String, eventName: String, filters: String, orgUnitID: String, pageToken: String, startTime: String, groupIdFilter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"admin/reports/v1/activity/users/${userKey}/applications/${applicationName}").addQueryStringParameters("actorIpAddress" -> actorIpAddress.toString, "customerId" -> customerId.toString, "endTime" -> endTime.toString, "eventName" -> eventName.toString, "filters" -> filters.toString, "orgUnitID" -> orgUnitID.toString, "pageToken" -> pageToken.toString, "startTime" -> startTime.toString, "groupIdFilter" -> groupIdFilter.toString))
			given Conversion[list, Future[Schema.Activities]] = (fun: list) => fun.apply()
		}
		/** Start receiving notifications for account activities. For more information, see Receiving Push Notifications. */
		class watch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/admin.reports.audit.readonly""")
			/** Determines how many activity records are shown on each response page. For example, if the request sets `maxResults=1` and the report has two activities, the report has two pages. The response's `nextPageToken` property has the token to the second page. The `maxResults` query string is optional in the request. The default value is 1000.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new watch(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Perform the request */
			def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
		}
		object watch {
			def apply(userKey: String, applicationName: String, actorIpAddress: String, customerId: String, endTime: String, eventName: String, filters: String, orgUnitID: String, pageToken: String, startTime: String, groupIdFilter: String)(using signer: RequestSigner, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"admin/reports/v1/activity/users/${userKey}/applications/${applicationName}/watch").addQueryStringParameters("actorIpAddress" -> actorIpAddress.toString, "customerId" -> customerId.toString, "endTime" -> endTime.toString, "eventName" -> eventName.toString, "filters" -> filters.toString, "orgUnitID" -> orgUnitID.toString, "pageToken" -> pageToken.toString, "startTime" -> startTime.toString, "groupIdFilter" -> groupIdFilter.toString))
		}
	}
	object channels {
		/** Stop watching resources through this channel. */
		class stop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/admin.reports.audit.readonly""")
			/** Perform the request */
			def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object stop {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"admin/reports_v1/channels/stop").addQueryStringParameters())
		}
	}
	object customerUsageReports {
		/** Retrieves a report which is a collection of properties and statistics for a specific customer's account. For more information, see the Customers Usage Report guide. For more information about the customer report's parameters, see the Customers Usage parameters reference guides.  */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UsageReports]) {
			val scopes = Seq("""https://www.googleapis.com/auth/admin.reports.usage.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UsageReports])
		}
		object get {
			def apply(date: String, customerId: String, pageToken: String, parameters: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"admin/reports/v1/usage/dates/${date}").addQueryStringParameters("customerId" -> customerId.toString, "pageToken" -> pageToken.toString, "parameters" -> parameters.toString))
			given Conversion[get, Future[Schema.UsageReports]] = (fun: get) => fun.apply()
		}
	}
	object entityUsageReports {
		/** Retrieves a report which is a collection of properties and statistics for entities used by users within the account. For more information, see the Entities Usage Report guide. For more information about the entities report's parameters, see the Entities Usage parameters reference guides. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UsageReports]) {
			val scopes = Seq("""https://www.googleapis.com/auth/admin.reports.usage.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UsageReports])
		}
		object get {
			def apply(entityType: String, entityKey: String, date: String, customerId: String, filters: String, maxResults: Int, pageToken: String, parameters: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"admin/reports/v1/usage/${entityType}/${entityKey}/dates/${date}").addQueryStringParameters("customerId" -> customerId.toString, "filters" -> filters.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "parameters" -> parameters.toString))
			given Conversion[get, Future[Schema.UsageReports]] = (fun: get) => fun.apply()
		}
	}
	object userUsageReport {
		/** Retrieves a report which is a collection of properties and statistics for a set of users with the account. For more information, see the User Usage Report guide. For more information about the user report's parameters, see the Users Usage parameters reference guides. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UsageReports]) {
			val scopes = Seq("""https://www.googleapis.com/auth/admin.reports.usage.readonly""")
			/** Determines how many activity records are shown on each response page. For example, if the request sets `maxResults=1` and the report has two activities, the report has two pages. The response's `nextPageToken` property has the token to the second page. The `maxResults` query string is optional.<br>Format: uint32 */
			def withMaxResults(maxResults: Int) = new get(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UsageReports])
		}
		object get {
			def apply(userKey: String, date: String, customerId: String, filters: String, orgUnitID: String, pageToken: String, parameters: String, groupIdFilter: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"admin/reports/v1/usage/users/${userKey}/dates/${date}").addQueryStringParameters("customerId" -> customerId.toString, "filters" -> filters.toString, "orgUnitID" -> orgUnitID.toString, "pageToken" -> pageToken.toString, "parameters" -> parameters.toString, "groupIdFilter" -> groupIdFilter.toString))
			given Conversion[get, Future[Schema.UsageReports]] = (fun: get) => fun.apply()
		}
	}
}
