package com.boresjo.play.api.google.osconfig

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://osconfig.googleapis.com/"

	object projects {
		object locations {
			object global {
				class getProjectFeatureSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProjectFeatureSettings]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ProjectFeatureSettings])
				}
				object getProjectFeatureSettings {
					def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getProjectFeatureSettings = new getProjectFeatureSettings(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/projectFeatureSettings")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getProjectFeatureSettings, Future[Schema.ProjectFeatureSettings]] = (fun: getProjectFeatureSettings) => fun.apply()
				}
				class updateProjectFeatureSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Field mask that controls which fields of the ProjectFeatureSettings should be updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new updateProjectFeatureSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withProjectFeatureSettings(body: Schema.ProjectFeatureSettings) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.ProjectFeatureSettings])
				}
				object updateProjectFeatureSettings {
					def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateProjectFeatureSettings = new updateProjectFeatureSettings(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/projectFeatureSettings")).addQueryStringParameters("name" -> name.toString))
				}
			}
			object osPolicyAssignments {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. A unique identifier for this request. Restricted to 36 ASCII characters. A random UUID is recommended. This request is only idempotent if a `request_id` is provided. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withOSPolicyAssignment(body: Schema.OSPolicyAssignment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, osPolicyAssignmentId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments")).addQueryStringParameters("parent" -> parent.toString, "osPolicyAssignmentId" -> osPolicyAssignmentId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. A unique identifier for this request. Restricted to 36 ASCII characters. A random UUID is recommended. This request is only idempotent if a `request_id` is provided. */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, osPolicyAssignmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments/${osPolicyAssignmentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Field mask that controls which fields of the assignment should be updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Optional. If set to true, and the OS policy assignment is not found, a new OS policy assignment will be created. In this situation, `update_mask` is ignored. */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Optional. A unique identifier for this request. Restricted to 36 ASCII characters. A random UUID is recommended. This request is only idempotent if a `request_id` is provided. */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withOSPolicyAssignment(body: Schema.OSPolicyAssignment) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, osPolicyAssignmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments/${osPolicyAssignmentsId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOSPolicyAssignmentsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListOSPolicyAssignmentsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOSPolicyAssignmentsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.OSPolicyAssignment]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.OSPolicyAssignment])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, osPolicyAssignmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments/${osPolicyAssignmentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.OSPolicyAssignment]] = (fun: get) => fun.apply()
				}
				class listRevisions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOSPolicyAssignmentRevisionsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListOSPolicyAssignmentRevisionsResponse])
				}
				object listRevisions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, osPolicyAssignmentsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listRevisions = new listRevisions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments/${osPolicyAssignmentsId}:listRevisions")).addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listRevisions, Future[Schema.ListOSPolicyAssignmentRevisionsResponse]] = (fun: listRevisions) => fun.apply()
				}
				object operations {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, osPolicyAssignmentsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments/${osPolicyAssignmentsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCancelOperationRequest(body: Schema.CancelOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, osPolicyAssignmentsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments/${osPolicyAssignmentsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					}
				}
			}
			object instances {
				object osPolicyAssignments {
					object reports {
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.OSPolicyAssignmentReport]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.OSPolicyAssignmentReport])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, osPolicyAssignmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/osPolicyAssignments/${osPolicyAssignmentsId}/report")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.OSPolicyAssignmentReport]] = (fun: get) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOSPolicyAssignmentReportsResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ListOSPolicyAssignmentReportsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, osPolicyAssignmentsId :PlayApi, parent: String, pageSize: Int, filter: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/osPolicyAssignments/${osPolicyAssignmentsId}/reports")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListOSPolicyAssignmentReportsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object inventories {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Inventory]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Inventory])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/inventory")).addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
						given Conversion[get, Future[Schema.Inventory]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInventoriesResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListInventoriesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, parent: String, view: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/inventories")).addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListInventoriesResponse]] = (fun: list) => fun.apply()
					}
				}
				object vulnerabilityReports {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VulnerabilityReport]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.VulnerabilityReport])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/vulnerabilityReport")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.VulnerabilityReport]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListVulnerabilityReportsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListVulnerabilityReportsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/vulnerabilityReports")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListVulnerabilityReportsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object patchJobs {
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCancelPatchJobRequest(body: Schema.CancelPatchJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.PatchJob])
			}
			object cancel {
				def apply(projectsId :PlayApi, patchJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchJobs/${patchJobsId}:cancel")).addQueryStringParameters("name" -> name.toString))
			}
			class execute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withExecutePatchJobRequest(body: Schema.ExecutePatchJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.PatchJob])
			}
			object execute {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): execute = new execute(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchJobs:execute")).addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PatchJob]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.PatchJob])
			}
			object get {
				def apply(projectsId :PlayApi, patchJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchJobs/${patchJobsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.PatchJob]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPatchJobsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListPatchJobsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchJobs")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListPatchJobsResponse]] = (fun: list) => fun.apply()
			}
			object instanceDetails {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPatchJobInstanceDetailsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListPatchJobInstanceDetailsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, patchJobsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchJobs/${patchJobsId}/instanceDetails")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListPatchJobInstanceDetailsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object patchDeployments {
			class resume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withResumePatchDeploymentRequest(body: Schema.ResumePatchDeploymentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.PatchDeployment])
			}
			object resume {
				def apply(projectsId :PlayApi, patchDeploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): resume = new resume(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments/${patchDeploymentsId}:resume")).addQueryStringParameters("name" -> name.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, patchDeploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments/${patchDeploymentsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PatchDeployment]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.PatchDeployment])
			}
			object get {
				def apply(projectsId :PlayApi, patchDeploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments/${patchDeploymentsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.PatchDeployment]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Field mask that controls which fields of the patch deployment should be updated.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withPatchDeployment(body: Schema.PatchDeployment) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.PatchDeployment])
			}
			object patch {
				def apply(projectsId :PlayApi, patchDeploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments/${patchDeploymentsId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPatchDeploymentsResponse]) {
				/** Optional. The maximum number of patch deployments to return. Default is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A pagination token returned from a previous call to ListPatchDeployments that indicates where this listing should continue from. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListPatchDeploymentsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListPatchDeploymentsResponse]] = (fun: list) => fun.apply()
			}
			class pause(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withPausePatchDeploymentRequest(body: Schema.PausePatchDeploymentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.PatchDeployment])
			}
			object pause {
				def apply(projectsId :PlayApi, patchDeploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): pause = new pause(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments/${patchDeploymentsId}:pause")).addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withPatchDeployment(body: Schema.PatchDeployment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.PatchDeployment])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, patchDeploymentId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments")).addQueryStringParameters("parent" -> parent.toString, "patchDeploymentId" -> patchDeploymentId.toString))
			}
		}
	}
}
