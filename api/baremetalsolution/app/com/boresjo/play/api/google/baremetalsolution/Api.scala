package com.boresjo.play.api.google.baremetalsolution

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

	private val BASE_URL = "https://baremetalsolution.googleapis.com/"

	object projects {
		object locations {
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object provisioningQuotas {
				/** List the budget details to provision resources on a given project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListProvisioningQuotasResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListProvisioningQuotasResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/provisioningQuotas").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListProvisioningQuotasResponse]] = (fun: list) => fun.apply()
				}
			}
			object provisioningConfigs {
				/** Submit a provisiong configuration for a given project. */
				class submit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSubmitProvisioningConfigRequest(body: Schema.SubmitProvisioningConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SubmitProvisioningConfigResponse])
				}
				object submit {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): submit = new submit(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/provisioningConfigs:submit").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Get ProvisioningConfig by name. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProvisioningConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProvisioningConfig])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, provisioningConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/provisioningConfigs/${provisioningConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ProvisioningConfig]] = (fun: get) => fun.apply()
				}
				/** Create new ProvisioningConfig. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Email provided to send a confirmation with provisioning config to. */
					def withEmail(email: String) = new create(req.addQueryStringParameters("email" -> email.toString))
					/** Perform the request */
					def withProvisioningConfig(body: Schema.ProvisioningConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ProvisioningConfig])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/provisioningConfigs").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Update existing ProvisioningConfig. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Email provided to send a confirmation with provisioning config to. */
					def withEmail(email: String) = new patch(req.addQueryStringParameters("email" -> email.toString))
					/** Perform the request */
					def withProvisioningConfig(body: Schema.ProvisioningConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ProvisioningConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, provisioningConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/provisioningConfigs/${provisioningConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
			}
			object volumes {
				/** RenameVolume sets a new name for a volume. Use with caution, previous names become immediately invalidated. */
				class rename(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRenameVolumeRequest(body: Schema.RenameVolumeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Volume])
				}
				object rename {
					def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}:rename").addQueryStringParameters("name" -> name.toString))
				}
				/** Emergency Volume resize. */
				class resize(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withResizeVolumeRequest(body: Schema.ResizeVolumeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object resize {
					def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, volume: String)(using signer: RequestSigner, ec: ExecutionContext): resize = new resize(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}:resize").addQueryStringParameters("volume" -> volume.toString))
				}
				/** Skips volume's cooloff and deletes it now. Volume must be in cooloff state. */
				class evict(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEvictVolumeRequest(body: Schema.EvictVolumeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object evict {
					def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): evict = new evict(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}:evict").addQueryStringParameters("name" -> name.toString))
				}
				/** Get details of a single storage volume. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Volume]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Volume])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Volume]] = (fun: get) => fun.apply()
				}
				/** Update details of a single storage volume. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withVolume(body: Schema.Volume) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** List storage volumes in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVolumesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVolumesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListVolumesResponse]] = (fun: list) => fun.apply()
				}
				object luns {
					/** Get details of a single storage logical unit number(LUN). */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Lun]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Lun])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, lunsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/luns/${lunsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Lun]] = (fun: get) => fun.apply()
					}
					/** List storage volume luns for given storage volume. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLunsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLunsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/luns").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListLunsResponse]] = (fun: list) => fun.apply()
					}
					/** Skips lun's cooloff and deletes it now. Lun must be in cooloff state. */
					class evict(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withEvictLunRequest(body: Schema.EvictLunRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object evict {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, lunsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): evict = new evict(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/luns/${lunsId}:evict").addQueryStringParameters("name" -> name.toString))
					}
				}
				object snapshots {
					/** Deletes a volume snapshot. Returns INVALID_ARGUMENT if called for a non-boot volume. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, snapshotsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/snapshots/${snapshotsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Returns the specified snapshot resource. Returns INVALID_ARGUMENT if called for a non-boot volume. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VolumeSnapshot]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VolumeSnapshot])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, snapshotsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/snapshots/${snapshotsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.VolumeSnapshot]] = (fun: get) => fun.apply()
					}
					/** Retrieves the list of snapshots for the specified volume. Returns a response with an empty list of snapshots if called for a non-boot volume. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVolumeSnapshotsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVolumeSnapshotsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/snapshots").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListVolumeSnapshotsResponse]] = (fun: list) => fun.apply()
					}
					/** Uses the specified snapshot to restore its parent volume. Returns INVALID_ARGUMENT if called for a non-boot volume. */
					class restoreVolumeSnapshot(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withRestoreVolumeSnapshotRequest(body: Schema.RestoreVolumeSnapshotRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object restoreVolumeSnapshot {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, snapshotsId :PlayApi, volumeSnapshot: String)(using signer: RequestSigner, ec: ExecutionContext): restoreVolumeSnapshot = new restoreVolumeSnapshot(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/snapshots/${snapshotsId}:restoreVolumeSnapshot").addQueryStringParameters("volumeSnapshot" -> volumeSnapshot.toString))
					}
					/** Takes a snapshot of a boot volume. Returns INVALID_ARGUMENT if called for a non-boot volume. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withVolumeSnapshot(body: Schema.VolumeSnapshot) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.VolumeSnapshot])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, volumesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/volumes/${volumesId}/snapshots").addQueryStringParameters("parent" -> parent.toString))
					}
				}
			}
			object operations {
				/** Get details about an operation. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
			object networks {
				/** List all Networks (and used IPs for each Network) in the vendor account associated with the specified project. */
				class listNetworkUsage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNetworkUsageResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNetworkUsageResponse])
				}
				object listNetworkUsage {
					def apply(projectsId :PlayApi, locationsId :PlayApi, location: String)(using signer: RequestSigner, ec: ExecutionContext): listNetworkUsage = new listNetworkUsage(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/networks:listNetworkUsage").addQueryStringParameters("location" -> location.toString))
					given Conversion[listNetworkUsage, Future[Schema.ListNetworkUsageResponse]] = (fun: listNetworkUsage) => fun.apply()
				}
				/** RenameNetwork sets a new name for a network. Use with caution, previous names become immediately invalidated. */
				class rename(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRenameNetworkRequest(body: Schema.RenameNetworkRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Network])
				}
				object rename {
					def apply(projectsId :PlayApi, locationsId :PlayApi, networksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/networks/${networksId}:rename").addQueryStringParameters("name" -> name.toString))
				}
				/** Get details of a single network. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Network]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Network])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, networksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/networks/${networksId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Network]] = (fun: get) => fun.apply()
				}
				/** Update details of a single network. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withNetwork(body: Schema.Network) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, networksId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/networks/${networksId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** List network in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNetworksResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNetworksResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/networks").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListNetworksResponse]] = (fun: list) => fun.apply()
				}
			}
			object sshKeys {
				/** Lists the public SSH keys registered for the specified project. These SSH keys are used only for the interactive serial console feature. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSSHKeysResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSSHKeysResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/sshKeys").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSSHKeysResponse]] = (fun: list) => fun.apply()
				}
				/** Register a public SSH key in the specified project for use with the interactive serial console feature. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSSHKey(body: Schema.SSHKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SSHKey])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, sshKeyId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/sshKeys").addQueryStringParameters("parent" -> parent.toString, "sshKeyId" -> sshKeyId.toString))
				}
				/** Deletes a public SSH key registered in the specified project. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sshKeysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/sshKeys/${sshKeysId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
			object instances {
				/** Load auth info for a server. */
				class loadAuthInfo(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LoadInstanceAuthInfoResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LoadInstanceAuthInfoResponse])
				}
				object loadAuthInfo {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): loadAuthInfo = new loadAuthInfo(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:loadAuthInfo").addQueryStringParameters("name" -> name.toString))
					given Conversion[loadAuthInfo, Future[Schema.LoadInstanceAuthInfoResponse]] = (fun: loadAuthInfo) => fun.apply()
				}
				/** Stop a running server. */
				class stop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withStopInstanceRequest(body: Schema.StopInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object stop {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:stop").addQueryStringParameters("name" -> name.toString))
				}
				/** RenameInstance sets a new name for an instance. Use with caution, previous names become immediately invalidated. */
				class rename(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRenameInstanceRequest(body: Schema.RenameInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Instance])
				}
				object rename {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:rename").addQueryStringParameters("name" -> name.toString))
				}
				/** Perform an ungraceful, hard reset on a server. Equivalent to shutting the power off and then turning it back on. */
				class reset(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withResetInstanceRequest(body: Schema.ResetInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object reset {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): reset = new reset(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:reset").addQueryStringParameters("name" -> name.toString))
				}
				/** Enable the interactive serial console feature on an instance. */
				class enableInteractiveSerialConsole(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEnableInteractiveSerialConsoleRequest(body: Schema.EnableInteractiveSerialConsoleRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object enableInteractiveSerialConsole {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): enableInteractiveSerialConsole = new enableInteractiveSerialConsole(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:enableInteractiveSerialConsole").addQueryStringParameters("name" -> name.toString))
				}
				/** Perform reimage operation on a single server. */
				class reimage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withReimageInstanceRequest(body: Schema.ReimageInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object reimage {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): reimage = new reimage(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:reimage").addQueryStringParameters("name" -> name.toString))
				}
				/** Perform disable hyperthreading operation on a single server. */
				class disableHyperthreading(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDisableHyperthreadingRequest(body: Schema.DisableHyperthreadingRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object disableHyperthreading {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): disableHyperthreading = new disableHyperthreading(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:disableHyperthreading").addQueryStringParameters("name" -> name.toString))
				}
				/** Perform enable hyperthreading operation on a single server. */
				class enableHyperthreading(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEnableHyperthreadingRequest(body: Schema.EnableHyperthreadingRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object enableHyperthreading {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): enableHyperthreading = new enableHyperthreading(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:enableHyperthreading").addQueryStringParameters("name" -> name.toString))
				}
				/** Get details about a single server. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Instance])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
				}
				/** Detach LUN from Instance. */
				class detachLun(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDetachLunRequest(body: Schema.DetachLunRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object detachLun {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, instance: String)(using signer: RequestSigner, ec: ExecutionContext): detachLun = new detachLun(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:detachLun").addQueryStringParameters("instance" -> instance.toString))
				}
				/** Update details of a single server. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withInstance(body: Schema.Instance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Starts a server that was shutdown. */
				class start(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withStartInstanceRequest(body: Schema.StartInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object start {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): start = new start(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:start").addQueryStringParameters("name" -> name.toString))
				}
				/** List servers in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInstancesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInstancesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListInstancesResponse]] = (fun: list) => fun.apply()
				}
				/** Disable the interactive serial console feature on an instance. */
				class disableInteractiveSerialConsole(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDisableInteractiveSerialConsoleRequest(body: Schema.DisableInteractiveSerialConsoleRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object disableInteractiveSerialConsole {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): disableInteractiveSerialConsole = new disableInteractiveSerialConsole(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:disableInteractiveSerialConsole").addQueryStringParameters("name" -> name.toString))
				}
			}
			object osImages {
				/** Retrieves the list of OS images which are currently approved. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOSImagesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOSImagesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/osImages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOSImagesResponse]] = (fun: list) => fun.apply()
				}
				/** Get details of a single OS image. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.OSImage]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.OSImage])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, osImagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/osImages/${osImagesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.OSImage]] = (fun: get) => fun.apply()
				}
			}
			object nfsShares {
				/** Create an NFS share. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withNfsShare(body: Schema.NfsShare) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/nfsShares").addQueryStringParameters("parent" -> parent.toString))
				}
				/** RenameNfsShare sets a new name for an nfsshare. Use with caution, previous names become immediately invalidated. */
				class rename(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRenameNfsShareRequest(body: Schema.RenameNfsShareRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.NfsShare])
				}
				object rename {
					def apply(projectsId :PlayApi, locationsId :PlayApi, nfsSharesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/nfsShares/${nfsSharesId}:rename").addQueryStringParameters("name" -> name.toString))
				}
				/** Delete an NFS share. The underlying volume is automatically deleted. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, nfsSharesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/nfsShares/${nfsSharesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Get details of a single NFS share. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.NfsShare]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.NfsShare])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, nfsSharesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/nfsShares/${nfsSharesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.NfsShare]] = (fun: get) => fun.apply()
				}
				/** Update details of a single NFS share. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withNfsShare(body: Schema.NfsShare) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, nfsSharesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/nfsShares/${nfsSharesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** List NFS shares. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNfsSharesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNfsSharesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/nfsShares").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListNfsSharesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
