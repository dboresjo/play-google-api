package com.boresjo.play.api.google.clouderrorreporting

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://clouderrorreporting.googleapis.com/"

	object projects {
		/** Deletes all error events of a given project. */
		class deleteEvents(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DeleteEventsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.DeleteEventsResponse])
		}
		object deleteEvents {
			def apply(projectsId :PlayApi, projectName: String)(using signer: RequestSigner, ec: ExecutionContext): deleteEvents = new deleteEvents(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/events").addQueryStringParameters("projectName" -> projectName.toString))
			given Conversion[deleteEvents, Future[Schema.DeleteEventsResponse]] = (fun: deleteEvents) => fun.apply()
		}
		object locations {
			/** Deletes all error events of a given project. */
			class deleteEvents(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DeleteEventsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.DeleteEventsResponse])
			}
			object deleteEvents {
				def apply(projectsId :PlayApi, locationsId :PlayApi, projectName: String)(using signer: RequestSigner, ec: ExecutionContext): deleteEvents = new deleteEvents(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/events").addQueryStringParameters("projectName" -> projectName.toString))
				given Conversion[deleteEvents, Future[Schema.DeleteEventsResponse]] = (fun: deleteEvents) => fun.apply()
			}
			object groups {
				/** Get the specified group. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ErrorGroup]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ErrorGroup])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groupsId :PlayApi, groupName: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/groups/${groupsId}").addQueryStringParameters("groupName" -> groupName.toString))
					given Conversion[get, Future[Schema.ErrorGroup]] = (fun: get) => fun.apply()
				}
				/** Replace the data for the specified group. Fails if the group does not exist. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withErrorGroup(body: Schema.ErrorGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ErrorGroup])
				}
				object update {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/groups/${groupsId}").addQueryStringParameters("name" -> name.toString))
				}
			}
			object groupStats {
				/** Lists the specified groups. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListGroupStatsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. List all ErrorGroupStats with these IDs. The `group_id` is a unique identifier for a particular error group. The identifier is derived from key parts of the error-log content and is treated as Service Data. For information about how Service Data is handled, see [Google Cloud Privacy Notice] (https://cloud.google.com/terms/cloud-privacy-notice). */
					def withGroupId(groupId: String) = new list(req.addQueryStringParameters("groupId" -> groupId.toString))
					/** Optional. The exact value to match against [`ServiceContext.service`](/error-reporting/reference/rest/v1beta1/ServiceContext#FIELDS.service). */
					def withServiceFilterService(serviceFilterService: String) = new list(req.addQueryStringParameters("serviceFilter.service" -> serviceFilterService.toString))
					/** Optional. The exact value to match against [`ServiceContext.version`](/error-reporting/reference/rest/v1beta1/ServiceContext#FIELDS.version). */
					def withServiceFilterVersion(serviceFilterVersion: String) = new list(req.addQueryStringParameters("serviceFilter.version" -> serviceFilterVersion.toString))
					/** Optional. The exact value to match against [`ServiceContext.resource_type`](/error-reporting/reference/rest/v1beta1/ServiceContext#FIELDS.resource_type). */
					def withServiceFilterResourceType(serviceFilterResourceType: String) = new list(req.addQueryStringParameters("serviceFilter.resourceType" -> serviceFilterResourceType.toString))
					/** Optional. The preferred duration for a single returned TimedCount. If not set, no timed counts are returned.<br>Format: google-duration */
					def withTimedCountDuration(timedCountDuration: String) = new list(req.addQueryStringParameters("timedCountDuration" -> timedCountDuration.toString))
					/** Optional. The alignment of the timed counts to be returned. Default is `ALIGNMENT_EQUAL_AT_END`.<br>Possible values:<br>ERROR_COUNT_ALIGNMENT_UNSPECIFIED: No alignment specified.<br>ALIGNMENT_EQUAL_ROUNDED: The time periods shall be consecutive, have width equal to the requested duration, and be aligned at the alignment_time provided in the request. The alignment_time does not have to be inside the query period but even if it is outside, only time periods are returned which overlap with the query period. A rounded alignment will typically result in a different size of the first or the last time period.<br>ALIGNMENT_EQUAL_AT_END: The time periods shall be consecutive, have width equal to the requested duration, and be aligned at the end of the requested time period. This can result in a different size of the first time period. */
					def withAlignment(alignment: String) = new list(req.addQueryStringParameters("alignment" -> alignment.toString))
					/** Optional. Time where the timed counts shall be aligned if rounded alignment is chosen. Default is 00:00 UTC.<br>Format: google-datetime */
					def withAlignmentTime(alignmentTime: String) = new list(req.addQueryStringParameters("alignmentTime" -> alignmentTime.toString))
					/** Optional. The sort order in which the results are returned. Default is `COUNT_DESC`.<br>Possible values:<br>GROUP_ORDER_UNSPECIFIED: No group order specified.<br>COUNT_DESC: Total count of errors in the given time window in descending order.<br>LAST_SEEN_DESC: Timestamp when the group was last seen in the given time window in descending order.<br>CREATED_DESC: Timestamp when the group was created in descending order.<br>AFFECTED_USERS_DESC: Number of affected users in the given time window in descending order. */
					def withOrder(order: String) = new list(req.addQueryStringParameters("order" -> order.toString))
					/** Optional. The maximum number of results to return per response. Default is 20.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A next_page_token provided by a previous response. To view additional results, pass this token along with the identical query parameters as the first request. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListGroupStatsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, projectName: String, timeRangePeriod: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/groupStats").addQueryStringParameters("projectName" -> projectName.toString, "timeRange.period" -> timeRangePeriod.toString))
					given Conversion[list, Future[Schema.ListGroupStatsResponse]] = (fun: list) => fun.apply()
				}
			}
			object events {
				/** Lists the specified events. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEventsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The exact value to match against [`ServiceContext.service`](/error-reporting/reference/rest/v1beta1/ServiceContext#FIELDS.service). */
					def withServiceFilterService(serviceFilterService: String) = new list(req.addQueryStringParameters("serviceFilter.service" -> serviceFilterService.toString))
					/** Optional. The exact value to match against [`ServiceContext.version`](/error-reporting/reference/rest/v1beta1/ServiceContext#FIELDS.version). */
					def withServiceFilterVersion(serviceFilterVersion: String) = new list(req.addQueryStringParameters("serviceFilter.version" -> serviceFilterVersion.toString))
					/** Optional. The exact value to match against [`ServiceContext.resource_type`](/error-reporting/reference/rest/v1beta1/ServiceContext#FIELDS.resource_type). */
					def withServiceFilterResourceType(serviceFilterResourceType: String) = new list(req.addQueryStringParameters("serviceFilter.resourceType" -> serviceFilterResourceType.toString))
					/** Optional. The maximum number of results to return per response.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A `next_page_token` provided by a previous response. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEventsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, projectName: String, groupId: String, timeRangePeriod: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/events").addQueryStringParameters("projectName" -> projectName.toString, "groupId" -> groupId.toString, "timeRange.period" -> timeRangePeriod.toString))
					given Conversion[list, Future[Schema.ListEventsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object groups {
			/** Get the specified group. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ErrorGroup]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ErrorGroup])
			}
			object get {
				def apply(projectsId :PlayApi, groupsId :PlayApi, groupName: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/groups/${groupsId}").addQueryStringParameters("groupName" -> groupName.toString))
				given Conversion[get, Future[Schema.ErrorGroup]] = (fun: get) => fun.apply()
			}
			/** Replace the data for the specified group. Fails if the group does not exist. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withErrorGroup(body: Schema.ErrorGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ErrorGroup])
			}
			object update {
				def apply(projectsId :PlayApi, groupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/groups/${groupsId}").addQueryStringParameters("name" -> name.toString))
			}
		}
		object groupStats {
			/** Lists the specified groups. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListGroupStatsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. List all ErrorGroupStats with these IDs. The `group_id` is a unique identifier for a particular error group. The identifier is derived from key parts of the error-log content and is treated as Service Data. For information about how Service Data is handled, see [Google Cloud Privacy Notice] (https://cloud.google.com/terms/cloud-privacy-notice). */
				def withGroupId(groupId: String) = new list(req.addQueryStringParameters("groupId" -> groupId.toString))
				/** Optional. The exact value to match against [`ServiceContext.service`](/error-reporting/reference/rest/v1beta1/ServiceContext#FIELDS.service). */
				def withServiceFilterService(serviceFilterService: String) = new list(req.addQueryStringParameters("serviceFilter.service" -> serviceFilterService.toString))
				/** Optional. The exact value to match against [`ServiceContext.version`](/error-reporting/reference/rest/v1beta1/ServiceContext#FIELDS.version). */
				def withServiceFilterVersion(serviceFilterVersion: String) = new list(req.addQueryStringParameters("serviceFilter.version" -> serviceFilterVersion.toString))
				/** Optional. The exact value to match against [`ServiceContext.resource_type`](/error-reporting/reference/rest/v1beta1/ServiceContext#FIELDS.resource_type). */
				def withServiceFilterResourceType(serviceFilterResourceType: String) = new list(req.addQueryStringParameters("serviceFilter.resourceType" -> serviceFilterResourceType.toString))
				/** Optional. The preferred duration for a single returned TimedCount. If not set, no timed counts are returned.<br>Format: google-duration */
				def withTimedCountDuration(timedCountDuration: String) = new list(req.addQueryStringParameters("timedCountDuration" -> timedCountDuration.toString))
				/** Optional. The alignment of the timed counts to be returned. Default is `ALIGNMENT_EQUAL_AT_END`.<br>Possible values:<br>ERROR_COUNT_ALIGNMENT_UNSPECIFIED: No alignment specified.<br>ALIGNMENT_EQUAL_ROUNDED: The time periods shall be consecutive, have width equal to the requested duration, and be aligned at the alignment_time provided in the request. The alignment_time does not have to be inside the query period but even if it is outside, only time periods are returned which overlap with the query period. A rounded alignment will typically result in a different size of the first or the last time period.<br>ALIGNMENT_EQUAL_AT_END: The time periods shall be consecutive, have width equal to the requested duration, and be aligned at the end of the requested time period. This can result in a different size of the first time period. */
				def withAlignment(alignment: String) = new list(req.addQueryStringParameters("alignment" -> alignment.toString))
				/** Optional. Time where the timed counts shall be aligned if rounded alignment is chosen. Default is 00:00 UTC.<br>Format: google-datetime */
				def withAlignmentTime(alignmentTime: String) = new list(req.addQueryStringParameters("alignmentTime" -> alignmentTime.toString))
				/** Optional. The sort order in which the results are returned. Default is `COUNT_DESC`.<br>Possible values:<br>GROUP_ORDER_UNSPECIFIED: No group order specified.<br>COUNT_DESC: Total count of errors in the given time window in descending order.<br>LAST_SEEN_DESC: Timestamp when the group was last seen in the given time window in descending order.<br>CREATED_DESC: Timestamp when the group was created in descending order.<br>AFFECTED_USERS_DESC: Number of affected users in the given time window in descending order. */
				def withOrder(order: String) = new list(req.addQueryStringParameters("order" -> order.toString))
				/** Optional. The maximum number of results to return per response. Default is 20.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A next_page_token provided by a previous response. To view additional results, pass this token along with the identical query parameters as the first request. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListGroupStatsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, projectName: String, timeRangePeriod: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/groupStats").addQueryStringParameters("projectName" -> projectName.toString, "timeRange.period" -> timeRangePeriod.toString))
				given Conversion[list, Future[Schema.ListGroupStatsResponse]] = (fun: list) => fun.apply()
			}
		}
		object events {
			/** Lists the specified events. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEventsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The exact value to match against [`ServiceContext.service`](/error-reporting/reference/rest/v1beta1/ServiceContext#FIELDS.service). */
				def withServiceFilterService(serviceFilterService: String) = new list(req.addQueryStringParameters("serviceFilter.service" -> serviceFilterService.toString))
				/** Optional. The exact value to match against [`ServiceContext.version`](/error-reporting/reference/rest/v1beta1/ServiceContext#FIELDS.version). */
				def withServiceFilterVersion(serviceFilterVersion: String) = new list(req.addQueryStringParameters("serviceFilter.version" -> serviceFilterVersion.toString))
				/** Optional. The exact value to match against [`ServiceContext.resource_type`](/error-reporting/reference/rest/v1beta1/ServiceContext#FIELDS.resource_type). */
				def withServiceFilterResourceType(serviceFilterResourceType: String) = new list(req.addQueryStringParameters("serviceFilter.resourceType" -> serviceFilterResourceType.toString))
				/** Optional. The maximum number of results to return per response.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A `next_page_token` provided by a previous response. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEventsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, projectName: String, groupId: String, timeRangePeriod: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/events").addQueryStringParameters("projectName" -> projectName.toString, "groupId" -> groupId.toString, "timeRange.period" -> timeRangePeriod.toString))
				given Conversion[list, Future[Schema.ListEventsResponse]] = (fun: list) => fun.apply()
			}
			/** Report an individual error event and record the event to a log. This endpoint accepts &#42;&#42;either&#42;&#42; an OAuth token, &#42;&#42;or&#42;&#42; an [API key](https://support.google.com/cloud/answer/6158862) for authentication. To use an API key, append it to the URL as the value of a `key` parameter. For example: `POST https://clouderrorreporting.googleapis.com/v1beta1/{projectName}/events:report?key=123ABC456` &#42;&#42;Note:&#42;&#42; [Error Reporting] (https://cloud.google.com/error-reporting) is a service built on Cloud Logging and can analyze log entries when all of the following are true: &#42; Customer-managed encryption keys (CMEK) are disabled on the log bucket. &#42; The log bucket satisfies one of the following: &#42; The log bucket is stored in the same project where the logs originated. &#42; The logs were routed to a project, and then that project stored those logs in a log bucket that it owns. */
			class report(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withReportedErrorEvent(body: Schema.ReportedErrorEvent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReportErrorEventResponse])
			}
			object report {
				def apply(projectsId :PlayApi, projectName: String)(using signer: RequestSigner, ec: ExecutionContext): report = new report(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/events:report").addQueryStringParameters("projectName" -> projectName.toString))
			}
		}
	}
}
