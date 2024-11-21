package com.boresjo.play.api.google.securitycenter

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://securitycenter.googleapis.com/"

	object folders {
		object assets {
			class group(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGroupAssetsRequest(body: Schema.GroupAssetsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GroupAssetsResponse])
			}
			object group {
				def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): group = new group(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/assets:group")).addQueryStringParameters("parent" -> parent.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAssetsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListAssetsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, filter: String, orderBy: String, readTime: String, compareDuration: String, fieldMask: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/assets")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "readTime" -> readTime.toString, "compareDuration" -> compareDuration.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListAssetsResponse]] = (fun: list) => fun.apply()
			}
			class updateSecurityMarks(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSecurityMarks(body: Schema.SecurityMarks) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SecurityMarks])
			}
			object updateSecurityMarks {
				def apply(foldersId :PlayApi, assetsId :PlayApi, name: String, updateMask: String, startTime: String)(using auth: AuthToken, ec: ExecutionContext): updateSecurityMarks = new updateSecurityMarks(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/assets/${assetsId}/securityMarks")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "startTime" -> startTime.toString))
			}
		}
		object findings {
			class bulkMute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBulkMuteFindingsRequest(body: Schema.BulkMuteFindingsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
			}
			object bulkMute {
				def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): bulkMute = new bulkMute(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/findings:bulkMute")).addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object eventThreatDetectionSettings {
			class validateCustomModule(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withValidateEventThreatDetectionCustomModuleRequest(body: Schema.ValidateEventThreatDetectionCustomModuleRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ValidateEventThreatDetectionCustomModuleResponse])
			}
			object validateCustomModule {
				def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): validateCustomModule = new validateCustomModule(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings:validateCustomModule")).addQueryStringParameters("parent" -> parent.toString))
			}
			object customModules {
				class listDescendant(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDescendantEventThreatDetectionCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListDescendantEventThreatDetectionCustomModulesResponse])
				}
				object listDescendant {
					def apply(foldersId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): listDescendant = new listDescendant(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/customModules:listDescendant")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[listDescendant, Future[Schema.ListDescendantEventThreatDetectionCustomModulesResponse]] = (fun: listDescendant) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEventThreatDetectionCustomModule(body: Schema.EventThreatDetectionCustomModule) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object create {
					def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/customModules")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(foldersId :PlayApi, customModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EventThreatDetectionCustomModule]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object get {
					def apply(foldersId :PlayApi, customModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EventThreatDetectionCustomModule]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEventThreatDetectionCustomModule(body: Schema.EventThreatDetectionCustomModule) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object patch {
					def apply(foldersId :PlayApi, customModulesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEventThreatDetectionCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListEventThreatDetectionCustomModulesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/customModules")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListEventThreatDetectionCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object effectiveCustomModules {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EffectiveEventThreatDetectionCustomModule]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.EffectiveEventThreatDetectionCustomModule])
				}
				object get {
					def apply(foldersId :PlayApi, effectiveCustomModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/effectiveCustomModules/${effectiveCustomModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EffectiveEventThreatDetectionCustomModule]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/eventThreatDetectionSettings/effectiveCustomModules")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object sources {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSourcesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListSourcesResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListSourcesResponse]] = (fun: list) => fun.apply()
			}
			object findings {
				class updateSecurityMarks(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSecurityMarks(body: Schema.SecurityMarks) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SecurityMarks])
				}
				object updateSecurityMarks {
					def apply(foldersId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String, updateMask: String, startTime: String)(using auth: AuthToken, ec: ExecutionContext): updateSecurityMarks = new updateSecurityMarks(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings/${findingsId}/securityMarks")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "startTime" -> startTime.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withFinding(body: Schema.Finding) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Finding])
				}
				object patch {
					def apply(foldersId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings/${findingsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class group(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGroupFindingsRequest(body: Schema.GroupFindingsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GroupFindingsResponse])
				}
				object group {
					def apply(foldersId :PlayApi, sourcesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): group = new group(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings:group")).addQueryStringParameters("parent" -> parent.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFindingsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListFindingsResponse])
				}
				object list {
					def apply(foldersId :PlayApi, sourcesId :PlayApi, parent: String, filter: String, orderBy: String, readTime: String, compareDuration: String, fieldMask: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "readTime" -> readTime.toString, "compareDuration" -> compareDuration.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListFindingsResponse]] = (fun: list) => fun.apply()
				}
				class setMute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetMuteRequest(body: Schema.SetMuteRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Finding])
				}
				object setMute {
					def apply(foldersId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setMute = new setMute(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings/${findingsId}:setMute")).addQueryStringParameters("name" -> name.toString))
				}
				class setState(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetFindingStateRequest(body: Schema.SetFindingStateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Finding])
				}
				object setState {
					def apply(foldersId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setState = new setState(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings/${findingsId}:setState")).addQueryStringParameters("name" -> name.toString))
				}
				object externalSystems {
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudSecuritycenterV1ExternalSystem(body: Schema.GoogleCloudSecuritycenterV1ExternalSystem) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1ExternalSystem])
					}
					object patch {
						def apply(foldersId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, externalSystemsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/sources/${sourcesId}/findings/${findingsId}/externalSystems/${externalSystemsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
				}
			}
		}
		object notificationConfigs {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withNotificationConfig(body: Schema.NotificationConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.NotificationConfig])
			}
			object create {
				def apply(foldersId :PlayApi, parent: String, configId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/notificationConfigs")).addQueryStringParameters("parent" -> parent.toString, "configId" -> configId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(foldersId :PlayApi, notificationConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/notificationConfigs/${notificationConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.NotificationConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.NotificationConfig])
			}
			object get {
				def apply(foldersId :PlayApi, notificationConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/notificationConfigs/${notificationConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.NotificationConfig]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withNotificationConfig(body: Schema.NotificationConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.NotificationConfig])
			}
			object patch {
				def apply(foldersId :PlayApi, notificationConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/notificationConfigs/${notificationConfigsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNotificationConfigsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListNotificationConfigsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/notificationConfigs")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListNotificationConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			object muteConfigs {
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(foldersId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
				}
				object get {
					def apply(foldersId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
				}
				object patch {
					def apply(foldersId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
			}
		}
		object bigQueryExports {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudSecuritycenterV1BigQueryExport(body: Schema.GoogleCloudSecuritycenterV1BigQueryExport) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object create {
				def apply(foldersId :PlayApi, parent: String, bigQueryExportId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/bigQueryExports")).addQueryStringParameters("parent" -> parent.toString, "bigQueryExportId" -> bigQueryExportId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(foldersId :PlayApi, bigQueryExportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/bigQueryExports/${bigQueryExportsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1BigQueryExport]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object get {
				def apply(foldersId :PlayApi, bigQueryExportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/bigQueryExports/${bigQueryExportsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1BigQueryExport]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudSecuritycenterV1BigQueryExport(body: Schema.GoogleCloudSecuritycenterV1BigQueryExport) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object patch {
				def apply(foldersId :PlayApi, bigQueryExportsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/bigQueryExports/${bigQueryExportsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBigQueryExportsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListBigQueryExportsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/bigQueryExports")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListBigQueryExportsResponse]] = (fun: list) => fun.apply()
			}
		}
		object muteConfigs {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object create {
				def apply(foldersId :PlayApi, parent: String, muteConfigId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/muteConfigs")).addQueryStringParameters("parent" -> parent.toString, "muteConfigId" -> muteConfigId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(foldersId :PlayApi, muteConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object get {
				def apply(foldersId :PlayApi, muteConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object patch {
				def apply(foldersId :PlayApi, muteConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMuteConfigsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListMuteConfigsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/muteConfigs")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListMuteConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
		object securityHealthAnalyticsSettings {
			object customModules {
				class simulate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSimulateSecurityHealthAnalyticsCustomModuleRequest(body: Schema.SimulateSecurityHealthAnalyticsCustomModuleRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SimulateSecurityHealthAnalyticsCustomModuleResponse])
				}
				object simulate {
					def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): simulate = new simulate(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules:simulate")).addQueryStringParameters("parent" -> parent.toString))
				}
				class listDescendant(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse])
				}
				object listDescendant {
					def apply(foldersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listDescendant = new listDescendant(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules:listDescendant")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listDescendant, Future[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse]] = (fun: listDescendant) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule(body: Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object create {
					def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(foldersId :PlayApi, customModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object get {
					def apply(foldersId :PlayApi, customModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule(body: Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object patch {
					def apply(foldersId :PlayApi, customModulesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSecurityHealthAnalyticsCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListSecurityHealthAnalyticsCustomModulesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/customModules")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSecurityHealthAnalyticsCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object effectiveCustomModules {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule])
				}
				object get {
					def apply(foldersId :PlayApi, effectiveCustomModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/effectiveCustomModules/${effectiveCustomModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse])
				}
				object list {
					def apply(foldersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/securityHealthAnalyticsSettings/effectiveCustomModules")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object projects {
		object assets {
			class group(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGroupAssetsRequest(body: Schema.GroupAssetsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GroupAssetsResponse])
			}
			object group {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): group = new group(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/assets:group")).addQueryStringParameters("parent" -> parent.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAssetsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListAssetsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, filter: String, orderBy: String, readTime: String, compareDuration: String, fieldMask: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/assets")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "readTime" -> readTime.toString, "compareDuration" -> compareDuration.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListAssetsResponse]] = (fun: list) => fun.apply()
			}
			class updateSecurityMarks(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSecurityMarks(body: Schema.SecurityMarks) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SecurityMarks])
			}
			object updateSecurityMarks {
				def apply(projectsId :PlayApi, assetsId :PlayApi, name: String, updateMask: String, startTime: String)(using auth: AuthToken, ec: ExecutionContext): updateSecurityMarks = new updateSecurityMarks(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/assets/${assetsId}/securityMarks")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "startTime" -> startTime.toString))
			}
		}
		object findings {
			class bulkMute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBulkMuteFindingsRequest(body: Schema.BulkMuteFindingsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
			}
			object bulkMute {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): bulkMute = new bulkMute(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/findings:bulkMute")).addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object eventThreatDetectionSettings {
			class validateCustomModule(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withValidateEventThreatDetectionCustomModuleRequest(body: Schema.ValidateEventThreatDetectionCustomModuleRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ValidateEventThreatDetectionCustomModuleResponse])
			}
			object validateCustomModule {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): validateCustomModule = new validateCustomModule(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings:validateCustomModule")).addQueryStringParameters("parent" -> parent.toString))
			}
			object customModules {
				class listDescendant(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDescendantEventThreatDetectionCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListDescendantEventThreatDetectionCustomModulesResponse])
				}
				object listDescendant {
					def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): listDescendant = new listDescendant(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/customModules:listDescendant")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[listDescendant, Future[Schema.ListDescendantEventThreatDetectionCustomModulesResponse]] = (fun: listDescendant) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEventThreatDetectionCustomModule(body: Schema.EventThreatDetectionCustomModule) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object create {
					def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/customModules")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, customModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EventThreatDetectionCustomModule]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object get {
					def apply(projectsId :PlayApi, customModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EventThreatDetectionCustomModule]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEventThreatDetectionCustomModule(body: Schema.EventThreatDetectionCustomModule) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object patch {
					def apply(projectsId :PlayApi, customModulesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEventThreatDetectionCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListEventThreatDetectionCustomModulesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/customModules")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListEventThreatDetectionCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object effectiveCustomModules {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EffectiveEventThreatDetectionCustomModule]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.EffectiveEventThreatDetectionCustomModule])
				}
				object get {
					def apply(projectsId :PlayApi, effectiveCustomModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/effectiveCustomModules/${effectiveCustomModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EffectiveEventThreatDetectionCustomModule]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/eventThreatDetectionSettings/effectiveCustomModules")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object sources {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSourcesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListSourcesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListSourcesResponse]] = (fun: list) => fun.apply()
			}
			object findings {
				class updateSecurityMarks(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSecurityMarks(body: Schema.SecurityMarks) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SecurityMarks])
				}
				object updateSecurityMarks {
					def apply(projectsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String, updateMask: String, startTime: String)(using auth: AuthToken, ec: ExecutionContext): updateSecurityMarks = new updateSecurityMarks(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings/${findingsId}/securityMarks")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "startTime" -> startTime.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withFinding(body: Schema.Finding) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Finding])
				}
				object patch {
					def apply(projectsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings/${findingsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class group(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGroupFindingsRequest(body: Schema.GroupFindingsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GroupFindingsResponse])
				}
				object group {
					def apply(projectsId :PlayApi, sourcesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): group = new group(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings:group")).addQueryStringParameters("parent" -> parent.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFindingsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListFindingsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, sourcesId :PlayApi, parent: String, filter: String, orderBy: String, readTime: String, compareDuration: String, fieldMask: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "readTime" -> readTime.toString, "compareDuration" -> compareDuration.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListFindingsResponse]] = (fun: list) => fun.apply()
				}
				class setMute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetMuteRequest(body: Schema.SetMuteRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Finding])
				}
				object setMute {
					def apply(projectsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setMute = new setMute(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings/${findingsId}:setMute")).addQueryStringParameters("name" -> name.toString))
				}
				class setState(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetFindingStateRequest(body: Schema.SetFindingStateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Finding])
				}
				object setState {
					def apply(projectsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setState = new setState(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings/${findingsId}:setState")).addQueryStringParameters("name" -> name.toString))
				}
				object externalSystems {
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudSecuritycenterV1ExternalSystem(body: Schema.GoogleCloudSecuritycenterV1ExternalSystem) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1ExternalSystem])
					}
					object patch {
						def apply(projectsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, externalSystemsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/sources/${sourcesId}/findings/${findingsId}/externalSystems/${externalSystemsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
				}
			}
		}
		object notificationConfigs {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withNotificationConfig(body: Schema.NotificationConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.NotificationConfig])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, configId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/notificationConfigs")).addQueryStringParameters("parent" -> parent.toString, "configId" -> configId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, notificationConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/notificationConfigs/${notificationConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.NotificationConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.NotificationConfig])
			}
			object get {
				def apply(projectsId :PlayApi, notificationConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/notificationConfigs/${notificationConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.NotificationConfig]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withNotificationConfig(body: Schema.NotificationConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.NotificationConfig])
			}
			object patch {
				def apply(projectsId :PlayApi, notificationConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/notificationConfigs/${notificationConfigsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNotificationConfigsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListNotificationConfigsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/notificationConfigs")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListNotificationConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			object muteConfigs {
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
			}
		}
		object bigQueryExports {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudSecuritycenterV1BigQueryExport(body: Schema.GoogleCloudSecuritycenterV1BigQueryExport) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, bigQueryExportId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/bigQueryExports")).addQueryStringParameters("parent" -> parent.toString, "bigQueryExportId" -> bigQueryExportId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, bigQueryExportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/bigQueryExports/${bigQueryExportsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1BigQueryExport]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object get {
				def apply(projectsId :PlayApi, bigQueryExportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/bigQueryExports/${bigQueryExportsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1BigQueryExport]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudSecuritycenterV1BigQueryExport(body: Schema.GoogleCloudSecuritycenterV1BigQueryExport) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object patch {
				def apply(projectsId :PlayApi, bigQueryExportsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/bigQueryExports/${bigQueryExportsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBigQueryExportsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListBigQueryExportsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/bigQueryExports")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListBigQueryExportsResponse]] = (fun: list) => fun.apply()
			}
		}
		object muteConfigs {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, muteConfigId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/muteConfigs")).addQueryStringParameters("parent" -> parent.toString, "muteConfigId" -> muteConfigId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, muteConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object get {
				def apply(projectsId :PlayApi, muteConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object patch {
				def apply(projectsId :PlayApi, muteConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMuteConfigsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListMuteConfigsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/muteConfigs")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListMuteConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
		object securityHealthAnalyticsSettings {
			object customModules {
				class simulate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSimulateSecurityHealthAnalyticsCustomModuleRequest(body: Schema.SimulateSecurityHealthAnalyticsCustomModuleRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SimulateSecurityHealthAnalyticsCustomModuleResponse])
				}
				object simulate {
					def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): simulate = new simulate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules:simulate")).addQueryStringParameters("parent" -> parent.toString))
				}
				class listDescendant(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse])
				}
				object listDescendant {
					def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listDescendant = new listDescendant(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules:listDescendant")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listDescendant, Future[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse]] = (fun: listDescendant) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule(body: Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object create {
					def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, customModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object get {
					def apply(projectsId :PlayApi, customModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule(body: Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object patch {
					def apply(projectsId :PlayApi, customModulesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSecurityHealthAnalyticsCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListSecurityHealthAnalyticsCustomModulesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/customModules")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSecurityHealthAnalyticsCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object effectiveCustomModules {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule])
				}
				object get {
					def apply(projectsId :PlayApi, effectiveCustomModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/effectiveCustomModules/${effectiveCustomModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/securityHealthAnalyticsSettings/effectiveCustomModules")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object organizations {
		class getOrganizationSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.OrganizationSettings]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.OrganizationSettings])
		}
		object getOrganizationSettings {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getOrganizationSettings = new getOrganizationSettings(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/organizationSettings")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getOrganizationSettings, Future[Schema.OrganizationSettings]] = (fun: getOrganizationSettings) => fun.apply()
		}
		class updateOrganizationSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withOrganizationSettings(body: Schema.OrganizationSettings) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.OrganizationSettings])
		}
		object updateOrganizationSettings {
			def apply(organizationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateOrganizationSettings = new updateOrganizationSettings(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/organizationSettings")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object operations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(organizationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("POST").map(_.json.as[Schema.Empty])
			}
			object cancel {
				def apply(organizationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
				given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
			}
		}
		object assets {
			class group(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGroupAssetsRequest(body: Schema.GroupAssetsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GroupAssetsResponse])
			}
			object group {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): group = new group(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/assets:group")).addQueryStringParameters("parent" -> parent.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAssetsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListAssetsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, filter: String, orderBy: String, readTime: String, compareDuration: String, fieldMask: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/assets")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "readTime" -> readTime.toString, "compareDuration" -> compareDuration.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListAssetsResponse]] = (fun: list) => fun.apply()
			}
			class runDiscovery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRunAssetDiscoveryRequest(body: Schema.RunAssetDiscoveryRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
			}
			object runDiscovery {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): runDiscovery = new runDiscovery(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/assets:runDiscovery")).addQueryStringParameters("parent" -> parent.toString))
			}
			class updateSecurityMarks(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSecurityMarks(body: Schema.SecurityMarks) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SecurityMarks])
			}
			object updateSecurityMarks {
				def apply(organizationsId :PlayApi, assetsId :PlayApi, name: String, updateMask: String, startTime: String)(using auth: AuthToken, ec: ExecutionContext): updateSecurityMarks = new updateSecurityMarks(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/assets/${assetsId}/securityMarks")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "startTime" -> startTime.toString))
			}
		}
		object findings {
			class bulkMute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBulkMuteFindingsRequest(body: Schema.BulkMuteFindingsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
			}
			object bulkMute {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): bulkMute = new bulkMute(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/findings:bulkMute")).addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object eventThreatDetectionSettings {
			class validateCustomModule(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withValidateEventThreatDetectionCustomModuleRequest(body: Schema.ValidateEventThreatDetectionCustomModuleRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ValidateEventThreatDetectionCustomModuleResponse])
			}
			object validateCustomModule {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): validateCustomModule = new validateCustomModule(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings:validateCustomModule")).addQueryStringParameters("parent" -> parent.toString))
			}
			object customModules {
				class listDescendant(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDescendantEventThreatDetectionCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListDescendantEventThreatDetectionCustomModulesResponse])
				}
				object listDescendant {
					def apply(organizationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): listDescendant = new listDescendant(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/customModules:listDescendant")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[listDescendant, Future[Schema.ListDescendantEventThreatDetectionCustomModulesResponse]] = (fun: listDescendant) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEventThreatDetectionCustomModule(body: Schema.EventThreatDetectionCustomModule) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object create {
					def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/customModules")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, customModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EventThreatDetectionCustomModule]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object get {
					def apply(organizationsId :PlayApi, customModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EventThreatDetectionCustomModule]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEventThreatDetectionCustomModule(body: Schema.EventThreatDetectionCustomModule) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.EventThreatDetectionCustomModule])
				}
				object patch {
					def apply(organizationsId :PlayApi, customModulesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEventThreatDetectionCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListEventThreatDetectionCustomModulesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/customModules")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListEventThreatDetectionCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object effectiveCustomModules {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EffectiveEventThreatDetectionCustomModule]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.EffectiveEventThreatDetectionCustomModule])
				}
				object get {
					def apply(organizationsId :PlayApi, effectiveCustomModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/effectiveCustomModules/${effectiveCustomModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EffectiveEventThreatDetectionCustomModule]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/eventThreatDetectionSettings/effectiveCustomModules")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListEffectiveEventThreatDetectionCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object simulations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Simulation]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Simulation])
			}
			object get {
				def apply(organizationsId :PlayApi, simulationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Simulation]] = (fun: get) => fun.apply()
			}
			object valuedResources {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ValuedResource]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ValuedResource])
				}
				object get {
					def apply(organizationsId :PlayApi, simulationsId :PlayApi, valuedResourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}/valuedResources/${valuedResourcesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ValuedResource]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListValuedResourcesResponse]) {
					/** Optional. The fields by which to order the valued resources response. Supported fields: &#42; `exposed_score` &#42; `resource_value` &#42; `resource_type` &#42; `resource` &#42; `display_name` Values should be a comma separated list of fields. For example: `exposed_score,resource_value`. The default sorting order is descending. To specify ascending or descending order for a field, append a ` ASC` or a ` DESC` suffix, respectively; for example: `exposed_score DESC`. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListValuedResourcesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, simulationsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}/valuedResources")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListValuedResourcesResponse]] = (fun: list) => fun.apply()
				}
				object attackPaths {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAttackPathsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListAttackPathsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, simulationsId :PlayApi, valuedResourcesId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}/valuedResources/${valuedResourcesId}/attackPaths")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.ListAttackPathsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object attackExposureResults {
				object valuedResources {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListValuedResourcesResponse]) {
						/** Optional. The fields by which to order the valued resources response. Supported fields: &#42; `exposed_score` &#42; `resource_value` &#42; `resource_type` &#42; `resource` &#42; `display_name` Values should be a comma separated list of fields. For example: `exposed_score,resource_value`. The default sorting order is descending. To specify ascending or descending order for a field, append a ` ASC` or a ` DESC` suffix, respectively; for example: `exposed_score DESC`. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListValuedResourcesResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, simulationsId :PlayApi, attackExposureResultsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}/attackExposureResults/${attackExposureResultsId}/valuedResources")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.ListValuedResourcesResponse]] = (fun: list) => fun.apply()
					}
				}
				object attackPaths {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAttackPathsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListAttackPathsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, simulationsId :PlayApi, attackExposureResultsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}/attackExposureResults/${attackExposureResultsId}/attackPaths")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.ListAttackPathsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object attackPaths {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAttackPathsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListAttackPathsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, simulationsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/simulations/${simulationsId}/attackPaths")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListAttackPathsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object sources {
			class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(organizationsId :PlayApi, sourcesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
			}
			class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
			}
			object getIamPolicy {
				def apply(organizationsId :PlayApi, sourcesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Source]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Source])
			}
			object get {
				def apply(organizationsId :PlayApi, sourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Source]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSource(body: Schema.Source) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Source])
			}
			object patch {
				def apply(organizationsId :PlayApi, sourcesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSourcesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListSourcesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListSourcesResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSource(body: Schema.Source) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Source])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources")).addQueryStringParameters("parent" -> parent.toString))
			}
			class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
			}
			object setIamPolicy {
				def apply(organizationsId :PlayApi, sourcesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
			}
			object findings {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withFinding(body: Schema.Finding) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Finding])
				}
				object create {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, parent: String, findingId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings")).addQueryStringParameters("parent" -> parent.toString, "findingId" -> findingId.toString))
				}
				class updateSecurityMarks(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSecurityMarks(body: Schema.SecurityMarks) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SecurityMarks])
				}
				object updateSecurityMarks {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String, updateMask: String, startTime: String)(using auth: AuthToken, ec: ExecutionContext): updateSecurityMarks = new updateSecurityMarks(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings/${findingsId}/securityMarks")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "startTime" -> startTime.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withFinding(body: Schema.Finding) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Finding])
				}
				object patch {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings/${findingsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class group(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGroupFindingsRequest(body: Schema.GroupFindingsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GroupFindingsResponse])
				}
				object group {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): group = new group(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings:group")).addQueryStringParameters("parent" -> parent.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFindingsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListFindingsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, parent: String, filter: String, orderBy: String, readTime: String, compareDuration: String, fieldMask: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "readTime" -> readTime.toString, "compareDuration" -> compareDuration.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListFindingsResponse]] = (fun: list) => fun.apply()
				}
				class setMute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetMuteRequest(body: Schema.SetMuteRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Finding])
				}
				object setMute {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setMute = new setMute(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings/${findingsId}:setMute")).addQueryStringParameters("name" -> name.toString))
				}
				class setState(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetFindingStateRequest(body: Schema.SetFindingStateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Finding])
				}
				object setState {
					def apply(organizationsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setState = new setState(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings/${findingsId}:setState")).addQueryStringParameters("name" -> name.toString))
				}
				object externalSystems {
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudSecuritycenterV1ExternalSystem(body: Schema.GoogleCloudSecuritycenterV1ExternalSystem) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1ExternalSystem])
					}
					object patch {
						def apply(organizationsId :PlayApi, sourcesId :PlayApi, findingsId :PlayApi, externalSystemsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sources/${sourcesId}/findings/${findingsId}/externalSystems/${externalSystemsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
				}
			}
		}
		object notificationConfigs {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withNotificationConfig(body: Schema.NotificationConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.NotificationConfig])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String, configId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/notificationConfigs")).addQueryStringParameters("parent" -> parent.toString, "configId" -> configId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, notificationConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/notificationConfigs/${notificationConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.NotificationConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.NotificationConfig])
			}
			object get {
				def apply(organizationsId :PlayApi, notificationConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/notificationConfigs/${notificationConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.NotificationConfig]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withNotificationConfig(body: Schema.NotificationConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.NotificationConfig])
			}
			object patch {
				def apply(organizationsId :PlayApi, notificationConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/notificationConfigs/${notificationConfigsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNotificationConfigsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListNotificationConfigsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/notificationConfigs")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListNotificationConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			object muteConfigs {
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
				}
				object patch {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, muteConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
			}
		}
		object bigQueryExports {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudSecuritycenterV1BigQueryExport(body: Schema.GoogleCloudSecuritycenterV1BigQueryExport) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String, bigQueryExportId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/bigQueryExports")).addQueryStringParameters("parent" -> parent.toString, "bigQueryExportId" -> bigQueryExportId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, bigQueryExportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/bigQueryExports/${bigQueryExportsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1BigQueryExport]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object get {
				def apply(organizationsId :PlayApi, bigQueryExportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/bigQueryExports/${bigQueryExportsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1BigQueryExport]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudSecuritycenterV1BigQueryExport(body: Schema.GoogleCloudSecuritycenterV1BigQueryExport) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1BigQueryExport])
			}
			object patch {
				def apply(organizationsId :PlayApi, bigQueryExportsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/bigQueryExports/${bigQueryExportsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBigQueryExportsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListBigQueryExportsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/bigQueryExports")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListBigQueryExportsResponse]] = (fun: list) => fun.apply()
			}
		}
		object muteConfigs {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String, muteConfigId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/muteConfigs")).addQueryStringParameters("parent" -> parent.toString, "muteConfigId" -> muteConfigId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object get {
				def apply(organizationsId :PlayApi, muteConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1MuteConfig]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudSecuritycenterV1MuteConfig(body: Schema.GoogleCloudSecuritycenterV1MuteConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1MuteConfig])
			}
			object patch {
				def apply(organizationsId :PlayApi, muteConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/muteConfigs/${muteConfigsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMuteConfigsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListMuteConfigsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/muteConfigs")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListMuteConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
		object securityHealthAnalyticsSettings {
			object customModules {
				class simulate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSimulateSecurityHealthAnalyticsCustomModuleRequest(body: Schema.SimulateSecurityHealthAnalyticsCustomModuleRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SimulateSecurityHealthAnalyticsCustomModuleResponse])
				}
				object simulate {
					def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): simulate = new simulate(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules:simulate")).addQueryStringParameters("parent" -> parent.toString))
				}
				class listDescendant(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse])
				}
				object listDescendant {
					def apply(organizationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listDescendant = new listDescendant(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules:listDescendant")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listDescendant, Future[Schema.ListDescendantSecurityHealthAnalyticsCustomModulesResponse]] = (fun: listDescendant) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule(body: Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object create {
					def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(organizationsId :PlayApi, customModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object get {
					def apply(organizationsId :PlayApi, customModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule(body: Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule])
				}
				object patch {
					def apply(organizationsId :PlayApi, customModulesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules/${customModulesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSecurityHealthAnalyticsCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListSecurityHealthAnalyticsCustomModulesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/customModules")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSecurityHealthAnalyticsCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object effectiveCustomModules {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule])
				}
				object get {
					def apply(organizationsId :PlayApi, effectiveCustomModulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/effectiveCustomModules/${effectiveCustomModulesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityHealthAnalyticsSettings/effectiveCustomModules")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListEffectiveSecurityHealthAnalyticsCustomModulesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object valuedResources {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListValuedResourcesResponse]) {
				/** Optional. The fields by which to order the valued resources response. Supported fields: &#42; `exposed_score` &#42; `resource_value` &#42; `resource_type` &#42; `resource` &#42; `display_name` Values should be a comma separated list of fields. For example: `exposed_score,resource_value`. The default sorting order is descending. To specify ascending or descending order for a field, append a ` ASC` or a ` DESC` suffix, respectively; for example: `exposed_score DESC`. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListValuedResourcesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/valuedResources")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListValuedResourcesResponse]] = (fun: list) => fun.apply()
			}
		}
		object resourceValueConfigs {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, resourceValueConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/resourceValueConfigs/${resourceValueConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class batchCreate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchCreateResourceValueConfigsRequest(body: Schema.BatchCreateResourceValueConfigsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchCreateResourceValueConfigsResponse])
			}
			object batchCreate {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchCreate = new batchCreate(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/resourceValueConfigs:batchCreate")).addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudSecuritycenterV1ResourceValueConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudSecuritycenterV1ResourceValueConfig])
			}
			object get {
				def apply(organizationsId :PlayApi, resourceValueConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/resourceValueConfigs/${resourceValueConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudSecuritycenterV1ResourceValueConfig]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudSecuritycenterV1ResourceValueConfig(body: Schema.GoogleCloudSecuritycenterV1ResourceValueConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudSecuritycenterV1ResourceValueConfig])
			}
			object patch {
				def apply(organizationsId :PlayApi, resourceValueConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/resourceValueConfigs/${resourceValueConfigsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListResourceValueConfigsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListResourceValueConfigsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/resourceValueConfigs")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListResourceValueConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
