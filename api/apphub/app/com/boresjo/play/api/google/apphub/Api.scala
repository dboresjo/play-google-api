package com.boresjo.play.api.google.apphub

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

	private val BASE_URL = "https://apphub.googleapis.com/"

	object projects {
		object locations {
			/** Detaches a service project from a host project. You can call this API from any service project without needing access to the host project that it is attached to. */
			class detachServiceProjectAttachment(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withDetachServiceProjectAttachmentRequest(body: Schema.DetachServiceProjectAttachmentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DetachServiceProjectAttachmentResponse])
			}
			object detachServiceProjectAttachment {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): detachServiceProjectAttachment = new detachServiceProjectAttachment(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:detachServiceProjectAttachment").addQueryStringParameters("name" -> name.toString))
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
			/** Lists a service project attachment for a given service project. You can call this API from any project to find if it is attached to a host project. */
			class lookupServiceProjectAttachment(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LookupServiceProjectAttachmentResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LookupServiceProjectAttachmentResponse])
			}
			object lookupServiceProjectAttachment {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): lookupServiceProjectAttachment = new lookupServiceProjectAttachment(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:lookupServiceProjectAttachment").addQueryStringParameters("name" -> name.toString))
				given Conversion[lookupServiceProjectAttachment, Future[Schema.LookupServiceProjectAttachmentResponse]] = (fun: lookupServiceProjectAttachment) => fun.apply()
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
			object applications {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Deletes an Application in a host project and location. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets an Application in a host project and location. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Application]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Application])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Application]] = (fun: get) => fun.apply()
				}
				/** Updates an Application in a host project and location. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withApplication(body: Schema.Application) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists Applications in a host project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListApplicationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results the server should return. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filtering results. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Hint for how to order the results. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListApplicationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListApplicationsResponse]] = (fun: list) => fun.apply()
				}
				/** Creates an Application in a host project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withApplication(body: Schema.Application) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, applicationId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications").addQueryStringParameters("parent" -> parent.toString, "applicationId" -> applicationId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object services {
					/** Creates a Service in an Application. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def withService(body: Schema.Service) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, parent: String, serviceId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/services").addQueryStringParameters("parent" -> parent.toString, "serviceId" -> serviceId.toString))
					}
					/** Deletes a Service from an Application. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, servicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/services/${servicesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Gets a Service in an Application. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Service]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Service])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, servicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/services/${servicesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Service]] = (fun: get) => fun.apply()
					}
					/** Updates a Service in an Application. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def withService(body: Schema.Service) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, servicesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/services/${servicesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists Services in an Application. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListServicesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token identifying a page of results the server should return. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Filtering results */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Hint for how to order the results */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListServicesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/services").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListServicesResponse]] = (fun: list) => fun.apply()
					}
				}
				object workloads {
					/** Creates a Workload in an Application. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def withWorkload(body: Schema.Workload) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, parent: String, workloadId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/workloads").addQueryStringParameters("parent" -> parent.toString, "workloadId" -> workloadId.toString))
					}
					/** Deletes a Workload from an Application. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, workloadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/workloads/${workloadsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Gets a Workload in an Application. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Workload]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Workload])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, workloadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/workloads/${workloadsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Workload]] = (fun: get) => fun.apply()
					}
					/** Updates a Workload in an Application. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def withWorkload(body: Schema.Workload) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, workloadsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/workloads/${workloadsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists Workloads in an Application. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListWorkloadsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token identifying a page of results the server should return. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Filtering results. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Hint for how to order the results. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListWorkloadsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, applicationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/applications/${applicationsId}/workloads").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListWorkloadsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object serviceProjectAttachments {
				/** Lists service projects attached to the host project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListServiceProjectAttachmentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results the server should return. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filtering results. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Hint for how to order the results. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListServiceProjectAttachmentsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/serviceProjectAttachments").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListServiceProjectAttachmentsResponse]] = (fun: list) => fun.apply()
				}
				/** Attaches a service project to the host project. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withServiceProjectAttachment(body: Schema.ServiceProjectAttachment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, serviceProjectAttachmentId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/serviceProjectAttachments").addQueryStringParameters("parent" -> parent.toString, "serviceProjectAttachmentId" -> serviceProjectAttachmentId.toString))
				}
				/** Gets a service project attachment. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ServiceProjectAttachment]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ServiceProjectAttachment])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, serviceProjectAttachmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/serviceProjectAttachments/${serviceProjectAttachmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ServiceProjectAttachment]] = (fun: get) => fun.apply()
				}
				/** Deletes a service project attachment. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, serviceProjectAttachmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/serviceProjectAttachments/${serviceProjectAttachmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
			}
			object discoveredServices {
				/** Lists Discovered Services that can be added to an Application in a host project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDiscoveredServicesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results the server should return. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filtering results. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Hint for how to order the results. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDiscoveredServicesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/discoveredServices").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListDiscoveredServicesResponse]] = (fun: list) => fun.apply()
				}
				/** Gets a Discovered Service in a host project and location. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DiscoveredService]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DiscoveredService])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, discoveredServicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/discoveredServices/${discoveredServicesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.DiscoveredService]] = (fun: get) => fun.apply()
				}
				/** Lists a Discovered Service in a host project and location, with a given resource URI. */
				class lookup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LookupDiscoveredServiceResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LookupDiscoveredServiceResponse])
				}
				object lookup {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, uri: String)(using signer: RequestSigner, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/discoveredServices:lookup").addQueryStringParameters("parent" -> parent.toString, "uri" -> uri.toString))
					given Conversion[lookup, Future[Schema.LookupDiscoveredServiceResponse]] = (fun: lookup) => fun.apply()
				}
			}
			object discoveredWorkloads {
				/** Lists Discovered Workloads that can be added to an Application in a host project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDiscoveredWorkloadsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results the server should return. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filtering results. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Hint for how to order the results. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDiscoveredWorkloadsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/discoveredWorkloads").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListDiscoveredWorkloadsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets a Discovered Workload in a host project and location. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DiscoveredWorkload]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DiscoveredWorkload])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, discoveredWorkloadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/discoveredWorkloads/${discoveredWorkloadsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.DiscoveredWorkload]] = (fun: get) => fun.apply()
				}
				/** Lists a Discovered Workload in a host project and location, with a given resource URI. */
				class lookup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LookupDiscoveredWorkloadResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LookupDiscoveredWorkloadResponse])
				}
				object lookup {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, uri: String)(using signer: RequestSigner, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/discoveredWorkloads:lookup").addQueryStringParameters("parent" -> parent.toString, "uri" -> uri.toString))
					given Conversion[lookup, Future[Schema.LookupDiscoveredWorkloadResponse]] = (fun: lookup) => fun.apply()
				}
			}
		}
	}
}
