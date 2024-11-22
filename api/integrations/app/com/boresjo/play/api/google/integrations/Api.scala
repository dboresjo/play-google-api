package com.boresjo.play.api.google.integrations

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

	private val BASE_URL = "https://integrations.googleapis.com/"

	object connectorPlatformRegions {
		/** Enumerates the regions for which Connector Platform is provisioned. */
		class enumerate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaEnumerateConnectorPlatformRegionsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaEnumerateConnectorPlatformRegionsResponse])
		}
		object enumerate {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): enumerate = new enumerate(ws.url(BASE_URL + s"v1/connectorPlatformRegions:enumerate").addQueryStringParameters())
			given Conversion[enumerate, Future[Schema.GoogleCloudIntegrationsV1alphaEnumerateConnectorPlatformRegionsResponse]] = (fun: enumerate) => fun.apply()
		}
	}
	object callback {
		/** Receives the auth code and auth config id to combine that with the client id and secret to retrieve access tokens from the token endpoint. Returns either a success or error message when it's done. */
		class generateToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaGenerateTokenResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaGenerateTokenResponse])
		}
		object generateToken {
			def apply(code: String, product: String, state: String, gcpProjectId: String, redirectUri: String)(using signer: RequestSigner, ec: ExecutionContext): generateToken = new generateToken(ws.url(BASE_URL + s"v1/callback:generateToken").addQueryStringParameters("code" -> code.toString, "product" -> product.toString, "state" -> state.toString, "gcpProjectId" -> gcpProjectId.toString, "redirectUri" -> redirectUri.toString))
			given Conversion[generateToken, Future[Schema.GoogleCloudIntegrationsV1alphaGenerateTokenResponse]] = (fun: generateToken) => fun.apply()
		}
	}
	object projects {
		/** Gets the metadata info for the requested client */
		class getClientmetadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaGetClientMetadataResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaGetClientMetadataResponse])
		}
		object getClientmetadata {
			def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): getClientmetadata = new getClientmetadata(ws.url(BASE_URL + s"v1/projects/${projectsId}/clientmetadata").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[getClientmetadata, Future[Schema.GoogleCloudIntegrationsV1alphaGetClientMetadataResponse]] = (fun: getClientmetadata) => fun.apply()
		}
		object locations {
			/** Gets the client configuration for the given project and location resource name */
			class getClients(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaGetClientResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaGetClientResponse])
			}
			object getClients {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): getClients = new getClients(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clients").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[getClients, Future[Schema.GoogleCloudIntegrationsV1alphaGetClientResponse]] = (fun: getClients) => fun.apply()
			}
			object clients {
				/** Update client from GMEK to CMEK */
				class switch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaSwitchEncryptionRequest(body: Schema.GoogleCloudIntegrationsV1alphaSwitchEncryptionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object switch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): switch = new switch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clients:switch").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Update run-as service account for provisioned client */
				class replace(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaReplaceServiceAccountRequest(body: Schema.GoogleCloudIntegrationsV1alphaReplaceServiceAccountRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object replace {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): replace = new replace(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clients:replace").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Perform the deprovisioning steps to disable a user GCP project to use IP and purge all related data in a wipeout-compliant way. */
				class deprovision(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaDeprovisionClientRequest(body: Schema.GoogleCloudIntegrationsV1alphaDeprovisionClientRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object deprovision {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): deprovision = new deprovision(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clients:deprovision").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Update variable masking for provisioned client */
				class switchVariableMasking(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaSwitchVariableMaskingRequest(body: Schema.GoogleCloudIntegrationsV1alphaSwitchVariableMaskingRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object switchVariableMasking {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): switchVariableMasking = new switchVariableMasking(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clients:switchVariableMasking").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Perform the provisioning steps to enable a user GCP project to use IP. If GCP project already registered on IP end via Apigee Integration, provisioning will fail. */
				class provision(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaProvisionClientRequest(body: Schema.GoogleCloudIntegrationsV1alphaProvisionClientRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object provision {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): provision = new provision(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clients:provision").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object products {
				object integrations {
					/** Executes integrations synchronously by passing the trigger id in the request body. The request is not returned until the requested executions are either fulfilled or experienced an error. If the integration name is not specified (passing `-`), all of the associated integration under the given trigger_id will be executed. Otherwise only the specified integration for the given `trigger_id` is executed. This is helpful for execution the integration from UI. */
					class execute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaExecuteIntegrationsRequest(body: Schema.GoogleCloudIntegrationsV1alphaExecuteIntegrationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaExecuteIntegrationsResponse])
					}
					object execute {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): execute = new execute(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}:execute").addQueryStringParameters("name" -> name.toString))
					}
					/** Schedules an integration for execution by passing the trigger id and the scheduled time in the request body. */
					class schedule(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaScheduleIntegrationsRequest(body: Schema.GoogleCloudIntegrationsV1alphaScheduleIntegrationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaScheduleIntegrationsResponse])
					}
					object schedule {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): schedule = new schedule(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}:schedule").addQueryStringParameters("name" -> name.toString))
					}
					/** Returns the list of all integrations in the specified project. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListIntegrationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String, orderBy: String, pageToken: String, pageSize: Int, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations").addQueryStringParameters("parent" -> parent.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationsResponse]] = (fun: list) => fun.apply()
					}
					/** Execute the integration in draft state */
					class test(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaTestIntegrationsRequest(body: Schema.GoogleCloudIntegrationsV1alphaTestIntegrationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTestIntegrationsResponse])
					}
					object test {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): test = new test(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}:test").addQueryStringParameters("name" -> name.toString))
					}
					object versions {
						/** Create a integration with a draft version in the specified project. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Optional. Indicates if sample workflow should be created. */
							def withCreateSampleIntegrations(createSampleIntegrations: Boolean) = new create(req.addQueryStringParameters("createSampleIntegrations" -> createSampleIntegrations.toString))
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaIntegrationVersion(body: Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, parent: String, newIntegration: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions").addQueryStringParameters("parent" -> parent.toString, "newIntegration" -> newIntegration.toString))
						}
						/** Sets the status of the ACTIVE integration to SNAPSHOT with a new tag "PREVIOUSLY_PUBLISHED" after validating it. The "HEAD" and "PUBLISH_REQUESTED" tags do not change. This RPC throws an exception if the version being snapshot is not ACTIVE. Audit fields added include action, action_by, action_timestamp. */
						class unpublish(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaUnpublishIntegrationVersionRequest(body: Schema.GoogleCloudIntegrationsV1alphaUnpublishIntegrationVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object unpublish {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): unpublish = new unpublish(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}:unpublish").addQueryStringParameters("name" -> name.toString))
						}
						/** This RPC throws an exception if the integration is in ARCHIVED or ACTIVE state. This RPC throws an exception if the version being published is DRAFT, and if the `locked_by` user is not the same as the user performing the Publish. Audit fields updated include last_published_timestamp, last_published_by, last_modified_timestamp, last_modified_by. Any existing lock is on this integration is released. */
						class publish(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaPublishIntegrationVersionRequest(body: Schema.GoogleCloudIntegrationsV1alphaPublishIntegrationVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaPublishIntegrationVersionResponse])
						}
						object publish {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): publish = new publish(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}:publish").addQueryStringParameters("name" -> name.toString))
						}
						/** Get a integration in the specified project. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion]] = (fun: get) => fun.apply()
						}
						/** Downloads an integration. Retrieves the `IntegrationVersion` for a given `integration_id` and returns the response as a string. */
						class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadIntegrationVersionResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Integration related file to download like Integration Json, Config variable, testcase etc.<br>Possible values:<br>INTEGRATION_FILE_UNSPECIFIED: Default value.<br>INTEGRATION: Integration file.<br>INTEGRATION_CONFIG_VARIABLES: Integration Config variables. */
							def withFiles(files: String) = new download(req.addQueryStringParameters("files" -> files.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadIntegrationVersionResponse])
						}
						object download {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String, fileFormat: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}:download").addQueryStringParameters("name" -> name.toString, "fileFormat" -> fileFormat.toString))
							given Conversion[download, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadIntegrationVersionResponse]] = (fun: download) => fun.apply()
						}
						/** Returns the list of all integration versions in the specified project. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationVersionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListIntegrationVersionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, filter: String, parent: String, pageToken: String, orderBy: String, pageSize: Int, fieldMask: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions").addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "fieldMask" -> fieldMask.toString))
							given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationVersionsResponse]] = (fun: list) => fun.apply()
						}
						/** Uploads an integration. The content can be a previously downloaded integration. Performs the same function as CreateDraftIntegrationVersion, but accepts input in a string format, which holds the complete representation of the IntegrationVersion content. */
						class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaUploadIntegrationVersionRequest(body: Schema.GoogleCloudIntegrationsV1alphaUploadIntegrationVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaUploadIntegrationVersionResponse])
						}
						object upload {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions:upload").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Soft-deletes the integration. Changes the status of the integration to ARCHIVED. If the integration being ARCHIVED is tagged as "HEAD", the tag is removed from this snapshot and set to the previous non-ARCHIVED snapshot. The PUBLISH_REQUESTED, DUE_FOR_DELETION tags are removed too. This RPC throws an exception if the version being deleted is DRAFT, and if the `locked_by` user is not the same as the user performing the Delete. Audit fields updated include last_modified_timestamp, last_modified_by. Any existing lock is released when Deleting a integration. Currently, there is no undelete mechanism. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Update a integration with a draft version in the specified project. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaIntegrationVersion(body: Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
						}
						/** Clears the `locked_by` and `locked_at_timestamp`in the DRAFT version of this integration. It then performs the same action as the CreateDraftIntegrationVersion (i.e., copies the DRAFT version of the integration as a SNAPSHOT and then creates a new DRAFT version with the `locked_by` set to the `user_taking_over` and the `locked_at_timestamp` set to the current timestamp). Both the `locked_by` and `user_taking_over` are notified via email about the takeover. This RPC throws an exception if the integration is not in DRAFT status or if the `locked_by` and `locked_at_timestamp` fields are not set.The TakeoverEdit lock is treated the same as an edit of the integration, and hence shares ACLs with edit. Audit fields updated include last_modified_timestamp, last_modified_by. */
						class takeoverEditLock(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaTakeoverEditLockRequest(body: Schema.GoogleCloudIntegrationsV1alphaTakeoverEditLockRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTakeoverEditLockResponse])
						}
						object takeoverEditLock {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, integrationVersion: String)(using signer: RequestSigner, ec: ExecutionContext): takeoverEditLock = new takeoverEditLock(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/versions/${versionsId}:takeoverEditLock").addQueryStringParameters("integrationVersion" -> integrationVersion.toString))
						}
					}
					object executions {
						/** Lists the results of all the integration executions. The response includes the same information as the [execution log](https://cloud.google.com/application-integration/docs/viewing-logs) in the Integration UI. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListExecutionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
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
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListExecutionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, filterParamsParameterKey: String, filterParamsExecutionId: String, filterParamsStartTime: String, parent: String, filterParamsParameterType: String, filterParamsParameterPairKey: String, filterParamsWorkflowName: String, filterParamsParameterPairValue: String, filterParamsEndTime: String, filterParamsTaskStatuses: String, filterParamsParameterValue: String, filterParamsEventStatuses: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/executions").addQueryStringParameters("filterParams.parameterKey" -> filterParamsParameterKey.toString, "filterParams.executionId" -> filterParamsExecutionId.toString, "filterParams.startTime" -> filterParamsStartTime.toString, "parent" -> parent.toString, "filterParams.parameterType" -> filterParamsParameterType.toString, "filterParams.parameterPairKey" -> filterParamsParameterPairKey.toString, "filterParams.workflowName" -> filterParamsWorkflowName.toString, "filterParams.parameterPairValue" -> filterParamsParameterPairValue.toString, "filterParams.endTime" -> filterParamsEndTime.toString, "filterParams.taskStatuses" -> filterParamsTaskStatuses.toString, "filterParams.parameterValue" -> filterParamsParameterValue.toString, "filterParams.eventStatuses" -> filterParamsEventStatuses.toString))
							given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListExecutionsResponse]] = (fun: list) => fun.apply()
						}
						/** Download the execution. */
						class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadExecutionResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadExecutionResponse])
						}
						object download {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/executions/${executionsId}:download").addQueryStringParameters("name" -> name.toString))
							given Conversion[download, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadExecutionResponse]] = (fun: download) => fun.apply()
						}
						/** Get an execution in the specified project. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaExecution]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaExecution])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/executions/${executionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaExecution]] = (fun: get) => fun.apply()
						}
						object suspensions {
							/** &#42; Lifts suspension for the Suspension task. Fetch corresponding suspension with provided suspension Id, resolve suspension, and set up suspension result for the Suspension Task. */
							class lift(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withGoogleCloudIntegrationsV1alphaLiftSuspensionRequest(body: Schema.GoogleCloudIntegrationsV1alphaLiftSuspensionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaLiftSuspensionResponse])
							}
							object lift {
								def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, suspensionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): lift = new lift(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/executions/${executionsId}/suspensions/${suspensionsId}:lift").addQueryStringParameters("name" -> name.toString))
							}
							/** &#42; Lists suspensions associated with a specific execution. Only those with permissions to resolve the relevant suspensions will be able to view them. */
							class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListSuspensionsResponse]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListSuspensionsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, pageToken: String, orderBy: String, filter: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/executions/${executionsId}/suspensions").addQueryStringParameters("pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
								given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListSuspensionsResponse]] = (fun: list) => fun.apply()
							}
							/** &#42; Resolves (lifts/rejects) any number of suspensions. If the integration is already running, only the status of the suspension is updated. Otherwise, the suspended integration will begin execution again. */
							class resolve(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withGoogleCloudIntegrationsV1alphaResolveSuspensionRequest(body: Schema.GoogleCloudIntegrationsV1alphaResolveSuspensionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaResolveSuspensionResponse])
							}
							object resolve {
								def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, suspensionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): resolve = new resolve(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/integrations/${integrationsId}/executions/${executionsId}/suspensions/${suspensionsId}:resolve").addQueryStringParameters("name" -> name.toString))
							}
						}
					}
				}
				object cloudFunctions {
					/** Creates a cloud function project. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaCreateCloudFunctionRequest(body: Schema.GoogleCloudIntegrationsV1alphaCreateCloudFunctionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCreateCloudFunctionResponse])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/cloudFunctions").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object certificates {
					/** Creates a new certificate. The certificate will be registered to the trawler service and will be encrypted using cloud KMS and stored in Spanner Returns the certificate. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaCertificate(body: Schema.GoogleCloudIntegrationsV1alphaCertificate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCertificate])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/certificates").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Delete a certificate */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, certificatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/certificates/${certificatesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Get a certificates in the specified project. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaCertificate]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCertificate])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, certificatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/certificates/${certificatesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaCertificate]] = (fun: get) => fun.apply()
					}
					/** Updates the certificate by id. If new certificate file is updated, it will register with the trawler service, re-encrypt with cloud KMS and update the Spanner record. Other fields will directly update the Spanner record. Returns the Certificate. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaCertificate(body: Schema.GoogleCloudIntegrationsV1alphaCertificate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCertificate])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, certificatesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/certificates/${certificatesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** List all the certificates that match the filter. Restrict to certificate of current client only. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListCertificatesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListCertificatesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, readMask: String, pageToken: String, pageSize: Int, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/certificates").addQueryStringParameters("readMask" -> readMask.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListCertificatesResponse]] = (fun: list) => fun.apply()
					}
				}
				object sfdcInstances {
					/** Creates an sfdc instance record. Store the sfdc instance in Spanner. Returns the sfdc instance. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaSfdcInstance(body: Schema.GoogleCloudIntegrationsV1alphaSfdcInstance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes an sfdc instance. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Gets an sfdc instance. If the instance doesn't exist, Code.NOT_FOUND exception will be thrown. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance]] = (fun: get) => fun.apply()
					}
					/** Updates an sfdc instance. Updates the sfdc instance in spanner. Returns the sfdc instance. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaSfdcInstance(body: Schema.GoogleCloudIntegrationsV1alphaSfdcInstance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists all sfdc instances that match the filter. Restrict to sfdc instances belonging to the current client only. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcInstancesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListSfdcInstancesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, readMask: String, filter: String, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances").addQueryStringParameters("readMask" -> readMask.toString, "filter" -> filter.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcInstancesResponse]] = (fun: list) => fun.apply()
					}
					object sfdcChannels {
						/** Creates an sfdc channel record. Store the sfdc channel in Spanner. Returns the sfdc channel. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaSfdcChannel(body: Schema.GoogleCloudIntegrationsV1alphaSfdcChannel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes an sfdc channel. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, sfdcChannelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels/${sfdcChannelsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Gets an sfdc channel. If the channel doesn't exist, Code.NOT_FOUND exception will be thrown. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, sfdcChannelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels/${sfdcChannelsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel]] = (fun: get) => fun.apply()
						}
						/** Updates an sfdc channel. Updates the sfdc channel in spanner. Returns the sfdc channel. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaSfdcChannel(body: Schema.GoogleCloudIntegrationsV1alphaSfdcChannel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, sfdcChannelsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels/${sfdcChannelsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
						}
						/** Lists all sfdc channels that match the filter. Restrict to sfdc channels belonging to the current client only. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcChannelsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListSfdcChannelsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, sfdcInstancesId :PlayApi, pageSize: Int, filter: String, parent: String, readMask: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels").addQueryStringParameters("pageSize" -> pageSize.toString, "filter" -> filter.toString, "parent" -> parent.toString, "readMask" -> readMask.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcChannelsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object authConfigs {
					/** Creates an auth config record. Fetch corresponding credentials for specific auth types, e.g. access token for OAuth 2.0, JWT token for JWT. Encrypt the auth config with Cloud KMS and store the encrypted credentials in Spanner. Returns the encrypted auth config. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaAuthConfig(body: Schema.GoogleCloudIntegrationsV1alphaAuthConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaAuthConfig])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, clientCertificatePassphrase: String, clientCertificateSslCertificate: String, parent: String, clientCertificateEncryptedPrivateKey: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/authConfigs").addQueryStringParameters("clientCertificate.passphrase" -> clientCertificatePassphrase.toString, "clientCertificate.sslCertificate" -> clientCertificateSslCertificate.toString, "parent" -> parent.toString, "clientCertificate.encryptedPrivateKey" -> clientCertificateEncryptedPrivateKey.toString))
					}
					/** Deletes an auth config. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, authConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/authConfigs/${authConfigsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Gets a complete auth config. If the auth config doesn't exist, Code.NOT_FOUND exception will be thrown. Returns the decrypted auth config. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaAuthConfig]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaAuthConfig])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, authConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/authConfigs/${authConfigsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaAuthConfig]] = (fun: get) => fun.apply()
					}
					/** Updates an auth config. If credential is updated, fetch the encrypted auth config from Spanner, decrypt with Cloud KMS key, update the credential fields, re-encrypt with Cloud KMS key and update the Spanner record. For other fields, directly update the Spanner record. Returns the encrypted auth config. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaAuthConfig(body: Schema.GoogleCloudIntegrationsV1alphaAuthConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaAuthConfig])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, authConfigsId :PlayApi, clientCertificatePassphrase: String, clientCertificateEncryptedPrivateKey: String, updateMask: String, clientCertificateSslCertificate: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/authConfigs/${authConfigsId}").addQueryStringParameters("clientCertificate.passphrase" -> clientCertificatePassphrase.toString, "clientCertificate.encryptedPrivateKey" -> clientCertificateEncryptedPrivateKey.toString, "updateMask" -> updateMask.toString, "clientCertificate.sslCertificate" -> clientCertificateSslCertificate.toString, "name" -> name.toString))
					}
					/** Lists all auth configs that match the filter. Restrict to auth configs belong to the current client only. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListAuthConfigsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListAuthConfigsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/authConfigs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "readMask" -> readMask.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListAuthConfigsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object integrations {
				/** Executes integrations synchronously by passing the trigger id in the request body. The request is not returned until the requested executions are either fulfilled or experienced an error. If the integration name is not specified (passing `-`), all of the associated integration under the given trigger_id will be executed. Otherwise only the specified integration for the given `trigger_id` is executed. This is helpful for execution the integration from UI. */
				class execute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaExecuteIntegrationsRequest(body: Schema.GoogleCloudIntegrationsV1alphaExecuteIntegrationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaExecuteIntegrationsResponse])
				}
				object execute {
					def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): execute = new execute(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}:execute").addQueryStringParameters("name" -> name.toString))
				}
				/** Executes an integration on receiving events from Integration Connector triggers, Eventarc or CPS Trigger. Input data to integration is received in body in json format */
				class executeEvent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaExecuteEventResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaExecuteEventResponse])
				}
				object executeEvent {
					def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, name: String, triggerId: String)(using signer: RequestSigner, ec: ExecutionContext): executeEvent = new executeEvent(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}:executeEvent").addQueryStringParameters("name" -> name.toString, "triggerId" -> triggerId.toString))
					given Conversion[executeEvent, Future[Schema.GoogleCloudIntegrationsV1alphaExecuteEventResponse]] = (fun: executeEvent) => fun.apply()
				}
				/** Returns the list of all integrations in the specified project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListIntegrationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, parent: String, filter: String, orderBy: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationsResponse]] = (fun: list) => fun.apply()
				}
				/** Execute the integration in draft state */
				class test(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaTestIntegrationsRequest(body: Schema.GoogleCloudIntegrationsV1alphaTestIntegrationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTestIntegrationsResponse])
				}
				object test {
					def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): test = new test(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}:test").addQueryStringParameters("name" -> name.toString))
				}
				/** Schedules an integration for execution by passing the trigger id and the scheduled time in the request body. */
				class schedule(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaScheduleIntegrationsRequest(body: Schema.GoogleCloudIntegrationsV1alphaScheduleIntegrationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaScheduleIntegrationsResponse])
				}
				object schedule {
					def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): schedule = new schedule(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}:schedule").addQueryStringParameters("name" -> name.toString))
				}
				/** Delete the selected integration and all versions inside */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				object versions {
					/** Create a integration with a draft version in the specified project. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Optional. Indicates if sample workflow should be created. */
						def withCreateSampleIntegrations(createSampleIntegrations: Boolean) = new create(req.addQueryStringParameters("createSampleIntegrations" -> createSampleIntegrations.toString))
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaIntegrationVersion(body: Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, parent: String, newIntegration: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions").addQueryStringParameters("parent" -> parent.toString, "newIntegration" -> newIntegration.toString))
					}
					/** Sets the status of the ACTIVE integration to SNAPSHOT with a new tag "PREVIOUSLY_PUBLISHED" after validating it. The "HEAD" and "PUBLISH_REQUESTED" tags do not change. This RPC throws an exception if the version being snapshot is not ACTIVE. Audit fields added include action, action_by, action_timestamp. */
					class unpublish(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaUnpublishIntegrationVersionRequest(body: Schema.GoogleCloudIntegrationsV1alphaUnpublishIntegrationVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object unpublish {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): unpublish = new unpublish(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}:unpublish").addQueryStringParameters("name" -> name.toString))
					}
					/** This RPC throws an exception if the integration is in ARCHIVED or ACTIVE state. This RPC throws an exception if the version being published is DRAFT, and if the `locked_by` user is not the same as the user performing the Publish. Audit fields updated include last_published_timestamp, last_published_by, last_modified_timestamp, last_modified_by. Any existing lock is on this integration is released. */
					class publish(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaPublishIntegrationVersionRequest(body: Schema.GoogleCloudIntegrationsV1alphaPublishIntegrationVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaPublishIntegrationVersionResponse])
					}
					object publish {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): publish = new publish(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}:publish").addQueryStringParameters("name" -> name.toString))
					}
					/** Downloads an Integration version package like IntegrationVersion,Integration Config etc. Retrieves the IntegrationVersion package for a given `integration_id` and returns the response as a JSON. */
					class downloadJsonPackage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadJsonPackageResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Integration related file to download like Integration Version, Config variable, testcase etc.<br>Possible values:<br>INTEGRATION_FILE_UNSPECIFIED: Default value.<br>INTEGRATION: Integration file.<br>INTEGRATION_CONFIG_VARIABLES: Integration Config variables. */
						def withFiles(files: String) = new downloadJsonPackage(req.addQueryStringParameters("files" -> files.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadJsonPackageResponse])
					}
					object downloadJsonPackage {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): downloadJsonPackage = new downloadJsonPackage(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}:downloadJsonPackage").addQueryStringParameters("name" -> name.toString))
						given Conversion[downloadJsonPackage, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadJsonPackageResponse]] = (fun: downloadJsonPackage) => fun.apply()
					}
					/** Update a integration with a draft version in the specified project. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaIntegrationVersion(body: Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Downloads an integration. Retrieves the `IntegrationVersion` for a given `integration_id` and returns the response as a string. */
					class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadIntegrationVersionResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Integration related file to download like Integration Json, Config variable, testcase etc.<br>Possible values:<br>INTEGRATION_FILE_UNSPECIFIED: Default value.<br>INTEGRATION: Integration file.<br>INTEGRATION_CONFIG_VARIABLES: Integration Config variables. */
						def withFiles(files: String) = new download(req.addQueryStringParameters("files" -> files.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadIntegrationVersionResponse])
					}
					object download {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String, fileFormat: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}:download").addQueryStringParameters("name" -> name.toString, "fileFormat" -> fileFormat.toString))
						given Conversion[download, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadIntegrationVersionResponse]] = (fun: download) => fun.apply()
					}
					/** Returns the list of all integration versions in the specified project. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationVersionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListIntegrationVersionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, parent: String, orderBy: String, filter: String, pageSize: Int, fieldMask: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions").addQueryStringParameters("parent" -> parent.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "fieldMask" -> fieldMask.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListIntegrationVersionsResponse]] = (fun: list) => fun.apply()
					}
					/** Uploads an integration. The content can be a previously downloaded integration. Performs the same function as CreateDraftIntegrationVersion, but accepts input in a string format, which holds the complete representation of the IntegrationVersion content. */
					class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaUploadIntegrationVersionRequest(body: Schema.GoogleCloudIntegrationsV1alphaUploadIntegrationVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaUploadIntegrationVersionResponse])
					}
					object upload {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions:upload").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Soft-deletes the integration. Changes the status of the integration to ARCHIVED. If the integration being ARCHIVED is tagged as "HEAD", the tag is removed from this snapshot and set to the previous non-ARCHIVED snapshot. The PUBLISH_REQUESTED, DUE_FOR_DELETION tags are removed too. This RPC throws an exception if the version being deleted is DRAFT, and if the `locked_by` user is not the same as the user performing the Delete. Audit fields updated include last_modified_timestamp, last_modified_by. Any existing lock is released when Deleting a integration. Currently, there is no undelete mechanism. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Get a integration in the specified project. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaIntegrationVersion]] = (fun: get) => fun.apply()
					}
					object testCases {
						/** Creates a new test case */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaTestCase(body: Schema.GoogleCloudIntegrationsV1alphaTestCase) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTestCase])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCaseId: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases").addQueryStringParameters("testCaseId" -> testCaseId.toString, "parent" -> parent.toString))
						}
						/** Executes functional test */
						class executeTest(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaExecuteTestCaseRequest(body: Schema.GoogleCloudIntegrationsV1alphaExecuteTestCaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaExecuteTestCaseResponse])
						}
						object executeTest {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, testCaseName: String)(using signer: RequestSigner, ec: ExecutionContext): executeTest = new executeTest(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}:executeTest").addQueryStringParameters("testCaseName" -> testCaseName.toString))
						}
						/** Get a test case */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaTestCase]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTestCase])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaTestCase]] = (fun: get) => fun.apply()
						}
						/** Downloads a test case. Retrieves the `TestCase` for a given `test_case_id` and returns the response as a string. */
						class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadTestCaseResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadTestCaseResponse])
						}
						object download {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, fileFormat: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}:download").addQueryStringParameters("fileFormat" -> fileFormat.toString, "name" -> name.toString))
							given Conversion[download, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadTestCaseResponse]] = (fun: download) => fun.apply()
						}
						/** Lists the results of all functional test executions. The response includes the same information as the [execution log](https://cloud.google.com/application-integration/docs/viewing-logs) in the Integration UI. */
						class listExecutions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListTestCaseExecutionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
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
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListTestCaseExecutionsResponse])
						}
						object listExecutions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): listExecutions = new listExecutions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}:executions").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[listExecutions, Future[Schema.GoogleCloudIntegrationsV1alphaListTestCaseExecutionsResponse]] = (fun: listExecutions) => fun.apply()
						}
						/** Lists all the test cases that satisfy the filters. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListTestCasesResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
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
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListTestCasesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListTestCasesResponse]] = (fun: list) => fun.apply()
						}
						/** Uploads a test case. The content can be a previously downloaded test case. Performs the same function as CreateTestCase, but accepts input in a string format, which holds the complete representation of the TestCase content. */
						class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaUploadTestCaseRequest(body: Schema.GoogleCloudIntegrationsV1alphaUploadTestCaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaUploadTestCaseResponse])
						}
						object upload {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases:upload").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes a test case */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Updates a test case */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Field mask specifying the fields in the above integration that have been modified and need to be updated.<br>Format: google-fieldmask */
							def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaTestCase(body: Schema.GoogleCloudIntegrationsV1alphaTestCase) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTestCase])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}").addQueryStringParameters("name" -> name.toString))
						}
						/** Clear the lock fields and assign them to current user */
						class takeoverEditLock(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaTakeoverTestCaseEditLockRequest(body: Schema.GoogleCloudIntegrationsV1alphaTakeoverTestCaseEditLockRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTestCase])
						}
						object takeoverEditLock {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, versionsId :PlayApi, testCasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): takeoverEditLock = new takeoverEditLock(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/versions/${versionsId}/testCases/${testCasesId}:takeoverEditLock").addQueryStringParameters("name" -> name.toString))
						}
					}
				}
				object executions {
					/** Cancellation of an execution and associated sub-executions. This will not cancel an IN_PROCESS or completed(SUCCESSFUL, FAILED or CANCELLED) executions. */
					class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaCancelExecutionRequest(body: Schema.GoogleCloudIntegrationsV1alphaCancelExecutionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCancelExecutionResponse])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}:cancel").addQueryStringParameters("name" -> name.toString))
					}
					/** Re-execute an existing execution, with same request parameters and execution strategy. */
					class replay(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaReplayExecutionRequest(body: Schema.GoogleCloudIntegrationsV1alphaReplayExecutionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaReplayExecutionResponse])
					}
					object replay {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): replay = new replay(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}:replay").addQueryStringParameters("name" -> name.toString))
					}
					/** Get an execution in the specified project. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaExecution]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaExecution])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaExecution]] = (fun: get) => fun.apply()
					}
					/** Download the execution. */
					class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadExecutionResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadExecutionResponse])
					}
					object download {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}:download").addQueryStringParameters("name" -> name.toString))
						given Conversion[download, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadExecutionResponse]] = (fun: download) => fun.apply()
					}
					/** Lists the results of all the integration executions. The response includes the same information as the [execution log](https://cloud.google.com/application-integration/docs/viewing-logs) in the Integration UI. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListExecutionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
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
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListExecutionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, filterParamsParameterPairValue: String, filterParamsExecutionId: String, filterParamsTaskStatuses: String, parent: String, filterParamsParameterPairKey: String, filterParamsParameterKey: String, filterParamsEventStatuses: String, filterParamsWorkflowName: String, filterParamsParameterValue: String, filterParamsEndTime: String, filterParamsStartTime: String, filterParamsParameterType: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions").addQueryStringParameters("filterParams.parameterPairValue" -> filterParamsParameterPairValue.toString, "filterParams.executionId" -> filterParamsExecutionId.toString, "filterParams.taskStatuses" -> filterParamsTaskStatuses.toString, "parent" -> parent.toString, "filterParams.parameterPairKey" -> filterParamsParameterPairKey.toString, "filterParams.parameterKey" -> filterParamsParameterKey.toString, "filterParams.eventStatuses" -> filterParamsEventStatuses.toString, "filterParams.workflowName" -> filterParamsWorkflowName.toString, "filterParams.parameterValue" -> filterParamsParameterValue.toString, "filterParams.endTime" -> filterParamsEndTime.toString, "filterParams.startTime" -> filterParamsStartTime.toString, "filterParams.parameterType" -> filterParamsParameterType.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListExecutionsResponse]] = (fun: list) => fun.apply()
					}
					object suspensions {
						/** &#42; Resolves (lifts/rejects) any number of suspensions. If the integration is already running, only the status of the suspension is updated. Otherwise, the suspended integration will begin execution again. */
						class resolve(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaResolveSuspensionRequest(body: Schema.GoogleCloudIntegrationsV1alphaResolveSuspensionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaResolveSuspensionResponse])
						}
						object resolve {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, suspensionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): resolve = new resolve(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}/suspensions/${suspensionsId}:resolve").addQueryStringParameters("name" -> name.toString))
						}
						/** &#42; Lifts suspension for the Suspension task. Fetch corresponding suspension with provided suspension Id, resolve suspension, and set up suspension result for the Suspension Task. */
						class lift(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudIntegrationsV1alphaLiftSuspensionRequest(body: Schema.GoogleCloudIntegrationsV1alphaLiftSuspensionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaLiftSuspensionResponse])
						}
						object lift {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, suspensionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): lift = new lift(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}/suspensions/${suspensionsId}:lift").addQueryStringParameters("name" -> name.toString))
						}
						/** &#42; Lists suspensions associated with a specific execution. Only those with permissions to resolve the relevant suspensions will be able to view them. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListSuspensionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListSuspensionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, integrationsId :PlayApi, executionsId :PlayApi, filter: String, pageToken: String, orderBy: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/integrations/${integrationsId}/executions/${executionsId}/suspensions").addQueryStringParameters("filter" -> filter.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
							given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListSuspensionsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object cloudFunctions {
				/** Creates a cloud function project. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaCreateCloudFunctionRequest(body: Schema.GoogleCloudIntegrationsV1alphaCreateCloudFunctionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCreateCloudFunctionResponse])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/cloudFunctions").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object certificates {
				/** Creates a new certificate. The certificate will be registered to the trawler service and will be encrypted using cloud KMS and stored in Spanner Returns the certificate. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaCertificate(body: Schema.GoogleCloudIntegrationsV1alphaCertificate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCertificate])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificates").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Delete a certificate */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, certificatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificates/${certificatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Get a certificates in the specified project. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaCertificate]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCertificate])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, certificatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificates/${certificatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaCertificate]] = (fun: get) => fun.apply()
				}
				/** Updates the certificate by id. If new certificate file is updated, it will register with the trawler service, re-encrypt with cloud KMS and update the Spanner record. Other fields will directly update the Spanner record. Returns the Certificate. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaCertificate(body: Schema.GoogleCloudIntegrationsV1alphaCertificate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCertificate])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, certificatesId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificates/${certificatesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				/** List all the certificates that match the filter. Restrict to certificate of current client only. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListCertificatesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListCertificatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, readMask: String, filter: String, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/certificates").addQueryStringParameters("pageSize" -> pageSize.toString, "readMask" -> readMask.toString, "filter" -> filter.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListCertificatesResponse]] = (fun: list) => fun.apply()
				}
			}
			object sfdcInstances {
				/** Creates an sfdc instance record. Store the sfdc instance in Spanner. Returns the sfdc instance. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaSfdcInstance(body: Schema.GoogleCloudIntegrationsV1alphaSfdcInstance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an sfdc instance. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets an sfdc instance. If the instance doesn't exist, Code.NOT_FOUND exception will be thrown. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance]] = (fun: get) => fun.apply()
				}
				/** Updates an sfdc instance. Updates the sfdc instance in spanner. Returns the sfdc instance. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaSfdcInstance(body: Schema.GoogleCloudIntegrationsV1alphaSfdcInstance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcInstance])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				/** Lists all sfdc instances that match the filter. Restrict to sfdc instances belonging to the current client only. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcInstancesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListSfdcInstancesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, filter: String, parent: String, pageSize: Int, readMask: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances").addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "readMask" -> readMask.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcInstancesResponse]] = (fun: list) => fun.apply()
				}
				object sfdcChannels {
					/** Creates an sfdc channel record. Store the sfdc channel in Spanner. Returns the sfdc channel. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaSfdcChannel(body: Schema.GoogleCloudIntegrationsV1alphaSfdcChannel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes an sfdc channel. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, sfdcChannelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels/${sfdcChannelsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Gets an sfdc channel. If the channel doesn't exist, Code.NOT_FOUND exception will be thrown. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, sfdcChannelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels/${sfdcChannelsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel]] = (fun: get) => fun.apply()
					}
					/** Updates an sfdc channel. Updates the sfdc channel in spanner. Returns the sfdc channel. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudIntegrationsV1alphaSfdcChannel(body: Schema.GoogleCloudIntegrationsV1alphaSfdcChannel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSfdcChannel])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, sfdcChannelsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels/${sfdcChannelsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
					}
					/** Lists all sfdc channels that match the filter. Restrict to sfdc channels belonging to the current client only. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcChannelsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListSfdcChannelsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sfdcInstancesId :PlayApi, pageToken: String, pageSize: Int, parent: String, filter: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sfdcInstances/${sfdcInstancesId}/sfdcChannels").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString, "readMask" -> readMask.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListSfdcChannelsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object authConfigs {
				/** Creates an auth config record. Fetch corresponding credentials for specific auth types, e.g. access token for OAuth 2.0, JWT token for JWT. Encrypt the auth config with Cloud KMS and store the encrypted credentials in Spanner. Returns the encrypted auth config. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaAuthConfig(body: Schema.GoogleCloudIntegrationsV1alphaAuthConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaAuthConfig])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clientCertificateEncryptedPrivateKey: String, clientCertificatePassphrase: String, parent: String, clientCertificateSslCertificate: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/authConfigs").addQueryStringParameters("clientCertificate.encryptedPrivateKey" -> clientCertificateEncryptedPrivateKey.toString, "clientCertificate.passphrase" -> clientCertificatePassphrase.toString, "parent" -> parent.toString, "clientCertificate.sslCertificate" -> clientCertificateSslCertificate.toString))
				}
				/** Deletes an auth config. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, authConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/authConfigs/${authConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a complete auth config. If the auth config doesn't exist, Code.NOT_FOUND exception will be thrown. Returns the decrypted auth config. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaAuthConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaAuthConfig])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, authConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/authConfigs/${authConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaAuthConfig]] = (fun: get) => fun.apply()
				}
				/** Updates an auth config. If credential is updated, fetch the encrypted auth config from Spanner, decrypt with Cloud KMS key, update the credential fields, re-encrypt with Cloud KMS key and update the Spanner record. For other fields, directly update the Spanner record. Returns the encrypted auth config. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaAuthConfig(body: Schema.GoogleCloudIntegrationsV1alphaAuthConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaAuthConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, authConfigsId :PlayApi, updateMask: String, clientCertificateSslCertificate: String, clientCertificateEncryptedPrivateKey: String, name: String, clientCertificatePassphrase: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/authConfigs/${authConfigsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "clientCertificate.sslCertificate" -> clientCertificateSslCertificate.toString, "clientCertificate.encryptedPrivateKey" -> clientCertificateEncryptedPrivateKey.toString, "name" -> name.toString, "clientCertificate.passphrase" -> clientCertificatePassphrase.toString))
				}
				/** Lists all auth configs that match the filter. Restrict to auth configs belong to the current client only. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListAuthConfigsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListAuthConfigsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageToken: String, parent: String, filter: String, pageSize: Int, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/authConfigs").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "readMask" -> readMask.toString))
					given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListAuthConfigsResponse]] = (fun: list) => fun.apply()
				}
			}
			object templates {
				/** Creates a new template */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaTemplate(body: Schema.GoogleCloudIntegrationsV1alphaTemplate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTemplate])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Unshare a template from given clients. Owner of the template can unshare template with clients. Shared client can only unshare the template from itself. PERMISSION_DENIED would be thrown if request is not from owner or for unsharing itself. */
				class unshare(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaUnshareTemplateRequest(body: Schema.GoogleCloudIntegrationsV1alphaUnshareTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object unshare {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): unshare = new unshare(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}:unshare").addQueryStringParameters("name" -> name.toString))
				}
				/** Share a template with other clients. Only the template owner can share the templates with other projects. PERMISSION_DENIED would be thrown if the request is not from the owner. */
				class share(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaShareTemplateRequest(body: Schema.GoogleCloudIntegrationsV1alphaShareTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object share {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): share = new share(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}:share").addQueryStringParameters("name" -> name.toString))
				}
				/** Import the template to an existing integration. This api would keep track of usage_count and last_used_time. PERMISSION_DENIED would be thrown if template is not accessible by client. */
				class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaImportTemplateRequest(body: Schema.GoogleCloudIntegrationsV1alphaImportTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaImportTemplateResponse])
				}
				object `import` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}:import").addQueryStringParameters("name" -> name.toString))
				}
				/** Use the template to create integration. This api would keep track of usage_count and last_used_time. PERMISSION_DENIED would be thrown if template is not accessible by client. */
				class use(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaUseTemplateRequest(body: Schema.GoogleCloudIntegrationsV1alphaUseTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaUseTemplateResponse])
				}
				object use {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): use = new use(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}:use").addQueryStringParameters("name" -> name.toString))
				}
				/** Get a template in the specified project. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaTemplate]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTemplate])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudIntegrationsV1alphaTemplate]] = (fun: get) => fun.apply()
				}
				/** Updates the template by given id. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaTemplate(body: Schema.GoogleCloudIntegrationsV1alphaTemplate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaTemplate])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Downloads a template. Retrieves the `Template` and returns the response as a string. */
				class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaDownloadTemplateResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaDownloadTemplateResponse])
				}
				object download {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, fileFormat: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}:download").addQueryStringParameters("fileFormat" -> fileFormat.toString, "name" -> name.toString))
					given Conversion[download, Future[Schema.GoogleCloudIntegrationsV1alphaDownloadTemplateResponse]] = (fun: download) => fun.apply()
				}
				/** Uploads a template. The content can be a previously downloaded template. Performs the same function as CreateTemplate, but accepts input in a string format, which holds the complete representation of the Template content. */
				class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaUploadTemplateRequest(body: Schema.GoogleCloudIntegrationsV1alphaUploadTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaUploadTemplateResponse])
				}
				object upload {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates:upload").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a template */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, templatesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates/${templatesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Lists all templates matching the filter. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListTemplatesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
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
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListTemplatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListTemplatesResponse]] = (fun: list) => fun.apply()
				}
				/** Search templates based on user query and filters. This api would query the templates and return a list of templates based on the user filter. */
				class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaSearchTemplatesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
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
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaSearchTemplatesResponse])
				}
				object search {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/templates:search").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[search, Future[Schema.GoogleCloudIntegrationsV1alphaSearchTemplatesResponse]] = (fun: search) => fun.apply()
				}
			}
			object connections {
				/** Lists Connections in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListConnectionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListConnectionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, orderBy: String, filter: String, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections").addQueryStringParameters("pageSize" -> pageSize.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListConnectionsResponse]] = (fun: list) => fun.apply()
				}
				/** Lists the available entities and actions associated with a Connection. */
				class getConnectionSchemaMetadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaConnectionSchemaMetadata]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaConnectionSchemaMetadata])
				}
				object getConnectionSchemaMetadata {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getConnectionSchemaMetadata = new getConnectionSchemaMetadata(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/connectionSchemaMetadata").addQueryStringParameters("name" -> name.toString))
					given Conversion[getConnectionSchemaMetadata, Future[Schema.GoogleCloudIntegrationsV1alphaConnectionSchemaMetadata]] = (fun: getConnectionSchemaMetadata) => fun.apply()
				}
				object runtimeEntitySchemas {
					/** Lists the JSON schemas for the properties of runtime entities, filtered by entity name. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListRuntimeEntitySchemasResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListRuntimeEntitySchemasResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, pageToken: String, filter: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/runtimeEntitySchemas").addQueryStringParameters("pageToken" -> pageToken.toString, "filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListRuntimeEntitySchemasResponse]] = (fun: list) => fun.apply()
					}
				}
				object runtimeActionSchemas {
					/** Lists the JSON schemas for the inputs and outputs of actions, filtered by action name. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudIntegrationsV1alphaListRuntimeActionSchemasResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaListRuntimeActionSchemasResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, pageToken: String, parent: String, filter: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/runtimeActionSchemas").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.GoogleCloudIntegrationsV1alphaListRuntimeActionSchemasResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object appsScriptProjects {
				/** Creates an Apps Script project. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaCreateAppsScriptProjectRequest(body: Schema.GoogleCloudIntegrationsV1alphaCreateAppsScriptProjectRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaCreateAppsScriptProjectResponse])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/appsScriptProjects").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Links a existing Apps Script project. */
				class link(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudIntegrationsV1alphaLinkAppsScriptProjectRequest(body: Schema.GoogleCloudIntegrationsV1alphaLinkAppsScriptProjectRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudIntegrationsV1alphaLinkAppsScriptProjectResponse])
				}
				object link {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): link = new link(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/appsScriptProjects:link").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
}
