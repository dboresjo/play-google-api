package com.boresjo.play.api.google.checks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq()

	private val BASE_URL = "https://checks.googleapis.com/"

	object accounts {
		object repos {
			object operations {
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq()
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(accountsId :PlayApi, reposId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/repos/${reposId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
		}
		object apps {
			/** Gets an app. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleChecksAccountV1alphaApp]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleChecksAccountV1alphaApp])
			}
			object get {
				def apply(accountsId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleChecksAccountV1alphaApp]] = (fun: get) => fun.apply()
			}
			/** Lists the apps under the given account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleChecksAccountV1alphaListAppsResponse]) {
				val scopes = Seq()
				/** Optional. A page token received from a previous `ListApps` call. Provide this to retrieve the subsequent page. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return. The server may further constrain the maximum number of results returned in a single page. If unspecified, the server will decide the number of results to be returned.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleChecksAccountV1alphaListAppsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleChecksAccountV1alphaListAppsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of `1`, corresponding to `Code.CANCELLED`. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq()
					/** Perform the request */
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(accountsId :PlayApi, appsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
				/** Waits until the specified long-running operation is done or reaches at most a specified timeout, returning the latest state. If the operation is already done, the latest state is immediately returned. If the timeout specified is greater than the default HTTP/RPC timeout, the HTTP/RPC timeout is used. If the server does not support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Note that this method is on a best-effort basis. It may return the latest state before the specified timeout (including immediately), meaning even an immediate response is no guarantee that the operation is done. */
				class _wait(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq()
					/** Perform the request */
					def withWaitOperationRequest(body: Schema.WaitOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object _wait {
					def apply(accountsId :PlayApi, appsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): _wait = new _wait(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/operations/${operationsId}:wait").addQueryStringParameters("name" -> name.toString))
				}
				/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq()
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(accountsId :PlayApi, appsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq()
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(accountsId :PlayApi, appsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq()
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, appsId :PlayApi, name: String, filter: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
			}
			object reports {
				/** Lists reports for the specified app. By default, only the name and results_uri fields are returned. You can include other fields by listing them in the `fields` URL query parameter. For example, `?fields=reports(name,checks)` will return the name and checks fields. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleChecksReportV1alphaListReportsResponse]) {
					val scopes = Seq()
					/** Optional. An [AIP-160](https://google.aip.dev/160) filter string to filter reports. Example: `appBundle.releaseType = PRE_RELEASE` */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. A page token received from a previous `ListReports` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListReports` must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of reports to return. If unspecified, at most 10 reports will be returned. The maximum value is 50; values above 50 will be coerced to 50.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. An [AIP-160](https://google.aip.dev/160) filter string to filter checks within reports. Only checks that match the filter string are included in the response. Example: `state = FAILED` */
					def withChecksFilter(checksFilter: String) = new list(req.addQueryStringParameters("checksFilter" -> checksFilter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleChecksReportV1alphaListReportsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, appsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/reports").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleChecksReportV1alphaListReportsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets a report. By default, only the name and results_uri fields are returned. You can include other fields by listing them in the `fields` URL query parameter. For example, `?fields=name,checks` will return the name and checks fields. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleChecksReportV1alphaReport]) {
					val scopes = Seq()
					/** Optional. An [AIP-160](https://google.aip.dev/160) filter string to filter checks within the report. Only checks that match the filter string are included in the response. Example: `state = FAILED` */
					def withChecksFilter(checksFilter: String) = new get(req.addQueryStringParameters("checksFilter" -> checksFilter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleChecksReportV1alphaReport])
				}
				object get {
					def apply(accountsId :PlayApi, appsId :PlayApi, reportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/reports/${reportsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleChecksReportV1alphaReport]] = (fun: get) => fun.apply()
				}
			}
		}
	}
	object aisafety {
		/** Analyze a piece of content with the provided set of policies. */
		class classifyContent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withGoogleChecksAisafetyV1alphaClassifyContentRequest(body: Schema.GoogleChecksAisafetyV1alphaClassifyContentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleChecksAisafetyV1alphaClassifyContentResponse])
		}
		object classifyContent {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): classifyContent = new classifyContent(ws.url(BASE_URL + s"v1alpha/aisafety:classifyContent").addQueryStringParameters())
		}
	}
	object media {
		/** Analyzes the uploaded app bundle and returns a google.longrunning.Operation containing the generated Report. ## Example (upload only) Send a regular POST request with the header `X-Goog-Upload-Protocol: raw`. ``` POST https://checks.googleapis.com/upload/v1alpha/{parent=accounts/&#42;/apps/&#42;}/reports:analyzeUpload HTTP/1.1 X-Goog-Upload-Protocol: raw Content-Length: Content-Type: application/octet-stream ``` ## Example (upload with metadata) Send a multipart POST request where the first body part contains the metadata JSON and the second body part contains the binary upload. Include the header `X-Goog-Upload-Protocol: multipart`. ``` POST https://checks.googleapis.com/upload/v1alpha/{parent=accounts/&#42;/apps/&#42;}/reports:analyzeUpload HTTP/1.1 X-Goog-Upload-Protocol: multipart Content-Length: ? Content-Type: multipart/related; boundary=BOUNDARY --BOUNDARY Content-Type: application/json {"code_reference_id":"db5bcc20f94055fb5bc08cbb9b0e7a5530308786"} --BOUNDARY --BOUNDARY-- ``` &#42;Note:&#42; Metadata-only requests are not supported.  */
		class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withGoogleChecksReportV1alphaAnalyzeUploadRequest(body: Schema.GoogleChecksReportV1alphaAnalyzeUploadRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object upload {
			def apply(accountsId :PlayApi, appsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1alpha/accounts/${accountsId}/apps/${appsId}/reports:analyzeUpload").addQueryStringParameters("parent" -> parent.toString))
		}
	}
}
