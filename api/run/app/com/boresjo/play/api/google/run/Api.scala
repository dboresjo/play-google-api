package com.boresjo.play.api.google.run

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://run.googleapis.com/"

	object projects {
		object locations {
			class exportImageMetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Metadata]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2Metadata])
			}
			object exportImageMetadata {
				def apply(projectsId :PlayApi, locationsId :PlayApi, locationsId1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): exportImageMetadata = new exportImageMetadata(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/${locationsId1}:exportImageMetadata")).addQueryStringParameters("name" -> name.toString))
				given Conversion[exportImageMetadata, Future[Schema.GoogleCloudRunV2Metadata]] = (fun: exportImageMetadata) => fun.apply()
			}
			class exportImage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudRunV2ExportImageRequest(body: Schema.GoogleCloudRunV2ExportImageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRunV2ExportImageResponse])
			}
			object exportImage {
				def apply(projectsId :PlayApi, locationsId :PlayApi, locationsId1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): exportImage = new exportImage(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/${locationsId1}:exportImage")).addQueryStringParameters("name" -> name.toString))
			}
			class exportMetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Metadata]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2Metadata])
			}
			object exportMetadata {
				def apply(projectsId :PlayApi, locationsId :PlayApi, locationsId1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): exportMetadata = new exportMetadata(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/${locationsId1}:exportMetadata")).addQueryStringParameters("name" -> name.toString))
				given Conversion[exportMetadata, Future[Schema.GoogleCloudRunV2Metadata]] = (fun: exportMetadata) => fun.apply()
			}
			class exportProjectMetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Metadata]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2Metadata])
			}
			object exportProjectMetadata {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): exportProjectMetadata = new exportProjectMetadata(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}:exportProjectMetadata")).addQueryStringParameters("name" -> name.toString))
				given Conversion[exportProjectMetadata, Future[Schema.GoogleCloudRunV2Metadata]] = (fun: exportProjectMetadata) => fun.apply()
			}
			object services {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleIamV1TestIamPermissionsRequest(body: Schema.GoogleIamV1TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleIamV1TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV1Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleIamV1Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.GoogleIamV1Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, name: String, validateOnly: Boolean, etag: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}")).addQueryStringParameters("name" -> name.toString, "validateOnly" -> validateOnly.toString, "etag" -> etag.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Service]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2Service])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudRunV2Service]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The list of fields to be updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Optional. If set to true, and if the Service does not exist, it will create a new one. The caller must have 'run.services.create' permissions if this is set to true and the Service does not exist. */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					def withGoogleCloudRunV2Service(body: Schema.GoogleCloudRunV2Service) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, name: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}")).addQueryStringParameters("name" -> name.toString, "validateOnly" -> validateOnly.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ListServicesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2ListServicesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, showDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString))
					given Conversion[list, Future[Schema.GoogleCloudRunV2ListServicesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRunV2Service(body: Schema.GoogleCloudRunV2Service) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, serviceId: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services")).addQueryStringParameters("parent" -> parent.toString, "serviceId" -> serviceId.toString, "validateOnly" -> validateOnly.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleIamV1SetIamPolicyRequest(body: Schema.GoogleIamV1SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleIamV1Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				object revisions {
					class exportStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ExportStatusResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2ExportStatusResponse])
					}
					object exportStatus {
						def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, revisionsId :PlayApi, revisionsId1 :PlayApi, name: String, operationId: String)(using auth: AuthToken, ec: ExecutionContext): exportStatus = new exportStatus(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}/revisions/${revisionsId}/${revisionsId1}:exportStatus")).addQueryStringParameters("name" -> name.toString, "operationId" -> operationId.toString))
						given Conversion[exportStatus, Future[Schema.GoogleCloudRunV2ExportStatusResponse]] = (fun: exportStatus) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Revision]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2Revision])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, revisionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}/revisions/${revisionsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRunV2Revision]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ListRevisionsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2ListRevisionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, parent: String, pageSize: Int, pageToken: String, showDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}/revisions")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString))
						given Conversion[list, Future[Schema.GoogleCloudRunV2ListRevisionsResponse]] = (fun: list) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, servicesId :PlayApi, revisionsId :PlayApi, name: String, validateOnly: Boolean, etag: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/services/${servicesId}/revisions/${revisionsId}")).addQueryStringParameters("name" -> name.toString, "validateOnly" -> validateOnly.toString, "etag" -> etag.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
				}
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					/** Optional. A filter for matching the completed or in-progress operations. The supported formats of &#42;filter&#42; are: To query for only completed operations: done:true To query for only ongoing operations: done:false Must be empty to query for all of the latest operations for the given parent project. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations")).addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class _wait(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleLongrunningWaitOperationRequest(body: Schema.GoogleLongrunningWaitOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object _wait {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): _wait = new _wait(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:wait")).addQueryStringParameters("name" -> name.toString))
				}
			}
			object jobs {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleIamV1TestIamPermissionsRequest(body: Schema.GoogleIamV1TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleIamV1TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class run(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRunV2RunJobRequest(body: Schema.GoogleCloudRunV2RunJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object run {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): run = new run(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}:run")).addQueryStringParameters("name" -> name.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV1Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleIamV1Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.GoogleIamV1Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, name: String, validateOnly: Boolean, etag: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}")).addQueryStringParameters("name" -> name.toString, "validateOnly" -> validateOnly.toString, "etag" -> etag.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Job]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2Job])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudRunV2Job]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. If set to true, and if the Job does not exist, it will create a new one. Caller must have both create and update permissions for this call if this is set to true. */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					def withGoogleCloudRunV2Job(body: Schema.GoogleCloudRunV2Job) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, name: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}")).addQueryStringParameters("name" -> name.toString, "validateOnly" -> validateOnly.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ListJobsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2ListJobsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, showDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString))
					given Conversion[list, Future[Schema.GoogleCloudRunV2ListJobsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRunV2Job(body: Schema.GoogleCloudRunV2Job) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, jobId: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs")).addQueryStringParameters("parent" -> parent.toString, "jobId" -> jobId.toString, "validateOnly" -> validateOnly.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleIamV1SetIamPolicyRequest(body: Schema.GoogleIamV1SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleIamV1Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				object executions {
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRunV2CancelExecutionRequest(body: Schema.GoogleCloudRunV2CancelExecutionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, executionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions/${executionsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, executionsId :PlayApi, name: String, validateOnly: Boolean, etag: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions/${executionsId}")).addQueryStringParameters("name" -> name.toString, "validateOnly" -> validateOnly.toString, "etag" -> etag.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Execution]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2Execution])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, executionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions/${executionsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRunV2Execution]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ListExecutionsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2ListExecutionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, parent: String, pageSize: Int, pageToken: String, showDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString))
						given Conversion[list, Future[Schema.GoogleCloudRunV2ListExecutionsResponse]] = (fun: list) => fun.apply()
					}
					class exportStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ExportStatusResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2ExportStatusResponse])
					}
					object exportStatus {
						def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, executionsId :PlayApi, executionsId1 :PlayApi, name: String, operationId: String)(using auth: AuthToken, ec: ExecutionContext): exportStatus = new exportStatus(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions/${executionsId}/${executionsId1}:exportStatus")).addQueryStringParameters("name" -> name.toString, "operationId" -> operationId.toString))
						given Conversion[exportStatus, Future[Schema.GoogleCloudRunV2ExportStatusResponse]] = (fun: exportStatus) => fun.apply()
					}
					object tasks {
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2Task]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2Task])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, executionsId :PlayApi, tasksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions/${executionsId}/tasks/${tasksId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudRunV2Task]] = (fun: get) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRunV2ListTasksResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRunV2ListTasksResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, jobsId :PlayApi, executionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, showDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/jobs/${jobsId}/executions/${executionsId}/tasks")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString))
							given Conversion[list, Future[Schema.GoogleCloudRunV2ListTasksResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object builds {
				class submit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRunV2SubmitBuildRequest(body: Schema.GoogleCloudRunV2SubmitBuildRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRunV2SubmitBuildResponse])
				}
				object submit {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): submit = new submit(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/builds:submit")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
}
