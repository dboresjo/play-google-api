package com.boresjo.play.api.google.privateca

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

	private val BASE_URL = "https://privateca.googleapis.com/"

	object projects {
		object locations {
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
			object caPools {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** FetchCaCerts returns the current trust anchor for the CaPool. This will include CA certificate chains for all certificate authorities in the ENABLED, DISABLED, or STAGED states. */
				class fetchCaCerts(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withFetchCaCertsRequest(body: Schema.FetchCaCertsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FetchCaCertsResponse])
				}
				object fetchCaCerts {
					def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, caPool: String)(using signer: RequestSigner, ec: ExecutionContext): fetchCaCerts = new fetchCaCerts(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}:fetchCaCerts").addQueryStringParameters("caPool" -> caPool.toString))
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Delete a CaPool. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. This field allows this pool to be deleted even if it's being depended on by another resource. However, doing so may result in unintended and unrecoverable effects on any dependent resources since the pool will no longer be able to issue certificates. */
					def withIgnoreDependentResources(ignoreDependentResources: Boolean) = new delete(req.addQueryStringParameters("ignoreDependentResources" -> ignoreDependentResources.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Returns a CaPool. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CaPool]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CaPool])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.CaPool]] = (fun: get) => fun.apply()
				}
				/** Update a CaPool. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withCaPool(body: Schema.CaPool) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists CaPools. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCaPoolsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Limit on the number of CaPools to include in the response. Further CaPools can subsequently be obtained by including the ListCaPoolsResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Pagination token, returned earlier via ListCaPoolsResponse.next_page_token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Only include resources that match the filter in the response. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Specify how the results should be sorted. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCaPoolsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListCaPoolsResponse]] = (fun: list) => fun.apply()
				}
				/** Create a CaPool. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withCaPool(body: Schema.CaPool) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, caPoolId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools").addQueryStringParameters("parent" -> parent.toString, "caPoolId" -> caPoolId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object certificateAuthorities {
					/** Enable a CertificateAuthority. */
					class enable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withEnableCertificateAuthorityRequest(body: Schema.EnableCertificateAuthorityRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object enable {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): enable = new enable(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}:enable").addQueryStringParameters("name" -> name.toString))
					}
					/** Undelete a CertificateAuthority that has been deleted. */
					class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUndeleteCertificateAuthorityRequest(body: Schema.UndeleteCertificateAuthorityRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object undelete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}:undelete").addQueryStringParameters("name" -> name.toString))
					}
					/** Create a new CertificateAuthority in a given Project and Location. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def withCertificateAuthority(body: Schema.CertificateAuthority) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, parent: String, certificateAuthorityId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities").addQueryStringParameters("parent" -> parent.toString, "certificateAuthorityId" -> certificateAuthorityId.toString))
					}
					/** Delete a CertificateAuthority. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. This field allows the CA to be deleted even if the CA has active certs. Active certs include both unrevoked and unexpired certs. */
						def withIgnoreActiveCertificates(ignoreActiveCertificates: Boolean) = new delete(req.addQueryStringParameters("ignoreActiveCertificates" -> ignoreActiveCertificates.toString))
						/** Optional. If this flag is set, the Certificate Authority will be deleted as soon as possible without a 30-day grace period where undeletion would have been allowed. If you proceed, there will be no way to recover this CA. */
						def withSkipGracePeriod(skipGracePeriod: Boolean) = new delete(req.addQueryStringParameters("skipGracePeriod" -> skipGracePeriod.toString))
						/** Optional. This field allows this CA to be deleted even if it's being depended on by another resource. However, doing so may result in unintended and unrecoverable effects on any dependent resources since the CA will no longer be able to issue certificates. */
						def withIgnoreDependentResources(ignoreDependentResources: Boolean) = new delete(req.addQueryStringParameters("ignoreDependentResources" -> ignoreDependentResources.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Returns a CertificateAuthority. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CertificateAuthority]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CertificateAuthority])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.CertificateAuthority]] = (fun: get) => fun.apply()
					}
					/** Activate a CertificateAuthority that is in state AWAITING_USER_ACTIVATION and is of type SUBORDINATE. After the parent Certificate Authority signs a certificate signing request from FetchCertificateAuthorityCsr, this method can complete the activation process. */
					class activate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withActivateCertificateAuthorityRequest(body: Schema.ActivateCertificateAuthorityRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object activate {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}:activate").addQueryStringParameters("name" -> name.toString))
					}
					/** Update a CertificateAuthority. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def withCertificateAuthority(body: Schema.CertificateAuthority) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists CertificateAuthorities. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCertificateAuthoritiesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Limit on the number of CertificateAuthorities to include in the response. Further CertificateAuthorities can subsequently be obtained by including the ListCertificateAuthoritiesResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Pagination token, returned earlier via ListCertificateAuthoritiesResponse.next_page_token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Only include resources that match the filter in the response. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Specify how the results should be sorted. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCertificateAuthoritiesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListCertificateAuthoritiesResponse]] = (fun: list) => fun.apply()
					}
					/** Fetch a certificate signing request (CSR) from a CertificateAuthority that is in state AWAITING_USER_ACTIVATION and is of type SUBORDINATE. The CSR must then be signed by the desired parent Certificate Authority, which could be another CertificateAuthority resource, or could be an on-prem certificate authority. See also ActivateCertificateAuthority. */
					class fetch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchCertificateAuthorityCsrResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchCertificateAuthorityCsrResponse])
					}
					object fetch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): fetch = new fetch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}:fetch").addQueryStringParameters("name" -> name.toString))
						given Conversion[fetch, Future[Schema.FetchCertificateAuthorityCsrResponse]] = (fun: fetch) => fun.apply()
					}
					/** Disable a CertificateAuthority. */
					class disable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withDisableCertificateAuthorityRequest(body: Schema.DisableCertificateAuthorityRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object disable {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}:disable").addQueryStringParameters("name" -> name.toString))
					}
					object certificateRevocationLists {
						/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
						class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
						}
						object testIamPermissions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, certificateRevocationListsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}/certificateRevocationLists/${certificateRevocationListsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
						}
						/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
						class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
						}
						object setIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, certificateRevocationListsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}/certificateRevocationLists/${certificateRevocationListsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
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
							def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, certificateRevocationListsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}/certificateRevocationLists/${certificateRevocationListsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
							given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
						}
						/** Returns a CertificateRevocationList. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CertificateRevocationList]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CertificateRevocationList])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, certificateRevocationListsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}/certificateRevocationLists/${certificateRevocationListsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.CertificateRevocationList]] = (fun: get) => fun.apply()
						}
						/** Update a CertificateRevocationList. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
							def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
							/** Perform the request */
							def withCertificateRevocationList(body: Schema.CertificateRevocationList) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, certificateRevocationListsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}/certificateRevocationLists/${certificateRevocationListsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Lists CertificateRevocationLists. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCertificateRevocationListsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Limit on the number of CertificateRevocationLists to include in the response. Further CertificateRevocationLists can subsequently be obtained by including the ListCertificateRevocationListsResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. Pagination token, returned earlier via ListCertificateRevocationListsResponse.next_page_token. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Only include resources that match the filter in the response. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. Specify how the results should be sorted. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCertificateRevocationListsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificateAuthoritiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificateAuthorities/${certificateAuthoritiesId}/certificateRevocationLists").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListCertificateRevocationListsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object certificates {
					/** Create a new Certificate in a given Project, Location from a particular CaPool. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. It must be unique within a location and match the regular expression `[a-zA-Z0-9_-]{1,63}`. This field is required when using a CertificateAuthority in the Enterprise CertificateAuthority.tier, but is optional and its value is ignored otherwise. */
						def withCertificateId(certificateId: String) = new create(req.addQueryStringParameters("certificateId" -> certificateId.toString))
						/** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If this is true, no Certificate resource will be persisted regardless of the CaPool's tier, and the returned Certificate will not contain the pem_certificate field. */
						def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Optional. The resource ID of the CertificateAuthority that should issue the certificate. This optional field will ignore the load-balancing scheme of the Pool and directly issue the certificate from the CA with the specified ID, contained in the same CaPool referenced by `parent`. Per-CA quota rules apply. If left empty, a CertificateAuthority will be chosen from the CaPool by the service. For example, to issue a Certificate from a Certificate Authority with resource name "projects/my-project/locations/us-central1/caPools/my-pool/certificateAuthorities/my-ca", you can set the parent to "projects/my-project/locations/us-central1/caPools/my-pool" and the issuing_certificate_authority_id to "my-ca". */
						def withIssuingCertificateAuthorityId(issuingCertificateAuthorityId: String) = new create(req.addQueryStringParameters("issuingCertificateAuthorityId" -> issuingCertificateAuthorityId.toString))
						/** Perform the request */
						def withCertificate(body: Schema.Certificate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Certificate])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificates").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Revoke a Certificate. */
					class revoke(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withRevokeCertificateRequest(body: Schema.RevokeCertificateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Certificate])
					}
					object revoke {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): revoke = new revoke(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificates/${certificatesId}:revoke").addQueryStringParameters("name" -> name.toString))
					}
					/** Returns a Certificate. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Certificate]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Certificate])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificates/${certificatesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Certificate]] = (fun: get) => fun.apply()
					}
					/** Update a Certificate. Currently, the only field you can update is the labels field. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def withCertificate(body: Schema.Certificate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Certificate])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, certificatesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificates/${certificatesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists Certificates. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCertificatesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Limit on the number of Certificates to include in the response. Further Certificates can subsequently be obtained by including the ListCertificatesResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Pagination token, returned earlier via ListCertificatesResponse.next_page_token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Only include resources that match the filter in the response. For details on supported filters and syntax, see [Certificates Filtering documentation](https://cloud.google.com/certificate-authority-service/docs/sorting-filtering-certificates#filtering_support). */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Specify how the results should be sorted. For details on supported fields and syntax, see [Certificates Sorting documentation](https://cloud.google.com/certificate-authority-service/docs/sorting-filtering-certificates#sorting_support). */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCertificatesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, caPoolsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/caPools/${caPoolsId}/certificates").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListCertificatesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object certificateTemplates {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, certificateTemplatesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificateTemplates/${certificateTemplatesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, certificateTemplatesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificateTemplates/${certificateTemplatesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** DeleteCertificateTemplate deletes a CertificateTemplate. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, certificateTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificateTemplates/${certificateTemplatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Returns a CertificateTemplate. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CertificateTemplate]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CertificateTemplate])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, certificateTemplatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificateTemplates/${certificateTemplatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.CertificateTemplate]] = (fun: get) => fun.apply()
				}
				/** Update a CertificateTemplate. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withCertificateTemplate(body: Schema.CertificateTemplate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, certificateTemplatesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificateTemplates/${certificateTemplatesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists CertificateTemplates. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCertificateTemplatesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Limit on the number of CertificateTemplates to include in the response. Further CertificateTemplates can subsequently be obtained by including the ListCertificateTemplatesResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Pagination token, returned earlier via ListCertificateTemplatesResponse.next_page_token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Only include resources that match the filter in the response. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Specify how the results should be sorted. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCertificateTemplatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificateTemplates").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListCertificateTemplatesResponse]] = (fun: list) => fun.apply()
				}
				/** Create a new CertificateTemplate in a given Project and Location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withCertificateTemplate(body: Schema.CertificateTemplate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, certificateTemplateId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificateTemplates").addQueryStringParameters("parent" -> parent.toString, "certificateTemplateId" -> certificateTemplateId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, certificateTemplatesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificateTemplates/${certificateTemplatesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
			}
		}
	}
}
