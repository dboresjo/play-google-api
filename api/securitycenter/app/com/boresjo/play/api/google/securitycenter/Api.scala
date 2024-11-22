package com.boresjo.play.api.google.securitycenter

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

	private val BASE_URL = "https://securitycenter.googleapis.com/"

	object folders {
		object assets {
			/** Filters an organization's assets and groups them by their specified properties. */
			class group(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGroupAssetsRequest(body: Schema.GroupAssetsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GroupAssetsResponse])
			}
			object group {
				def apply(foldersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): group = new group(ws.url(BASE_URL + s"v1/folders/${foldersId}/assets:group").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Lists an organization's assets. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAssetsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAssetsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, filter: String, orderBy: String, readTime: String, compareDuration: String, fieldMask: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/assets").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "readTime" -> readTime.toString, "compareDuration" -> compareDuration.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListAssetsResponse]] = (fun: list) => fun.apply()
			}
			/** Updates security marks. */
			class updateSecurityMarks(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSecurityMarks(body: Schema.SecurityMarks) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SecurityMarks])
			}
			object updateSecurityMarks {
				def apply(foldersId :PlayApi, assetsId :PlayApi, name: String, updateMask: String, startTime: String)(using signer: RequestSigner, ec: ExecutionContext): updateSecurityMarks = new updateSecurityMarks(ws.url(BASE_URL + s"v1/folders/${foldersId}/assets/${assetsId}/securityMarks").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "startTime" -> startTime.toString))
			}
		}
		object findings {
			/** Kicks off an LRO to bulk mute findings for a parent based on a filter. The parent can be either an organization, folder or project. The findings matched by the filter will be muted after the LRO is done. */
			class bulkMute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withBulkMuteFindingsRequest(body: Schema.BulkMuteFindingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object bulkMute {
				def apply(foldersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): bulkMute = new bulkMute(ws.url(BASE_URL + s"v1/folders/${foldersId}/findings:bulkMute").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object eventThreatDetectionSettings {
			/** Validates the given Event Threat Detection custom module. */
			class validateCustomModule(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withValidateEventThreatDetectionCustomModuleRequest(body: Schema.ValidateEventThreatDetectionCustomModuleRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ValidateEventThreatDetectionCustomModuleResponse])
			}
			object validateCustomModule {
				def apply(foldersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): validateCustomModule = new validateCustomModule(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings:validateCustomModule").addQueryStringParameters("parent" -> parent.toString))
			}
			object customModules {
				/** Lists all resident Event Threat Detection custom modules under the given Resource Manager parent and its descendants. */
				class listDescendant(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDescendantEventThreatDetectionCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDescendantEventThreatDetectionCustomModulesResponse])
				}
				object listDescendant {
					def apply(foldersId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): listDescendant = new listDescendant(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/customModules:listDescendant").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[listDescendant, Future[Schema.ListDescendantEventThreatDetectionCustomModulesResponse]] = (fun: listDescendant) => fun.apply()
				}
				/** Creates a resident Event Threat Detection custom module at the scope of the given Resource Manager parent, and also creates inherited custom modules for all descendants of the given parent. These modules are enabled by default. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEventThreatDetectionCustomModule(body: Schema.EventThreatDetectionCustomModule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object create {
					def apply(foldersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/customModules").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the specified Event Threat Detection custom module and all of its descendants in the Resource Manager hierarchy. This method is only supported for resident custom modules. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(foldersId :PlayApi, customModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets an Event Threat Detection custom module. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EventThreatDetectionCustomModule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object get {
					def apply(foldersId :PlayApi, customModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EventThreatDetectionCustomModule]] = (fun: get) => fun.apply()
				}
				/** Updates the Event Threat Detection custom module with the given name based on the given update mask. Updating the enablement state is supported for both resident and inherited modules (though resident modules cannot have an enablement state of "inherited"). Updating the display name or configuration of a module is supported for resident modules only. The type of a module cannot be changed. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEventThreatDetectionCustomModule(body: Schema.EventThreatDetectionCustomModule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object patch {
					def apply(foldersId :PlayApi, customModulesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists all Event Threat Detection custom modules for the given Resource Manager parent. This includes resident modules defined at the scope of the parent along with modules inherited from ancestors. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEventThreatDetectionCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEventThreatDetectionCustomModulesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/customModules").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListEventThreatDetectionCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object effectiveCustomModules {
				/** Gets an effective Event Threat Detection custom module at the given level. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EffectiveEventThreatDetectionCustomModule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EffectiveEventThreatDetectionCustomModule])
				}
				object get {
					def apply(foldersId :PlayApi, effectiveCustomModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/effectiveCustomModules/${effectiveCustomModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EffectiveEventThreatDetectionCustomModule]] = (fun: get) => fun.apply()
				}
				/** Lists all effective Event Threat Detection custom modules for the given parent. This includes resident modules defined at the scope of the parent along with modules inherited from its ancestors. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/effectiveCustomModules").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object sources {
			/** Lists all sources belonging to an organization. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSourcesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSourcesResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListSourcesResponse]] = (fun: list) => fun.apply()
			}
			object findings {
				/** Updates security marks. */
				class updateSecurityMarks(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSecurityMarks(body: Schema.SecurityMarks) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SecurityMarks])
				}
				object updateSecurityMarks {
					def apply(foldersId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String, updateMask: String, startTime: String)(using signer: RequestSigner, ec: ExecutionContext): updateSecurityMarks = new updateSecurityMarks(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings/${findingsId}/securityMarks").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "startTime" -> startTime.toString))
				}
				/** Creates or updates a finding. The corresponding source must exist for a finding creation to succeed. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withFinding(body: Schema.Finding) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Finding])
				}
				object patch {
					def apply(foldersId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings/${findingsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Filters an organization or source's findings and groups them by their specified properties. To group across all sources provide a `-` as the source id. Example: /v1/organizations/{organization_id}/sources/-/findings, /v1/folders/{folder_id}/sources/-/findings, /v1/projects/{project_id}/sources/-/findings */
				class group(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGroupFindingsRequest(body: Schema.GroupFindingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GroupFindingsResponse])
				}
				object group {
					def apply(foldersId :PlayApi, sourcesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): group = new group(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings:group").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lists an organization or source's findings. To list across all sources provide a `-` as the source id. Example: /v1/organizations/{organization_id}/sources/-/findings */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFindingsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFindingsResponse])
				}
				object list {
					def apply(foldersId :PlayApi, sourcesId :PlayApi, parent: String, filter: String, orderBy: String, readTime: String, compareDuration: String, fieldMask: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "readTime" -> readTime.toString, "compareDuration" -> compareDuration.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListFindingsResponse]] = (fun: list) => fun.apply()
				}
				/** Updates the mute state of a finding. */
				class setMute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetMuteRequest(body: Schema.SetMuteRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Finding])
				}
				object setMute {
					def apply(foldersId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setMute = new setMute(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings/${findingsId}:setMute").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates the state of a finding. */
				class setState(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetFindingStateRequest(body: Schema.SetFindingStateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Finding])
				}
				object setState {
					def apply(foldersId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setState = new setState(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings/${findingsId}:setState").addQueryStringParameters("name" -> name.toString))
				}
				object externalSystems {
					/** Updates external system. This is for a given finding. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudSecuritycenterV1ExternalSystem(body: Schema.GoogleCloudSecuritycenterV1ExternalSystem) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1ExternalSystem])
					}
					object patch {
						def apply(foldersId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, externalSystemsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings/${findingsId}/externalSystems/${externalSystemsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
				}
			}
		}
		object notificationConfigs {
			/** Creates a notification config. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withNotificationConfig(body: Schema.NotificationConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.NotificationConfig])
			}
			object create {
				def apply(foldersId :PlayApi, parent: String, configId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/folders/${foldersId}/notificationConfigs").addQueryStringParameters("parent" -> parent.toString, "configId" -> configId.toString))
			}
			/** Deletes a notification config. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(foldersId :PlayApi, notificationConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/folders/${foldersId}/notificationConfigs/${notificationConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a notification config. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.NotificationConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.NotificationConfig])
			}
			object get {
				def apply(foldersId :PlayApi, notificationConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/notificationConfigs/${notificationConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.NotificationConfig]] = (fun: get) => fun.apply()
			}
			/**  Updates a notification config. The following update fields are allowed: description, pubsub_topic, streaming_config.filter */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withNotificationConfig(body: Schema.NotificationConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.NotificationConfig])
			}
			object patch {
				def apply(foldersId :PlayApi, notificationConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/folders/${foldersId}/notificationConfigs/${notificationConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists notification configs. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNotificationConfigsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNotificationConfigsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/notificationConfigs").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListNotificationConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			object muteConfigs {
				/** Deletes an existing mute config. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(foldersId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a mute config. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
				}
				object get {
					def apply(foldersId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]] = (fun: get) => fun.apply()
				}
				/** Updates a mute config. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
				}
				object patch {
					def apply(foldersId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
			}
		}
		object bigQueryExports {
			/** Creates a BigQuery export. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudSecuritycenterV1BigQueryExport(body: Schema.GoogleCloudSecuritycenterV1BigQueryExport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object create {
				def apply(foldersId :PlayApi, parent: String, bigQueryExportId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/folders/${foldersId}/bigQueryExports").addQueryStringParameters("parent" -> parent.toString, "bigQueryExportId" -> bigQueryExportId.toString))
			}
			/** Deletes an existing BigQuery export. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(foldersId :PlayApi, bigQueryExportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/folders/${foldersId}/bigQueryExports/${bigQueryExportsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a BigQuery export. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1BigQueryExport]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object get {
				def apply(foldersId :PlayApi, bigQueryExportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/bigQueryExports/${bigQueryExportsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1BigQueryExport]] = (fun: get) => fun.apply()
			}
			/** Updates a BigQuery export. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudSecuritycenterV1BigQueryExport(body: Schema.GoogleCloudSecuritycenterV1BigQueryExport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object patch {
				def apply(foldersId :PlayApi, bigQueryExportsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/folders/${foldersId}/bigQueryExports/${bigQueryExportsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists BigQuery exports. Note that when requesting BigQuery exports at a given level all exports under that level are also returned e.g. if requesting BigQuery exports under a folder, then all BigQuery exports immediately under the folder plus the ones created under the projects within the folder are returned. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBigQueryExportsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBigQueryExportsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/bigQueryExports").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListBigQueryExportsResponse]] = (fun: list) => fun.apply()
			}
		}
		object muteConfigs {
			/** Creates a mute config. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object create {
				def apply(foldersId :PlayApi, parent: String, muteConfigId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/folders/${foldersId}/muteConfigs").addQueryStringParameters("parent" -> parent.toString, "muteConfigId" -> muteConfigId.toString))
			}
			/** Deletes an existing mute config. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(foldersId :PlayApi, muteConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/folders/${foldersId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a mute config. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object get {
				def apply(foldersId :PlayApi, muteConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]] = (fun: get) => fun.apply()
			}
			/** Updates a mute config. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object patch {
				def apply(foldersId :PlayApi, muteConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/folders/${foldersId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists mute configs. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMuteConfigsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMuteConfigsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/muteConfigs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListMuteConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
		object securityHealthAnalyticsSettings {
			object customModules {
				/** Simulates a given SecurityHealthAnalyticsCustomModule and Resource. */
				class simulate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSimulateSecurityHealthAnalyticsCustomModuleRequest(body: Schema.SimulateSecurityHealthAnalyticsCustomModuleRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SimulateSecurityHealthAnalyticsCustomModuleResponse])
				}
				object simulate {
					def apply(foldersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): simulate = new simulate(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules:simulate").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Returns a list of all resident SecurityHealthAnalyticsCustomModules under the given CRM parent and all of the parent’s CRM descendants. */
				class listDescendant(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse])
				}
				object listDescendant {
					def apply(foldersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listDescendant = new listDescendant(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules:listDescendant").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listDescendant, Future[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse]] = (fun: listDescendant) => fun.apply()
				}
				/** Creates a resident SecurityHealthAnalyticsCustomModule at the scope of the given CRM parent, and also creates inherited SecurityHealthAnalyticsCustomModules for all CRM descendants of the given parent. These modules are enabled by default. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule(body: Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object create {
					def apply(foldersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the specified SecurityHealthAnalyticsCustomModule and all of its descendants in the CRM hierarchy. This method is only supported for resident custom modules. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(foldersId :PlayApi, customModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Retrieves a SecurityHealthAnalyticsCustomModule. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object get {
					def apply(foldersId :PlayApi, customModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]] = (fun: get) => fun.apply()
				}
				/** Updates the SecurityHealthAnalyticsCustomModule under the given name based on the given update mask. Updating the enablement state is supported on both resident and inherited modules (though resident modules cannot have an enablement state of "inherited"). Updating the display name and custom config of a module is supported on resident modules only. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule(body: Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object patch {
					def apply(foldersId :PlayApi, customModulesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Returns a list of all SecurityHealthAnalyticsCustomModules for the given parent. This includes resident modules defined at the scope of the parent, and inherited modules, inherited from CRM ancestors. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSecurityHealthAnalyticsCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSecurityHealthAnalyticsCustomModulesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSecurityHealthAnalyticsCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object effectiveCustomModules {
				/** Retrieves an EffectiveSecurityHealthAnalyticsCustomModule. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule])
				}
				object get {
					def apply(foldersId :PlayApi, effectiveCustomModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/effectiveCustomModules/${effectiveCustomModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule]] = (fun: get) => fun.apply()
				}
				/** Returns a list of all EffectiveSecurityHealthAnalyticsCustomModules for the given parent. This includes resident modules defined at the scope of the parent, and inherited modules, inherited from CRM ancestors. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/effectiveCustomModules").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object projects {
		object assets {
			/** Filters an organization's assets and groups them by their specified properties. */
			class group(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGroupAssetsRequest(body: Schema.GroupAssetsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GroupAssetsResponse])
			}
			object group {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): group = new group(ws.url(BASE_URL + s"v1/projects/${projectsId}/assets:group").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Lists an organization's assets. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAssetsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAssetsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, filter: String, orderBy: String, readTime: String, compareDuration: String, fieldMask: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/assets").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "readTime" -> readTime.toString, "compareDuration" -> compareDuration.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListAssetsResponse]] = (fun: list) => fun.apply()
			}
			/** Updates security marks. */
			class updateSecurityMarks(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSecurityMarks(body: Schema.SecurityMarks) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SecurityMarks])
			}
			object updateSecurityMarks {
				def apply(projectsId :PlayApi, assetsId :PlayApi, name: String, updateMask: String, startTime: String)(using signer: RequestSigner, ec: ExecutionContext): updateSecurityMarks = new updateSecurityMarks(ws.url(BASE_URL + s"v1/projects/${projectsId}/assets/${assetsId}/securityMarks").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "startTime" -> startTime.toString))
			}
		}
		object findings {
			/** Kicks off an LRO to bulk mute findings for a parent based on a filter. The parent can be either an organization, folder or project. The findings matched by the filter will be muted after the LRO is done. */
			class bulkMute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withBulkMuteFindingsRequest(body: Schema.BulkMuteFindingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object bulkMute {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): bulkMute = new bulkMute(ws.url(BASE_URL + s"v1/projects/${projectsId}/findings:bulkMute").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object eventThreatDetectionSettings {
			/** Validates the given Event Threat Detection custom module. */
			class validateCustomModule(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withValidateEventThreatDetectionCustomModuleRequest(body: Schema.ValidateEventThreatDetectionCustomModuleRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ValidateEventThreatDetectionCustomModuleResponse])
			}
			object validateCustomModule {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): validateCustomModule = new validateCustomModule(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings:validateCustomModule").addQueryStringParameters("parent" -> parent.toString))
			}
			object customModules {
				/** Lists all resident Event Threat Detection custom modules under the given Resource Manager parent and its descendants. */
				class listDescendant(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDescendantEventThreatDetectionCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDescendantEventThreatDetectionCustomModulesResponse])
				}
				object listDescendant {
					def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): listDescendant = new listDescendant(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/customModules:listDescendant").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[listDescendant, Future[Schema.ListDescendantEventThreatDetectionCustomModulesResponse]] = (fun: listDescendant) => fun.apply()
				}
				/** Creates a resident Event Threat Detection custom module at the scope of the given Resource Manager parent, and also creates inherited custom modules for all descendants of the given parent. These modules are enabled by default. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEventThreatDetectionCustomModule(body: Schema.EventThreatDetectionCustomModule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object create {
					def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/customModules").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the specified Event Threat Detection custom module and all of its descendants in the Resource Manager hierarchy. This method is only supported for resident custom modules. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, customModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets an Event Threat Detection custom module. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EventThreatDetectionCustomModule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object get {
					def apply(projectsId :PlayApi, customModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EventThreatDetectionCustomModule]] = (fun: get) => fun.apply()
				}
				/** Updates the Event Threat Detection custom module with the given name based on the given update mask. Updating the enablement state is supported for both resident and inherited modules (though resident modules cannot have an enablement state of "inherited"). Updating the display name or configuration of a module is supported for resident modules only. The type of a module cannot be changed. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEventThreatDetectionCustomModule(body: Schema.EventThreatDetectionCustomModule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object patch {
					def apply(projectsId :PlayApi, customModulesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists all Event Threat Detection custom modules for the given Resource Manager parent. This includes resident modules defined at the scope of the parent along with modules inherited from ancestors. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEventThreatDetectionCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEventThreatDetectionCustomModulesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/customModules").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListEventThreatDetectionCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object effectiveCustomModules {
				/** Gets an effective Event Threat Detection custom module at the given level. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EffectiveEventThreatDetectionCustomModule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EffectiveEventThreatDetectionCustomModule])
				}
				object get {
					def apply(projectsId :PlayApi, effectiveCustomModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/effectiveCustomModules/${effectiveCustomModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EffectiveEventThreatDetectionCustomModule]] = (fun: get) => fun.apply()
				}
				/** Lists all effective Event Threat Detection custom modules for the given parent. This includes resident modules defined at the scope of the parent along with modules inherited from its ancestors. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/effectiveCustomModules").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object sources {
			/** Lists all sources belonging to an organization. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSourcesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSourcesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListSourcesResponse]] = (fun: list) => fun.apply()
			}
			object findings {
				/** Updates security marks. */
				class updateSecurityMarks(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSecurityMarks(body: Schema.SecurityMarks) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SecurityMarks])
				}
				object updateSecurityMarks {
					def apply(projectsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String, updateMask: String, startTime: String)(using signer: RequestSigner, ec: ExecutionContext): updateSecurityMarks = new updateSecurityMarks(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings/${findingsId}/securityMarks").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "startTime" -> startTime.toString))
				}
				/** Creates or updates a finding. The corresponding source must exist for a finding creation to succeed. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withFinding(body: Schema.Finding) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Finding])
				}
				object patch {
					def apply(projectsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings/${findingsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Filters an organization or source's findings and groups them by their specified properties. To group across all sources provide a `-` as the source id. Example: /v1/organizations/{organization_id}/sources/-/findings, /v1/folders/{folder_id}/sources/-/findings, /v1/projects/{project_id}/sources/-/findings */
				class group(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGroupFindingsRequest(body: Schema.GroupFindingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GroupFindingsResponse])
				}
				object group {
					def apply(projectsId :PlayApi, sourcesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): group = new group(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings:group").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lists an organization or source's findings. To list across all sources provide a `-` as the source id. Example: /v1/organizations/{organization_id}/sources/-/findings */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFindingsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFindingsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, sourcesId :PlayApi, parent: String, filter: String, orderBy: String, readTime: String, compareDuration: String, fieldMask: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "readTime" -> readTime.toString, "compareDuration" -> compareDuration.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListFindingsResponse]] = (fun: list) => fun.apply()
				}
				/** Updates the mute state of a finding. */
				class setMute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetMuteRequest(body: Schema.SetMuteRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Finding])
				}
				object setMute {
					def apply(projectsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setMute = new setMute(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings/${findingsId}:setMute").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates the state of a finding. */
				class setState(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetFindingStateRequest(body: Schema.SetFindingStateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Finding])
				}
				object setState {
					def apply(projectsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setState = new setState(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings/${findingsId}:setState").addQueryStringParameters("name" -> name.toString))
				}
				object externalSystems {
					/** Updates external system. This is for a given finding. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudSecuritycenterV1ExternalSystem(body: Schema.GoogleCloudSecuritycenterV1ExternalSystem) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1ExternalSystem])
					}
					object patch {
						def apply(projectsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, externalSystemsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings/${findingsId}/externalSystems/${externalSystemsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
				}
			}
		}
		object notificationConfigs {
			/** Creates a notification config. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withNotificationConfig(body: Schema.NotificationConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.NotificationConfig])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, configId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/notificationConfigs").addQueryStringParameters("parent" -> parent.toString, "configId" -> configId.toString))
			}
			/** Deletes a notification config. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, notificationConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/notificationConfigs/${notificationConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a notification config. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.NotificationConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.NotificationConfig])
			}
			object get {
				def apply(projectsId :PlayApi, notificationConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/notificationConfigs/${notificationConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.NotificationConfig]] = (fun: get) => fun.apply()
			}
			/**  Updates a notification config. The following update fields are allowed: description, pubsub_topic, streaming_config.filter */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withNotificationConfig(body: Schema.NotificationConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.NotificationConfig])
			}
			object patch {
				def apply(projectsId :PlayApi, notificationConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/notificationConfigs/${notificationConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists notification configs. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNotificationConfigsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNotificationConfigsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/notificationConfigs").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListNotificationConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			object muteConfigs {
				/** Deletes an existing mute config. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a mute config. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]] = (fun: get) => fun.apply()
				}
				/** Updates a mute config. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
			}
		}
		object bigQueryExports {
			/** Creates a BigQuery export. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudSecuritycenterV1BigQueryExport(body: Schema.GoogleCloudSecuritycenterV1BigQueryExport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, bigQueryExportId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/bigQueryExports").addQueryStringParameters("parent" -> parent.toString, "bigQueryExportId" -> bigQueryExportId.toString))
			}
			/** Deletes an existing BigQuery export. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, bigQueryExportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/bigQueryExports/${bigQueryExportsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a BigQuery export. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1BigQueryExport]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object get {
				def apply(projectsId :PlayApi, bigQueryExportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/bigQueryExports/${bigQueryExportsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1BigQueryExport]] = (fun: get) => fun.apply()
			}
			/** Updates a BigQuery export. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudSecuritycenterV1BigQueryExport(body: Schema.GoogleCloudSecuritycenterV1BigQueryExport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object patch {
				def apply(projectsId :PlayApi, bigQueryExportsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/bigQueryExports/${bigQueryExportsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists BigQuery exports. Note that when requesting BigQuery exports at a given level all exports under that level are also returned e.g. if requesting BigQuery exports under a folder, then all BigQuery exports immediately under the folder plus the ones created under the projects within the folder are returned. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBigQueryExportsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBigQueryExportsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/bigQueryExports").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListBigQueryExportsResponse]] = (fun: list) => fun.apply()
			}
		}
		object muteConfigs {
			/** Creates a mute config. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, muteConfigId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/muteConfigs").addQueryStringParameters("parent" -> parent.toString, "muteConfigId" -> muteConfigId.toString))
			}
			/** Deletes an existing mute config. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, muteConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a mute config. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object get {
				def apply(projectsId :PlayApi, muteConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]] = (fun: get) => fun.apply()
			}
			/** Updates a mute config. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object patch {
				def apply(projectsId :PlayApi, muteConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists mute configs. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMuteConfigsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMuteConfigsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/muteConfigs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListMuteConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
		object securityHealthAnalyticsSettings {
			object customModules {
				/** Simulates a given SecurityHealthAnalyticsCustomModule and Resource. */
				class simulate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSimulateSecurityHealthAnalyticsCustomModuleRequest(body: Schema.SimulateSecurityHealthAnalyticsCustomModuleRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SimulateSecurityHealthAnalyticsCustomModuleResponse])
				}
				object simulate {
					def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): simulate = new simulate(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules:simulate").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Returns a list of all resident SecurityHealthAnalyticsCustomModules under the given CRM parent and all of the parent’s CRM descendants. */
				class listDescendant(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse])
				}
				object listDescendant {
					def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listDescendant = new listDescendant(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules:listDescendant").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listDescendant, Future[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse]] = (fun: listDescendant) => fun.apply()
				}
				/** Creates a resident SecurityHealthAnalyticsCustomModule at the scope of the given CRM parent, and also creates inherited SecurityHealthAnalyticsCustomModules for all CRM descendants of the given parent. These modules are enabled by default. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule(body: Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object create {
					def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the specified SecurityHealthAnalyticsCustomModule and all of its descendants in the CRM hierarchy. This method is only supported for resident custom modules. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, customModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Retrieves a SecurityHealthAnalyticsCustomModule. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object get {
					def apply(projectsId :PlayApi, customModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]] = (fun: get) => fun.apply()
				}
				/** Updates the SecurityHealthAnalyticsCustomModule under the given name based on the given update mask. Updating the enablement state is supported on both resident and inherited modules (though resident modules cannot have an enablement state of "inherited"). Updating the display name and custom config of a module is supported on resident modules only. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule(body: Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object patch {
					def apply(projectsId :PlayApi, customModulesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Returns a list of all SecurityHealthAnalyticsCustomModules for the given parent. This includes resident modules defined at the scope of the parent, and inherited modules, inherited from CRM ancestors. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSecurityHealthAnalyticsCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSecurityHealthAnalyticsCustomModulesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSecurityHealthAnalyticsCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object effectiveCustomModules {
				/** Retrieves an EffectiveSecurityHealthAnalyticsCustomModule. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule])
				}
				object get {
					def apply(projectsId :PlayApi, effectiveCustomModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/effectiveCustomModules/${effectiveCustomModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule]] = (fun: get) => fun.apply()
				}
				/** Returns a list of all EffectiveSecurityHealthAnalyticsCustomModules for the given parent. This includes resident modules defined at the scope of the parent, and inherited modules, inherited from CRM ancestors. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/effectiveCustomModules").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object organizations {
		/** Gets the settings for an organization. */
		class getOrganizationSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.OrganizationSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.OrganizationSettings])
		}
		object getOrganizationSettings {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getOrganizationSettings = new getOrganizationSettings(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/organizationSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getOrganizationSettings, Future[Schema.OrganizationSettings]] = (fun: getOrganizationSettings) => fun.apply()
		}
		/** Updates an organization's settings. */
		class updateOrganizationSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withOrganizationSettings(body: Schema.OrganizationSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.OrganizationSettings])
		}
		object updateOrganizationSettings {
			def apply(organizationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateOrganizationSettings = new updateOrganizationSettings(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/organizationSettings").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object operations {
			/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(organizationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
			/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object cancel {
				def apply(organizationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
			}
		}
		object assets {
			/** Filters an organization's assets and groups them by their specified properties. */
			class group(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGroupAssetsRequest(body: Schema.GroupAssetsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GroupAssetsResponse])
			}
			object group {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): group = new group(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/assets:group").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Lists an organization's assets. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAssetsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAssetsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, filter: String, orderBy: String, readTime: String, compareDuration: String, fieldMask: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/assets").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "readTime" -> readTime.toString, "compareDuration" -> compareDuration.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListAssetsResponse]] = (fun: list) => fun.apply()
			}
			/** Runs asset discovery. The discovery is tracked with a long-running operation. This API can only be called with limited frequency for an organization. If it is called too frequently the caller will receive a TOO_MANY_REQUESTS error. */
			class runDiscovery(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withRunAssetDiscoveryRequest(body: Schema.RunAssetDiscoveryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object runDiscovery {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): runDiscovery = new runDiscovery(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/assets:runDiscovery").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Updates security marks. */
			class updateSecurityMarks(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSecurityMarks(body: Schema.SecurityMarks) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SecurityMarks])
			}
			object updateSecurityMarks {
				def apply(organizationsId :PlayApi, assetsId :PlayApi, name: String, updateMask: String, startTime: String)(using signer: RequestSigner, ec: ExecutionContext): updateSecurityMarks = new updateSecurityMarks(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/assets/${assetsId}/securityMarks").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "startTime" -> startTime.toString))
			}
		}
		object findings {
			/** Kicks off an LRO to bulk mute findings for a parent based on a filter. The parent can be either an organization, folder or project. The findings matched by the filter will be muted after the LRO is done. */
			class bulkMute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withBulkMuteFindingsRequest(body: Schema.BulkMuteFindingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object bulkMute {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): bulkMute = new bulkMute(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/findings:bulkMute").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object eventThreatDetectionSettings {
			/** Validates the given Event Threat Detection custom module. */
			class validateCustomModule(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withValidateEventThreatDetectionCustomModuleRequest(body: Schema.ValidateEventThreatDetectionCustomModuleRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ValidateEventThreatDetectionCustomModuleResponse])
			}
			object validateCustomModule {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): validateCustomModule = new validateCustomModule(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings:validateCustomModule").addQueryStringParameters("parent" -> parent.toString))
			}
			object customModules {
				/** Lists all resident Event Threat Detection custom modules under the given Resource Manager parent and its descendants. */
				class listDescendant(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDescendantEventThreatDetectionCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDescendantEventThreatDetectionCustomModulesResponse])
				}
				object listDescendant {
					def apply(organizationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): listDescendant = new listDescendant(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/customModules:listDescendant").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[listDescendant, Future[Schema.ListDescendantEventThreatDetectionCustomModulesResponse]] = (fun: listDescendant) => fun.apply()
				}
				/** Creates a resident Event Threat Detection custom module at the scope of the given Resource Manager parent, and also creates inherited custom modules for all descendants of the given parent. These modules are enabled by default. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEventThreatDetectionCustomModule(body: Schema.EventThreatDetectionCustomModule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object create {
					def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/customModules").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the specified Event Threat Detection custom module and all of its descendants in the Resource Manager hierarchy. This method is only supported for resident custom modules. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, customModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets an Event Threat Detection custom module. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EventThreatDetectionCustomModule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object get {
					def apply(organizationsId :PlayApi, customModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EventThreatDetectionCustomModule]] = (fun: get) => fun.apply()
				}
				/** Updates the Event Threat Detection custom module with the given name based on the given update mask. Updating the enablement state is supported for both resident and inherited modules (though resident modules cannot have an enablement state of "inherited"). Updating the display name or configuration of a module is supported for resident modules only. The type of a module cannot be changed. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEventThreatDetectionCustomModule(body: Schema.EventThreatDetectionCustomModule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object patch {
					def apply(organizationsId :PlayApi, customModulesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists all Event Threat Detection custom modules for the given Resource Manager parent. This includes resident modules defined at the scope of the parent along with modules inherited from ancestors. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEventThreatDetectionCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEventThreatDetectionCustomModulesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/customModules").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListEventThreatDetectionCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object effectiveCustomModules {
				/** Gets an effective Event Threat Detection custom module at the given level. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EffectiveEventThreatDetectionCustomModule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EffectiveEventThreatDetectionCustomModule])
				}
				object get {
					def apply(organizationsId :PlayApi, effectiveCustomModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/effectiveCustomModules/${effectiveCustomModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EffectiveEventThreatDetectionCustomModule]] = (fun: get) => fun.apply()
				}
				/** Lists all effective Event Threat Detection custom modules for the given parent. This includes resident modules defined at the scope of the parent along with modules inherited from its ancestors. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/effectiveCustomModules").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object simulations {
			/** Get the simulation by name or the latest simulation for the given organization. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Simulation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Simulation])
			}
			object get {
				def apply(organizationsId :PlayApi, simulationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Simulation]] = (fun: get) => fun.apply()
			}
			object valuedResources {
				/** Get the valued resource by name */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ValuedResource]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ValuedResource])
				}
				object get {
					def apply(organizationsId :PlayApi, simulationsId :PlayApi, valuedResourcesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}/valuedResources/${valuedResourcesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ValuedResource]] = (fun: get) => fun.apply()
				}
				/** Lists the valued resources for a set of simulation results and filter. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListValuedResourcesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The fields by which to order the valued resources response. Supported fields: &#42; `exposed_score` &#42; `resource_value` &#42; `resource_type` &#42; `resource` &#42; `display_name` Values should be a comma separated list of fields. For example: `exposed_score,resource_value`. The default sorting order is descending. To specify ascending or descending order for a field, append a ` ASC` or a ` DESC` suffix, respectively; for example: `exposed_score DESC`. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListValuedResourcesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, simulationsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}/valuedResources").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListValuedResourcesResponse]] = (fun: list) => fun.apply()
				}
				object attackPaths {
					/** Lists the attack paths for a set of simulation results or valued resources and filter. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAttackPathsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAttackPathsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, simulationsId :PlayApi, valuedResourcesId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}/valuedResources/${valuedResourcesId}/attackPaths").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.ListAttackPathsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object attackExposureResults {
				object valuedResources {
					/** Lists the valued resources for a set of simulation results and filter. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListValuedResourcesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The fields by which to order the valued resources response. Supported fields: &#42; `exposed_score` &#42; `resource_value` &#42; `resource_type` &#42; `resource` &#42; `display_name` Values should be a comma separated list of fields. For example: `exposed_score,resource_value`. The default sorting order is descending. To specify ascending or descending order for a field, append a ` ASC` or a ` DESC` suffix, respectively; for example: `exposed_score DESC`. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListValuedResourcesResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, simulationsId :PlayApi, attackExposureResultsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}/attackExposureResults/${attackExposureResultsId}/valuedResources").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.ListValuedResourcesResponse]] = (fun: list) => fun.apply()
					}
				}
				object attackPaths {
					/** Lists the attack paths for a set of simulation results or valued resources and filter. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAttackPathsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAttackPathsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, simulationsId :PlayApi, attackExposureResultsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}/attackExposureResults/${attackExposureResultsId}/attackPaths").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.ListAttackPathsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object attackPaths {
				/** Lists the attack paths for a set of simulation results or valued resources and filter. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAttackPathsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAttackPathsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, simulationsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}/attackPaths").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListAttackPathsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object sources {
			/** Returns the permissions that a caller has on the specified source. */
			class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(organizationsId :PlayApi, sourcesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Gets the access control policy on the specified Source. */
			class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object getIamPolicy {
				def apply(organizationsId :PlayApi, sourcesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Gets a source. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Source]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Source])
			}
			object get {
				def apply(organizationsId :PlayApi, sourcesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Source]] = (fun: get) => fun.apply()
			}
			/** Updates a source. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSource(body: Schema.Source) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Source])
			}
			object patch {
				def apply(organizationsId :PlayApi, sourcesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists all sources belonging to an organization. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSourcesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSourcesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListSourcesResponse]] = (fun: list) => fun.apply()
			}
			/** Creates a source. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSource(body: Schema.Source) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Source])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Sets the access control policy on the specified Source. */
			class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object setIamPolicy {
				def apply(organizationsId :PlayApi, sourcesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			object findings {
				/** Creates a finding. The corresponding source must exist for finding creation to succeed. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withFinding(body: Schema.Finding) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Finding])
				}
				object create {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, parent: String, findingId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings").addQueryStringParameters("parent" -> parent.toString, "findingId" -> findingId.toString))
				}
				/** Updates security marks. */
				class updateSecurityMarks(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSecurityMarks(body: Schema.SecurityMarks) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SecurityMarks])
				}
				object updateSecurityMarks {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String, updateMask: String, startTime: String)(using signer: RequestSigner, ec: ExecutionContext): updateSecurityMarks = new updateSecurityMarks(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings/${findingsId}/securityMarks").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "startTime" -> startTime.toString))
				}
				/** Creates or updates a finding. The corresponding source must exist for a finding creation to succeed. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withFinding(body: Schema.Finding) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Finding])
				}
				object patch {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings/${findingsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Filters an organization or source's findings and groups them by their specified properties. To group across all sources provide a `-` as the source id. Example: /v1/organizations/{organization_id}/sources/-/findings, /v1/folders/{folder_id}/sources/-/findings, /v1/projects/{project_id}/sources/-/findings */
				class group(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGroupFindingsRequest(body: Schema.GroupFindingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GroupFindingsResponse])
				}
				object group {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): group = new group(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings:group").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lists an organization or source's findings. To list across all sources provide a `-` as the source id. Example: /v1/organizations/{organization_id}/sources/-/findings */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFindingsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFindingsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, parent: String, filter: String, orderBy: String, readTime: String, compareDuration: String, fieldMask: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "readTime" -> readTime.toString, "compareDuration" -> compareDuration.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListFindingsResponse]] = (fun: list) => fun.apply()
				}
				/** Updates the mute state of a finding. */
				class setMute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetMuteRequest(body: Schema.SetMuteRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Finding])
				}
				object setMute {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setMute = new setMute(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings/${findingsId}:setMute").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates the state of a finding. */
				class setState(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetFindingStateRequest(body: Schema.SetFindingStateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Finding])
				}
				object setState {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setState = new setState(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings/${findingsId}:setState").addQueryStringParameters("name" -> name.toString))
				}
				object externalSystems {
					/** Updates external system. This is for a given finding. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudSecuritycenterV1ExternalSystem(body: Schema.GoogleCloudSecuritycenterV1ExternalSystem) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1ExternalSystem])
					}
					object patch {
						def apply(organizationsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, externalSystemsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings/${findingsId}/externalSystems/${externalSystemsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
				}
			}
		}
		object notificationConfigs {
			/** Creates a notification config. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withNotificationConfig(body: Schema.NotificationConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.NotificationConfig])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String, configId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/notificationConfigs").addQueryStringParameters("parent" -> parent.toString, "configId" -> configId.toString))
			}
			/** Deletes a notification config. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, notificationConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/notificationConfigs/${notificationConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a notification config. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.NotificationConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.NotificationConfig])
			}
			object get {
				def apply(organizationsId :PlayApi, notificationConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/notificationConfigs/${notificationConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.NotificationConfig]] = (fun: get) => fun.apply()
			}
			/**  Updates a notification config. The following update fields are allowed: description, pubsub_topic, streaming_config.filter */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withNotificationConfig(body: Schema.NotificationConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.NotificationConfig])
			}
			object patch {
				def apply(organizationsId :PlayApi, notificationConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/notificationConfigs/${notificationConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists notification configs. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNotificationConfigsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNotificationConfigsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/notificationConfigs").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListNotificationConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			object muteConfigs {
				/** Deletes an existing mute config. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a mute config. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]] = (fun: get) => fun.apply()
				}
				/** Updates a mute config. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
			}
		}
		object bigQueryExports {
			/** Creates a BigQuery export. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudSecuritycenterV1BigQueryExport(body: Schema.GoogleCloudSecuritycenterV1BigQueryExport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String, bigQueryExportId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/bigQueryExports").addQueryStringParameters("parent" -> parent.toString, "bigQueryExportId" -> bigQueryExportId.toString))
			}
			/** Deletes an existing BigQuery export. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, bigQueryExportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/bigQueryExports/${bigQueryExportsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a BigQuery export. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1BigQueryExport]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object get {
				def apply(organizationsId :PlayApi, bigQueryExportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/bigQueryExports/${bigQueryExportsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1BigQueryExport]] = (fun: get) => fun.apply()
			}
			/** Updates a BigQuery export. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudSecuritycenterV1BigQueryExport(body: Schema.GoogleCloudSecuritycenterV1BigQueryExport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object patch {
				def apply(organizationsId :PlayApi, bigQueryExportsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/bigQueryExports/${bigQueryExportsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists BigQuery exports. Note that when requesting BigQuery exports at a given level all exports under that level are also returned e.g. if requesting BigQuery exports under a folder, then all BigQuery exports immediately under the folder plus the ones created under the projects within the folder are returned. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBigQueryExportsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBigQueryExportsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/bigQueryExports").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListBigQueryExportsResponse]] = (fun: list) => fun.apply()
			}
		}
		object muteConfigs {
			/** Creates a mute config. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String, muteConfigId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/muteConfigs").addQueryStringParameters("parent" -> parent.toString, "muteConfigId" -> muteConfigId.toString))
			}
			/** Deletes an existing mute config. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a mute config. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object get {
				def apply(organizationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]] = (fun: get) => fun.apply()
			}
			/** Updates a mute config. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object patch {
				def apply(organizationsId :PlayApi, muteConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/muteConfigs/${muteConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists mute configs. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMuteConfigsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMuteConfigsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/muteConfigs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListMuteConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
		object securityHealthAnalyticsSettings {
			object customModules {
				/** Simulates a given SecurityHealthAnalyticsCustomModule and Resource. */
				class simulate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSimulateSecurityHealthAnalyticsCustomModuleRequest(body: Schema.SimulateSecurityHealthAnalyticsCustomModuleRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SimulateSecurityHealthAnalyticsCustomModuleResponse])
				}
				object simulate {
					def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): simulate = new simulate(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules:simulate").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Returns a list of all resident SecurityHealthAnalyticsCustomModules under the given CRM parent and all of the parent’s CRM descendants. */
				class listDescendant(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse])
				}
				object listDescendant {
					def apply(organizationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listDescendant = new listDescendant(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules:listDescendant").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listDescendant, Future[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse]] = (fun: listDescendant) => fun.apply()
				}
				/** Creates a resident SecurityHealthAnalyticsCustomModule at the scope of the given CRM parent, and also creates inherited SecurityHealthAnalyticsCustomModules for all CRM descendants of the given parent. These modules are enabled by default. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule(body: Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object create {
					def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the specified SecurityHealthAnalyticsCustomModule and all of its descendants in the CRM hierarchy. This method is only supported for resident custom modules. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, customModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Retrieves a SecurityHealthAnalyticsCustomModule. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object get {
					def apply(organizationsId :PlayApi, customModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]] = (fun: get) => fun.apply()
				}
				/** Updates the SecurityHealthAnalyticsCustomModule under the given name based on the given update mask. Updating the enablement state is supported on both resident and inherited modules (though resident modules cannot have an enablement state of "inherited"). Updating the display name and custom config of a module is supported on resident modules only. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule(body: Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object patch {
					def apply(organizationsId :PlayApi, customModulesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules/${customModulesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Returns a list of all SecurityHealthAnalyticsCustomModules for the given parent. This includes resident modules defined at the scope of the parent, and inherited modules, inherited from CRM ancestors. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSecurityHealthAnalyticsCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSecurityHealthAnalyticsCustomModulesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSecurityHealthAnalyticsCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object effectiveCustomModules {
				/** Retrieves an EffectiveSecurityHealthAnalyticsCustomModule. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule])
				}
				object get {
					def apply(organizationsId :PlayApi, effectiveCustomModulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/effectiveCustomModules/${effectiveCustomModulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule]] = (fun: get) => fun.apply()
				}
				/** Returns a list of all EffectiveSecurityHealthAnalyticsCustomModules for the given parent. This includes resident modules defined at the scope of the parent, and inherited modules, inherited from CRM ancestors. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/effectiveCustomModules").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object valuedResources {
			/** Lists the valued resources for a set of simulation results and filter. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListValuedResourcesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The fields by which to order the valued resources response. Supported fields: &#42; `exposed_score` &#42; `resource_value` &#42; `resource_type` &#42; `resource` &#42; `display_name` Values should be a comma separated list of fields. For example: `exposed_score,resource_value`. The default sorting order is descending. To specify ascending or descending order for a field, append a ` ASC` or a ` DESC` suffix, respectively; for example: `exposed_score DESC`. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListValuedResourcesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/valuedResources").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListValuedResourcesResponse]] = (fun: list) => fun.apply()
			}
		}
		object resourceValueConfigs {
			/** Deletes a ResourceValueConfig. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, resourceValueConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/resourceValueConfigs/${resourceValueConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Creates a ResourceValueConfig for an organization. Maps user's tags to difference resource values for use by the attack path simulation. */
			class batchCreate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withBatchCreateResourceValueConfigsRequest(body: Schema.BatchCreateResourceValueConfigsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchCreateResourceValueConfigsResponse])
			}
			object batchCreate {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/resourceValueConfigs:batchCreate").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Gets a ResourceValueConfig. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1ResourceValueConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1ResourceValueConfig])
			}
			object get {
				def apply(organizationsId :PlayApi, resourceValueConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/resourceValueConfigs/${resourceValueConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1ResourceValueConfig]] = (fun: get) => fun.apply()
			}
			/** Updates an existing ResourceValueConfigs with new rules. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudSecuritycenterV1ResourceValueConfig(body: Schema.GoogleCloudSecuritycenterV1ResourceValueConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudSecuritycenterV1ResourceValueConfig])
			}
			object patch {
				def apply(organizationsId :PlayApi, resourceValueConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/resourceValueConfigs/${resourceValueConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists all ResourceValueConfigs. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListResourceValueConfigsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListResourceValueConfigsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/resourceValueConfigs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListResourceValueConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
