package com.boresjo.play.api.google.bigtableadmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://bigtableadmin.googleapis.com/"

	object operations {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		object projects {
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/operations/projects/${projectsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object projects {
		object instances {
			class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Instance])
			}
			object get {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInstancesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInstancesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListInstancesResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCreateInstanceRequest(body: Schema.CreateInstanceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances").addQueryStringParameters("parent" -> parent.toString))
			}
			class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withInstance(body: Schema.Instance) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Instance])
			}
			object update {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
			}
			class partialUpdateInstance(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withInstance(body: Schema.Instance) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object partialUpdateInstance {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): partialUpdateInstance = new partialUpdateInstance(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			object clusters {
				class partialUpdateCluster(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCluster(body: Schema.Cluster) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object partialUpdateCluster {
					def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): partialUpdateCluster = new partialUpdateCluster(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCluster(body: Schema.Cluster) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters").addQueryStringParameters("parent" -> parent.toString, "clusterId" -> clusterId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Cluster]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Cluster])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Cluster]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListClustersResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListClustersResponse]] = (fun: list) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCluster(body: Schema.Cluster) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
				}
				object update {
					def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString))
				}
				object backups {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, backupsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups/${backupsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, backupsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups/${backupsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class copy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCopyBackupRequest(body: Schema.CopyBackupRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object copy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): copy = new copy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups:copy").addQueryStringParameters("parent" -> parent.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Backup]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Backup])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Backup]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBackup(body: Schema.Backup) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Backup])
					}
					object patch {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, backupsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBackupsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBackupsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, parent: String, filter: String, orderBy: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListBackupsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBackup(body: Schema.Backup) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, parent: String, backupId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups").addQueryStringParameters("parent" -> parent.toString, "backupId" -> backupId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, backupsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups/${backupsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
				}
				object hotTablets {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListHotTabletsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListHotTabletsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, parent: String, startTime: String, endTime: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/hotTablets").addQueryStringParameters("parent" -> parent.toString, "startTime" -> startTime.toString, "endTime" -> endTime.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListHotTabletsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object appProfiles {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAppProfile(body: Schema.AppProfile) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AppProfile])
				}
				object create {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, appProfileId: String, ignoreWarnings: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/appProfiles").addQueryStringParameters("parent" -> parent.toString, "appProfileId" -> appProfileId.toString, "ignoreWarnings" -> ignoreWarnings.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, appProfilesId :PlayApi, name: String, ignoreWarnings: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/appProfiles/${appProfilesId}").addQueryStringParameters("name" -> name.toString, "ignoreWarnings" -> ignoreWarnings.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AppProfile]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AppProfile])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, appProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/appProfiles/${appProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.AppProfile]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAppProfile(body: Schema.AppProfile) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, instancesId :PlayApi, appProfilesId :PlayApi, name: String, updateMask: String, ignoreWarnings: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/appProfiles/${appProfilesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "ignoreWarnings" -> ignoreWarnings.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAppProfilesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAppProfilesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/appProfiles").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListAppProfilesResponse]] = (fun: list) => fun.apply()
				}
			}
			object tables {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUndeleteTableRequest(body: Schema.UndeleteTableRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object undelete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:undelete").addQueryStringParameters("name" -> name.toString))
				}
				class restore(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRestoreTableRequest(body: Schema.RestoreTableRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object restore {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): restore = new restore(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables:restore").addQueryStringParameters("parent" -> parent.toString))
				}
				class checkConsistency(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCheckConsistencyRequest(body: Schema.CheckConsistencyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CheckConsistencyResponse])
				}
				object checkConsistency {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): checkConsistency = new checkConsistency(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:checkConsistency").addQueryStringParameters("name" -> name.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Table]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Table])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.Table]] = (fun: get) => fun.apply()
				}
				class generateConsistencyToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGenerateConsistencyTokenRequest(body: Schema.GenerateConsistencyTokenRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateConsistencyTokenResponse])
				}
				object generateConsistencyToken {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): generateConsistencyToken = new generateConsistencyToken(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:generateConsistencyToken").addQueryStringParameters("name" -> name.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTable(body: Schema.Table) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class dropRowRange(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDropRowRangeRequest(body: Schema.DropRowRangeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object dropRowRange {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): dropRowRange = new dropRowRange(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:dropRowRange").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTablesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTablesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, view: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables").addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListTablesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCreateTableRequest(body: Schema.CreateTableRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Table])
				}
				object create {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables").addQueryStringParameters("parent" -> parent.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class modifyColumnFamilies(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withModifyColumnFamiliesRequest(body: Schema.ModifyColumnFamiliesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Table])
				}
				object modifyColumnFamilies {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): modifyColumnFamilies = new modifyColumnFamilies(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:modifyColumnFamilies").addQueryStringParameters("name" -> name.toString))
				}
				object authorizedViews {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, authorizedViewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews/${authorizedViewsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, authorizedViewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews/${authorizedViewsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						/** Optional. The current etag of the AuthorizedView. If an etag is provided and does not match the current etag of the AuthorizedView, deletion will be blocked and an ABORTED error will be returned. */
						def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, authorizedViewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews/${authorizedViewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AuthorizedView]) {
						/** Optional. The resource_view to be applied to the returned AuthorizedView's fields. Default to BASIC.<br>Possible values:<br>RESPONSE_VIEW_UNSPECIFIED: Uses the default view for each method as documented in the request.<br>NAME_ONLY: Only populates `name`.<br>BASIC: Only populates the AuthorizedView's basic metadata. This includes: name, deletion_protection, etag.<br>FULL: Populates every fields. */
						def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AuthorizedView])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, authorizedViewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews/${authorizedViewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.AuthorizedView]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. The list of fields to update. A mask specifying which fields in the AuthorizedView resource should be updated. This mask is relative to the AuthorizedView resource, not to the request message. A field will be overwritten if it is in the mask. If empty, all fields set in the request will be overwritten. A special value `&#42;` means to overwrite all fields (including fields not set in the request).<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Optional. If true, ignore the safety checks when updating the AuthorizedView. */
						def withIgnoreWarnings(ignoreWarnings: Boolean) = new patch(req.addQueryStringParameters("ignoreWarnings" -> ignoreWarnings.toString))
						def withAuthorizedView(body: Schema.AuthorizedView) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, authorizedViewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews/${authorizedViewsId}").addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAuthorizedViewsResponse]) {
						/** Optional. Maximum number of results per page. A page_size of zero lets the server choose the number of items to return. A page_size which is strictly positive will return at most that many items. A negative page_size will cause an error. Following the first request, subsequent paginated calls are not required to pass a page_size. If a page_size is set in subsequent calls, it must match the page_size given in the first request.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The value of `next_page_token` returned by a previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The resource_view to be applied to the returned AuthorizedViews' fields. Default to NAME_ONLY.<br>Possible values:<br>RESPONSE_VIEW_UNSPECIFIED: Uses the default view for each method as documented in the request.<br>NAME_ONLY: Only populates `name`.<br>BASIC: Only populates the AuthorizedView's basic metadata. This includes: name, deletion_protection, etag.<br>FULL: Populates every fields. */
						def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAuthorizedViewsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListAuthorizedViewsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withAuthorizedView(body: Schema.AuthorizedView) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, parent: String, authorizedViewId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews").addQueryStringParameters("parent" -> parent.toString, "authorizedViewId" -> authorizedViewId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, authorizedViewsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews/${authorizedViewsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
		}
		object locations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
