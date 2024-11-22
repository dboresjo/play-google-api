package com.boresjo.play.api.google.alertcenter

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
		"""https://www.googleapis.com/auth/apps.alerts""" /* See and delete your domain's G Suite alerts, and send alert feedback */
	)

	private val BASE_URL = "https://alertcenter.googleapis.com/"

	object alerts {
		/** Performs batch undelete operation on alerts. */
		class batchUndelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.alerts""")
			/** Perform the request */
			def withBatchUndeleteAlertsRequest(body: Schema.BatchUndeleteAlertsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUndeleteAlertsResponse])
		}
		object batchUndelete {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): batchUndelete = new batchUndelete(ws.url(BASE_URL + s"v1beta1/alerts:batchUndelete").addQueryStringParameters())
		}
		/** Performs batch delete operation on alerts. */
		class batchDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.alerts""")
			/** Perform the request */
			def withBatchDeleteAlertsRequest(body: Schema.BatchDeleteAlertsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchDeleteAlertsResponse])
		}
		object batchDelete {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v1beta1/alerts:batchDelete").addQueryStringParameters())
		}
		/** Marks the specified alert for deletion. An alert that has been marked for deletion is removed from Alert Center after 30 days. Marking an alert for deletion has no effect on an alert which has already been marked for deletion. Attempting to mark a nonexistent alert for deletion results in a `NOT_FOUND` error. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.alerts""")
			/** Optional. The unique identifier of the Google Workspace account of the customer the alert is associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
			def withCustomerId(customerId: String) = new delete(req.addQueryStringParameters("customerId" -> customerId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(alertId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/alerts/${alertId}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Gets the specified alert. Attempting to get a nonexistent alert returns `NOT_FOUND` error. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Alert]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.alerts""")
			/** Optional. The unique identifier of the Google Workspace account of the customer the alert is associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
			def withCustomerId(customerId: String) = new get(req.addQueryStringParameters("customerId" -> customerId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Alert])
		}
		object get {
			def apply(alertId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/alerts/${alertId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Alert]] = (fun: get) => fun.apply()
		}
		/** Lists the alerts. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAlertsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.alerts""")
			/** Optional. The unique identifier of the Google Workspace account of the customer the alerts are associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
			def withCustomerId(customerId: String) = new list(req.addQueryStringParameters("customerId" -> customerId.toString))
			/** Optional. The requested page size. Server may return fewer items than requested. If unspecified, server picks an appropriate default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A token identifying a page of results the server should return. If empty, a new iteration is started. To continue an iteration, pass in the value from the previous ListAlertsResponse's next_page_token field. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. A query string for filtering alert results. For more details, see [Query filters](https://developers.google.com/admin-sdk/alertcenter/guides/query-filters) and [Supported query filter fields](https://developers.google.com/admin-sdk/alertcenter/reference/filter-fields#alerts.list). */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Optional. The sort order of the list results. If not specified results may be returned in arbitrary order. You can sort the results in descending order based on the creation timestamp using `order_by="create_time desc"`. Currently, supported sorting are `create_time asc`, `create_time desc`, `update_time desc` */
			def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAlertsResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/alerts").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListAlertsResponse]] = (fun: list) => fun.apply()
		}
		/** Restores, or "undeletes", an alert that was marked for deletion within the past 30 days. Attempting to undelete an alert which was marked for deletion over 30 days ago (which has been removed from the Alert Center database) or a nonexistent alert returns a `NOT_FOUND` error. Attempting to undelete an alert which has not been marked for deletion has no effect. */
		class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.alerts""")
			/** Perform the request */
			def withUndeleteAlertRequest(body: Schema.UndeleteAlertRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Alert])
		}
		object undelete {
			def apply(alertId: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v1beta1/alerts/${alertId}:undelete").addQueryStringParameters())
		}
		/** Returns the metadata of an alert. Attempting to get metadata for a non-existent alert returns `NOT_FOUND` error. */
		class getMetadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AlertMetadata]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.alerts""")
			/** Optional. The unique identifier of the Google Workspace account of the customer the alert metadata is associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
			def withCustomerId(customerId: String) = new getMetadata(req.addQueryStringParameters("customerId" -> customerId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AlertMetadata])
		}
		object getMetadata {
			def apply(alertId: String)(using signer: RequestSigner, ec: ExecutionContext): getMetadata = new getMetadata(ws.url(BASE_URL + s"v1beta1/alerts/${alertId}/metadata").addQueryStringParameters())
			given Conversion[getMetadata, Future[Schema.AlertMetadata]] = (fun: getMetadata) => fun.apply()
		}
		object feedback {
			/** Creates new feedback for an alert. Attempting to create a feedback for a non-existent alert returns `NOT_FOUND` error. Attempting to create a feedback for an alert that is marked for deletion returns `FAILED_PRECONDITION' error. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.alerts""")
				/** Optional. The unique identifier of the Google Workspace account of the customer the alert is associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
				def withCustomerId(customerId: String) = new create(req.addQueryStringParameters("customerId" -> customerId.toString))
				/** Perform the request */
				def withAlertFeedback(body: Schema.AlertFeedback) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AlertFeedback])
			}
			object create {
				def apply(alertId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/alerts/${alertId}/feedback").addQueryStringParameters())
			}
			/** Lists all the feedback for an alert. Attempting to list feedbacks for a non-existent alert returns `NOT_FOUND` error. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAlertFeedbackResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.alerts""")
				/** Optional. The unique identifier of the Google Workspace account of the customer the alert is associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
				def withCustomerId(customerId: String) = new list(req.addQueryStringParameters("customerId" -> customerId.toString))
				/** Optional. A query string for filtering alert feedback results. For more details, see [Query filters](https://developers.google.com/admin-sdk/alertcenter/guides/query-filters) and [Supported query filter fields](https://developers.google.com/admin-sdk/alertcenter/reference/filter-fields#alerts.feedback.list). */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAlertFeedbackResponse])
			}
			object list {
				def apply(alertId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/alerts/${alertId}/feedback").addQueryStringParameters())
				given Conversion[list, Future[Schema.ListAlertFeedbackResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object v1beta1 {
		/** Returns customer-level settings. */
		class getSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Settings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.alerts""")
			/** Optional. The unique identifier of the Google Workspace account of the customer the alert settings are associated with. The `customer_id` must/ have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
			def withCustomerId(customerId: String) = new getSettings(req.addQueryStringParameters("customerId" -> customerId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Settings])
		}
		object getSettings {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v1beta1/settings").addQueryStringParameters())
			given Conversion[getSettings, Future[Schema.Settings]] = (fun: getSettings) => fun.apply()
		}
		/** Updates the customer-level settings. */
		class updateSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.alerts""")
			/** Optional. The unique identifier of the Google Workspace account of the customer the alert settings are associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
			def withCustomerId(customerId: String) = new updateSettings(req.addQueryStringParameters("customerId" -> customerId.toString))
			/** Perform the request */
			def withSettings(body: Schema.Settings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Settings])
		}
		object updateSettings {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): updateSettings = new updateSettings(ws.url(BASE_URL + s"v1beta1/settings").addQueryStringParameters())
		}
	}
}
