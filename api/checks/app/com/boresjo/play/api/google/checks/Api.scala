package com.boresjo.play.api.google.checks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://checks.googleapis.com/"

	object accounts {
		object repos {
			object operations {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(accountsId :PlayApi, reposId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/repos/${reposId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
		}
		object apps {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChecksAccountV1alphaApp]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleChecksAccountV1alphaApp])
			}
			object get {
				def apply(accountsId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleChecksAccountV1alphaApp]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChecksAccountV1alphaListAppsResponse]) {
				/** Optional. A page token received from a previous `ListApps` call. Provide this to retrieve the subsequent page. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return. The server may further constrain the maximum number of results returned in a single page. If unspecified, the server will decide the number of results to be returned.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleChecksAccountV1alphaListAppsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleChecksAccountV1alphaListAppsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(accountsId :PlayApi, appsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
				}
				class _wait(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withWaitOperationRequest(body: Schema.WaitOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object _wait {
					def apply(accountsId :PlayApi, appsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): _wait = new _wait(auth(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/operations/${operationsId}:wait")).addQueryStringParameters("name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(accountsId :PlayApi, appsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(accountsId :PlayApi, appsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, appsId :PlayApi, name: String, filter: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
			}
			object reports {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChecksReportV1alphaListReportsResponse]) {
					/** Optional. An [AIP-160](https://google.aip.dev/160) filter string to filter reports. Example: `appBundle.releaseType = PRE_RELEASE` */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. A page token received from a previous `ListReports` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListReports` must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of reports to return. If unspecified, at most 10 reports will be returned. The maximum value is 50; values above 50 will be coerced to 50.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. An [AIP-160](https://google.aip.dev/160) filter string to filter checks within reports. Only checks that match the filter string are included in the response. Example: `state = FAILED` */
					def withChecksFilter(checksFilter: String) = new list(req.addQueryStringParameters("checksFilter" -> checksFilter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleChecksReportV1alphaListReportsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, appsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/reports")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleChecksReportV1alphaListReportsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleChecksReportV1alphaReport]) {
					/** Optional. An [AIP-160](https://google.aip.dev/160) filter string to filter checks within the report. Only checks that match the filter string are included in the response. Example: `state = FAILED` */
					def withChecksFilter(checksFilter: String) = new get(req.addQueryStringParameters("checksFilter" -> checksFilter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleChecksReportV1alphaReport])
				}
				object get {
					def apply(accountsId :PlayApi, appsId :PlayApi, reportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/reports/${reportsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleChecksReportV1alphaReport]] = (fun: get) => fun.apply()
				}
			}
		}
	}
	object aisafety {
		class classifyContent(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleChecksAisafetyV1alphaClassifyContentRequest(body: Schema.GoogleChecksAisafetyV1alphaClassifyContentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleChecksAisafetyV1alphaClassifyContentResponse])
		}
		object classifyContent {
			def apply()(using auth: AuthToken, ec: ExecutionContext): classifyContent = new classifyContent(auth(ws.url(BASE_URL + s"v1alpha/aisafety:classifyContent")).addQueryStringParameters())
		}
	}
	object media {
		class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleChecksReportV1alphaAnalyzeUploadRequest(body: Schema.GoogleChecksReportV1alphaAnalyzeUploadRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
		}
		object upload {
			def apply(accountsId :PlayApi, appsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(auth(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/reports:analyzeUpload")).addQueryStringParameters("parent" -> parent.toString))
		}
	}
}
