package com.boresjo.play.api.google.assuredworkloads

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://assuredworkloads.googleapis.com/"

	object organizations {
		object locations {
			object workloads {
				class analyzeWorkloadMove(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAssuredworkloadsV1AnalyzeWorkloadMoveResponse]) {
					/** Optional. List of asset types to be analyzed, including and under the source resource. If empty, all assets are analyzed. The complete list of asset types is available [here](https://cloud.google.com/asset-inventory/docs/supported-asset-types). */
					def withAssetTypes(assetTypes: String) = new analyzeWorkloadMove(req.addQueryStringParameters("assetTypes" -> assetTypes.toString))
					/** Optional. The page token from the previous response. It needs to be passed in the second and following requests. */
					def withPageToken(pageToken: String) = new analyzeWorkloadMove(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Page size. If a value is not specified, the default value of 10 is used.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new analyzeWorkloadMove(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1AnalyzeWorkloadMoveResponse])
				}
				object analyzeWorkloadMove {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, target: String, project: String)(using auth: AuthToken, ec: ExecutionContext): analyzeWorkloadMove = new analyzeWorkloadMove(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}:analyzeWorkloadMove")).addQueryStringParameters("target" -> target.toString, "project" -> project.toString))
					given Conversion[analyzeWorkloadMove, Future[Schema.GoogleCloudAssuredworkloadsV1AnalyzeWorkloadMoveResponse]] = (fun: analyzeWorkloadMove) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. A identifier associated with the workload and underlying projects which allows for the break down of billing costs for a workload. The value provided for the identifier will add a label to the workload and contained projects with the identifier as the value. */
					def withExternalId(externalId: String) = new create(req.addQueryStringParameters("externalId" -> externalId.toString))
					def withGoogleCloudAssuredworkloadsV1Workload(body: Schema.GoogleCloudAssuredworkloadsV1Workload) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads")).addQueryStringParameters("parent" -> parent.toString))
				}
				class enableResourceMonitoring(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAssuredworkloadsV1EnableResourceMonitoringResponse]) {
					def apply() = req.execute("POST").map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1EnableResourceMonitoringResponse])
				}
				object enableResourceMonitoring {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): enableResourceMonitoring = new enableResourceMonitoring(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}:enableResourceMonitoring")).addQueryStringParameters("name" -> name.toString))
					given Conversion[enableResourceMonitoring, Future[Schema.GoogleCloudAssuredworkloadsV1EnableResourceMonitoringResponse]] = (fun: enableResourceMonitoring) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					/** Optional. The etag of the workload. If this is provided, it must match the server's etag. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAssuredworkloadsV1Workload]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1Workload])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudAssuredworkloadsV1Workload]] = (fun: get) => fun.apply()
				}
				class restrictAllowedResources(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudAssuredworkloadsV1RestrictAllowedResourcesRequest(body: Schema.GoogleCloudAssuredworkloadsV1RestrictAllowedResourcesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1RestrictAllowedResourcesResponse])
				}
				object restrictAllowedResources {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): restrictAllowedResources = new restrictAllowedResources(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}:restrictAllowedResources")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAssuredworkloadsV1ListWorkloadsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1ListWorkloadsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, pageSize: Int, pageToken: String, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GoogleCloudAssuredworkloadsV1ListWorkloadsResponse]] = (fun: list) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The resource name of the workload. Format: organizations/{organization}/locations/{location}/workloads/{workload} Read-only. */
					def withName(name: String) = new patch(req.addQueryStringParameters("name" -> name.toString))
					def withGoogleCloudAssuredworkloadsV1Workload(body: Schema.GoogleCloudAssuredworkloadsV1Workload) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1Workload])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}")).addQueryStringParameters("updateMask" -> updateMask.toString))
				}
				class mutatePartnerPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudAssuredworkloadsV1MutatePartnerPermissionsRequest(body: Schema.GoogleCloudAssuredworkloadsV1MutatePartnerPermissionsRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1Workload])
				}
				object mutatePartnerPermissions {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): mutatePartnerPermissions = new mutatePartnerPermissions(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}:mutatePartnerPermissions")).addQueryStringParameters("name" -> name.toString))
				}
				object violations {
					class acknowledge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudAssuredworkloadsV1AcknowledgeViolationRequest(body: Schema.GoogleCloudAssuredworkloadsV1AcknowledgeViolationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1AcknowledgeViolationResponse])
					}
					object acknowledge {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, violationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): acknowledge = new acknowledge(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}/violations/${violationsId}:acknowledge")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAssuredworkloadsV1ListViolationsResponse]) {
						/** Optional. A custom filter for filtering by the Violations properties. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Page size.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token returned from previous request. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1ListViolationsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, intervalEndTime: String, parent: String, intervalStartTime: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}/violations")).addQueryStringParameters("interval.endTime" -> intervalEndTime.toString, "parent" -> parent.toString, "interval.startTime" -> intervalStartTime.toString))
						given Conversion[list, Future[Schema.GoogleCloudAssuredworkloadsV1ListViolationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAssuredworkloadsV1Violation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudAssuredworkloadsV1Violation])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, workloadsId :PlayApi, violationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/workloads/${workloadsId}/violations/${violationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudAssuredworkloadsV1Violation]] = (fun: get) => fun.apply()
					}
				}
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, filter: String, name: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/operations")).addQueryStringParameters("filter" -> filter.toString, "name" -> name.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
			}
		}
	}
}
