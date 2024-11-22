package com.boresjo.play.api.google.assuredworkloads

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

	private val BASE_URL = "https://assuredworkloads.googleapis.com/"

	object organizations {
		object locations {
			object workloads {
				/** Analyzes a hypothetical move of a source resource to a target workload to surface compliance risks. The analysis is best effort and is not guaranteed to be exhaustive. */
				class analyzeWorkloadMove(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAssuredworkloadsV1AnalyzeWorkloadMoveResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. List of asset types to be analyzed, including and under the source resource. If empty, all assets are analyzed. The complete list of asset types is available [here](https://cloud.google.com/asset-inventory/docs/supported-asset-types). */
					def withAssetTypes(assetTypes: String) = new analyzeWorkloadMove(req.addQueryStringParameters("assetTypes" -> assetTypes.toString))
					/** Optional. The page token from the previous response. It needs to be passed in the second and following requests. */
					def withPageToken(pageToken: String) = new analyzeWorkloadMove(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Page size. If a value is not specified, the default value of 10 is used.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new analyzeWorkloadMove(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1AnalyzeWorkloadMoveResponse])
				}
				object analyzeWorkloadMove {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, target: String, project: String)(using signer: RequestSigner, ec: ExecutionContext): analyzeWorkloadMove = new analyzeWorkloadMove(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}:analyzeWorkloadMove").addQueryStringParameters("target" -> target.toString, "project" -> project.toString))
					given Conversion[analyzeWorkloadMove, Future[Schema.GoogleCloudAssuredworkloadsV1AnalyzeWorkloadMoveResponse]] = (fun: analyzeWorkloadMove) => fun.apply()
				}
				/** Creates Assured Workload. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A identifier associated with the workload and underlying projects which allows for the break down of billing costs for a workload. The value provided for the identifier will add a label to the workload and contained projects with the identifier as the value. */
					def withExternalId(externalId: String) = new create(req.addQueryStringParameters("externalId" -> externalId.toString))
					/** Perform the request */
					def withGoogleCloudAssuredworkloadsV1Workload(body: Schema.GoogleCloudAssuredworkloadsV1Workload) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Enable resource violation monitoring for a workload. */
				class enableResourceMonitoring(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAssuredworkloadsV1EnableResourceMonitoringResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1EnableResourceMonitoringResponse])
				}
				object enableResourceMonitoring {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): enableResourceMonitoring = new enableResourceMonitoring(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}:enableResourceMonitoring").addQueryStringParameters("name" -> name.toString))
					given Conversion[enableResourceMonitoring, Future[Schema.GoogleCloudAssuredworkloadsV1EnableResourceMonitoringResponse]] = (fun: enableResourceMonitoring) => fun.apply()
				}
				/** Deletes the workload. Make sure that workload's direct children are already in a deleted state, otherwise the request will fail with a FAILED_PRECONDITION error. In addition to assuredworkloads.workload.delete permission, the user should also have orgpolicy.policy.set permission on the deleted folder to remove Assured Workloads OrgPolicies. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The etag of the workload. If this is provided, it must match the server's etag. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets Assured Workload associated with a CRM Node */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAssuredworkloadsV1Workload]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1Workload])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudAssuredworkloadsV1Workload]] = (fun: get) => fun.apply()
				}
				/** Restrict the list of resources allowed in the Workload environment. The current list of allowed products can be found at https://cloud.google.com/assured-workloads/docs/supported-products In addition to assuredworkloads.workload.update permission, the user should also have orgpolicy.policy.set permission on the folder resource to use this functionality. */
				class restrictAllowedResources(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudAssuredworkloadsV1RestrictAllowedResourcesRequest(body: Schema.GoogleCloudAssuredworkloadsV1RestrictAllowedResourcesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1RestrictAllowedResourcesResponse])
				}
				object restrictAllowedResources {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): restrictAllowedResources = new restrictAllowedResources(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}:restrictAllowedResources").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists Assured Workloads under a CRM Node. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAssuredworkloadsV1ListWorkloadsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1ListWorkloadsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, pageSize: Int, pageToken: String, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GoogleCloudAssuredworkloadsV1ListWorkloadsResponse]] = (fun: list) => fun.apply()
				}
				/** Updates an existing workload. Currently allows updating of workload display_name and labels. For force updates don't set etag field in the Workload. Only one update operation per workload can be in progress. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The resource name of the workload. Format: organizations/{organization}/locations/{location}/workloads/{workload} Read-only. */
					def withName(name: String) = new patch(req.addQueryStringParameters("name" -> name.toString))
					/** Perform the request */
					def withGoogleCloudAssuredworkloadsV1Workload(body: Schema.GoogleCloudAssuredworkloadsV1Workload) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1Workload])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}").addQueryStringParameters("updateMask" -> updateMask.toString))
				}
				/** Update the permissions settings for an existing partner workload. For force updates don't set etag field in the Workload. Only one update operation per workload can be in progress. */
				class mutatePartnerPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudAssuredworkloadsV1MutatePartnerPermissionsRequest(body: Schema.GoogleCloudAssuredworkloadsV1MutatePartnerPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1Workload])
				}
				object mutatePartnerPermissions {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): mutatePartnerPermissions = new mutatePartnerPermissions(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}:mutatePartnerPermissions").addQueryStringParameters("name" -> name.toString))
				}
				object violations {
					/** Acknowledges an existing violation. By acknowledging a violation, users acknowledge the existence of a compliance violation in their workload and decide to ignore it due to a valid business justification. Acknowledgement is a permanent operation and it cannot be reverted. */
					class acknowledge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudAssuredworkloadsV1AcknowledgeViolationRequest(body: Schema.GoogleCloudAssuredworkloadsV1AcknowledgeViolationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1AcknowledgeViolationResponse])
					}
					object acknowledge {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, violationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): acknowledge = new acknowledge(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}/violations/${violationsId}:acknowledge").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists the Violations in the AssuredWorkload Environment. Callers may also choose to read across multiple Workloads as per [AIP-159](https://google.aip.dev/159) by using '-' (the hyphen or dash character) as a wildcard character instead of workload-id in the parent. Format `organizations/{org_id}/locations/{location}/workloads/-` */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAssuredworkloadsV1ListViolationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. A custom filter for filtering by the Violations properties. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Page size.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token returned from previous request. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1ListViolationsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, intervalEndTime: String, parent: String, intervalStartTime: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}/violations").addQueryStringParameters("interval.endTime" -> intervalEndTime.toString, "parent" -> parent.toString, "interval.startTime" -> intervalStartTime.toString))
						given Conversion[list, Future[Schema.GoogleCloudAssuredworkloadsV1ListViolationsResponse]] = (fun: list) => fun.apply()
					}
					/** Retrieves Assured Workload Violation based on ID. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAssuredworkloadsV1Violation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1Violation])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, violationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}/violations/${violationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudAssuredworkloadsV1Violation]] = (fun: get) => fun.apply()
					}
				}
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, filter: String, name: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/operations").addQueryStringParameters("filter" -> filter.toString, "name" -> name.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
			}
		}
	}
}
