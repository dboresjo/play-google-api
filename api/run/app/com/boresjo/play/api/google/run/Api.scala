package com.boresjo.play.api.google.run

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

	private val BASE_URL = "https://run.googleapis.com/"

	object projects {
		object locations {
			/** Export image metadata for a given resource. */
			class exportImageMetadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Metadata]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2Metadata])
			}
			object exportImageMetadata {
				def apply(projectsId :PlayApi, locationsId :PlayApi, locationsId1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): exportImageMetadata = new exportImageMetadata(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/${locationsId1}:exportImageMetadata").addQueryStringParameters("name" -> name.toString))
				given Conversion[exportImageMetadata, Future[Schema.GoogleCloudRunV2Metadata]] = (fun: exportImageMetadata) => fun.apply()
			}
			/** Export image for a given resource. */
			class exportImage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudRunV2ExportImageRequest(body: Schema.GoogleCloudRunV2ExportImageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRunV2ExportImageResponse])
			}
			object exportImage {
				def apply(projectsId :PlayApi, locationsId :PlayApi, locationsId1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): exportImage = new exportImage(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/${locationsId1}:exportImage").addQueryStringParameters("name" -> name.toString))
			}
			/** Export generated customer metadata for a given resource. */
			class exportMetadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Metadata]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2Metadata])
			}
			object exportMetadata {
				def apply(projectsId :PlayApi, locationsId :PlayApi, locationsId1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): exportMetadata = new exportMetadata(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/${locationsId1}:exportMetadata").addQueryStringParameters("name" -> name.toString))
				given Conversion[exportMetadata, Future[Schema.GoogleCloudRunV2Metadata]] = (fun: exportMetadata) => fun.apply()
			}
			/** Export generated customer metadata for a given project. */
			class exportProjectMetadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Metadata]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2Metadata])
			}
			object exportProjectMetadata {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): exportProjectMetadata = new exportProjectMetadata(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}:exportProjectMetadata").addQueryStringParameters("name" -> name.toString))
				given Conversion[exportProjectMetadata, Future[Schema.GoogleCloudRunV2Metadata]] = (fun: exportProjectMetadata) => fun.apply()
			}
			object services {
				/** Returns permissions that a caller has on the specified Project. There are no permissions required for making this API call. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleIamV1TestIamPermissionsRequest(body: Schema.GoogleIamV1TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the IAM Access Control policy currently in effect for the given Cloud Run Service. This result does not include any inherited policies. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV1Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleIamV1Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.GoogleIamV1Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Deletes a Service. This will cause the Service to stop serving traffic and will delete all revisions. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, name: String, validateOnly: Boolean, etag: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}").addQueryStringParameters("name" -> name.toString, "validateOnly" -> validateOnly.toString, "etag" -> etag.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				/** Gets information about a Service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Service]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2Service])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudRunV2Service]] = (fun: get) => fun.apply()
				}
				/** Updates a Service. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The list of fields to be updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Optional. If set to true, and if the Service does not exist, it will create a new one. The caller must have 'run.services.create' permissions if this is set to true and the Service does not exist. */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def withGoogleCloudRunV2Service(body: Schema.GoogleCloudRunV2Service) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, name: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}").addQueryStringParameters("name" -> name.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Lists Services. Results are sorted by creation time, descending. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ListServicesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2ListServicesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, showDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString))
					given Conversion[list, Future[Schema.GoogleCloudRunV2ListServicesResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new Service in a given project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRunV2Service(body: Schema.GoogleCloudRunV2Service) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, serviceId: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services").addQueryStringParameters("parent" -> parent.toString, "serviceId" -> serviceId.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Sets the IAM Access control policy for the specified Service. Overwrites any existing policy. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleIamV1SetIamPolicyRequest(body: Schema.GoogleIamV1SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object revisions {
					/** Read the status of an image export operation. */
					class exportStatus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ExportStatusResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2ExportStatusResponse])
					}
					object exportStatus {
						def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, revisionsId :PlayApi, revisionsId1 :PlayApi, name: String, operationId: String)(using signer: RequestSigner, ec: ExecutionContext): exportStatus = new exportStatus(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}/revisions/${revisionsId}/${revisionsId1}:exportStatus").addQueryStringParameters("name" -> name.toString, "operationId" -> operationId.toString))
						given Conversion[exportStatus, Future[Schema.GoogleCloudRunV2ExportStatusResponse]] = (fun: exportStatus) => fun.apply()
					}
					/** Gets information about a Revision. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Revision]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2Revision])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, revisionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}/revisions/${revisionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRunV2Revision]] = (fun: get) => fun.apply()
					}
					/** Lists Revisions from a given Service, or from a given location. Results are sorted by creation time, descending. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ListRevisionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2ListRevisionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, parent: String, pageSize: Int, pageToken: String, showDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}/revisions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString))
						given Conversion[list, Future[Schema.GoogleCloudRunV2ListRevisionsResponse]] = (fun: list) => fun.apply()
					}
					/** Deletes a Revision. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, revisionsId :PlayApi, name: String, validateOnly: Boolean, etag: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}/revisions/${revisionsId}").addQueryStringParameters("name" -> name.toString, "validateOnly" -> validateOnly.toString, "etag" -> etag.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
				}
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A filter for matching the completed or in-progress operations. The supported formats of &#42;filter&#42; are: To query for only completed operations: done:true To query for only ongoing operations: done:false Must be empty to query for all of the latest operations for the given parent project. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
				/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Waits until the specified long-running operation is done or reaches at most a specified timeout, returning the latest state. If the operation is already done, the latest state is immediately returned. If the timeout specified is greater than the default HTTP/RPC timeout, the HTTP/RPC timeout is used. If the server does not support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Note that this method is on a best-effort basis. It may return the latest state before the specified timeout (including immediately), meaning even an immediate response is no guarantee that the operation is done. */
				class _wait(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleLongrunningWaitOperationRequest(body: Schema.GoogleLongrunningWaitOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object _wait {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): _wait = new _wait(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:wait").addQueryStringParameters("name" -> name.toString))
				}
			}
			object jobs {
				/** Returns permissions that a caller has on the specified Project. There are no permissions required for making this API call. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleIamV1TestIamPermissionsRequest(body: Schema.GoogleIamV1TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Triggers creation of a new Execution of this Job. */
				class run(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRunV2RunJobRequest(body: Schema.GoogleCloudRunV2RunJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object run {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): run = new run(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}:run").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets the IAM Access Control policy currently in effect for the given Job. This result does not include any inherited policies. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV1Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleIamV1Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.GoogleIamV1Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Deletes a Job. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, name: String, validateOnly: Boolean, etag: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}").addQueryStringParameters("name" -> name.toString, "validateOnly" -> validateOnly.toString, "etag" -> etag.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				/** Gets information about a Job. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Job]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2Job])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudRunV2Job]] = (fun: get) => fun.apply()
				}
				/** Updates a Job. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If set to true, and if the Job does not exist, it will create a new one. Caller must have both create and update permissions for this call if this is set to true. */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def withGoogleCloudRunV2Job(body: Schema.GoogleCloudRunV2Job) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, name: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}").addQueryStringParameters("name" -> name.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Lists Jobs. Results are sorted by creation time, descending. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ListJobsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2ListJobsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, showDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString))
					given Conversion[list, Future[Schema.GoogleCloudRunV2ListJobsResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a Job. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRunV2Job(body: Schema.GoogleCloudRunV2Job) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, jobId: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs").addQueryStringParameters("parent" -> parent.toString, "jobId" -> jobId.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Sets the IAM Access control policy for the specified Job. Overwrites any existing policy. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleIamV1SetIamPolicyRequest(body: Schema.GoogleIamV1SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object executions {
					/** Cancels an Execution. */
					class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRunV2CancelExecutionRequest(body: Schema.GoogleCloudRunV2CancelExecutionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, executionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions/${executionsId}:cancel").addQueryStringParameters("name" -> name.toString))
					}
					/** Deletes an Execution. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, executionsId :PlayApi, name: String, validateOnly: Boolean, etag: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions/${executionsId}").addQueryStringParameters("name" -> name.toString, "validateOnly" -> validateOnly.toString, "etag" -> etag.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
					/** Gets information about an Execution. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Execution]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2Execution])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, executionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions/${executionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRunV2Execution]] = (fun: get) => fun.apply()
					}
					/** Lists Executions from a Job. Results are sorted by creation time, descending. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ListExecutionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2ListExecutionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, parent: String, pageSize: Int, pageToken: String, showDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString))
						given Conversion[list, Future[Schema.GoogleCloudRunV2ListExecutionsResponse]] = (fun: list) => fun.apply()
					}
					/** Read the status of an image export operation. */
					class exportStatus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ExportStatusResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2ExportStatusResponse])
					}
					object exportStatus {
						def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, executionsId :PlayApi, executionsId1 :PlayApi, name: String, operationId: String)(using signer: RequestSigner, ec: ExecutionContext): exportStatus = new exportStatus(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions/${executionsId}/${executionsId1}:exportStatus").addQueryStringParameters("name" -> name.toString, "operationId" -> operationId.toString))
						given Conversion[exportStatus, Future[Schema.GoogleCloudRunV2ExportStatusResponse]] = (fun: exportStatus) => fun.apply()
					}
					object tasks {
						/** Gets information about a Task. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Task]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2Task])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, executionsId :PlayApi, tasksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions/${executionsId}/tasks/${tasksId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudRunV2Task]] = (fun: get) => fun.apply()
						}
						/** Lists Tasks from an Execution of a Job. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ListTasksResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRunV2ListTasksResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, executionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, showDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions/${executionsId}/tasks").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString))
							given Conversion[list, Future[Schema.GoogleCloudRunV2ListTasksResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object builds {
				/** Submits a build in a given project. */
				class submit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRunV2SubmitBuildRequest(body: Schema.GoogleCloudRunV2SubmitBuildRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRunV2SubmitBuildResponse])
				}
				object submit {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): submit = new submit(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/builds:submit").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
}
