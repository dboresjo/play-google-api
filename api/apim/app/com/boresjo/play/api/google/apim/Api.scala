package com.boresjo.play.api.google.apim

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://apim.googleapis.com/"

	object projects {
		object locations {
			class listApiObservationTags(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListApiObservationTagsResponse]) {
				/** Optional. The maximum number of tags to return. The service may return fewer than this value. If unspecified, at most 10 tags will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new listApiObservationTags(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListApiObservationTags` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListApiObservationTags` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new listApiObservationTags(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListApiObservationTagsResponse])
			}
			object listApiObservationTags {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): listApiObservationTags = new listApiObservationTags(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}:listApiObservationTags")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[listApiObservationTags, Future[Schema.ListApiObservationTagsResponse]] = (fun: listApiObservationTags) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
				}
			}
			object observationJobs {
				class enable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEnableObservationJobRequest(body: Schema.EnableObservationJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object enable {
					def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): enable = new enable(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}:enable")).addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withObservationJob(body: Schema.ObservationJob) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, observationJobId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs")).addQueryStringParameters("parent" -> parent.toString, "observationJobId" -> observationJobId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class disable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDisableObservationJobRequest(body: Schema.DisableObservationJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object disable {
					def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): disable = new disable(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}:disable")).addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ObservationJob]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ObservationJob])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ObservationJob]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListObservationJobsResponse]) {
					/** Optional. The maximum number of ObservationJobs to return. The service may return fewer than this value. If unspecified, at most 10 ObservationJobs will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous `ListObservationJobs` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListObservationJobs` must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListObservationJobsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListObservationJobsResponse]] = (fun: list) => fun.apply()
				}
				object apiObservations {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ApiObservation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ApiObservation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, apiObservationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}/apiObservations/${apiObservationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ApiObservation]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListApiObservationsResponse]) {
						/** Optional. The maximum number of ApiObservations to return. The service may return fewer than this value. If unspecified, at most 10 ApiObservations will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token, received from a previous `ListApiObservations` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListApiObservations` must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListApiObservationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}/apiObservations")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListApiObservationsResponse]] = (fun: list) => fun.apply()
					}
					class batchEditTags(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBatchEditTagsApiObservationsRequest(body: Schema.BatchEditTagsApiObservationsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchEditTagsApiObservationsResponse])
					}
					object batchEditTags {
						def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchEditTags = new batchEditTags(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}/apiObservations:batchEditTags")).addQueryStringParameters("parent" -> parent.toString))
					}
					object apiOperations {
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ApiOperation]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ApiOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, apiObservationsId :PlayApi, apiOperationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}/apiObservations/${apiObservationsId}/apiOperations/${apiOperationsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.ApiOperation]] = (fun: get) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListApiOperationsResponse]) {
							/** Optional. The maximum number of ApiOperations to return. The service may return fewer than this value. If unspecified, at most 10 ApiOperations will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. A page token, received from a previous `ListApiApiOperations` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListApiApiOperations` must match the call that provided the page token. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListApiOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, apiObservationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}/apiObservations/${apiObservationsId}/apiOperations")).addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListApiOperationsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object observationSources {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withObservationSource(body: Schema.ObservationSource) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, observationSourceId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationSources")).addQueryStringParameters("parent" -> parent.toString, "observationSourceId" -> observationSourceId.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ObservationSource]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ObservationSource])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, observationSourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationSources/${observationSourcesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ObservationSource]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListObservationSourcesResponse]) {
					/** Optional. The maximum number of ObservationSources to return. The service may return fewer than this value. If unspecified, at most 10 ObservationSources will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous `ListObservationSources` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListObservationSources` must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListObservationSourcesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationSources")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListObservationSourcesResponse]] = (fun: list) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, observationSourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationSources/${observationSourcesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
			}
		}
	}
}
