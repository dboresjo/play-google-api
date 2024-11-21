package com.boresjo.play.api.google.integrations

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://integrations.googleapis.com/"

	object connectorPlatformRegions {
		class enumerate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaEnumerateConnectorPlatformRegionsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaEnumerateConnectorPlatformRegionsResponse])
		}
		object enumerate {
			def apply()(using auth: AuthToken, ec: ExecutionContext): enumerate = new enumerate(auth(ws.url(BASE_URL + s"v1/connectorPlatformRegions:enumerate")).addQueryStringParameters())
			given Conversion[enumerate, Future[Schema.GoogleCloudIntegrationsV1alphaEnumerateConnectorPlatformRegionsResponse]] = (fun: enumerate) => fun.apply()
		}
	}
	object callback {
		class generateToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaGenerateTokenResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaGenerateTokenResponse])
		}
		object generateToken {
			def apply(code: String, product: String, state: String, gcpProjectId: String, redirectUri: String)(using auth: AuthToken, ec: ExecutionContext): generateToken = new generateToken(auth(ws.url(BASE_URL + s"v1/callback:generateToken")).addQueryStringParameters("code" -> code.toString, "product" -> product.toString, "state" -> state.toString, "gcpProjectId" -> gcpProjectId.toString, "redirectUri" -> redirectUri.toString))
			given Conversion[generateToken, Future[Schema.GoogleCloudIntegrationsV1alphaGenerateTokenResponse]] = (fun: generateToken) => fun.apply()
		}
	}
	object projects {
		class getClientmetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaGetClientMetadataResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaGetClientMetadataResponse])
		}
		object getClientmetadata {
			def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): getClientmetadata = new getClientmetadata(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/clientmetadata")).addQueryStringParameters("parent" -> parent.toString))
			given Conversion[getClientmetadata, Future[Schema.GoogleCloudIntegrationsV1alphaGetClientMetadataResponse]] = (fun: getClientmetadata) => fun.apply()
		}
		object locations {
			class getClients(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaGetClientResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaGetClientResponse])
			}
			object getClients {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): getClients = new getClients(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clients")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[getClients, Future[Schema.GoogleCloudIntegrationsV1alphaGetClientResponse]] = (fun: getClients) => fun.apply()
			}
			object clients {
				class switch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaSwitchEncryptionRequest(body: Schema.GoogleCloudIntegrationsV1alphaSwitchEncryptionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object switch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): switch = new switch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clients:switch")).addQueryStringParameters("parent" -> parent.toString))
				}
				class replace(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaReplaceServiceAccountRequest(body: Schema.GoogleCloudIntegrationsV1alphaReplaceServiceAccountRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object replace {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): replace = new replace(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clients:replace")).addQueryStringParameters("parent" -> parent.toString))
				}
				class deprovision(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaDeprovisionClientRequest(body: Schema.GoogleCloudIntegrationsV1alphaDeprovisionClientRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object deprovision {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): deprovision = new deprovision(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clients:deprovision")).addQueryStringParameters("parent" -> parent.toString))
				}
				class switchVariableMasking(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaSwitchVariableMaskingRequest(body: Schema.GoogleCloudIntegrationsV1alphaSwitchVariableMaskingRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object switchVariableMasking {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): switchVariableMasking = new switchVariableMasking(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clients:switchVariableMasking")).addQueryStringParameters("parent" -> parent.toString))
				}
				class provision(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaProvisionClientRequest(body: Schema.GoogleCloudIntegrationsV1alphaProvisionClientRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object provision {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): provision = new provision(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clients:provision")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object products {
				object integrations {
					class execute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaExecuteIntegrationsRequest(body: Schema.GoogleCloudIntegrationsV1alphaExecuteIntegrationsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaExecuteIntegrationsResponse])
					}
					object execute {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): execute = new execute(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}:execute")).addQueryStringParameters("name" -> name.toString))
					}
					class schedule(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaScheduleIntegrationsRequest(body: Schema.GoogleCloudIntegrationsV1alphaScheduleIntegrationsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaScheduleIntegrationsResponse])
					}
					object schedule {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): schedule = new schedule(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}:schedule")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListIntegrationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String, orderBy: String, pageToken: String, pageSize: Int, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations")).addQueryStringParameters("parent" -> parent.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationsResponse]] = (fun: list) => fun.apply()
					}
					class test(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaTestIntegrationsRequest(body: Schema.GoogleCloudIntegrationsV1alphaTestIntegrationsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTestIntegrationsResponse])
					}
					object test {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): test = new test(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}:test")).addQueryStringParameters("name" -> name.toString))
					}
					object versions {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							/** Optional. Optional. Indicates if sample workflow should be created. */
							def withCreateSampleIntegrations(createSampleIntegrations: Boolean) = new create(req.addQueryStringParameters("createSampleIntegrations" -> createSampleIntegrations.toString))
							def withGoogleCloudIntegrationsV1alphaIntegrationVersion(body: Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, parent: String, newIntegration: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions")).addQueryStringParameters("parent" -> parent.toString, "newIntegration" -> newIntegration.toString))
						}
						class unpublish(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudIntegrationsV1alphaUnpublishIntegrationVersionRequest(body: Schema.GoogleCloudIntegrationsV1alphaUnpublishIntegrationVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object unpublish {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): unpublish = new unpublish(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}:unpublish")).addQueryStringParameters("name" -> name.toString))
						}
						class publish(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudIntegrationsV1alphaPublishIntegrationVersionRequest(body: Schema.GoogleCloudIntegrationsV1alphaPublishIntegrationVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaPublishIntegrationVersionResponse])
						}
						object publish {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): publish = new publish(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}:publish")).addQueryStringParameters("name" -> name.toString))
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion]] = (fun: get) => fun.apply()
						}
						class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadIntegrationVersionResponse]) {
							/** Optional. Integration related file to download like Integration Json, Config variable, testcase etc.<br>Possible values:<br>INTEGRATION_FILE_UNSPECIFIED: Default value.<br>INTEGRATION: Integration file.<br>INTEGRATION_CONFIG_VARIABLES: Integration Config variables. */
							def withFiles(files: String) = new download(req.addQueryStringParameters("files" -> files.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadIntegrationVersionResponse])
						}
						object download {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String, fileFormat: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}:download")).addQueryStringParameters("name" -> name.toString, "fileFormat" -> fileFormat.toString))
							given Conversion[download, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadIntegrationVersionResponse]] = (fun: download) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationVersionsResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListIntegrationVersionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, filter: String, parent: String, pageToken: String, orderBy: String, pageSize: Int, fieldMask: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions")).addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "fieldMask" -> fieldMask.toString))
							given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationVersionsResponse]] = (fun: list) => fun.apply()
						}
						class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudIntegrationsV1alphaUploadIntegrationVersionRequest(body: Schema.GoogleCloudIntegrationsV1alphaUploadIntegrationVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaUploadIntegrationVersionResponse])
						}
						object upload {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions:upload")).addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudIntegrationsV1alphaIntegrationVersion(body: Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
						}
						class takeoverEditLock(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudIntegrationsV1alphaTakeoverEditLockRequest(body: Schema.GoogleCloudIntegrationsV1alphaTakeoverEditLockRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTakeoverEditLockResponse])
						}
						object takeoverEditLock {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, integrationVersion: String)(using auth: AuthToken, ec: ExecutionContext): takeoverEditLock = new takeoverEditLock(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}:takeoverEditLock")).addQueryStringParameters("integrationVersion" -> integrationVersion.toString))
						}
					}
					object executions {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListExecutionsResponse]) {
							/** Optional. View mask for the response data. If set, only the field specified will be returned as part of the result. If not set, all fields in Execution will be filled and returned. Supported fields: trigger_id execution_method create_time update_time execution_details execution_details.state execution_details.execution_snapshots execution_details.attempt_stats execution_details.event_execution_snapshots_size request_parameters cloud_logging_details snapshot_number replay_info<br>Format: google-fieldmask */
							def withReadMask(readMask: String) = new list(req.addQueryStringParameters("readMask" -> readMask.toString))
							/** Optional. Standard filter field, we support filtering on following fields: workflow_name: the name of the integration. CreateTimestamp: the execution created time. event_execution_state: the state of the executions. execution_id: the id of the execution. trigger_id: the id of the trigger. parameter_type: the type of the parameters involved in the execution. All fields support for EQUALS, in additional: CreateTimestamp support for LESS_THAN, GREATER_THAN ParameterType support for HAS For example: "parameter_type" HAS \"string\" Also supports operators like AND, OR, NOT For example, trigger_id=\"id1\" AND workflow_name=\"testWorkflow\" */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional user-provided custom filter. */
							def withFilterParamsCustomFilter(filterParamsCustomFilter: String) = new list(req.addQueryStringParameters("filterParams.customFilter" -> filterParamsCustomFilter.toString))
							/** Optional. The token returned in the previous response. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. If true, the service will use the most recent acl information to list event execution infos and renew the acl cache. Note that fetching the most recent acl is synchronous, so it will increase RPC call latency. */
							def withRefreshAcl(refreshAcl: Boolean) = new list(req.addQueryStringParameters("refreshAcl" -> refreshAcl.toString))
							/** Optional. If true, the service will truncate the params to only keep the first 1000 characters of string params and empty the executions in order to make response smaller. Only works for UI and when the params fields are not filtered out. */
							def withTruncateParams(truncateParams: Boolean) = new list(req.addQueryStringParameters("truncateParams" -> truncateParams.toString))
							/** Optional. The size of entries in the response.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. If true, the service will provide execution info with snapshot metadata only i.e. without event parameters. */
							def withSnapshotMetadataWithoutParams(snapshotMetadataWithoutParams: Boolean) = new list(req.addQueryStringParameters("snapshotMetadataWithoutParams" -> snapshotMetadataWithoutParams.toString))
							/** Optional. The results would be returned in order you specified here. Currently supporting "create_time". */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListExecutionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, filterParamsParameterKey: String, filterParamsExecutionId: String, filterParamsStartTime: String, parent: String, filterParamsParameterType: String, filterParamsParameterPairKey: String, filterParamsWorkflowName: String, filterParamsParameterPairValue: String, filterParamsEndTime: String, filterParamsTaskStatuses: String, filterParamsParameterValue: String, filterParamsEventStatuses: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/executions")).addQueryStringParameters("filterParams.parameterKey" -> filterParamsParameterKey.toString, "filterParams.executionId" -> filterParamsExecutionId.toString, "filterParams.startTime" -> filterParamsStartTime.toString, "parent" -> parent.toString, "filterParams.parameterType" -> filterParamsParameterType.toString, "filterParams.parameterPairKey" -> filterParamsParameterPairKey.toString, "filterParams.workflowName" -> filterParamsWorkflowName.toString, "filterParams.parameterPairValue" -> filterParamsParameterPairValue.toString, "filterParams.endTime" -> filterParamsEndTime.toString, "filterParams.taskStatuses" -> filterParamsTaskStatuses.toString, "filterParams.parameterValue" -> filterParamsParameterValue.toString, "filterParams.eventStatuses" -> filterParamsEventStatuses.toString))
							given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListExecutionsResponse]] = (fun: list) => fun.apply()
						}
						class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadExecutionResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadExecutionResponse])
						}
						object download {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/executions/${executionsId}:download")).addQueryStringParameters("name" -> name.toString))
							given Conversion[download, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadExecutionResponse]] = (fun: download) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaExecution]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaExecution])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/executions/${executionsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaExecution]] = (fun: get) => fun.apply()
						}
						object suspensions {
							class lift(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withGoogleCloudIntegrationsV1alphaLiftSuspensionRequest(body: Schema.GoogleCloudIntegrationsV1alphaLiftSuspensionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaLiftSuspensionResponse])
							}
							object lift {
								def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, suspensionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): lift = new lift(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/executions/${executionsId}/suspensions/${suspensionsId}:lift")).addQueryStringParameters("name" -> name.toString))
							}
							class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListSuspensionsResponse]) {
								def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListSuspensionsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, pageToken: String, orderBy: String, filter: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/executions/${executionsId}/suspensions")).addQueryStringParameters("pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
								given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListSuspensionsResponse]] = (fun: list) => fun.apply()
							}
							class resolve(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withGoogleCloudIntegrationsV1alphaResolveSuspensionRequest(body: Schema.GoogleCloudIntegrationsV1alphaResolveSuspensionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaResolveSuspensionResponse])
							}
							object resolve {
								def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, suspensionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): resolve = new resolve(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/executions/${executionsId}/suspensions/${suspensionsId}:resolve")).addQueryStringParameters("name" -> name.toString))
							}
						}
					}
				}
				object cloudFunctions {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaCreateCloudFunctionRequest(body: Schema.GoogleCloudIntegrationsV1alphaCreateCloudFunctionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCreateCloudFunctionResponse])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/cloudFunctions")).addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object certificates {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaCertificate(body: Schema.GoogleCloudIntegrationsV1alphaCertificate) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCertificate])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/certificates")).addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, certificatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/certificates/${certificatesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaCertificate]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCertificate])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, certificatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/certificates/${certificatesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaCertificate]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaCertificate(body: Schema.GoogleCloudIntegrationsV1alphaCertificate) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCertificate])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, certificatesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/certificates/${certificatesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListCertificatesResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListCertificatesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, readMask: String, pageToken: String, pageSize: Int, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/certificates")).addQueryStringParameters("readMask" -> readMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListCertificatesResponse]] = (fun: list) => fun.apply()
					}
				}
				object sfdcInstances {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaSfdcInstance(body: Schema.GoogleCloudIntegrationsV1alphaSfdcInstance) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances")).addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaSfdcInstance(body: Schema.GoogleCloudIntegrationsV1alphaSfdcInstance) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcInstancesResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListSfdcInstancesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, readMask: String, filter: String, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances")).addQueryStringParameters("readMask" -> readMask.toString, "filter" -> filter.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcInstancesResponse]] = (fun: list) => fun.apply()
					}
					object sfdcChannels {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudIntegrationsV1alphaSfdcChannel(body: Schema.GoogleCloudIntegrationsV1alphaSfdcChannel) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels")).addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, sfdcChannelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels/${sfdcChannelsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, sfdcChannelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels/${sfdcChannelsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudIntegrationsV1alphaSfdcChannel(body: Schema.GoogleCloudIntegrationsV1alphaSfdcChannel) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, sfdcChannelsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels/${sfdcChannelsId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcChannelsResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListSfdcChannelsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, pageSize: Int, filter: String, parent: String, readMask: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels")).addQueryStringParameters("pageSize" -> pageSize.toString, "filter" -> filter.toString, "parent" -> parent.toString, "readMask" -> readMask.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcChannelsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object authConfigs {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaAuthConfig(body: Schema.GoogleCloudIntegrationsV1alphaAuthConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaAuthConfig])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, clientCertificatePassphrase: String, clientCertificateSslCertificate: String, parent: String, clientCertificateEncryptedPrivateKey: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/authConfigs")).addQueryStringParameters("clientCertificate.passphrase" -> clientCertificatePassphrase.toString, "clientCertificate.sslCertificate" -> clientCertificateSslCertificate.toString, "parent" -> parent.toString, "clientCertificate.encryptedPrivateKey" -> clientCertificateEncryptedPrivateKey.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, authConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/authConfigs/${authConfigsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaAuthConfig]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaAuthConfig])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, authConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/authConfigs/${authConfigsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaAuthConfig]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaAuthConfig(body: Schema.GoogleCloudIntegrationsV1alphaAuthConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaAuthConfig])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, authConfigsId :PlayApi, clientCertificatePassphrase: String, clientCertificateEncryptedPrivateKey: String, updateMask: String, clientCertificateSslCertificate: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/authConfigs/${authConfigsId}")).addQueryStringParameters("clientCertificate.passphrase" -> clientCertificatePassphrase.toString, "clientCertificate.encryptedPrivateKey" -> clientCertificateEncryptedPrivateKey.toString, "updateMask" -> updateMask.toString, "clientCertificate.sslCertificate" -> clientCertificateSslCertificate.toString, "name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListAuthConfigsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListAuthConfigsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/authConfigs")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "readMask" -> readMask.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListAuthConfigsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object integrations {
				class execute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaExecuteIntegrationsRequest(body: Schema.GoogleCloudIntegrationsV1alphaExecuteIntegrationsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaExecuteIntegrationsResponse])
				}
				object execute {
					def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): execute = new execute(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}:execute")).addQueryStringParameters("name" -> name.toString))
				}
				class executeEvent(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaExecuteEventResponse]) {
					def apply() = req.execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaExecuteEventResponse])
				}
				object executeEvent {
					def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, name: String, triggerId: String)(using auth: AuthToken, ec: ExecutionContext): executeEvent = new executeEvent(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}:executeEvent")).addQueryStringParameters("name" -> name.toString, "triggerId" -> triggerId.toString))
					given Conversion[executeEvent, Future[Schema.GoogleCloudIntegrationsV1alphaExecuteEventResponse]] = (fun: executeEvent) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListIntegrationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, parent: String, filter: String, orderBy: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations")).addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationsResponse]] = (fun: list) => fun.apply()
				}
				class test(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaTestIntegrationsRequest(body: Schema.GoogleCloudIntegrationsV1alphaTestIntegrationsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTestIntegrationsResponse])
				}
				object test {
					def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): test = new test(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}:test")).addQueryStringParameters("name" -> name.toString))
				}
				class schedule(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaScheduleIntegrationsRequest(body: Schema.GoogleCloudIntegrationsV1alphaScheduleIntegrationsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaScheduleIntegrationsResponse])
				}
				object schedule {
					def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): schedule = new schedule(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}:schedule")).addQueryStringParameters("name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				object versions {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Optional. Indicates if sample workflow should be created. */
						def withCreateSampleIntegrations(createSampleIntegrations: Boolean) = new create(req.addQueryStringParameters("createSampleIntegrations" -> createSampleIntegrations.toString))
						def withGoogleCloudIntegrationsV1alphaIntegrationVersion(body: Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, parent: String, newIntegration: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions")).addQueryStringParameters("parent" -> parent.toString, "newIntegration" -> newIntegration.toString))
					}
					class unpublish(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaUnpublishIntegrationVersionRequest(body: Schema.GoogleCloudIntegrationsV1alphaUnpublishIntegrationVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object unpublish {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): unpublish = new unpublish(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}:unpublish")).addQueryStringParameters("name" -> name.toString))
					}
					class publish(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaPublishIntegrationVersionRequest(body: Schema.GoogleCloudIntegrationsV1alphaPublishIntegrationVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaPublishIntegrationVersionResponse])
					}
					object publish {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): publish = new publish(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}:publish")).addQueryStringParameters("name" -> name.toString))
					}
					class downloadJsonPackage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadJsonPackageResponse]) {
						/** Optional. Integration related file to download like Integration Version, Config variable, testcase etc.<br>Possible values:<br>INTEGRATION_FILE_UNSPECIFIED: Default value.<br>INTEGRATION: Integration file.<br>INTEGRATION_CONFIG_VARIABLES: Integration Config variables. */
						def withFiles(files: String) = new downloadJsonPackage(req.addQueryStringParameters("files" -> files.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadJsonPackageResponse])
					}
					object downloadJsonPackage {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): downloadJsonPackage = new downloadJsonPackage(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}:downloadJsonPackage")).addQueryStringParameters("name" -> name.toString))
						given Conversion[downloadJsonPackage, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadJsonPackageResponse]] = (fun: downloadJsonPackage) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaIntegrationVersion(body: Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadIntegrationVersionResponse]) {
						/** Optional. Integration related file to download like Integration Json, Config variable, testcase etc.<br>Possible values:<br>INTEGRATION_FILE_UNSPECIFIED: Default value.<br>INTEGRATION: Integration file.<br>INTEGRATION_CONFIG_VARIABLES: Integration Config variables. */
						def withFiles(files: String) = new download(req.addQueryStringParameters("files" -> files.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadIntegrationVersionResponse])
					}
					object download {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String, fileFormat: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}:download")).addQueryStringParameters("name" -> name.toString, "fileFormat" -> fileFormat.toString))
						given Conversion[download, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadIntegrationVersionResponse]] = (fun: download) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationVersionsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListIntegrationVersionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, parent: String, orderBy: String, filter: String, pageSize: Int, fieldMask: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions")).addQueryStringParameters("parent" -> parent.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationVersionsResponse]] = (fun: list) => fun.apply()
					}
					class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaUploadIntegrationVersionRequest(body: Schema.GoogleCloudIntegrationsV1alphaUploadIntegrationVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaUploadIntegrationVersionResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions:upload")).addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion]] = (fun: get) => fun.apply()
					}
					object testCases {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudIntegrationsV1alphaTestCase(body: Schema.GoogleCloudIntegrationsV1alphaTestCase) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTestCase])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCaseId: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases")).addQueryStringParameters("testCaseId" -> testCaseId.toString, "parent" -> parent.toString))
						}
						class executeTest(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudIntegrationsV1alphaExecuteTestCaseRequest(body: Schema.GoogleCloudIntegrationsV1alphaExecuteTestCaseRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaExecuteTestCaseResponse])
						}
						object executeTest {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, testCaseName: String)(using auth: AuthToken, ec: ExecutionContext): executeTest = new executeTest(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}:executeTest")).addQueryStringParameters("testCaseName" -> testCaseName.toString))
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaTestCase]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTestCase])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaTestCase]] = (fun: get) => fun.apply()
						}
						class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadTestCaseResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadTestCaseResponse])
						}
						object download {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, fileFormat: String, name: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}:download")).addQueryStringParameters("fileFormat" -> fileFormat.toString, "name" -> name.toString))
							given Conversion[download, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadTestCaseResponse]] = (fun: download) => fun.apply()
						}
						class listExecutions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListTestCaseExecutionsResponse]) {
							/** Optional. The token returned in the previous response. */
							def withPageToken(pageToken: String) = new listExecutions(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. The results would be returned in order you specified here. Currently supporting "last_modified_time" and "create_time". */
							def withOrderBy(orderBy: String) = new listExecutions(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Optional. View mask for the response data. If set, only the field specified will be returned as part of the result. If not set, all fields in event execution info will be filled and returned.<br>Format: google-fieldmask */
							def withReadMask(readMask: String) = new listExecutions(req.addQueryStringParameters("readMask" -> readMask.toString))
							/** Optional. Standard filter field, we support filtering on following fields: test_case_id: the ID of the test case. CreateTimestamp: the execution created time. event_execution_state: the state of the executions. execution_id: the id of the execution. trigger_id: the id of the trigger. parameter_type: the type of the parameters involved in the execution. All fields support for EQUALS, in additional: CreateTimestamp support for LESS_THAN, GREATER_THAN ParameterType support for HAS For example: "parameter_type" HAS \"string\" Also supports operators like AND, OR, NOT For example, trigger_id=\"id1\" AND test_case_id=\"testCaseId\" */
							def withFilter(filter: String) = new listExecutions(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. The size of entries in the response.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new listExecutions(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. If true, the service will truncate the params to only keep the first 1000 characters of string params and empty the executions in order to make response smaller. Only works for UI and when the params fields are not filtered out. */
							def withTruncateParams(truncateParams: Boolean) = new listExecutions(req.addQueryStringParameters("truncateParams" -> truncateParams.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListTestCaseExecutionsResponse])
						}
						object listExecutions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): listExecutions = new listExecutions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}:executions")).addQueryStringParameters("parent" -> parent.toString))
							given Conversion[listExecutions, Future[Schema.GoogleCloudIntegrationsV1alphaListTestCaseExecutionsResponse]] = (fun: listExecutions) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListTestCasesResponse]) {
							/** Optional. A page token, received from a previous `ListTestCases` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListTestCases` must match the call that provided the page token. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. The maximum number of test cases to return. The service may return fewer than this value. If unspecified, at most 100 test cases will be returned.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. Standard filter field. Filtering as supported in https://developers.google.com/authorized-buyers/apis/guides/list-filters. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. The results would be returned in order specified here. Currently supported sort keys are: Descending sort order for "last_modified_time", "created_time". Ascending sort order for "name". */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Optional. The mask which specifies fields that need to be returned in the TestCases's response.<br>Format: google-fieldmask */
							def withReadMask(readMask: String) = new list(req.addQueryStringParameters("readMask" -> readMask.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListTestCasesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases")).addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListTestCasesResponse]] = (fun: list) => fun.apply()
						}
						class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudIntegrationsV1alphaUploadTestCaseRequest(body: Schema.GoogleCloudIntegrationsV1alphaUploadTestCaseRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaUploadTestCaseResponse])
						}
						object upload {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases:upload")).addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							/** Optional. Field mask specifying the fields in the above integration that have been modified and need to be updated.<br>Format: google-fieldmask */
							def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
							def withGoogleCloudIntegrationsV1alphaTestCase(body: Schema.GoogleCloudIntegrationsV1alphaTestCase) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTestCase])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}")).addQueryStringParameters("name" -> name.toString))
						}
						class takeoverEditLock(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudIntegrationsV1alphaTakeoverTestCaseEditLockRequest(body: Schema.GoogleCloudIntegrationsV1alphaTakeoverTestCaseEditLockRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTestCase])
						}
						object takeoverEditLock {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): takeoverEditLock = new takeoverEditLock(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}:takeoverEditLock")).addQueryStringParameters("name" -> name.toString))
						}
					}
				}
				object executions {
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaCancelExecutionRequest(body: Schema.GoogleCloudIntegrationsV1alphaCancelExecutionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCancelExecutionResponse])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					}
					class replay(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaReplayExecutionRequest(body: Schema.GoogleCloudIntegrationsV1alphaReplayExecutionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaReplayExecutionResponse])
					}
					object replay {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): replay = new replay(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}:replay")).addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaExecution]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaExecution])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaExecution]] = (fun: get) => fun.apply()
					}
					class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadExecutionResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadExecutionResponse])
					}
					object download {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}:download")).addQueryStringParameters("name" -> name.toString))
						given Conversion[download, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadExecutionResponse]] = (fun: download) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListExecutionsResponse]) {
						/** Optional. If true, the service will use the most recent acl information to list event execution infos and renew the acl cache. Note that fetching the most recent acl is synchronous, so it will increase RPC call latency. */
						def withRefreshAcl(refreshAcl: Boolean) = new list(req.addQueryStringParameters("refreshAcl" -> refreshAcl.toString))
						/** Optional. The results would be returned in order you specified here. Currently supporting "create_time". */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Optional. The size of entries in the response.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional user-provided custom filter. */
						def withFilterParamsCustomFilter(filterParamsCustomFilter: String) = new list(req.addQueryStringParameters("filterParams.customFilter" -> filterParamsCustomFilter.toString))
						/** Optional. The token returned in the previous response. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Standard filter field, we support filtering on following fields: workflow_name: the name of the integration. CreateTimestamp: the execution created time. event_execution_state: the state of the executions. execution_id: the id of the execution. trigger_id: the id of the trigger. parameter_type: the type of the parameters involved in the execution. All fields support for EQUALS, in additional: CreateTimestamp support for LESS_THAN, GREATER_THAN ParameterType support for HAS For example: "parameter_type" HAS \"string\" Also supports operators like AND, OR, NOT For example, trigger_id=\"id1\" AND workflow_name=\"testWorkflow\" */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. View mask for the response data. If set, only the field specified will be returned as part of the result. If not set, all fields in Execution will be filled and returned. Supported fields: trigger_id execution_method create_time update_time execution_details execution_details.state execution_details.execution_snapshots execution_details.attempt_stats execution_details.event_execution_snapshots_size request_parameters cloud_logging_details snapshot_number replay_info<br>Format: google-fieldmask */
						def withReadMask(readMask: String) = new list(req.addQueryStringParameters("readMask" -> readMask.toString))
						/** Optional. If true, the service will truncate the params to only keep the first 1000 characters of string params and empty the executions in order to make response smaller. Only works for UI and when the params fields are not filtered out. */
						def withTruncateParams(truncateParams: Boolean) = new list(req.addQueryStringParameters("truncateParams" -> truncateParams.toString))
						/** Optional. If true, the service will provide execution info with snapshot metadata only i.e. without event parameters. */
						def withSnapshotMetadataWithoutParams(snapshotMetadataWithoutParams: Boolean) = new list(req.addQueryStringParameters("snapshotMetadataWithoutParams" -> snapshotMetadataWithoutParams.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListExecutionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, filterParamsParameterPairValue: String, filterParamsExecutionId: String, filterParamsTaskStatuses: String, parent: String, filterParamsParameterPairKey: String, filterParamsParameterKey: String, filterParamsEventStatuses: String, filterParamsWorkflowName: String, filterParamsParameterValue: String, filterParamsEndTime: String, filterParamsStartTime: String, filterParamsParameterType: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions")).addQueryStringParameters("filterParams.parameterPairValue" -> filterParamsParameterPairValue.toString, "filterParams.executionId" -> filterParamsExecutionId.toString, "filterParams.taskStatuses" -> filterParamsTaskStatuses.toString, "parent" -> parent.toString, "filterParams.parameterPairKey" -> filterParamsParameterPairKey.toString, "filterParams.parameterKey" -> filterParamsParameterKey.toString, "filterParams.eventStatuses" -> filterParamsEventStatuses.toString, "filterParams.workflowName" -> filterParamsWorkflowName.toString, "filterParams.parameterValue" -> filterParamsParameterValue.toString, "filterParams.endTime" -> filterParamsEndTime.toString, "filterParams.startTime" -> filterParamsStartTime.toString, "filterParams.parameterType" -> filterParamsParameterType.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListExecutionsResponse]] = (fun: list) => fun.apply()
					}
					object suspensions {
						class resolve(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudIntegrationsV1alphaResolveSuspensionRequest(body: Schema.GoogleCloudIntegrationsV1alphaResolveSuspensionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaResolveSuspensionResponse])
						}
						object resolve {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, suspensionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): resolve = new resolve(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}/suspensions/${suspensionsId}:resolve")).addQueryStringParameters("name" -> name.toString))
						}
						class lift(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudIntegrationsV1alphaLiftSuspensionRequest(body: Schema.GoogleCloudIntegrationsV1alphaLiftSuspensionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaLiftSuspensionResponse])
						}
						object lift {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, suspensionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): lift = new lift(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}/suspensions/${suspensionsId}:lift")).addQueryStringParameters("name" -> name.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListSuspensionsResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListSuspensionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, filter: String, pageToken: String, orderBy: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}/suspensions")).addQueryStringParameters("filter" -> filter.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
							given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListSuspensionsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object cloudFunctions {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaCreateCloudFunctionRequest(body: Schema.GoogleCloudIntegrationsV1alphaCreateCloudFunctionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCreateCloudFunctionResponse])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/cloudFunctions")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object certificates {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaCertificate(body: Schema.GoogleCloudIntegrationsV1alphaCertificate) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCertificate])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificates")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, certificatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificates/${certificatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaCertificate]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCertificate])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, certificatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificates/${certificatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaCertificate]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaCertificate(body: Schema.GoogleCloudIntegrationsV1alphaCertificate) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCertificate])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, certificatesId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificates/${certificatesId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListCertificatesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListCertificatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, readMask: String, filter: String, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificates")).addQueryStringParameters("pageSize" -> pageSize.toString, "readMask" -> readMask.toString, "filter" -> filter.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListCertificatesResponse]] = (fun: list) => fun.apply()
				}
			}
			object sfdcInstances {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaSfdcInstance(body: Schema.GoogleCloudIntegrationsV1alphaSfdcInstance) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaSfdcInstance(body: Schema.GoogleCloudIntegrationsV1alphaSfdcInstance) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcInstancesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListSfdcInstancesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, filter: String, parent: String, pageSize: Int, readMask: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances")).addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "readMask" -> readMask.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcInstancesResponse]] = (fun: list) => fun.apply()
				}
				object sfdcChannels {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaSfdcChannel(body: Schema.GoogleCloudIntegrationsV1alphaSfdcChannel) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels")).addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, sfdcChannelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels/${sfdcChannelsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, sfdcChannelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels/${sfdcChannelsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudIntegrationsV1alphaSfdcChannel(body: Schema.GoogleCloudIntegrationsV1alphaSfdcChannel) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, sfdcChannelsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels/${sfdcChannelsId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcChannelsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListSfdcChannelsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, pageToken: String, pageSize: Int, parent: String, filter: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString, "readMask" -> readMask.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcChannelsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object authConfigs {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaAuthConfig(body: Schema.GoogleCloudIntegrationsV1alphaAuthConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaAuthConfig])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clientCertificateEncryptedPrivateKey: String, clientCertificatePassphrase: String, parent: String, clientCertificateSslCertificate: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/authConfigs")).addQueryStringParameters("clientCertificate.encryptedPrivateKey" -> clientCertificateEncryptedPrivateKey.toString, "clientCertificate.passphrase" -> clientCertificatePassphrase.toString, "parent" -> parent.toString, "clientCertificate.sslCertificate" -> clientCertificateSslCertificate.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, authConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/authConfigs/${authConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaAuthConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaAuthConfig])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, authConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/authConfigs/${authConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaAuthConfig]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaAuthConfig(body: Schema.GoogleCloudIntegrationsV1alphaAuthConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaAuthConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, authConfigsId :PlayApi, updateMask: String, clientCertificateSslCertificate: String, clientCertificateEncryptedPrivateKey: String, name: String, clientCertificatePassphrase: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/authConfigs/${authConfigsId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "clientCertificate.sslCertificate" -> clientCertificateSslCertificate.toString, "clientCertificate.encryptedPrivateKey" -> clientCertificateEncryptedPrivateKey.toString, "name" -> name.toString, "clientCertificate.passphrase" -> clientCertificatePassphrase.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListAuthConfigsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListAuthConfigsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageToken: String, parent: String, filter: String, pageSize: Int, readMask: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/authConfigs")).addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "readMask" -> readMask.toString))
					given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListAuthConfigsResponse]] = (fun: list) => fun.apply()
				}
			}
			object templates {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaTemplate(body: Schema.GoogleCloudIntegrationsV1alphaTemplate) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTemplate])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates")).addQueryStringParameters("parent" -> parent.toString))
				}
				class unshare(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaUnshareTemplateRequest(body: Schema.GoogleCloudIntegrationsV1alphaUnshareTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object unshare {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): unshare = new unshare(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}:unshare")).addQueryStringParameters("name" -> name.toString))
				}
				class share(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaShareTemplateRequest(body: Schema.GoogleCloudIntegrationsV1alphaShareTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object share {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): share = new share(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}:share")).addQueryStringParameters("name" -> name.toString))
				}
				class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaImportTemplateRequest(body: Schema.GoogleCloudIntegrationsV1alphaImportTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaImportTemplateResponse])
				}
				object `import` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}:import")).addQueryStringParameters("name" -> name.toString))
				}
				class use(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaUseTemplateRequest(body: Schema.GoogleCloudIntegrationsV1alphaUseTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaUseTemplateResponse])
				}
				object use {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): use = new use(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}:use")).addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaTemplate]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTemplate])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaTemplate]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaTemplate(body: Schema.GoogleCloudIntegrationsV1alphaTemplate) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTemplate])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadTemplateResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadTemplateResponse])
				}
				object download {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, fileFormat: String, name: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}:download")).addQueryStringParameters("fileFormat" -> fileFormat.toString, "name" -> name.toString))
					given Conversion[download, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadTemplateResponse]] = (fun: download) => fun.apply()
				}
				class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaUploadTemplateRequest(body: Schema.GoogleCloudIntegrationsV1alphaUploadTemplateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaUploadTemplateResponse])
				}
				object upload {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates:upload")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListTemplatesResponse]) {
					/** Optional. The size of the response entries. If unspecified, defaults to 100. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The results would be returned in the order you specified here. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Optional. The token returned in the previous response. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The mask which specifies fields that need to be returned in the template's response.<br>Format: google-fieldmask */
					def withReadMask(readMask: String) = new list(req.addQueryStringParameters("readMask" -> readMask.toString))
					/** Optional. Standard filter field to filter templates. client_id filter won't be supported and will restrict to templates belonging to the current client only. Return all templates of the current client if the filter is empty. Also supports operators like AND, OR, NOT For example, "status=\"ACTIVE\" */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListTemplatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListTemplatesResponse]] = (fun: list) => fun.apply()
				}
				class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaSearchTemplatesResponse]) {
					/** Optional. The token returned in the previous response. */
					def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The mask which specifies fields that need to be returned in the template's response.<br>Format: google-fieldmask */
					def withReadMask(readMask: String) = new search(req.addQueryStringParameters("readMask" -> readMask.toString))
					/** Optional. The results would be returned in the order you specified here. */
					def withOrderBy(orderBy: String) = new search(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Optional. Standard filter field to filter templates. client_id filter won't be supported and will restrict to templates belonging to the current client only. Return all templates of the current client if the filter is empty. Also supports operators like AND, OR, NOT For example, "status=\"ACTIVE\" */
					def withFilter(filter: String) = new search(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. The size of the response entries. If unspecified, defaults to 100. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSearchTemplatesResponse])
				}
				object search {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates:search")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[search, Future[Schema.GoogleCloudIntegrationsV1alphaSearchTemplatesResponse]] = (fun: search) => fun.apply()
				}
			}
			object connections {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListConnectionsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListConnectionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, orderBy: String, filter: String, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections")).addQueryStringParameters("pageSize" -> pageSize.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListConnectionsResponse]] = (fun: list) => fun.apply()
				}
				class getConnectionSchemaMetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaConnectionSchemaMetadata]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaConnectionSchemaMetadata])
				}
				object getConnectionSchemaMetadata {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getConnectionSchemaMetadata = new getConnectionSchemaMetadata(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/connectionSchemaMetadata")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getConnectionSchemaMetadata, Future[Schema.GoogleCloudIntegrationsV1alphaConnectionSchemaMetadata]] = (fun: getConnectionSchemaMetadata) => fun.apply()
				}
				object runtimeEntitySchemas {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListRuntimeEntitySchemasResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListRuntimeEntitySchemasResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, pageToken: String, filter: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/runtimeEntitySchemas")).addQueryStringParameters("pageToken" -> pageToken.toString, "filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListRuntimeEntitySchemasResponse]] = (fun: list) => fun.apply()
					}
				}
				object runtimeActionSchemas {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListRuntimeActionSchemasResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListRuntimeActionSchemasResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, pageToken: String, parent: String, filter: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/runtimeActionSchemas")).addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListRuntimeActionSchemasResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object appsScriptProjects {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaCreateAppsScriptProjectRequest(body: Schema.GoogleCloudIntegrationsV1alphaCreateAppsScriptProjectRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCreateAppsScriptProjectResponse])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/appsScriptProjects")).addQueryStringParameters("parent" -> parent.toString))
				}
				class link(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudIntegrationsV1alphaLinkAppsScriptProjectRequest(body: Schema.GoogleCloudIntegrationsV1alphaLinkAppsScriptProjectRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaLinkAppsScriptProjectResponse])
				}
				object link {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): link = new link(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/appsScriptProjects:link")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
}
