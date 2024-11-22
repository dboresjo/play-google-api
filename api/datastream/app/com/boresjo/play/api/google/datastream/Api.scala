package com.boresjo.play.api.google.datastream

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

	private val BASE_URL = "https://datastream.googleapis.com/"

	object projects {
		object locations {
			/** The FetchStaticIps API call exposes the static IP addresses used by Datastream. */
			class fetchStaticIps(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchStaticIpsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchStaticIpsResponse])
			}
			object fetchStaticIps {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): fetchStaticIps = new fetchStaticIps(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:fetchStaticIps").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[fetchStaticIps, Future[Schema.FetchStaticIpsResponse]] = (fun: fetchStaticIps) => fun.apply()
			}
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object privateConnections {
				/** Use this method to create a private connectivity configuration. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set to true, will skip validations. */
					def withForce(force: Boolean) = new create(req.addQueryStringParameters("force" -> force.toString))
					/** Perform the request */
					def withPrivateConnection(body: Schema.PrivateConnection) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, privateConnectionId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections").addQueryStringParameters("parent" -> parent.toString, "privateConnectionId" -> privateConnectionId.toString))
				}
				/** Use this method to delete a private connectivity configuration. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set to true, any child routes that belong to this PrivateConnection will also be deleted. */
					def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Use this method to get details about a private connectivity configuration. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PrivateConnection]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PrivateConnection])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.PrivateConnection]] = (fun: get) => fun.apply()
				}
				/** Use this method to list private connectivity configurations in a project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPrivateConnectionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPrivateConnectionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListPrivateConnectionsResponse]] = (fun: list) => fun.apply()
				}
				object routes {
					/** Use this method to create a route for a private connectivity configuration in a project and location. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def withRoute(body: Schema.Route) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, parent: String, routeId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}/routes").addQueryStringParameters("parent" -> parent.toString, "routeId" -> routeId.toString))
					}
					/** Use this method to get details about a route. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Route]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Route])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, routesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}/routes/${routesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Route]] = (fun: get) => fun.apply()
					}
					/** Use this method to list routes created for a private connectivity configuration in a project and location. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRoutesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRoutesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}/routes").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.ListRoutesResponse]] = (fun: list) => fun.apply()
					}
					/** Use this method to delete a route. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, routesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}/routes/${routesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
			}
			object streams {
				/** Use this method to start, resume or recover a stream with a non default CDC strategy. */
				class run(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRunStreamRequest(body: Schema.RunStreamRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object run {
					def apply(projectsId :PlayApi, locationsId :PlayApi, streamsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): run = new run(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/streams/${streamsId}:run").addQueryStringParameters("name" -> name.toString))
				}
				/** Use this method to create a stream. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. Only validate the stream, but don't create any resources. The default is false. */
					def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. Create the stream without validating it. */
					def withForce(force: Boolean) = new create(req.addQueryStringParameters("force" -> force.toString))
					/** Perform the request */
					def withStream(body: Schema.Stream) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, streamId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/streams").addQueryStringParameters("parent" -> parent.toString, "streamId" -> streamId.toString))
				}
				/** Use this method to delete a stream. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, streamsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/streams/${streamsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Use this method to get details about a stream. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Stream]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Stream])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, streamsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/streams/${streamsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Stream]] = (fun: get) => fun.apply()
				}
				/** Use this method to update the configuration of a stream. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Field mask is used to specify the fields to be overwritten in the stream resource by the update. The fields specified in the update_mask are relative to the resource, not the full request. A field will be overwritten if it is in the mask. If the user does not provide a mask then all fields will be overwritten.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. Only validate the stream with the changes, without actually updating it. The default is false. */
					def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. Update the stream without validating it. */
					def withForce(force: Boolean) = new patch(req.addQueryStringParameters("force" -> force.toString))
					/** Perform the request */
					def withStream(body: Schema.Stream) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, streamsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/streams/${streamsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Use this method to list streams in a project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListStreamsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListStreamsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/streams").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListStreamsResponse]] = (fun: list) => fun.apply()
				}
				object objects {
					/** Use this method to look up a stream object by its source object identifier. */
					class lookup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withLookupStreamObjectRequest(body: Schema.LookupStreamObjectRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.StreamObject])
					}
					object lookup {
						def apply(projectsId :PlayApi, locationsId :PlayApi, streamsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/streams/${streamsId}/objects:lookup").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Use this method to stop a backfill job for the specified stream object. */
					class stopBackfillJob(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withStopBackfillJobRequest(body: Schema.StopBackfillJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.StopBackfillJobResponse])
					}
					object stopBackfillJob {
						def apply(projectsId :PlayApi, locationsId :PlayApi, streamsId :PlayApi, objectsId :PlayApi, `object`: String)(using signer: RequestSigner, ec: ExecutionContext): stopBackfillJob = new stopBackfillJob(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/streams/${streamsId}/objects/${objectsId}:stopBackfillJob").addQueryStringParameters("object" -> `object`.toString))
					}
					/** Use this method to start a backfill job for the specified stream object. */
					class startBackfillJob(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withStartBackfillJobRequest(body: Schema.StartBackfillJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.StartBackfillJobResponse])
					}
					object startBackfillJob {
						def apply(projectsId :PlayApi, locationsId :PlayApi, streamsId :PlayApi, objectsId :PlayApi, `object`: String)(using signer: RequestSigner, ec: ExecutionContext): startBackfillJob = new startBackfillJob(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/streams/${streamsId}/objects/${objectsId}:startBackfillJob").addQueryStringParameters("object" -> `object`.toString))
					}
					/** Use this method to get details about a stream object. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.StreamObject]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.StreamObject])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, streamsId :PlayApi, objectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/streams/${streamsId}/objects/${objectsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.StreamObject]] = (fun: get) => fun.apply()
					}
					/** Use this method to list the objects of a specific stream. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListStreamObjectsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListStreamObjectsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, streamsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/streams/${streamsId}/objects").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListStreamObjectsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object connectionProfiles {
				/** Use this method to create a connection profile in a project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. Only validate the connection profile, but don't create any resources. The default is false. */
					def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. Create the connection profile without validating it. */
					def withForce(force: Boolean) = new create(req.addQueryStringParameters("force" -> force.toString))
					/** Perform the request */
					def withConnectionProfile(body: Schema.ConnectionProfile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, connectionProfileId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles").addQueryStringParameters("parent" -> parent.toString, "connectionProfileId" -> connectionProfileId.toString))
				}
				/** Use this method to discover a connection profile. The discover API call exposes the data objects and metadata belonging to the profile. Typically, a request returns children data objects of a parent data object that's optionally supplied in the request. */
				class discover(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDiscoverConnectionProfileRequest(body: Schema.DiscoverConnectionProfileRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DiscoverConnectionProfileResponse])
				}
				object discover {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): discover = new discover(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles:discover").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Use this method to delete a connection profile. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Use this method to get details about a connection profile. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ConnectionProfile]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ConnectionProfile])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ConnectionProfile]] = (fun: get) => fun.apply()
				}
				/** Use this method to update the parameters of a connection profile. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Field mask is used to specify the fields to be overwritten in the ConnectionProfile resource by the update. The fields specified in the update_mask are relative to the resource, not the full request. A field will be overwritten if it is in the mask. If the user does not provide a mask then all fields will be overwritten.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. Only validate the connection profile, but don't update any resources. The default is false. */
					def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. Update the connection profile without validating it. */
					def withForce(force: Boolean) = new patch(req.addQueryStringParameters("force" -> force.toString))
					/** Perform the request */
					def withConnectionProfile(body: Schema.ConnectionProfile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Use this method to list connection profiles created in a project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListConnectionProfilesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListConnectionProfilesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListConnectionProfilesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
