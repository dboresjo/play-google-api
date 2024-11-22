package com.boresjo.play.api.google.spanner

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://spanner.googleapis.com/"

	object scans {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListScansResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListScansResponse])
		}
		object list {
			def apply(parent: String, view: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/scans").addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListScansResponse]] = (fun: list) => fun.apply()
		}
	}
	object projects {
		object instanceConfigs {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, name: String, etag: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "validateOnly" -> validateOnly.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InstanceConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.InstanceConfig])
			}
			object get {
				def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.InstanceConfig]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUpdateInstanceConfigRequest(body: Schema.UpdateInstanceConfigRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInstanceConfigsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInstanceConfigsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListInstanceConfigsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCreateInstanceConfigRequest(body: Schema.CreateInstanceConfigRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs").addQueryStringParameters("parent" -> parent.toString))
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
				}
			}
			object ssdCaches {
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, ssdCachesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/ssdCaches/${ssdCachesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, ssdCachesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/ssdCaches/${ssdCachesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, ssdCachesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/ssdCaches/${ssdCachesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, ssdCachesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/ssdCaches/${ssdCachesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
						given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
					}
				}
			}
		}
		object instanceConfigOperations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInstanceConfigOperationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInstanceConfigOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigOperations").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListInstanceConfigOperationsResponse]] = (fun: list) => fun.apply()
			}
		}
		object instances {
			class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Instance])
			}
			object get {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String, fieldMask: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString, "fieldMask" -> fieldMask.toString))
				given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
			}
			class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withMoveInstanceRequest(body: Schema.MoveInstanceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object move {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}:move").addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCreateInstanceRequest(body: Schema.CreateInstanceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances").addQueryStringParameters("parent" -> parent.toString))
			}
			class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUpdateInstanceRequest(body: Schema.UpdateInstanceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInstancesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInstancesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, instanceDeadline: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "instanceDeadline" -> instanceDeadline.toString))
				given Conversion[list, Future[Schema.ListInstancesResponse]] = (fun: list) => fun.apply()
			}
			object backups {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class copy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCopyBackupRequest(body: Schema.CopyBackupRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object copy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): copy = new copy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups:copy").addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Backup]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Backup])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Backup]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBackup(body: Schema.Backup) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Backup])
				}
				object patch {
					def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBackupsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBackupsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBackupsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The Cloud KMS key that will be used to protect the backup. This field should be set only when encryption_type is `CUSTOMER_MANAGED_ENCRYPTION`. Values are of the form `projects//locations//keyRings//cryptoKeys/`. */
					def withEncryptionConfigKmsKeyName(encryptionConfigKmsKeyName: String) = new create(req.addQueryStringParameters("encryptionConfig.kmsKeyName" -> encryptionConfigKmsKeyName.toString))
					/** Optional. Specifies the KMS configuration for the one or more keys used to protect the backup. Values are of the form `projects//locations//keyRings//cryptoKeys/`. The keys referenced by `kms_key_names` must fully cover all regions of the backup's instance configuration. Some examples: &#42; For regional (single-region) instance configurations, specify a regional location KMS key. &#42; For multi-region instance configurations of type `GOOGLE_MANAGED`, either specify a multi-region location KMS key or multiple regional location KMS keys that cover all regions in the instance configuration. &#42; For an instance configuration of type `USER_MANAGED`, specify only regional location KMS keys to cover each region in the instance configuration. Multi-region location KMS keys aren't supported for `USER_MANAGED` type instance configurations. */
					def withEncryptionConfigKmsKeyNames(encryptionConfigKmsKeyNames: String) = new create(req.addQueryStringParameters("encryptionConfig.kmsKeyNames" -> encryptionConfigKmsKeyNames.toString))
					def withBackup(body: Schema.Backup) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, backupId: String, encryptionConfigEncryptionType: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups").addQueryStringParameters("parent" -> parent.toString, "backupId" -> backupId.toString, "encryptionConfig.encryptionType" -> encryptionConfigEncryptionType.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
						given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
					}
				}
			}
			object instancePartitions {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCreateInstancePartitionRequest(body: Schema.CreateInstancePartitionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					/** Optional. If not empty, the API only deletes the instance partition when the etag provided matches the current status of the requested instance partition. Otherwise, deletes the instance partition without checking the current status of the requested instance partition. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InstancePartition]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.InstancePartition])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.InstancePartition]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUpdateInstancePartitionRequest(body: Schema.UpdateInstancePartitionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInstancePartitionsResponse]) {
					/** Optional. Deadline used while retrieving metadata for instance partitions. Instance partitions whose metadata cannot be retrieved within this deadline will be added to unreachable in ListInstancePartitionsResponse.<br>Format: google-datetime */
					def withInstancePartitionDeadline(instancePartitionDeadline: String) = new list(req.addQueryStringParameters("instancePartitionDeadline" -> instancePartitionDeadline.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInstancePartitionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListInstancePartitionsResponse]] = (fun: list) => fun.apply()
				}
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
						given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
					}
				}
			}
			object databases {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class getDdl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetDatabaseDdlResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetDatabaseDdlResponse])
				}
				object getDdl {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, database: String)(using auth: AuthToken, ec: ExecutionContext): getDdl = new getDdl(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/ddl").addQueryStringParameters("database" -> database.toString))
					given Conversion[getDdl, Future[Schema.GetDatabaseDdlResponse]] = (fun: getDdl) => fun.apply()
				}
				class restore(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRestoreDatabaseRequest(body: Schema.RestoreDatabaseRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object restore {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): restore = new restore(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases:restore").addQueryStringParameters("parent" -> parent.toString))
				}
				class dropDatabase(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object dropDatabase {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, database: String)(using auth: AuthToken, ec: ExecutionContext): dropDatabase = new dropDatabase(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}").addQueryStringParameters("database" -> database.toString))
					given Conversion[dropDatabase, Future[Schema.Empty]] = (fun: dropDatabase) => fun.apply()
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class getScans(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Scan]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Scan])
				}
				object getScans {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, name: String, view: String, startTime: String, endTime: String)(using auth: AuthToken, ec: ExecutionContext): getScans = new getScans(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/scans").addQueryStringParameters("name" -> name.toString, "view" -> view.toString, "startTime" -> startTime.toString, "endTime" -> endTime.toString))
					given Conversion[getScans, Future[Schema.Scan]] = (fun: getScans) => fun.apply()
				}
				class changequorum(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withChangeQuorumRequest(body: Schema.ChangeQuorumRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object changequorum {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): changequorum = new changequorum(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}:changequorum").addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Database]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Database])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Database]] = (fun: get) => fun.apply()
				}
				class updateDdl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUpdateDatabaseDdlRequest(body: Schema.UpdateDatabaseDdlRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object updateDdl {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, database: String)(using auth: AuthToken, ec: ExecutionContext): updateDdl = new updateDdl(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/ddl").addQueryStringParameters("database" -> database.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDatabase(body: Schema.Database) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDatabasesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDatabasesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDatabasesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCreateDatabaseRequest(body: Schema.CreateDatabaseRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases").addQueryStringParameters("parent" -> parent.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
						given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
					}
				}
				object sessions {
					class read(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withReadRequest(body: Schema.ReadRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ResultSet])
					}
					object read {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): read = new read(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:read").addQueryStringParameters("session" -> session.toString))
					}
					class commit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCommitRequest(body: Schema.CommitRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CommitResponse])
					}
					object commit {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): commit = new commit(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:commit").addQueryStringParameters("session" -> session.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCreateSessionRequest(body: Schema.CreateSessionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Session])
					}
					object create {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, database: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions").addQueryStringParameters("database" -> database.toString))
					}
					class beginTransaction(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBeginTransactionRequest(body: Schema.BeginTransactionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Transaction])
					}
					object beginTransaction {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): beginTransaction = new beginTransaction(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:beginTransaction").addQueryStringParameters("session" -> session.toString))
					}
					class rollback(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRollbackRequest(body: Schema.RollbackRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object rollback {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:rollback").addQueryStringParameters("session" -> session.toString))
					}
					class partitionQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withPartitionQueryRequest(body: Schema.PartitionQueryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PartitionResponse])
					}
					object partitionQuery {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): partitionQuery = new partitionQuery(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:partitionQuery").addQueryStringParameters("session" -> session.toString))
					}
					class executeSql(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withExecuteSqlRequest(body: Schema.ExecuteSqlRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ResultSet])
					}
					object executeSql {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): executeSql = new executeSql(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:executeSql").addQueryStringParameters("session" -> session.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Session]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Session])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Session]] = (fun: get) => fun.apply()
					}
					class executeStreamingSql(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withExecuteSqlRequest(body: Schema.ExecuteSqlRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PartialResultSet])
					}
					object executeStreamingSql {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): executeStreamingSql = new executeStreamingSql(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:executeStreamingSql").addQueryStringParameters("session" -> session.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSessionsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSessionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, database: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions").addQueryStringParameters("database" -> database.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListSessionsResponse]] = (fun: list) => fun.apply()
					}
					class batchWrite(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBatchWriteRequest(body: Schema.BatchWriteRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchWriteResponse])
					}
					object batchWrite {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): batchWrite = new batchWrite(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:batchWrite").addQueryStringParameters("session" -> session.toString))
					}
					class partitionRead(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withPartitionReadRequest(body: Schema.PartitionReadRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PartitionResponse])
					}
					object partitionRead {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): partitionRead = new partitionRead(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:partitionRead").addQueryStringParameters("session" -> session.toString))
					}
					class executeBatchDml(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withExecuteBatchDmlRequest(body: Schema.ExecuteBatchDmlRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ExecuteBatchDmlResponse])
					}
					object executeBatchDml {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): executeBatchDml = new executeBatchDml(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:executeBatchDml").addQueryStringParameters("session" -> session.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class batchCreate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBatchCreateSessionsRequest(body: Schema.BatchCreateSessionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchCreateSessionsResponse])
					}
					object batchCreate {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, database: String)(using auth: AuthToken, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions:batchCreate").addQueryStringParameters("database" -> database.toString))
					}
					class streamingRead(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withReadRequest(body: Schema.ReadRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PartialResultSet])
					}
					object streamingRead {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): streamingRead = new streamingRead(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:streamingRead").addQueryStringParameters("session" -> session.toString))
					}
				}
				object databaseRoles {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, databaseRolesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/databaseRoles/${databaseRolesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDatabaseRolesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDatabaseRolesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/databaseRoles").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListDatabaseRolesResponse]] = (fun: list) => fun.apply()
					}
				}
				object backupSchedules {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BackupSchedule]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BackupSchedule])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.BackupSchedule]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBackupSchedule(body: Schema.BackupSchedule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.BackupSchedule])
					}
					object patch {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBackupSchedulesResponse]) {
						/** Optional. Number of backup schedules to be returned in the response. If 0 or less, defaults to the server's maximum allowed page size.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. If non-empty, `page_token` should contain a next_page_token from a previous ListBackupSchedulesResponse to the same `parent`. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBackupSchedulesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListBackupSchedulesResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBackupSchedule(body: Schema.BackupSchedule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BackupSchedule])
					}
					object create {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, parent: String, backupScheduleId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules").addQueryStringParameters("parent" -> parent.toString, "backupScheduleId" -> backupScheduleId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
			object databaseOperations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDatabaseOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDatabaseOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databaseOperations").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDatabaseOperationsResponse]] = (fun: list) => fun.apply()
				}
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, instancesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
				}
			}
			object backupOperations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBackupOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBackupOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backupOperations").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBackupOperationsResponse]] = (fun: list) => fun.apply()
				}
			}
			object instancePartitionOperations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInstancePartitionOperationsResponse]) {
					/** Optional. An expression that filters the list of returned operations. A filter expression consists of a field name, a comparison operator, and a value for filtering. The value must be a string, a number, or a boolean. The comparison operator must be one of: `<`, `>`, `<=`, `>=`, `!=`, `=`, or `:`. Colon `:` is the contains operator. Filter rules are not case sensitive. The following fields in the Operation are eligible for filtering: &#42; `name` - The name of the long-running operation &#42; `done` - False if the operation is in progress, else true. &#42; `metadata.@type` - the type of metadata. For example, the type string for CreateInstancePartitionMetadata is `type.googleapis.com/google.spanner.admin.instance.v1.CreateInstancePartitionMetadata`. &#42; `metadata.` - any field in metadata.value. `metadata.@type` must be specified first, if filtering on metadata fields. &#42; `error` - Error associated with the long-running operation. &#42; `response.@type` - the type of response. &#42; `response.` - any field in response.value. You can combine multiple expressions by enclosing each expression in parentheses. By default, expressions are combined with AND logic. However, you can specify AND, OR, and NOT logic explicitly. Here are a few examples: &#42; `done:true` - The operation is complete. &#42; `(metadata.@type=` \ `type.googleapis.com/google.spanner.admin.instance.v1.CreateInstancePartitionMetadata) AND` \ `(metadata.instance_partition.name:custom-instance-partition) AND` \ `(metadata.start_time < \"2021-03-28T14:50:00Z\") AND` \ `(error:&#42;)` - Return operations where: &#42; The operation's metadata type is CreateInstancePartitionMetadata. &#42; The instance partition name contains "custom-instance-partition". &#42; The operation started before 2021-03-28T14:50:00Z. &#42; The operation resulted in an error. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Number of operations to be returned in the response. If 0 or less, defaults to the server's maximum allowed page size.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. If non-empty, `page_token` should contain a next_page_token from a previous ListInstancePartitionOperationsResponse to the same `parent` and with the same `filter`. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Deadline used while retrieving metadata for instance partition operations. Instance partitions whose operation metadata cannot be retrieved within this deadline will be added to unreachable in ListInstancePartitionOperationsResponse.<br>Format: google-datetime */
					def withInstancePartitionDeadline(instancePartitionDeadline: String) = new list(req.addQueryStringParameters("instancePartitionDeadline" -> instancePartitionDeadline.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInstancePartitionOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitionOperations").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListInstancePartitionOperationsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
