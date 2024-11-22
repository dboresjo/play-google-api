package com.boresjo.play.api.google.osconfig

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

	private val BASE_URL = "https://osconfig.googleapis.com/"

	object projects {
		object locations {
			object global {
				/** GetProjectFeatureSettings returns the VM Manager feature settings for a project. */
				class getProjectFeatureSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProjectFeatureSettings]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProjectFeatureSettings])
				}
				object getProjectFeatureSettings {
					def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getProjectFeatureSettings = new getProjectFeatureSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/projectFeatureSettings").addQueryStringParameters("name" -> name.toString))
					given Conversion[getProjectFeatureSettings, Future[Schema.ProjectFeatureSettings]] = (fun: getProjectFeatureSettings) => fun.apply()
				}
				/** UpdateProjectFeatureSettings sets the VM Manager features for a project. */
				class updateProjectFeatureSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Field mask that controls which fields of the ProjectFeatureSettings should be updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new updateProjectFeatureSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withProjectFeatureSettings(body: Schema.ProjectFeatureSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ProjectFeatureSettings])
				}
				object updateProjectFeatureSettings {
					def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateProjectFeatureSettings = new updateProjectFeatureSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/projectFeatureSettings").addQueryStringParameters("name" -> name.toString))
				}
			}
			object osPolicyAssignments {
				/** Create an OS policy assignment. This method also creates the first revision of the OS policy assignment. This method returns a long running operation (LRO) that contains the rollout details. The rollout can be cancelled by cancelling the LRO. For more information, see [Method: projects.locations.osPolicyAssignments.operations.cancel](https://cloud.google.com/compute/docs/osconfig/rest/v1/projects.locations.osPolicyAssignments.operations/cancel). */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A unique identifier for this request. Restricted to 36 ASCII characters. A random UUID is recommended. This request is only idempotent if a `request_id` is provided. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withOSPolicyAssignment(body: Schema.OSPolicyAssignment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, osPolicyAssignmentId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments").addQueryStringParameters("parent" -> parent.toString, "osPolicyAssignmentId" -> osPolicyAssignmentId.toString))
				}
				/** Delete the OS policy assignment. This method creates a new revision of the OS policy assignment. This method returns a long running operation (LRO) that contains the rollout details. The rollout can be cancelled by cancelling the LRO. If the LRO completes and is not cancelled, all revisions associated with the OS policy assignment are deleted. For more information, see [Method: projects.locations.osPolicyAssignments.operations.cancel](https://cloud.google.com/compute/docs/osconfig/rest/v1/projects.locations.osPolicyAssignments.operations/cancel). */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A unique identifier for this request. Restricted to 36 ASCII characters. A random UUID is recommended. This request is only idempotent if a `request_id` is provided. */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, osPolicyAssignmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments/${osPolicyAssignmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Update an existing OS policy assignment. This method creates a new revision of the OS policy assignment. This method returns a long running operation (LRO) that contains the rollout details. The rollout can be cancelled by cancelling the LRO. For more information, see [Method: projects.locations.osPolicyAssignments.operations.cancel](https://cloud.google.com/compute/docs/osconfig/rest/v1/projects.locations.osPolicyAssignments.operations/cancel). */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Field mask that controls which fields of the assignment should be updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Optional. If set to true, and the OS policy assignment is not found, a new OS policy assignment will be created. In this situation, `update_mask` is ignored. */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Optional. A unique identifier for this request. Restricted to 36 ASCII characters. A random UUID is recommended. This request is only idempotent if a `request_id` is provided. */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withOSPolicyAssignment(body: Schema.OSPolicyAssignment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, osPolicyAssignmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments/${osPolicyAssignmentsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** List the OS policy assignments under the parent resource. For each OS policy assignment, the latest revision is returned. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOSPolicyAssignmentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOSPolicyAssignmentsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOSPolicyAssignmentsResponse]] = (fun: list) => fun.apply()
				}
				/** Retrieve an existing OS policy assignment. This method always returns the latest revision. In order to retrieve a previous revision of the assignment, also provide the revision ID in the `name` parameter. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.OSPolicyAssignment]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.OSPolicyAssignment])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, osPolicyAssignmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments/${osPolicyAssignmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.OSPolicyAssignment]] = (fun: get) => fun.apply()
				}
				/** List the OS policy assignment revisions for a given OS policy assignment. */
				class listRevisions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOSPolicyAssignmentRevisionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOSPolicyAssignmentRevisionsResponse])
				}
				object listRevisions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, osPolicyAssignmentsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listRevisions = new listRevisions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments/${osPolicyAssignmentsId}:listRevisions").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listRevisions, Future[Schema.ListOSPolicyAssignmentRevisionsResponse]] = (fun: listRevisions) => fun.apply()
				}
				object operations {
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, osPolicyAssignmentsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments/${osPolicyAssignmentsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
					class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, osPolicyAssignmentsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/osPolicyAssignments/${osPolicyAssignmentsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					}
				}
			}
			object instances {
				object osPolicyAssignments {
					object reports {
						/** Get the OS policy assignment report for the specified Compute Engine VM instance. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.OSPolicyAssignmentReport]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.OSPolicyAssignmentReport])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, osPolicyAssignmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/osPolicyAssignments/${osPolicyAssignmentsId}/report").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.OSPolicyAssignmentReport]] = (fun: get) => fun.apply()
						}
						/** List OS policy assignment reports for all Compute Engine VM instances in the specified zone. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOSPolicyAssignmentReportsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOSPolicyAssignmentReportsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, osPolicyAssignmentsId :PlayApi, parent: String, pageSize: Int, filter: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/osPolicyAssignments/${osPolicyAssignmentsId}/reports").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListOSPolicyAssignmentReportsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object inventories {
					/** Get inventory data for the specified VM instance. If the VM has no associated inventory, the message `NOT_FOUND` is returned. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Inventory]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Inventory])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/inventory").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
						given Conversion[get, Future[Schema.Inventory]] = (fun: get) => fun.apply()
					}
					/** List inventory data for all VM instances in the specified zone. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInventoriesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInventoriesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, parent: String, view: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/inventories").addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListInventoriesResponse]] = (fun: list) => fun.apply()
					}
				}
				object vulnerabilityReports {
					/** Gets the vulnerability report for the specified VM instance. Only VMs with inventory data have vulnerability reports associated with them. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VulnerabilityReport]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VulnerabilityReport])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/vulnerabilityReport").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.VulnerabilityReport]] = (fun: get) => fun.apply()
					}
					/** List vulnerability reports for all VM instances in the specified zone. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVulnerabilityReportsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVulnerabilityReportsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/vulnerabilityReports").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListVulnerabilityReportsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object patchJobs {
			/** Cancel a patch job. The patch job must be active. Canceled patch jobs cannot be restarted. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withCancelPatchJobRequest(body: Schema.CancelPatchJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PatchJob])
			}
			object cancel {
				def apply(projectsId :PlayApi, patchJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchJobs/${patchJobsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
			/** Patch VM instances by creating and running a patch job. */
			class execute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withExecutePatchJobRequest(body: Schema.ExecutePatchJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PatchJob])
			}
			object execute {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): execute = new execute(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchJobs:execute").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Get the patch job. This can be used to track the progress of an ongoing patch job or review the details of completed jobs. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PatchJob]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PatchJob])
			}
			object get {
				def apply(projectsId :PlayApi, patchJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchJobs/${patchJobsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.PatchJob]] = (fun: get) => fun.apply()
			}
			/** Get a list of patch jobs. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPatchJobsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPatchJobsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchJobs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListPatchJobsResponse]] = (fun: list) => fun.apply()
			}
			object instanceDetails {
				/** Get a list of instance details for a given patch job. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPatchJobInstanceDetailsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPatchJobInstanceDetailsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, patchJobsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchJobs/${patchJobsId}/instanceDetails").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListPatchJobInstanceDetailsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object patchDeployments {
			/** Change state of patch deployment back to "ACTIVE". Patch deployment in active state continues to generate patch jobs. */
			class resume(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withResumePatchDeploymentRequest(body: Schema.ResumePatchDeploymentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PatchDeployment])
			}
			object resume {
				def apply(projectsId :PlayApi, patchDeploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): resume = new resume(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments/${patchDeploymentsId}:resume").addQueryStringParameters("name" -> name.toString))
			}
			/** Delete an OS Config patch deployment. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, patchDeploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments/${patchDeploymentsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Get an OS Config patch deployment. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PatchDeployment]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PatchDeployment])
			}
			object get {
				def apply(projectsId :PlayApi, patchDeploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments/${patchDeploymentsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.PatchDeployment]] = (fun: get) => fun.apply()
			}
			/** Update an OS Config patch deployment. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Field mask that controls which fields of the patch deployment should be updated.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withPatchDeployment(body: Schema.PatchDeployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.PatchDeployment])
			}
			object patch {
				def apply(projectsId :PlayApi, patchDeploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments/${patchDeploymentsId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Get a page of OS Config patch deployments. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPatchDeploymentsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum number of patch deployments to return. Default is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A pagination token returned from a previous call to ListPatchDeployments that indicates where this listing should continue from. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPatchDeploymentsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListPatchDeploymentsResponse]] = (fun: list) => fun.apply()
			}
			/** Change state of patch deployment to "PAUSED". Patch deployment in paused state doesn't generate patch jobs. */
			class pause(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withPausePatchDeploymentRequest(body: Schema.PausePatchDeploymentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PatchDeployment])
			}
			object pause {
				def apply(projectsId :PlayApi, patchDeploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): pause = new pause(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments/${patchDeploymentsId}:pause").addQueryStringParameters("name" -> name.toString))
			}
			/** Create an OS Config patch deployment. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withPatchDeployment(body: Schema.PatchDeployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PatchDeployment])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, patchDeploymentId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/patchDeployments").addQueryStringParameters("parent" -> parent.toString, "patchDeploymentId" -> patchDeploymentId.toString))
			}
		}
	}
}
