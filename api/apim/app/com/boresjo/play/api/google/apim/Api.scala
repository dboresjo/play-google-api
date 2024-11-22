package com.boresjo.play.api.google.apim

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

	private val BASE_URL = "https://apim.googleapis.com/"

	object projects {
		object locations {
			/** ListApiObservationTags lists all extant tags on any observation in the given project. */
			class listApiObservationTags(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListApiObservationTagsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum number of tags to return. The service may return fewer than this value. If unspecified, at most 10 tags will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new listApiObservationTags(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListApiObservationTags` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListApiObservationTags` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new listApiObservationTags(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListApiObservationTagsResponse])
			}
			object listApiObservationTags {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): listApiObservationTags = new listApiObservationTags(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}:listApiObservationTags").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[listApiObservationTags, Future[Schema.ListApiObservationTagsResponse]] = (fun: listApiObservationTags) => fun.apply()
			}
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object observationJobs {
				/** Enables the given ObservationJob. */
				class enable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEnableObservationJobRequest(body: Schema.EnableObservationJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object enable {
					def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): enable = new enable(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}:enable").addQueryStringParameters("name" -> name.toString))
				}
				/** CreateObservationJob creates a new ObservationJob but does not have any effecton its own. It is a configuration that can be used in an Observation Job to collect data about existing APIs. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withObservationJob(body: Schema.ObservationJob) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, observationJobId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs").addQueryStringParameters("parent" -> parent.toString, "observationJobId" -> observationJobId.toString))
				}
				/** DeleteObservationJob deletes an ObservationJob. This method will fail if the observation job is currently being used by any ObservationSource, even if not enabled. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Disables the given ObservationJob. */
				class disable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDisableObservationJobRequest(body: Schema.DisableObservationJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object disable {
					def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}:disable").addQueryStringParameters("name" -> name.toString))
				}
				/** GetObservationJob retrieves a single ObservationJob by name. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ObservationJob]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ObservationJob])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ObservationJob]] = (fun: get) => fun.apply()
				}
				/** ListObservationJobs gets all ObservationJobs for a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListObservationJobsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum number of ObservationJobs to return. The service may return fewer than this value. If unspecified, at most 10 ObservationJobs will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous `ListObservationJobs` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListObservationJobs` must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListObservationJobsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListObservationJobsResponse]] = (fun: list) => fun.apply()
				}
				object apiObservations {
					/** GetApiObservation retrieves a single ApiObservation by name. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ApiObservation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ApiObservation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, apiObservationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}/apiObservations/${apiObservationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ApiObservation]] = (fun: get) => fun.apply()
					}
					/** ListApiObservations gets all ApiObservations for a given project and location and ObservationJob. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListApiObservationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum number of ApiObservations to return. The service may return fewer than this value. If unspecified, at most 10 ApiObservations will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token, received from a previous `ListApiObservations` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListApiObservations` must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListApiObservationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}/apiObservations").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListApiObservationsResponse]] = (fun: list) => fun.apply()
					}
					/** BatchEditTagsApiObservations adds or removes Tags for ApiObservations. */
					class batchEditTags(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withBatchEditTagsApiObservationsRequest(body: Schema.BatchEditTagsApiObservationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchEditTagsApiObservationsResponse])
					}
					object batchEditTags {
						def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchEditTags = new batchEditTags(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}/apiObservations:batchEditTags").addQueryStringParameters("parent" -> parent.toString))
					}
					object apiOperations {
						/** GetApiOperation retrieves a single ApiOperation by name. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ApiOperation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ApiOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, apiObservationsId :PlayApi, apiOperationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}/apiObservations/${apiObservationsId}/apiOperations/${apiOperationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.ApiOperation]] = (fun: get) => fun.apply()
						}
						/** ListApiOperations gets all ApiOperations for a given project and location and ObservationJob and ApiObservation. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListApiOperationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. The maximum number of ApiOperations to return. The service may return fewer than this value. If unspecified, at most 10 ApiOperations will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. A page token, received from a previous `ListApiApiOperations` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListApiApiOperations` must match the call that provided the page token. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListApiOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, observationJobsId :PlayApi, apiObservationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationJobs/${observationJobsId}/apiObservations/${apiObservationsId}/apiOperations").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListApiOperationsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object observationSources {
				/** CreateObservationSource creates a new ObservationSource but does not affect any deployed infrastructure. It is a configuration that can be used in an Observation Job to collect data about APIs running in user's dataplane. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withObservationSource(body: Schema.ObservationSource) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, observationSourceId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationSources").addQueryStringParameters("parent" -> parent.toString, "observationSourceId" -> observationSourceId.toString))
				}
				/** GetObservationSource retrieves a single ObservationSource by name. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ObservationSource]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ObservationSource])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, observationSourcesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationSources/${observationSourcesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ObservationSource]] = (fun: get) => fun.apply()
				}
				/** ListObservationSources gets all ObservationSources for a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListObservationSourcesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum number of ObservationSources to return. The service may return fewer than this value. If unspecified, at most 10 ObservationSources will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous `ListObservationSources` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListObservationSources` must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListObservationSourcesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationSources").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListObservationSourcesResponse]] = (fun: list) => fun.apply()
				}
				/** DeleteObservationSource deletes an observation source. This method will fail if the observation source is currently being used by any ObservationJob, even if not enabled. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, observationSourcesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha/projects/${projectsId}/locations/${locationsId}/observationSources/${observationSourcesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
			}
		}
	}
}
