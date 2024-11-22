package com.boresjo.play.api.google.baremetalsolution

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://baremetalsolution.googleapis.com/"

	object projects {
		object locations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object provisioningQuotas {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListProvisioningQuotasResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListProvisioningQuotasResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/provisioningQuotas").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListProvisioningQuotasResponse]] = (fun: list) => fun.apply()
				}
			}
			object provisioningConfigs {
				class submit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSubmitProvisioningConfigRequest(body: Schema.SubmitProvisioningConfigRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SubmitProvisioningConfigResponse])
				}
				object submit {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): submit = new submit(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/provisioningConfigs:submit").addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProvisioningConfig]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ProvisioningConfig])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, provisioningConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/provisioningConfigs/${provisioningConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ProvisioningConfig]] = (fun: get) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Email provided to send a confirmation with provisioning config to. */
					def withEmail(email: String) = new create(req.addQueryStringParameters("email" -> email.toString))
					def withProvisioningConfig(body: Schema.ProvisioningConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ProvisioningConfig])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/provisioningConfigs").addQueryStringParameters("parent" -> parent.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Email provided to send a confirmation with provisioning config to. */
					def withEmail(email: String) = new patch(req.addQueryStringParameters("email" -> email.toString))
					def withProvisioningConfig(body: Schema.ProvisioningConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ProvisioningConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, provisioningConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/provisioningConfigs/${provisioningConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
			}
			object volumes {
				class rename(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRenameVolumeRequest(body: Schema.RenameVolumeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Volume])
				}
				object rename {
					def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}:rename").addQueryStringParameters("name" -> name.toString))
				}
				class resize(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withResizeVolumeRequest(body: Schema.ResizeVolumeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object resize {
					def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, volume: String)(using auth: AuthToken, ec: ExecutionContext): resize = new resize(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}:resize").addQueryStringParameters("volume" -> volume.toString))
				}
				class evict(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEvictVolumeRequest(body: Schema.EvictVolumeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object evict {
					def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): evict = new evict(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}:evict").addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Volume]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Volume])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Volume]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withVolume(body: Schema.Volume) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListVolumesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListVolumesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListVolumesResponse]] = (fun: list) => fun.apply()
				}
				object luns {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Lun]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Lun])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, lunsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/luns/${lunsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Lun]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLunsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLunsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/luns").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListLunsResponse]] = (fun: list) => fun.apply()
					}
					class evict(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withEvictLunRequest(body: Schema.EvictLunRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object evict {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, lunsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): evict = new evict(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/luns/${lunsId}:evict").addQueryStringParameters("name" -> name.toString))
					}
				}
				object snapshots {
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, snapshotsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/snapshots/${snapshotsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VolumeSnapshot]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.VolumeSnapshot])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, snapshotsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/snapshots/${snapshotsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.VolumeSnapshot]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListVolumeSnapshotsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListVolumeSnapshotsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/snapshots").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListVolumeSnapshotsResponse]] = (fun: list) => fun.apply()
					}
					class restoreVolumeSnapshot(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRestoreVolumeSnapshotRequest(body: Schema.RestoreVolumeSnapshotRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object restoreVolumeSnapshot {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, snapshotsId :PlayApi, volumeSnapshot: String)(using auth: AuthToken, ec: ExecutionContext): restoreVolumeSnapshot = new restoreVolumeSnapshot(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/snapshots/${snapshotsId}:restoreVolumeSnapshot").addQueryStringParameters("volumeSnapshot" -> volumeSnapshot.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withVolumeSnapshot(body: Schema.VolumeSnapshot) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.VolumeSnapshot])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/snapshots").addQueryStringParameters("parent" -> parent.toString))
					}
				}
			}
			object operations {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
			object networks {
				class listNetworkUsage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNetworkUsageResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListNetworkUsageResponse])
				}
				object listNetworkUsage {
					def apply(projectsId :PlayApi, locationsId :PlayApi, location: String)(using auth: AuthToken, ec: ExecutionContext): listNetworkUsage = new listNetworkUsage(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/networks:listNetworkUsage").addQueryStringParameters("location" -> location.toString))
					given Conversion[listNetworkUsage, Future[Schema.ListNetworkUsageResponse]] = (fun: listNetworkUsage) => fun.apply()
				}
				class rename(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRenameNetworkRequest(body: Schema.RenameNetworkRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Network])
				}
				object rename {
					def apply(projectsId :PlayApi, locationsId :PlayApi, networksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/networks/${networksId}:rename").addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Network]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Network])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, networksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/networks/${networksId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Network]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withNetwork(body: Schema.Network) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, networksId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/networks/${networksId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNetworksResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListNetworksResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/networks").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListNetworksResponse]] = (fun: list) => fun.apply()
				}
			}
			object sshKeys {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSSHKeysResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSSHKeysResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/sshKeys").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSSHKeysResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSSHKey(body: Schema.SSHKey) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SSHKey])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, sshKeyId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/sshKeys").addQueryStringParameters("parent" -> parent.toString, "sshKeyId" -> sshKeyId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sshKeysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/sshKeys/${sshKeysId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
			object instances {
				class loadAuthInfo(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LoadInstanceAuthInfoResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LoadInstanceAuthInfoResponse])
				}
				object loadAuthInfo {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): loadAuthInfo = new loadAuthInfo(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:loadAuthInfo").addQueryStringParameters("name" -> name.toString))
					given Conversion[loadAuthInfo, Future[Schema.LoadInstanceAuthInfoResponse]] = (fun: loadAuthInfo) => fun.apply()
				}
				class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStopInstanceRequest(body: Schema.StopInstanceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object stop {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:stop").addQueryStringParameters("name" -> name.toString))
				}
				class rename(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRenameInstanceRequest(body: Schema.RenameInstanceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Instance])
				}
				object rename {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:rename").addQueryStringParameters("name" -> name.toString))
				}
				class reset(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withResetInstanceRequest(body: Schema.ResetInstanceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object reset {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): reset = new reset(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:reset").addQueryStringParameters("name" -> name.toString))
				}
				class enableInteractiveSerialConsole(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEnableInteractiveSerialConsoleRequest(body: Schema.EnableInteractiveSerialConsoleRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object enableInteractiveSerialConsole {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): enableInteractiveSerialConsole = new enableInteractiveSerialConsole(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:enableInteractiveSerialConsole").addQueryStringParameters("name" -> name.toString))
				}
				class reimage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withReimageInstanceRequest(body: Schema.ReimageInstanceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object reimage {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): reimage = new reimage(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:reimage").addQueryStringParameters("name" -> name.toString))
				}
				class disableHyperthreading(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDisableHyperthreadingRequest(body: Schema.DisableHyperthreadingRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object disableHyperthreading {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): disableHyperthreading = new disableHyperthreading(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:disableHyperthreading").addQueryStringParameters("name" -> name.toString))
				}
				class enableHyperthreading(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEnableHyperthreadingRequest(body: Schema.EnableHyperthreadingRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object enableHyperthreading {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): enableHyperthreading = new enableHyperthreading(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:enableHyperthreading").addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Instance])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
				}
				class detachLun(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDetachLunRequest(body: Schema.DetachLunRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object detachLun {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, instance: String)(using auth: AuthToken, ec: ExecutionContext): detachLun = new detachLun(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:detachLun").addQueryStringParameters("instance" -> instance.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withInstance(body: Schema.Instance) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class start(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStartInstanceRequest(body: Schema.StartInstanceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object start {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): start = new start(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:start").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInstancesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInstancesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListInstancesResponse]] = (fun: list) => fun.apply()
				}
				class disableInteractiveSerialConsole(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDisableInteractiveSerialConsoleRequest(body: Schema.DisableInteractiveSerialConsoleRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object disableInteractiveSerialConsole {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): disableInteractiveSerialConsole = new disableInteractiveSerialConsole(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:disableInteractiveSerialConsole").addQueryStringParameters("name" -> name.toString))
				}
			}
			object osImages {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOSImagesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOSImagesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/osImages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOSImagesResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.OSImage]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.OSImage])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, osImagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/osImages/${osImagesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.OSImage]] = (fun: get) => fun.apply()
				}
			}
			object nfsShares {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withNfsShare(body: Schema.NfsShare) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/nfsShares").addQueryStringParameters("parent" -> parent.toString))
				}
				class rename(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRenameNfsShareRequest(body: Schema.RenameNfsShareRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.NfsShare])
				}
				object rename {
					def apply(projectsId :PlayApi, locationsId :PlayApi, nfsSharesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/nfsShares/${nfsSharesId}:rename").addQueryStringParameters("name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, nfsSharesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/nfsShares/${nfsSharesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.NfsShare]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.NfsShare])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, nfsSharesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/nfsShares/${nfsSharesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.NfsShare]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withNfsShare(body: Schema.NfsShare) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, nfsSharesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/nfsShares/${nfsSharesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNfsSharesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListNfsSharesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/nfsShares").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListNfsSharesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
