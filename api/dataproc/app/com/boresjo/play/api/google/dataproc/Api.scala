package com.boresjo.play.api.google.dataproc

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://dataproc.googleapis.com/"

	object projects {
		object regions {
			object operations {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, regionsId :PlayApi, operationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/operations/${operationsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("POST").map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, regionsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, regionsId :PlayApi, operationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/operations/${operationsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, regionsId :PlayApi, operationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/operations/${operationsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, regionsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, regionsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, regionsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
			}
			object clusters {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, regionsId :PlayApi, clustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/clusters/${clustersId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class diagnose(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDiagnoseClusterRequest(body: Schema.DiagnoseClusterRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object diagnose {
					def apply(projectId: String, region: String, clusterName: String)(using auth: AuthToken, ec: ExecutionContext): diagnose = new diagnose(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/clusters/${clusterName}:diagnose")).addQueryStringParameters())
				}
				class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStopClusterRequest(body: Schema.StopClusterRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object stop {
					def apply(projectId: String, region: String, clusterName: String)(using auth: AuthToken, ec: ExecutionContext): stop = new stop(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/clusters/${clusterName}:stop")).addQueryStringParameters())
				}
				class repair(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRepairClusterRequest(body: Schema.RepairClusterRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object repair {
					def apply(projectId: String, region: String, clusterName: String)(using auth: AuthToken, ec: ExecutionContext): repair = new repair(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/clusters/${clusterName}:repair")).addQueryStringParameters())
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, regionsId :PlayApi, clustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/clusters/${clustersId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. Specifying the cluster_uuid means the RPC should fail (with error NOT_FOUND) if cluster with specified UUID does not exist. */
					def withClusterUuid(clusterUuid: String) = new delete(req.addQueryStringParameters("clusterUuid" -> clusterUuid.toString))
					/** Optional. A unique ID used to identify the request. If the server receives two DeleteClusterRequest (https://cloud.google.com/dataproc/docs/reference/rpc/google.cloud.dataproc.v1#google.cloud.dataproc.v1.DeleteClusterRequest)s with the same id, then the second request will be ignored and the first google.longrunning.Operation created and stored in the backend is returned.It is recommended to always set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. The graceful termination timeout for the deletion of the cluster. Indicate the time the request will wait to complete the running jobs on the cluster before its forceful deletion. Default value is 0 indicating that the user has not enabled the graceful termination. Value can be between 60 second and 6 Hours, in case the graceful termination is enabled. (There is no separate flag to check the enabling or disabling of graceful termination, it can be checked by the values in the field).<br>Format: google-duration */
					def withGracefulTerminationTimeout(gracefulTerminationTimeout: String) = new delete(req.addQueryStringParameters("gracefulTerminationTimeout" -> gracefulTerminationTimeout.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectId: String, region: String, clusterName: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/clusters/${clusterName}")).addQueryStringParameters())
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class injectCredentials(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withInjectCredentialsRequest(body: Schema.InjectCredentialsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object injectCredentials {
					def apply(projectsId :PlayApi, regionsId :PlayApi, clustersId :PlayApi, project: String, region: String, cluster: String)(using auth: AuthToken, ec: ExecutionContext): injectCredentials = new injectCredentials(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/clusters/${clustersId}:injectCredentials")).addQueryStringParameters("project" -> project.toString, "region" -> region.toString, "cluster" -> cluster.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Cluster]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Cluster])
				}
				object get {
					def apply(projectId: String, region: String, clusterName: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/clusters/${clusterName}")).addQueryStringParameters())
					given Conversion[get, Future[Schema.Cluster]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListClustersResponse]) {
					/** Optional. A filter constraining the clusters to list. Filters are case-sensitive and have the following syntax:field = value AND field = value ...where field is one of status.state, clusterName, or labels.[KEY], and [KEY] is a label key. value can be &#42; to match all values. status.state can be one of the following: ACTIVE, INACTIVE, CREATING, RUNNING, ERROR, DELETING, UPDATING, STOPPING, or STOPPED. ACTIVE contains the CREATING, UPDATING, and RUNNING states. INACTIVE contains the DELETING, ERROR, STOPPING, and STOPPED states. clusterName is the name of the cluster provided at creation time. Only the logical AND operator is supported; space-separated items are treated as having an implicit AND operator.Example filter:status.state = ACTIVE AND clusterName = mycluster AND labels.env = staging AND labels.starred = &#42; */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. The standard List page size.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The standard List page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListClustersResponse])
				}
				object list {
					def apply(projectId: String, region: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/clusters")).addQueryStringParameters())
					given Conversion[list, Future[Schema.ListClustersResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. A unique ID used to identify the request. If the server receives two CreateClusterRequest (https://cloud.google.com/dataproc/docs/reference/rpc/google.cloud.dataproc.v1#google.cloud.dataproc.v1.CreateClusterRequest)s with the same id, then the second request will be ignored and the first google.longrunning.Operation created and stored in the backend is returned.It is recommended to always set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. Failure action when primary worker creation fails.<br>Possible values:<br>FAILURE_ACTION_UNSPECIFIED: When FailureAction is unspecified, failure action defaults to NO_ACTION.<br>NO_ACTION: Take no action on failure to create a cluster resource. NO_ACTION is the default.<br>DELETE: Delete the failed cluster resource. */
					def withActionOnFailedPrimaryWorkers(actionOnFailedPrimaryWorkers: String) = new create(req.addQueryStringParameters("actionOnFailedPrimaryWorkers" -> actionOnFailedPrimaryWorkers.toString))
					def withCluster(body: Schema.Cluster) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectId: String, region: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/clusters")).addQueryStringParameters())
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, regionsId :PlayApi, clustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/clusters/${clustersId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Timeout for graceful YARN decommissioning. Graceful decommissioning allows removing nodes from the cluster without interrupting jobs in progress. Timeout specifies how long to wait for jobs in progress to finish before forcefully removing nodes (and potentially interrupting jobs). Default timeout is 0 (for forceful decommission), and the maximum allowed timeout is 1 day. (see JSON representation of Duration (https://developers.google.com/protocol-buffers/docs/proto3#json)).Only supported on Dataproc image versions 1.2 and higher.<br>Format: google-duration */
					def withGracefulDecommissionTimeout(gracefulDecommissionTimeout: String) = new patch(req.addQueryStringParameters("gracefulDecommissionTimeout" -> gracefulDecommissionTimeout.toString))
					/** Optional. A unique ID used to identify the request. If the server receives two UpdateClusterRequest (https://cloud.google.com/dataproc/docs/reference/rpc/google.cloud.dataproc.v1#google.cloud.dataproc.v1.UpdateClusterRequest)s with the same id, then the second request will be ignored and the first google.longrunning.Operation created and stored in the backend is returned.It is recommended to always set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withCluster(body: Schema.Cluster) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectId: String, region: String, clusterName: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/clusters/${clusterName}")).addQueryStringParameters("updateMask" -> updateMask.toString))
				}
				class start(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStartClusterRequest(body: Schema.StartClusterRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object start {
					def apply(projectId: String, region: String, clusterName: String)(using auth: AuthToken, ec: ExecutionContext): start = new start(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/clusters/${clusterName}:start")).addQueryStringParameters())
				}
				object nodeGroups {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. An optional node group ID. Generated if not specified.The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). Cannot begin or end with underscore or hyphen. Must consist of from 3 to 33 characters. */
						def withNodeGroupId(nodeGroupId: String) = new create(req.addQueryStringParameters("nodeGroupId" -> nodeGroupId.toString))
						/** Optional. A unique ID used to identify the request. If the server receives two CreateNodeGroupRequest (https://cloud.google.com/dataproc/docs/reference/rpc/google.cloud.dataproc.v1#google.cloud.dataproc.v1.CreateNodeGroupRequest) with the same ID, the second request is ignored and the first google.longrunning.Operation created and stored in the backend is returned.Recommendation: Set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
						def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. operation id of the parent operation sending the create request */
						def withParentOperationId(parentOperationId: String) = new create(req.addQueryStringParameters("parentOperationId" -> parentOperationId.toString))
						def withNodeGroup(body: Schema.NodeGroup) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, regionsId :PlayApi, clustersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/clusters/${clustersId}/nodeGroups")).addQueryStringParameters("parent" -> parent.toString))
					}
					class resize(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withResizeNodeGroupRequest(body: Schema.ResizeNodeGroupRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object resize {
						def apply(projectsId :PlayApi, regionsId :PlayApi, clustersId :PlayApi, nodeGroupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): resize = new resize(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/clusters/${clustersId}/nodeGroups/${nodeGroupsId}:resize")).addQueryStringParameters("name" -> name.toString))
					}
					class repair(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRepairNodeGroupRequest(body: Schema.RepairNodeGroupRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object repair {
						def apply(projectsId :PlayApi, regionsId :PlayApi, clustersId :PlayApi, nodeGroupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): repair = new repair(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/clusters/${clustersId}/nodeGroups/${nodeGroupsId}:repair")).addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.NodeGroup]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.NodeGroup])
					}
					object get {
						def apply(projectsId :PlayApi, regionsId :PlayApi, clustersId :PlayApi, nodeGroupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/clusters/${clustersId}/nodeGroups/${nodeGroupsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.NodeGroup]] = (fun: get) => fun.apply()
					}
				}
			}
			object jobs {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, regionsId :PlayApi, jobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/jobs/${jobsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelJobRequest(body: Schema.CancelJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Job])
				}
				object cancel {
					def apply(projectId: String, region: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/jobs/${jobId}:cancel")).addQueryStringParameters())
				}
				class submitAsOperation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSubmitJobRequest(body: Schema.SubmitJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object submitAsOperation {
					def apply(projectId: String, region: String)(using auth: AuthToken, ec: ExecutionContext): submitAsOperation = new submitAsOperation(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/jobs:submitAsOperation")).addQueryStringParameters())
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, regionsId :PlayApi, jobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/jobs/${jobsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class submit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSubmitJobRequest(body: Schema.SubmitJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Job])
				}
				object submit {
					def apply(projectId: String, region: String)(using auth: AuthToken, ec: ExecutionContext): submit = new submit(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/jobs:submit")).addQueryStringParameters())
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, regionsId :PlayApi, jobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/jobs/${jobsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectId: String, region: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/jobs/${jobId}")).addQueryStringParameters())
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Job]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Job])
				}
				object get {
					def apply(projectId: String, region: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/jobs/${jobId}")).addQueryStringParameters())
					given Conversion[get, Future[Schema.Job]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withJob(body: Schema.Job) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Job])
				}
				object patch {
					def apply(projectId: String, region: String, jobId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/jobs/${jobId}")).addQueryStringParameters("updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListJobsResponse]) {
					/** Optional. The number of results to return in each response.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The page token, returned by a previous call, to request the next page of results. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. If set, the returned jobs list includes only jobs that were submitted to the named cluster. */
					def withClusterName(clusterName: String) = new list(req.addQueryStringParameters("clusterName" -> clusterName.toString))
					/** Optional. Specifies enumerated categories of jobs to list. (default = match ALL jobs).If filter is provided, jobStateMatcher will be ignored.<br>Possible values:<br>ALL: Match all jobs, regardless of state.<br>ACTIVE: Only match jobs in non-terminal states: PENDING, RUNNING, or CANCEL_PENDING.<br>NON_ACTIVE: Only match jobs in terminal states: CANCELLED, DONE, or ERROR. */
					def withJobStateMatcher(jobStateMatcher: String) = new list(req.addQueryStringParameters("jobStateMatcher" -> jobStateMatcher.toString))
					/** Optional. A filter constraining the jobs to list. Filters are case-sensitive and have the following syntax:field = value AND field = value ...where field is status.state or labels.[KEY], and [KEY] is a label key. value can be &#42; to match all values. status.state can be either ACTIVE or NON_ACTIVE. Only the logical AND operator is supported; space-separated items are treated as having an implicit AND operator.Example filter:status.state = ACTIVE AND labels.env = staging AND labels.starred = &#42; */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListJobsResponse])
				}
				object list {
					def apply(projectId: String, region: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectId}/regions/${region}/jobs")).addQueryStringParameters())
					given Conversion[list, Future[Schema.ListJobsResponse]] = (fun: list) => fun.apply()
				}
			}
			object workflowTemplates {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, regionsId :PlayApi, workflowTemplatesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/workflowTemplates/${workflowTemplatesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class instantiateInline(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. A tag that prevents multiple concurrent workflow instances with the same tag from running. This mitigates risk of concurrent instances started due to retries.It is recommended to always set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The tag must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new instantiateInline(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withWorkflowTemplate(body: Schema.WorkflowTemplate) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object instantiateInline {
					def apply(projectsId :PlayApi, regionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): instantiateInline = new instantiateInline(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/workflowTemplates:instantiateInline")).addQueryStringParameters("parent" -> parent.toString))
				}
				class instantiate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withInstantiateWorkflowTemplateRequest(body: Schema.InstantiateWorkflowTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object instantiate {
					def apply(projectsId :PlayApi, regionsId :PlayApi, workflowTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): instantiate = new instantiate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/workflowTemplates/${workflowTemplatesId}:instantiate")).addQueryStringParameters("name" -> name.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, regionsId :PlayApi, workflowTemplatesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/workflowTemplates/${workflowTemplatesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					/** Optional. The version of workflow template to delete. If specified, will only delete the template if the current server version matches specified version.<br>Format: int32 */
					def withVersion(version: Int) = new delete(req.addQueryStringParameters("version" -> version.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, regionsId :PlayApi, workflowTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/workflowTemplates/${workflowTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.WorkflowTemplate]) {
					/** Optional. The version of workflow template to retrieve. Only previously instantiated versions can be retrieved.If unspecified, retrieves the current version.<br>Format: int32 */
					def withVersion(version: Int) = new get(req.addQueryStringParameters("version" -> version.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.WorkflowTemplate])
				}
				object get {
					def apply(projectsId :PlayApi, regionsId :PlayApi, workflowTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/workflowTemplates/${workflowTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.WorkflowTemplate]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withWorkflowTemplate(body: Schema.WorkflowTemplate) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.WorkflowTemplate])
				}
				object update {
					def apply(projectsId :PlayApi, regionsId :PlayApi, workflowTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/workflowTemplates/${workflowTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWorkflowTemplatesResponse]) {
					/** Optional. The maximum number of results to return in each response.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The page token, returned by a previous call, to request the next page of results. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListWorkflowTemplatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, regionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/workflowTemplates")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListWorkflowTemplatesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withWorkflowTemplate(body: Schema.WorkflowTemplate) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.WorkflowTemplate])
				}
				object create {
					def apply(projectsId :PlayApi, regionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/workflowTemplates")).addQueryStringParameters("parent" -> parent.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, regionsId :PlayApi, workflowTemplatesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/workflowTemplates/${workflowTemplatesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object autoscalingPolicies {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, regionsId :PlayApi, autoscalingPoliciesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/autoscalingPolicies/${autoscalingPoliciesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, regionsId :PlayApi, autoscalingPoliciesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/autoscalingPolicies/${autoscalingPoliciesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, regionsId :PlayApi, autoscalingPoliciesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/autoscalingPolicies/${autoscalingPoliciesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AutoscalingPolicy]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.AutoscalingPolicy])
				}
				object get {
					def apply(projectsId :PlayApi, regionsId :PlayApi, autoscalingPoliciesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/autoscalingPolicies/${autoscalingPoliciesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.AutoscalingPolicy]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAutoscalingPolicy(body: Schema.AutoscalingPolicy) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.AutoscalingPolicy])
				}
				object update {
					def apply(projectsId :PlayApi, regionsId :PlayApi, autoscalingPoliciesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/autoscalingPolicies/${autoscalingPoliciesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAutoscalingPoliciesResponse]) {
					/** Optional. The maximum number of results to return in each response. Must be less than or equal to 1000. Defaults to 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The page token, returned by a previous call, to request the next page of results. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListAutoscalingPoliciesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, regionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/autoscalingPolicies")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListAutoscalingPoliciesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAutoscalingPolicy(body: Schema.AutoscalingPolicy) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AutoscalingPolicy])
				}
				object create {
					def apply(projectsId :PlayApi, regionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/autoscalingPolicies")).addQueryStringParameters("parent" -> parent.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, regionsId :PlayApi, autoscalingPoliciesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/regions/${regionsId}/autoscalingPolicies/${autoscalingPoliciesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
			}
		}
		object locations {
			object sessions {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. A unique ID used to identify the request. If the service receives two CreateSessionRequests (https://cloud.google.com/dataproc/docs/reference/rpc/google.cloud.dataproc.v1#google.cloud.dataproc.v1.CreateSessionRequest)s with the same ID, the second request is ignored, and the first Session is created and stored in the backend.Recommendation: Set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The value must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withSession(body: Schema.Session) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, sessionId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions")).addQueryStringParameters("parent" -> parent.toString, "sessionId" -> sessionId.toString))
				}
				class terminate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTerminateSessionRequest(body: Schema.TerminateSessionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object terminate {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): terminate = new terminate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}:terminate")).addQueryStringParameters("name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. A unique ID used to identify the request. If the service receives two DeleteSessionRequest (https://cloud.google.com/dataproc/docs/reference/rpc/google.cloud.dataproc.v1#google.cloud.dataproc.v1.DeleteSessionRequest)s with the same ID, the second request is ignored.Recommendation: Set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The value must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Session]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Session])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Session]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSessionsResponse]) {
					/** Optional. The maximum number of sessions to return in each response. The service may return fewer than this value.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token received from a previous ListSessions call. Provide this token to retrieve the subsequent page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. A filter for the sessions to return in the response.A filter is a logical expression constraining the values of various fields in each session resource. Filters are case sensitive, and may contain multiple clauses combined with logical operators (AND, OR). Supported fields are session_id, session_uuid, state, create_time, and labels.Example: state = ACTIVE and create_time < "2023-01-01T00:00:00Z" is a filter for sessions in an ACTIVE state that were created before 2023-01-01. state = ACTIVE and labels.environment=production is a filter for sessions in an ACTIVE state that have a production environment label.See https://google.aip.dev/assets/misc/ebnf-filtering.txt for a detailed description of the filter syntax and a list of supported comparators. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListSessionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSessionsResponse]] = (fun: list) => fun.apply()
				}
				object sparkApplications {
					class searchExecutors(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSessionSparkApplicationExecutorsResponse]) {
						/** Optional. Filter to select whether active/ dead or all executors should be selected.<br>Possible values:<br>EXECUTOR_STATUS_UNSPECIFIED: <br>EXECUTOR_STATUS_ACTIVE: <br>EXECUTOR_STATUS_DEAD:  */
						def withExecutorStatus(executorStatus: String) = new searchExecutors(req.addQueryStringParameters("executorStatus" -> executorStatus.toString))
						/** Optional. Maximum number of executors to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchExecutors(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous SearchSessionSparkApplicationExecutors call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchExecutors(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSessionSparkApplicationExecutorsResponse])
					}
					object searchExecutors {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): searchExecutors = new searchExecutors(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:searchExecutors")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[searchExecutors, Future[Schema.SearchSessionSparkApplicationExecutorsResponse]] = (fun: searchExecutors) => fun.apply()
					}
					class accessJob(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSessionSparkApplicationJobResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSessionSparkApplicationJobResponse])
					}
					object accessJob {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): accessJob = new accessJob(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:accessJob")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "jobId" -> jobId.toString))
						given Conversion[accessJob, Future[Schema.AccessSessionSparkApplicationJobResponse]] = (fun: accessJob) => fun.apply()
					}
					class accessSqlQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSessionSparkApplicationSqlQueryResponse]) {
						/** Optional. Lists/ hides details of Spark plan nodes. True is set to list and false to hide. */
						def withDetails(details: Boolean) = new accessSqlQuery(req.addQueryStringParameters("details" -> details.toString))
						/** Optional. Enables/ disables physical plan description on demand */
						def withPlanDescription(planDescription: Boolean) = new accessSqlQuery(req.addQueryStringParameters("planDescription" -> planDescription.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSessionSparkApplicationSqlQueryResponse])
					}
					object accessSqlQuery {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, executionId: String)(using auth: AuthToken, ec: ExecutionContext): accessSqlQuery = new accessSqlQuery(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:accessSqlQuery")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "executionId" -> executionId.toString))
						given Conversion[accessSqlQuery, Future[Schema.AccessSessionSparkApplicationSqlQueryResponse]] = (fun: accessSqlQuery) => fun.apply()
					}
					class summarizeJobs(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SummarizeSessionSparkApplicationJobsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.SummarizeSessionSparkApplicationJobsResponse])
					}
					object summarizeJobs {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): summarizeJobs = new summarizeJobs(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:summarizeJobs")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[summarizeJobs, Future[Schema.SummarizeSessionSparkApplicationJobsResponse]] = (fun: summarizeJobs) => fun.apply()
					}
					class summarizeStages(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SummarizeSessionSparkApplicationStagesResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.SummarizeSessionSparkApplicationStagesResponse])
					}
					object summarizeStages {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): summarizeStages = new summarizeStages(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:summarizeStages")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[summarizeStages, Future[Schema.SummarizeSessionSparkApplicationStagesResponse]] = (fun: summarizeStages) => fun.apply()
					}
					class searchStages(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSessionSparkApplicationStagesResponse]) {
						/** Optional. List only stages in the given state.<br>Possible values:<br>STAGE_STATUS_UNSPECIFIED: <br>STAGE_STATUS_ACTIVE: <br>STAGE_STATUS_COMPLETE: <br>STAGE_STATUS_FAILED: <br>STAGE_STATUS_PENDING: <br>STAGE_STATUS_SKIPPED:  */
						def withStageStatus(stageStatus: String) = new searchStages(req.addQueryStringParameters("stageStatus" -> stageStatus.toString))
						/** Optional. Maximum number of stages (paging based on stage_id) to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchStages(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous SearchSessionSparkApplicationStages call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchStages(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The list of summary metrics fields to include. Empty list will default to skip all summary metrics fields. Example, if the response should include TaskQuantileMetrics, the request should have task_quantile_metrics in summary_metrics_mask field<br>Format: google-fieldmask */
						def withSummaryMetricsMask(summaryMetricsMask: String) = new searchStages(req.addQueryStringParameters("summaryMetricsMask" -> summaryMetricsMask.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSessionSparkApplicationStagesResponse])
					}
					object searchStages {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): searchStages = new searchStages(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:searchStages")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[searchStages, Future[Schema.SearchSessionSparkApplicationStagesResponse]] = (fun: searchStages) => fun.apply()
					}
					class summarizeExecutors(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SummarizeSessionSparkApplicationExecutorsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.SummarizeSessionSparkApplicationExecutorsResponse])
					}
					object summarizeExecutors {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): summarizeExecutors = new summarizeExecutors(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:summarizeExecutors")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[summarizeExecutors, Future[Schema.SummarizeSessionSparkApplicationExecutorsResponse]] = (fun: summarizeExecutors) => fun.apply()
					}
					class summarizeStageAttemptTasks(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SummarizeSessionSparkApplicationStageAttemptTasksResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.SummarizeSessionSparkApplicationStageAttemptTasksResponse])
					}
					object summarizeStageAttemptTasks {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, stageId: String, stageAttemptId: Int)(using auth: AuthToken, ec: ExecutionContext): summarizeStageAttemptTasks = new summarizeStageAttemptTasks(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:summarizeStageAttemptTasks")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "stageId" -> stageId.toString, "stageAttemptId" -> stageAttemptId.toString))
						given Conversion[summarizeStageAttemptTasks, Future[Schema.SummarizeSessionSparkApplicationStageAttemptTasksResponse]] = (fun: summarizeStageAttemptTasks) => fun.apply()
					}
					class accessStageAttempt(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSessionSparkApplicationStageAttemptResponse]) {
						/** Optional. The list of summary metrics fields to include. Empty list will default to skip all summary metrics fields. Example, if the response should include TaskQuantileMetrics, the request should have task_quantile_metrics in summary_metrics_mask field<br>Format: google-fieldmask */
						def withSummaryMetricsMask(summaryMetricsMask: String) = new accessStageAttempt(req.addQueryStringParameters("summaryMetricsMask" -> summaryMetricsMask.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSessionSparkApplicationStageAttemptResponse])
					}
					object accessStageAttempt {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, stageId: String, stageAttemptId: Int)(using auth: AuthToken, ec: ExecutionContext): accessStageAttempt = new accessStageAttempt(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:accessStageAttempt")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "stageId" -> stageId.toString, "stageAttemptId" -> stageAttemptId.toString))
						given Conversion[accessStageAttempt, Future[Schema.AccessSessionSparkApplicationStageAttemptResponse]] = (fun: accessStageAttempt) => fun.apply()
					}
					class write(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withWriteSessionSparkApplicationContextRequest(body: Schema.WriteSessionSparkApplicationContextRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.WriteSessionSparkApplicationContextResponse])
					}
					object write {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): write = new write(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:write")).addQueryStringParameters("name" -> name.toString))
					}
					class searchExecutorStageSummary(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSessionSparkApplicationExecutorStageSummaryResponse]) {
						/** Optional. Maximum number of executors to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchExecutorStageSummary(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous SearchSessionSparkApplicationExecutorStageSummary call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchExecutorStageSummary(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSessionSparkApplicationExecutorStageSummaryResponse])
					}
					object searchExecutorStageSummary {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, stageId: String, stageAttemptId: Int)(using auth: AuthToken, ec: ExecutionContext): searchExecutorStageSummary = new searchExecutorStageSummary(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:searchExecutorStageSummary")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "stageId" -> stageId.toString, "stageAttemptId" -> stageAttemptId.toString))
						given Conversion[searchExecutorStageSummary, Future[Schema.SearchSessionSparkApplicationExecutorStageSummaryResponse]] = (fun: searchExecutorStageSummary) => fun.apply()
					}
					class searchJobs(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSessionSparkApplicationJobsResponse]) {
						/** Optional. List only jobs in the specific state.<br>Possible values:<br>JOB_EXECUTION_STATUS_UNSPECIFIED: <br>JOB_EXECUTION_STATUS_RUNNING: <br>JOB_EXECUTION_STATUS_SUCCEEDED: <br>JOB_EXECUTION_STATUS_FAILED: <br>JOB_EXECUTION_STATUS_UNKNOWN:  */
						def withJobStatus(jobStatus: String) = new searchJobs(req.addQueryStringParameters("jobStatus" -> jobStatus.toString))
						/** Optional. Maximum number of jobs to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchJobs(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous SearchSessionSparkApplicationJobs call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchJobs(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSessionSparkApplicationJobsResponse])
					}
					object searchJobs {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): searchJobs = new searchJobs(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:searchJobs")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[searchJobs, Future[Schema.SearchSessionSparkApplicationJobsResponse]] = (fun: searchJobs) => fun.apply()
					}
					class accessEnvironmentInfo(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSessionSparkApplicationEnvironmentInfoResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSessionSparkApplicationEnvironmentInfoResponse])
					}
					object accessEnvironmentInfo {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): accessEnvironmentInfo = new accessEnvironmentInfo(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:accessEnvironmentInfo")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[accessEnvironmentInfo, Future[Schema.AccessSessionSparkApplicationEnvironmentInfoResponse]] = (fun: accessEnvironmentInfo) => fun.apply()
					}
					class searchStageAttempts(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSessionSparkApplicationStageAttemptsResponse]) {
						/** Optional. Maximum number of stage attempts (paging based on stage_attempt_id) to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchStageAttempts(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous SearchSessionSparkApplicationStageAttempts call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchStageAttempts(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The list of summary metrics fields to include. Empty list will default to skip all summary metrics fields. Example, if the response should include TaskQuantileMetrics, the request should have task_quantile_metrics in summary_metrics_mask field<br>Format: google-fieldmask */
						def withSummaryMetricsMask(summaryMetricsMask: String) = new searchStageAttempts(req.addQueryStringParameters("summaryMetricsMask" -> summaryMetricsMask.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSessionSparkApplicationStageAttemptsResponse])
					}
					object searchStageAttempts {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, stageId: String)(using auth: AuthToken, ec: ExecutionContext): searchStageAttempts = new searchStageAttempts(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:searchStageAttempts")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "stageId" -> stageId.toString))
						given Conversion[searchStageAttempts, Future[Schema.SearchSessionSparkApplicationStageAttemptsResponse]] = (fun: searchStageAttempts) => fun.apply()
					}
					class searchSqlQueries(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSessionSparkApplicationSqlQueriesResponse]) {
						/** Optional. Lists/ hides details of Spark plan nodes. True is set to list and false to hide. */
						def withDetails(details: Boolean) = new searchSqlQueries(req.addQueryStringParameters("details" -> details.toString))
						/** Optional. Enables/ disables physical plan description on demand */
						def withPlanDescription(planDescription: Boolean) = new searchSqlQueries(req.addQueryStringParameters("planDescription" -> planDescription.toString))
						/** Optional. Maximum number of queries to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchSqlQueries(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous SearchSessionSparkApplicationSqlQueries call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchSqlQueries(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSessionSparkApplicationSqlQueriesResponse])
					}
					object searchSqlQueries {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): searchSqlQueries = new searchSqlQueries(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:searchSqlQueries")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[searchSqlQueries, Future[Schema.SearchSessionSparkApplicationSqlQueriesResponse]] = (fun: searchSqlQueries) => fun.apply()
					}
					class searchStageAttemptTasks(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSessionSparkApplicationStageAttemptTasksResponse]) {
						/** Optional. Stage ID<br>Format: int64 */
						def withStageId(stageId: String) = new searchStageAttemptTasks(req.addQueryStringParameters("stageId" -> stageId.toString))
						/** Optional. Stage Attempt ID<br>Format: int32 */
						def withStageAttemptId(stageAttemptId: Int) = new searchStageAttemptTasks(req.addQueryStringParameters("stageAttemptId" -> stageAttemptId.toString))
						/** Optional. Sort the tasks by runtime. */
						def withSortRuntime(sortRuntime: Boolean) = new searchStageAttemptTasks(req.addQueryStringParameters("sortRuntime" -> sortRuntime.toString))
						/** Optional. List only tasks in the state.<br>Possible values:<br>TASK_STATUS_UNSPECIFIED: <br>TASK_STATUS_RUNNING: <br>TASK_STATUS_SUCCESS: <br>TASK_STATUS_FAILED: <br>TASK_STATUS_KILLED: <br>TASK_STATUS_PENDING:  */
						def withTaskStatus(taskStatus: String) = new searchStageAttemptTasks(req.addQueryStringParameters("taskStatus" -> taskStatus.toString))
						/** Optional. Maximum number of tasks to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchStageAttemptTasks(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous SearchSessionSparkApplicationStageAttemptTasks call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchStageAttemptTasks(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSessionSparkApplicationStageAttemptTasksResponse])
					}
					object searchStageAttemptTasks {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): searchStageAttemptTasks = new searchStageAttemptTasks(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:searchStageAttemptTasks")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[searchStageAttemptTasks, Future[Schema.SearchSessionSparkApplicationStageAttemptTasksResponse]] = (fun: searchStageAttemptTasks) => fun.apply()
					}
					class accessStageRddGraph(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSessionSparkApplicationStageRddOperationGraphResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSessionSparkApplicationStageRddOperationGraphResponse])
					}
					object accessStageRddGraph {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, stageId: String)(using auth: AuthToken, ec: ExecutionContext): accessStageRddGraph = new accessStageRddGraph(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:accessStageRddGraph")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "stageId" -> stageId.toString))
						given Conversion[accessStageRddGraph, Future[Schema.AccessSessionSparkApplicationStageRddOperationGraphResponse]] = (fun: accessStageRddGraph) => fun.apply()
					}
					class accessSqlPlan(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSessionSparkApplicationSqlSparkPlanGraphResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSessionSparkApplicationSqlSparkPlanGraphResponse])
					}
					object accessSqlPlan {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, executionId: String)(using auth: AuthToken, ec: ExecutionContext): accessSqlPlan = new accessSqlPlan(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:accessSqlPlan")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "executionId" -> executionId.toString))
						given Conversion[accessSqlPlan, Future[Schema.AccessSessionSparkApplicationSqlSparkPlanGraphResponse]] = (fun: accessSqlPlan) => fun.apply()
					}
					class access(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSessionSparkApplicationResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSessionSparkApplicationResponse])
					}
					object access {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): access = new access(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications/${sparkApplicationsId}:access")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[access, Future[Schema.AccessSessionSparkApplicationResponse]] = (fun: access) => fun.apply()
					}
					class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSessionSparkApplicationsResponse]) {
						/** Optional. Search only applications in the chosen state.<br>Possible values:<br>APPLICATION_STATUS_UNSPECIFIED: <br>APPLICATION_STATUS_RUNNING: <br>APPLICATION_STATUS_COMPLETED:  */
						def withApplicationStatus(applicationStatus: String) = new search(req.addQueryStringParameters("applicationStatus" -> applicationStatus.toString))
						/** Optional. Earliest start timestamp to list.<br>Format: google-datetime */
						def withMinTime(minTime: String) = new search(req.addQueryStringParameters("minTime" -> minTime.toString))
						/** Optional. Latest start timestamp to list.<br>Format: google-datetime */
						def withMaxTime(maxTime: String) = new search(req.addQueryStringParameters("maxTime" -> maxTime.toString))
						/** Optional. Earliest end timestamp to list.<br>Format: google-datetime */
						def withMinEndTime(minEndTime: String) = new search(req.addQueryStringParameters("minEndTime" -> minEndTime.toString))
						/** Optional. Latest end timestamp to list.<br>Format: google-datetime */
						def withMaxEndTime(maxEndTime: String) = new search(req.addQueryStringParameters("maxEndTime" -> maxEndTime.toString))
						/** Optional. Maximum number of applications to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous SearchSessionSparkApplications call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSessionSparkApplicationsResponse])
					}
					object search {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sessionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessions/${sessionsId}/sparkApplications:search")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[search, Future[Schema.SearchSessionSparkApplicationsResponse]] = (fun: search) => fun.apply()
					}
				}
			}
			object autoscalingPolicies {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, autoscalingPoliciesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/autoscalingPolicies/${autoscalingPoliciesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, autoscalingPoliciesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/autoscalingPolicies/${autoscalingPoliciesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, autoscalingPoliciesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/autoscalingPolicies/${autoscalingPoliciesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AutoscalingPolicy]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.AutoscalingPolicy])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, autoscalingPoliciesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/autoscalingPolicies/${autoscalingPoliciesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.AutoscalingPolicy]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAutoscalingPolicy(body: Schema.AutoscalingPolicy) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.AutoscalingPolicy])
				}
				object update {
					def apply(projectsId :PlayApi, locationsId :PlayApi, autoscalingPoliciesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/autoscalingPolicies/${autoscalingPoliciesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAutoscalingPoliciesResponse]) {
					/** Optional. The maximum number of results to return in each response. Must be less than or equal to 1000. Defaults to 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The page token, returned by a previous call, to request the next page of results. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListAutoscalingPoliciesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/autoscalingPolicies")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListAutoscalingPoliciesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAutoscalingPolicy(body: Schema.AutoscalingPolicy) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AutoscalingPolicy])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/autoscalingPolicies")).addQueryStringParameters("parent" -> parent.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, autoscalingPoliciesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/autoscalingPolicies/${autoscalingPoliciesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("POST").map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
				}
			}
			object batches {
				class analyze(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAnalyzeBatchRequest(body: Schema.AnalyzeBatchRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object analyze {
					def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): analyze = new analyze(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}:analyze")).addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The ID to use for the batch, which will become the final component of the batch's resource name.This value must be 4-63 characters. Valid characters are /[a-z][0-9]-/. */
					def withBatchId(batchId: String) = new create(req.addQueryStringParameters("batchId" -> batchId.toString))
					/** Optional. A unique ID used to identify the request. If the service receives two CreateBatchRequest (https://cloud.google.com/dataproc/docs/reference/rpc/google.cloud.dataproc.v1#google.cloud.dataproc.v1.CreateBatchRequest)s with the same request_id, the second request is ignored and the Operation that corresponds to the first Batch created and stored in the backend is returned.Recommendation: Set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The value must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withBatch(body: Schema.Batch) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Batch]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Batch])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Batch]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBatchesResponse]) {
					/** Optional. The maximum number of batches to return in each response. The service may return fewer than this value. The default page size is 20; the maximum page size is 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token received from a previous ListBatches call. Provide this token to retrieve the subsequent page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. A filter for the batches to return in the response.A filter is a logical expression constraining the values of various fields in each batch resource. Filters are case sensitive, and may contain multiple clauses combined with logical operators (AND/OR). Supported fields are batch_id, batch_uuid, state, create_time, and labels.e.g. state = RUNNING and create_time < "2023-01-01T00:00:00Z" filters for batches in state RUNNING that were created before 2023-01-01. state = RUNNING and labels.environment=production filters for batches in state in a RUNNING state that have a production environment label.See https://google.aip.dev/assets/misc/ebnf-filtering.txt for a detailed description of the filter syntax and a list of supported comparisons. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Field(s) on which to sort the list of batches.Currently the only supported sort orders are unspecified (empty) and create_time desc to sort by most recently created batches first.See https://google.aip.dev/132#ordering for more details. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListBatchesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListBatchesResponse]] = (fun: list) => fun.apply()
				}
				object sparkApplications {
					class searchExecutors(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSparkApplicationExecutorsResponse]) {
						/** Optional. Filter to select whether active/ dead or all executors should be selected.<br>Possible values:<br>EXECUTOR_STATUS_UNSPECIFIED: <br>EXECUTOR_STATUS_ACTIVE: <br>EXECUTOR_STATUS_DEAD:  */
						def withExecutorStatus(executorStatus: String) = new searchExecutors(req.addQueryStringParameters("executorStatus" -> executorStatus.toString))
						/** Optional. Maximum number of executors to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchExecutors(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous AccessSparkApplicationExecutorsList call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchExecutors(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSparkApplicationExecutorsResponse])
					}
					object searchExecutors {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): searchExecutors = new searchExecutors(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:searchExecutors")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[searchExecutors, Future[Schema.SearchSparkApplicationExecutorsResponse]] = (fun: searchExecutors) => fun.apply()
					}
					class accessJob(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSparkApplicationJobResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSparkApplicationJobResponse])
					}
					object accessJob {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): accessJob = new accessJob(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:accessJob")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "jobId" -> jobId.toString))
						given Conversion[accessJob, Future[Schema.AccessSparkApplicationJobResponse]] = (fun: accessJob) => fun.apply()
					}
					class accessSqlQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSparkApplicationSqlQueryResponse]) {
						/** Optional. Lists/ hides details of Spark plan nodes. True is set to list and false to hide. */
						def withDetails(details: Boolean) = new accessSqlQuery(req.addQueryStringParameters("details" -> details.toString))
						/** Optional. Enables/ disables physical plan description on demand */
						def withPlanDescription(planDescription: Boolean) = new accessSqlQuery(req.addQueryStringParameters("planDescription" -> planDescription.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSparkApplicationSqlQueryResponse])
					}
					object accessSqlQuery {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, executionId: String)(using auth: AuthToken, ec: ExecutionContext): accessSqlQuery = new accessSqlQuery(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:accessSqlQuery")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "executionId" -> executionId.toString))
						given Conversion[accessSqlQuery, Future[Schema.AccessSparkApplicationSqlQueryResponse]] = (fun: accessSqlQuery) => fun.apply()
					}
					class summarizeJobs(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SummarizeSparkApplicationJobsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.SummarizeSparkApplicationJobsResponse])
					}
					object summarizeJobs {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): summarizeJobs = new summarizeJobs(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:summarizeJobs")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[summarizeJobs, Future[Schema.SummarizeSparkApplicationJobsResponse]] = (fun: summarizeJobs) => fun.apply()
					}
					class summarizeStages(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SummarizeSparkApplicationStagesResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.SummarizeSparkApplicationStagesResponse])
					}
					object summarizeStages {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): summarizeStages = new summarizeStages(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:summarizeStages")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[summarizeStages, Future[Schema.SummarizeSparkApplicationStagesResponse]] = (fun: summarizeStages) => fun.apply()
					}
					class searchStages(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSparkApplicationStagesResponse]) {
						/** Optional. List only stages in the given state.<br>Possible values:<br>STAGE_STATUS_UNSPECIFIED: <br>STAGE_STATUS_ACTIVE: <br>STAGE_STATUS_COMPLETE: <br>STAGE_STATUS_FAILED: <br>STAGE_STATUS_PENDING: <br>STAGE_STATUS_SKIPPED:  */
						def withStageStatus(stageStatus: String) = new searchStages(req.addQueryStringParameters("stageStatus" -> stageStatus.toString))
						/** Optional. Maximum number of stages (paging based on stage_id) to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchStages(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous FetchSparkApplicationStagesList call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchStages(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The list of summary metrics fields to include. Empty list will default to skip all summary metrics fields. Example, if the response should include TaskQuantileMetrics, the request should have task_quantile_metrics in summary_metrics_mask field<br>Format: google-fieldmask */
						def withSummaryMetricsMask(summaryMetricsMask: String) = new searchStages(req.addQueryStringParameters("summaryMetricsMask" -> summaryMetricsMask.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSparkApplicationStagesResponse])
					}
					object searchStages {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): searchStages = new searchStages(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:searchStages")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[searchStages, Future[Schema.SearchSparkApplicationStagesResponse]] = (fun: searchStages) => fun.apply()
					}
					class summarizeExecutors(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SummarizeSparkApplicationExecutorsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.SummarizeSparkApplicationExecutorsResponse])
					}
					object summarizeExecutors {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): summarizeExecutors = new summarizeExecutors(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:summarizeExecutors")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[summarizeExecutors, Future[Schema.SummarizeSparkApplicationExecutorsResponse]] = (fun: summarizeExecutors) => fun.apply()
					}
					class summarizeStageAttemptTasks(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SummarizeSparkApplicationStageAttemptTasksResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.SummarizeSparkApplicationStageAttemptTasksResponse])
					}
					object summarizeStageAttemptTasks {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, stageId: String, stageAttemptId: Int)(using auth: AuthToken, ec: ExecutionContext): summarizeStageAttemptTasks = new summarizeStageAttemptTasks(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:summarizeStageAttemptTasks")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "stageId" -> stageId.toString, "stageAttemptId" -> stageAttemptId.toString))
						given Conversion[summarizeStageAttemptTasks, Future[Schema.SummarizeSparkApplicationStageAttemptTasksResponse]] = (fun: summarizeStageAttemptTasks) => fun.apply()
					}
					class accessStageAttempt(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSparkApplicationStageAttemptResponse]) {
						/** Optional. The list of summary metrics fields to include. Empty list will default to skip all summary metrics fields. Example, if the response should include TaskQuantileMetrics, the request should have task_quantile_metrics in summary_metrics_mask field<br>Format: google-fieldmask */
						def withSummaryMetricsMask(summaryMetricsMask: String) = new accessStageAttempt(req.addQueryStringParameters("summaryMetricsMask" -> summaryMetricsMask.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSparkApplicationStageAttemptResponse])
					}
					object accessStageAttempt {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, stageId: String, stageAttemptId: Int)(using auth: AuthToken, ec: ExecutionContext): accessStageAttempt = new accessStageAttempt(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:accessStageAttempt")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "stageId" -> stageId.toString, "stageAttemptId" -> stageAttemptId.toString))
						given Conversion[accessStageAttempt, Future[Schema.AccessSparkApplicationStageAttemptResponse]] = (fun: accessStageAttempt) => fun.apply()
					}
					class write(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withWriteSparkApplicationContextRequest(body: Schema.WriteSparkApplicationContextRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.WriteSparkApplicationContextResponse])
					}
					object write {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): write = new write(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:write")).addQueryStringParameters("name" -> name.toString))
					}
					class searchExecutorStageSummary(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSparkApplicationExecutorStageSummaryResponse]) {
						/** Optional. Maximum number of executors to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchExecutorStageSummary(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous AccessSparkApplicationExecutorsList call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchExecutorStageSummary(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSparkApplicationExecutorStageSummaryResponse])
					}
					object searchExecutorStageSummary {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, stageId: String, stageAttemptId: Int)(using auth: AuthToken, ec: ExecutionContext): searchExecutorStageSummary = new searchExecutorStageSummary(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:searchExecutorStageSummary")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "stageId" -> stageId.toString, "stageAttemptId" -> stageAttemptId.toString))
						given Conversion[searchExecutorStageSummary, Future[Schema.SearchSparkApplicationExecutorStageSummaryResponse]] = (fun: searchExecutorStageSummary) => fun.apply()
					}
					class searchJobs(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSparkApplicationJobsResponse]) {
						/** Optional. List only jobs in the specific state.<br>Possible values:<br>JOB_EXECUTION_STATUS_UNSPECIFIED: <br>JOB_EXECUTION_STATUS_RUNNING: <br>JOB_EXECUTION_STATUS_SUCCEEDED: <br>JOB_EXECUTION_STATUS_FAILED: <br>JOB_EXECUTION_STATUS_UNKNOWN:  */
						def withJobStatus(jobStatus: String) = new searchJobs(req.addQueryStringParameters("jobStatus" -> jobStatus.toString))
						/** Optional. Maximum number of jobs to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchJobs(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous SearchSparkApplicationJobs call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchJobs(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSparkApplicationJobsResponse])
					}
					object searchJobs {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): searchJobs = new searchJobs(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:searchJobs")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[searchJobs, Future[Schema.SearchSparkApplicationJobsResponse]] = (fun: searchJobs) => fun.apply()
					}
					class accessEnvironmentInfo(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSparkApplicationEnvironmentInfoResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSparkApplicationEnvironmentInfoResponse])
					}
					object accessEnvironmentInfo {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): accessEnvironmentInfo = new accessEnvironmentInfo(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:accessEnvironmentInfo")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[accessEnvironmentInfo, Future[Schema.AccessSparkApplicationEnvironmentInfoResponse]] = (fun: accessEnvironmentInfo) => fun.apply()
					}
					class searchStageAttempts(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSparkApplicationStageAttemptsResponse]) {
						/** Optional. Maximum number of stage attempts (paging based on stage_attempt_id) to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchStageAttempts(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous SearchSparkApplicationStageAttempts call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchStageAttempts(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The list of summary metrics fields to include. Empty list will default to skip all summary metrics fields. Example, if the response should include TaskQuantileMetrics, the request should have task_quantile_metrics in summary_metrics_mask field<br>Format: google-fieldmask */
						def withSummaryMetricsMask(summaryMetricsMask: String) = new searchStageAttempts(req.addQueryStringParameters("summaryMetricsMask" -> summaryMetricsMask.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSparkApplicationStageAttemptsResponse])
					}
					object searchStageAttempts {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, stageId: String)(using auth: AuthToken, ec: ExecutionContext): searchStageAttempts = new searchStageAttempts(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:searchStageAttempts")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "stageId" -> stageId.toString))
						given Conversion[searchStageAttempts, Future[Schema.SearchSparkApplicationStageAttemptsResponse]] = (fun: searchStageAttempts) => fun.apply()
					}
					class searchSqlQueries(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSparkApplicationSqlQueriesResponse]) {
						/** Optional. Lists/ hides details of Spark plan nodes. True is set to list and false to hide. */
						def withDetails(details: Boolean) = new searchSqlQueries(req.addQueryStringParameters("details" -> details.toString))
						/** Optional. Enables/ disables physical plan description on demand */
						def withPlanDescription(planDescription: Boolean) = new searchSqlQueries(req.addQueryStringParameters("planDescription" -> planDescription.toString))
						/** Optional. Maximum number of queries to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchSqlQueries(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous SearchSparkApplicationSqlQueries call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchSqlQueries(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSparkApplicationSqlQueriesResponse])
					}
					object searchSqlQueries {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): searchSqlQueries = new searchSqlQueries(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:searchSqlQueries")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[searchSqlQueries, Future[Schema.SearchSparkApplicationSqlQueriesResponse]] = (fun: searchSqlQueries) => fun.apply()
					}
					class searchStageAttemptTasks(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSparkApplicationStageAttemptTasksResponse]) {
						/** Optional. Stage ID<br>Format: int64 */
						def withStageId(stageId: String) = new searchStageAttemptTasks(req.addQueryStringParameters("stageId" -> stageId.toString))
						/** Optional. Stage Attempt ID<br>Format: int32 */
						def withStageAttemptId(stageAttemptId: Int) = new searchStageAttemptTasks(req.addQueryStringParameters("stageAttemptId" -> stageAttemptId.toString))
						/** Optional. Sort the tasks by runtime. */
						def withSortRuntime(sortRuntime: Boolean) = new searchStageAttemptTasks(req.addQueryStringParameters("sortRuntime" -> sortRuntime.toString))
						/** Optional. List only tasks in the state.<br>Possible values:<br>TASK_STATUS_UNSPECIFIED: <br>TASK_STATUS_RUNNING: <br>TASK_STATUS_SUCCESS: <br>TASK_STATUS_FAILED: <br>TASK_STATUS_KILLED: <br>TASK_STATUS_PENDING:  */
						def withTaskStatus(taskStatus: String) = new searchStageAttemptTasks(req.addQueryStringParameters("taskStatus" -> taskStatus.toString))
						/** Optional. Maximum number of tasks to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchStageAttemptTasks(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous ListSparkApplicationStageAttemptTasks call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new searchStageAttemptTasks(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSparkApplicationStageAttemptTasksResponse])
					}
					object searchStageAttemptTasks {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): searchStageAttemptTasks = new searchStageAttemptTasks(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:searchStageAttemptTasks")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[searchStageAttemptTasks, Future[Schema.SearchSparkApplicationStageAttemptTasksResponse]] = (fun: searchStageAttemptTasks) => fun.apply()
					}
					class accessStageRddGraph(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSparkApplicationStageRddOperationGraphResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSparkApplicationStageRddOperationGraphResponse])
					}
					object accessStageRddGraph {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, stageId: String)(using auth: AuthToken, ec: ExecutionContext): accessStageRddGraph = new accessStageRddGraph(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:accessStageRddGraph")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "stageId" -> stageId.toString))
						given Conversion[accessStageRddGraph, Future[Schema.AccessSparkApplicationStageRddOperationGraphResponse]] = (fun: accessStageRddGraph) => fun.apply()
					}
					class accessSqlPlan(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSparkApplicationSqlSparkPlanGraphResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSparkApplicationSqlSparkPlanGraphResponse])
					}
					object accessSqlPlan {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String, executionId: String)(using auth: AuthToken, ec: ExecutionContext): accessSqlPlan = new accessSqlPlan(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:accessSqlPlan")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "executionId" -> executionId.toString))
						given Conversion[accessSqlPlan, Future[Schema.AccessSparkApplicationSqlSparkPlanGraphResponse]] = (fun: accessSqlPlan) => fun.apply()
					}
					class access(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSparkApplicationResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.AccessSparkApplicationResponse])
					}
					object access {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, sparkApplicationsId :PlayApi, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): access = new access(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications/${sparkApplicationsId}:access")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString))
						given Conversion[access, Future[Schema.AccessSparkApplicationResponse]] = (fun: access) => fun.apply()
					}
					class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchSparkApplicationsResponse]) {
						/** Optional. Search only applications in the chosen state.<br>Possible values:<br>APPLICATION_STATUS_UNSPECIFIED: <br>APPLICATION_STATUS_RUNNING: <br>APPLICATION_STATUS_COMPLETED:  */
						def withApplicationStatus(applicationStatus: String) = new search(req.addQueryStringParameters("applicationStatus" -> applicationStatus.toString))
						/** Optional. Earliest start timestamp to list.<br>Format: google-datetime */
						def withMinTime(minTime: String) = new search(req.addQueryStringParameters("minTime" -> minTime.toString))
						/** Optional. Latest start timestamp to list.<br>Format: google-datetime */
						def withMaxTime(maxTime: String) = new search(req.addQueryStringParameters("maxTime" -> maxTime.toString))
						/** Optional. Earliest end timestamp to list.<br>Format: google-datetime */
						def withMinEndTime(minEndTime: String) = new search(req.addQueryStringParameters("minEndTime" -> minEndTime.toString))
						/** Optional. Latest end timestamp to list.<br>Format: google-datetime */
						def withMaxEndTime(maxEndTime: String) = new search(req.addQueryStringParameters("maxEndTime" -> maxEndTime.toString))
						/** Optional. Maximum number of applications to return in each response. The service may return fewer than this. The default page size is 10; the maximum page size is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token received from a previous SearchSparkApplications call. Provide this token to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchSparkApplicationsResponse])
					}
					object search {
						def apply(projectsId :PlayApi, locationsId :PlayApi, batchesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/batches/${batchesId}/sparkApplications:search")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[search, Future[Schema.SearchSparkApplicationsResponse]] = (fun: search) => fun.apply()
					}
				}
			}
			object workflowTemplates {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workflowTemplatesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflowTemplates/${workflowTemplatesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class instantiateInline(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. A tag that prevents multiple concurrent workflow instances with the same tag from running. This mitigates risk of concurrent instances started due to retries.It is recommended to always set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The tag must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new instantiateInline(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withWorkflowTemplate(body: Schema.WorkflowTemplate) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object instantiateInline {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): instantiateInline = new instantiateInline(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflowTemplates:instantiateInline")).addQueryStringParameters("parent" -> parent.toString))
				}
				class instantiate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withInstantiateWorkflowTemplateRequest(body: Schema.InstantiateWorkflowTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object instantiate {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workflowTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): instantiate = new instantiate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflowTemplates/${workflowTemplatesId}:instantiate")).addQueryStringParameters("name" -> name.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workflowTemplatesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflowTemplates/${workflowTemplatesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					/** Optional. The version of workflow template to delete. If specified, will only delete the template if the current server version matches specified version.<br>Format: int32 */
					def withVersion(version: Int) = new delete(req.addQueryStringParameters("version" -> version.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workflowTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflowTemplates/${workflowTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.WorkflowTemplate]) {
					/** Optional. The version of workflow template to retrieve. Only previously instantiated versions can be retrieved.If unspecified, retrieves the current version.<br>Format: int32 */
					def withVersion(version: Int) = new get(req.addQueryStringParameters("version" -> version.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.WorkflowTemplate])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workflowTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflowTemplates/${workflowTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.WorkflowTemplate]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withWorkflowTemplate(body: Schema.WorkflowTemplate) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.WorkflowTemplate])
				}
				object update {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workflowTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflowTemplates/${workflowTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWorkflowTemplatesResponse]) {
					/** Optional. The maximum number of results to return in each response.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The page token, returned by a previous call, to request the next page of results. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListWorkflowTemplatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflowTemplates")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListWorkflowTemplatesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withWorkflowTemplate(body: Schema.WorkflowTemplate) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.WorkflowTemplate])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflowTemplates")).addQueryStringParameters("parent" -> parent.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workflowTemplatesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflowTemplates/${workflowTemplatesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object sessionTemplates {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSessionTemplate(body: Schema.SessionTemplate) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SessionTemplate])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessionTemplates")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sessionTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessionTemplates/${sessionTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SessionTemplate]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.SessionTemplate])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sessionTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessionTemplates/${sessionTemplatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.SessionTemplate]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSessionTemplate(body: Schema.SessionTemplate) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SessionTemplate])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sessionTemplatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessionTemplates/${sessionTemplatesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSessionTemplatesResponse]) {
					/** Optional. The maximum number of sessions to return in each response. The service may return fewer than this value.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token received from a previous ListSessions call. Provide this token to retrieve the subsequent page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. A filter for the session templates to return in the response. Filters are case sensitive and have the following syntax:field = value AND field = value ... */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListSessionTemplatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sessionTemplates")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSessionTemplatesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
