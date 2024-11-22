package com.boresjo.play.api.google.alertcenter

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://alertcenter.googleapis.com/"

	object alerts {
		class batchUndelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBatchUndeleteAlertsRequest(body: Schema.BatchUndeleteAlertsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUndeleteAlertsResponse])
		}
		object batchUndelete {
			def apply()(using auth: AuthToken, ec: ExecutionContext): batchUndelete = new batchUndelete(ws.url(BASE_URL + s"v1beta1/alerts:batchUndelete").addQueryStringParameters())
		}
		class batchDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBatchDeleteAlertsRequest(body: Schema.BatchDeleteAlertsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchDeleteAlertsResponse])
		}
		object batchDelete {
			def apply()(using auth: AuthToken, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v1beta1/alerts:batchDelete").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			/** Optional. The unique identifier of the Google Workspace account of the customer the alert is associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
			def withCustomerId(customerId: String) = new delete(req.addQueryStringParameters("customerId" -> customerId.toString))
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(alertId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/alerts/${alertId}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Alert]) {
			/** Optional. The unique identifier of the Google Workspace account of the customer the alert is associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
			def withCustomerId(customerId: String) = new get(req.addQueryStringParameters("customerId" -> customerId.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Alert])
		}
		object get {
			def apply(alertId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/alerts/${alertId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Alert]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAlertsResponse]) {
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
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAlertsResponse])
		}
		object list {
			def apply()(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/alerts").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListAlertsResponse]] = (fun: list) => fun.apply()
		}
		class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUndeleteAlertRequest(body: Schema.UndeleteAlertRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Alert])
		}
		object undelete {
			def apply(alertId: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v1beta1/alerts/${alertId}:undelete").addQueryStringParameters())
		}
		class getMetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AlertMetadata]) {
			/** Optional. The unique identifier of the Google Workspace account of the customer the alert metadata is associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
			def withCustomerId(customerId: String) = new getMetadata(req.addQueryStringParameters("customerId" -> customerId.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AlertMetadata])
		}
		object getMetadata {
			def apply(alertId: String)(using auth: AuthToken, ec: ExecutionContext): getMetadata = new getMetadata(ws.url(BASE_URL + s"v1beta1/alerts/${alertId}/metadata").addQueryStringParameters())
			given Conversion[getMetadata, Future[Schema.AlertMetadata]] = (fun: getMetadata) => fun.apply()
		}
		object feedback {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. The unique identifier of the Google Workspace account of the customer the alert is associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
				def withCustomerId(customerId: String) = new create(req.addQueryStringParameters("customerId" -> customerId.toString))
				def withAlertFeedback(body: Schema.AlertFeedback) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AlertFeedback])
			}
			object create {
				def apply(alertId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/alerts/${alertId}/feedback").addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAlertFeedbackResponse]) {
				/** Optional. The unique identifier of the Google Workspace account of the customer the alert is associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
				def withCustomerId(customerId: String) = new list(req.addQueryStringParameters("customerId" -> customerId.toString))
				/** Optional. A query string for filtering alert feedback results. For more details, see [Query filters](https://developers.google.com/admin-sdk/alertcenter/guides/query-filters) and [Supported query filter fields](https://developers.google.com/admin-sdk/alertcenter/reference/filter-fields#alerts.feedback.list). */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAlertFeedbackResponse])
			}
			object list {
				def apply(alertId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/alerts/${alertId}/feedback").addQueryStringParameters())
				given Conversion[list, Future[Schema.ListAlertFeedbackResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object v1beta1 {
		class getSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Settings]) {
			/** Optional. The unique identifier of the Google Workspace account of the customer the alert settings are associated with. The `customer_id` must/ have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
			def withCustomerId(customerId: String) = new getSettings(req.addQueryStringParameters("customerId" -> customerId.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Settings])
		}
		object getSettings {
			def apply()(using auth: AuthToken, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v1beta1/settings").addQueryStringParameters())
			given Conversion[getSettings, Future[Schema.Settings]] = (fun: getSettings) => fun.apply()
		}
		class updateSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The unique identifier of the Google Workspace account of the customer the alert settings are associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
			def withCustomerId(customerId: String) = new updateSettings(req.addQueryStringParameters("customerId" -> customerId.toString))
			def withSettings(body: Schema.Settings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Settings])
		}
		object updateSettings {
			def apply()(using auth: AuthToken, ec: ExecutionContext): updateSettings = new updateSettings(ws.url(BASE_URL + s"v1beta1/settings").addQueryStringParameters())
		}
	}
}
