package com.boresjo.play.api.google.bigquerydatatransfer

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
		"""https://www.googleapis.com/auth/bigquery""" /* View and manage your data in Google BigQuery and see the email address for your Google Account */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */
	)

	private val BASE_URL = "https://bigquerydatatransfer.googleapis.com/"

	object projects {
		/** Enroll data sources in a user project. This allows users to create transfer configurations for these data sources. They will also appear in the ListDataSources RPC and as such, will appear in the [BigQuery UI](https://console.cloud.google.com/bigquery), and the documents can be found in the public guide for [BigQuery Web UI](https://cloud.google.com/bigquery/bigquery-web-ui) and [Data Transfer Service](https://cloud.google.com/bigquery/docs/working-with-transfers). */
		class enrollDataSources(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withEnrollDataSourcesRequest(body: Schema.EnrollDataSourcesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object enrollDataSources {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): enrollDataSources = new enrollDataSources(ws.url(BASE_URL + s"v1/projects/${projectsId}:enrollDataSources").addQueryStringParameters("name" -> name.toString))
		}
		object dataSources {
			/** Retrieves a supported data source and returns its settings. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DataSource]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DataSource])
			}
			object get {
				def apply(projectsId :PlayApi, dataSourcesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/dataSources/${dataSourcesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.DataSource]] = (fun: get) => fun.apply()
			}
			/** Lists supported data sources and returns their settings. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDataSourcesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDataSourcesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/dataSources").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListDataSourcesResponse]] = (fun: list) => fun.apply()
			}
			/** Returns true if valid credentials exist for the given data source and requesting user. */
			class checkValidCreds(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def withCheckValidCredsRequest(body: Schema.CheckValidCredsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CheckValidCredsResponse])
			}
			object checkValidCreds {
				def apply(projectsId :PlayApi, dataSourcesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): checkValidCreds = new checkValidCreds(ws.url(BASE_URL + s"v1/projects/${projectsId}/dataSources/${dataSourcesId}:checkValidCreds").addQueryStringParameters("name" -> name.toString))
			}
		}
		object transferConfigs {
			/** Creates a new data transfer configuration. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Deprecated: Authorization code was required when `transferConfig.dataSourceId` is 'youtube_channel' but it is no longer used in any data sources. Use `version_info` instead. Optional OAuth2 authorization code to use with this transfer configuration. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' and new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain authorization_code, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=authorization_code&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to create the transfer config. */
				def withAuthorizationCode(authorizationCode: String) = new create(req.addQueryStringParameters("authorizationCode" -> authorizationCode.toString))
				/** Optional version info. This parameter replaces `authorization_code` which is no longer used in any data sources. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' &#42;or&#42; new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain version info, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=version_info&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to create the transfer config. */
				def withVersionInfo(versionInfo: String) = new create(req.addQueryStringParameters("versionInfo" -> versionInfo.toString))
				/** Optional service account email. If this field is set, the transfer config will be created with this service account's credentials. It requires that the requesting user calling this API has permissions to act as this service account. Note that not all data sources support service account credentials when creating a transfer config. For the latest list of data sources, read about [using service accounts](https://cloud.google.com/bigquery-transfer/docs/use-service-accounts). */
				def withServiceAccountName(serviceAccountName: String) = new create(req.addQueryStringParameters("serviceAccountName" -> serviceAccountName.toString))
				/** Perform the request */
				def withTransferConfig(body: Schema.TransferConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TransferConfig])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Creates transfer runs for a time range [start_time, end_time]. For each date - or whatever granularity the data source supports - in the range, one transfer run is created. Note that runs are created per UTC time in the time range. DEPRECATED: use StartManualTransferRuns instead. */
			class scheduleRuns(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withScheduleTransferRunsRequest(body: Schema.ScheduleTransferRunsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ScheduleTransferRunsResponse])
			}
			object scheduleRuns {
				def apply(projectsId :PlayApi, transferConfigsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): scheduleRuns = new scheduleRuns(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}:scheduleRuns").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a data transfer configuration, including any associated transfer runs and logs. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, transferConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Returns information about a data transfer config. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TransferConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TransferConfig])
			}
			object get {
				def apply(projectsId :PlayApi, transferConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.TransferConfig]] = (fun: get) => fun.apply()
			}
			/** Updates a data transfer configuration. All fields must be set, even if they are not updated. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Deprecated: Authorization code was required when `transferConfig.dataSourceId` is 'youtube_channel' but it is no longer used in any data sources. Use `version_info` instead. Optional OAuth2 authorization code to use with this transfer configuration. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' and new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain authorization_code, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=authorization_code&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to update the transfer config. */
				def withAuthorizationCode(authorizationCode: String) = new patch(req.addQueryStringParameters("authorizationCode" -> authorizationCode.toString))
				/** Optional version info. This parameter replaces `authorization_code` which is no longer used in any data sources. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' &#42;or&#42; new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain version info, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=version_info&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to update the transfer config. */
				def withVersionInfo(versionInfo: String) = new patch(req.addQueryStringParameters("versionInfo" -> versionInfo.toString))
				/** Optional service account email. If this field is set, the transfer config will be created with this service account's credentials. It requires that the requesting user calling this API has permissions to act as this service account. Note that not all data sources support service account credentials when creating a transfer config. For the latest list of data sources, read about [using service accounts](https://cloud.google.com/bigquery-transfer/docs/use-service-accounts). */
				def withServiceAccountName(serviceAccountName: String) = new patch(req.addQueryStringParameters("serviceAccountName" -> serviceAccountName.toString))
				/** Perform the request */
				def withTransferConfig(body: Schema.TransferConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.TransferConfig])
			}
			object patch {
				def apply(projectsId :PlayApi, transferConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Start manual transfer runs to be executed now with schedule_time equal to current time. The transfer runs can be created for a time range where the run_time is between start_time (inclusive) and end_time (exclusive), or for a specific run_time. */
			class startManualRuns(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withStartManualTransferRunsRequest(body: Schema.StartManualTransferRunsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.StartManualTransferRunsResponse])
			}
			object startManualRuns {
				def apply(projectsId :PlayApi, transferConfigsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): startManualRuns = new startManualRuns(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}:startManualRuns").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Returns information about all transfer configs owned by a project in the specified location. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTransferConfigsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTransferConfigsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, dataSourceIds: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs").addQueryStringParameters("parent" -> parent.toString, "dataSourceIds" -> dataSourceIds.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListTransferConfigsResponse]] = (fun: list) => fun.apply()
			}
			object runs {
				/** Returns information about the particular transfer run. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TransferRun]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TransferRun])
				}
				object get {
					def apply(projectsId :PlayApi, transferConfigsId :PlayApi, runsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}/runs/${runsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.TransferRun]] = (fun: get) => fun.apply()
				}
				/** Deletes the specified transfer run. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, transferConfigsId :PlayApi, runsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}/runs/${runsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Returns information about running and completed transfer runs. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTransferRunsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTransferRunsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, transferConfigsId :PlayApi, parent: String, states: String, pageToken: String, pageSize: Int, runAttempt: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}/runs").addQueryStringParameters("parent" -> parent.toString, "states" -> states.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "runAttempt" -> runAttempt.toString))
					given Conversion[list, Future[Schema.ListTransferRunsResponse]] = (fun: list) => fun.apply()
				}
				object transferLogs {
					/** Returns log messages for the transfer run. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTransferLogsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTransferLogsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, transferConfigsId :PlayApi, runsId :PlayApi, parent: String, pageToken: String, pageSize: Int, messageTypes: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}/runs/${runsId}/transferLogs").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "messageTypes" -> messageTypes.toString))
						given Conversion[list, Future[Schema.ListTransferLogsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object locations {
			/** Enroll data sources in a user project. This allows users to create transfer configurations for these data sources. They will also appear in the ListDataSources RPC and as such, will appear in the [BigQuery UI](https://console.cloud.google.com/bigquery), and the documents can be found in the public guide for [BigQuery Web UI](https://cloud.google.com/bigquery/bigquery-web-ui) and [Data Transfer Service](https://cloud.google.com/bigquery/docs/working-with-transfers). */
			class enrollDataSources(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withEnrollDataSourcesRequest(body: Schema.EnrollDataSourcesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object enrollDataSources {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): enrollDataSources = new enrollDataSources(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:enrollDataSources").addQueryStringParameters("name" -> name.toString))
			}
			/** Unenroll data sources in a user project. This allows users to remove transfer configurations for these data sources. They will no longer appear in the ListDataSources RPC and will also no longer appear in the [BigQuery UI](https://console.cloud.google.com/bigquery). Data transfers configurations of unenrolled data sources will not be scheduled. */
			class unenrollDataSources(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withUnenrollDataSourcesRequest(body: Schema.UnenrollDataSourcesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object unenrollDataSources {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): unenrollDataSources = new unenrollDataSources(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:unenrollDataSources").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object transferConfigs {
				/** Creates a new data transfer configuration. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Deprecated: Authorization code was required when `transferConfig.dataSourceId` is 'youtube_channel' but it is no longer used in any data sources. Use `version_info` instead. Optional OAuth2 authorization code to use with this transfer configuration. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' and new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain authorization_code, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=authorization_code&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to create the transfer config. */
					def withAuthorizationCode(authorizationCode: String) = new create(req.addQueryStringParameters("authorizationCode" -> authorizationCode.toString))
					/** Optional version info. This parameter replaces `authorization_code` which is no longer used in any data sources. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' &#42;or&#42; new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain version info, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=version_info&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to create the transfer config. */
					def withVersionInfo(versionInfo: String) = new create(req.addQueryStringParameters("versionInfo" -> versionInfo.toString))
					/** Optional service account email. If this field is set, the transfer config will be created with this service account's credentials. It requires that the requesting user calling this API has permissions to act as this service account. Note that not all data sources support service account credentials when creating a transfer config. For the latest list of data sources, read about [using service accounts](https://cloud.google.com/bigquery-transfer/docs/use-service-accounts). */
					def withServiceAccountName(serviceAccountName: String) = new create(req.addQueryStringParameters("serviceAccountName" -> serviceAccountName.toString))
					/** Perform the request */
					def withTransferConfig(body: Schema.TransferConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TransferConfig])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Creates transfer runs for a time range [start_time, end_time]. For each date - or whatever granularity the data source supports - in the range, one transfer run is created. Note that runs are created per UTC time in the time range. DEPRECATED: use StartManualTransferRuns instead. */
				class scheduleRuns(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withScheduleTransferRunsRequest(body: Schema.ScheduleTransferRunsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ScheduleTransferRunsResponse])
				}
				object scheduleRuns {
					def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): scheduleRuns = new scheduleRuns(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}:scheduleRuns").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a data transfer configuration, including any associated transfer runs and logs. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Returns information about a data transfer config. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TransferConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TransferConfig])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.TransferConfig]] = (fun: get) => fun.apply()
				}
				/** Updates a data transfer configuration. All fields must be set, even if they are not updated. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Deprecated: Authorization code was required when `transferConfig.dataSourceId` is 'youtube_channel' but it is no longer used in any data sources. Use `version_info` instead. Optional OAuth2 authorization code to use with this transfer configuration. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' and new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain authorization_code, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=authorization_code&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to update the transfer config. */
					def withAuthorizationCode(authorizationCode: String) = new patch(req.addQueryStringParameters("authorizationCode" -> authorizationCode.toString))
					/** Optional version info. This parameter replaces `authorization_code` which is no longer used in any data sources. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' &#42;or&#42; new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain version info, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=version_info&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to update the transfer config. */
					def withVersionInfo(versionInfo: String) = new patch(req.addQueryStringParameters("versionInfo" -> versionInfo.toString))
					/** Optional service account email. If this field is set, the transfer config will be created with this service account's credentials. It requires that the requesting user calling this API has permissions to act as this service account. Note that not all data sources support service account credentials when creating a transfer config. For the latest list of data sources, read about [using service accounts](https://cloud.google.com/bigquery-transfer/docs/use-service-accounts). */
					def withServiceAccountName(serviceAccountName: String) = new patch(req.addQueryStringParameters("serviceAccountName" -> serviceAccountName.toString))
					/** Perform the request */
					def withTransferConfig(body: Schema.TransferConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.TransferConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Start manual transfer runs to be executed now with schedule_time equal to current time. The transfer runs can be created for a time range where the run_time is between start_time (inclusive) and end_time (exclusive), or for a specific run_time. */
				class startManualRuns(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withStartManualTransferRunsRequest(body: Schema.StartManualTransferRunsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.StartManualTransferRunsResponse])
				}
				object startManualRuns {
					def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): startManualRuns = new startManualRuns(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}:startManualRuns").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Returns information about all transfer configs owned by a project in the specified location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTransferConfigsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTransferConfigsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, dataSourceIds: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs").addQueryStringParameters("parent" -> parent.toString, "dataSourceIds" -> dataSourceIds.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListTransferConfigsResponse]] = (fun: list) => fun.apply()
				}
				object runs {
					/** Returns information about the particular transfer run. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TransferRun]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TransferRun])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, runsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}/runs/${runsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.TransferRun]] = (fun: get) => fun.apply()
					}
					/** Deletes the specified transfer run. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, runsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}/runs/${runsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Returns information about running and completed transfer runs. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTransferRunsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTransferRunsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, parent: String, states: String, pageToken: String, pageSize: Int, runAttempt: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}/runs").addQueryStringParameters("parent" -> parent.toString, "states" -> states.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "runAttempt" -> runAttempt.toString))
						given Conversion[list, Future[Schema.ListTransferRunsResponse]] = (fun: list) => fun.apply()
					}
					object transferLogs {
						/** Returns log messages for the transfer run. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTransferLogsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTransferLogsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, runsId :PlayApi, parent: String, pageToken: String, pageSize: Int, messageTypes: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}/runs/${runsId}/transferLogs").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "messageTypes" -> messageTypes.toString))
							given Conversion[list, Future[Schema.ListTransferLogsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object dataSources {
				/** Retrieves a supported data source and returns its settings. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DataSource]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DataSource])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataSourcesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataSources/${dataSourcesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.DataSource]] = (fun: get) => fun.apply()
				}
				/** Lists supported data sources and returns their settings. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDataSourcesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDataSourcesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataSources").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListDataSourcesResponse]] = (fun: list) => fun.apply()
				}
				/** Returns true if valid credentials exist for the given data source and requesting user. */
				class checkValidCreds(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def withCheckValidCredsRequest(body: Schema.CheckValidCredsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CheckValidCredsResponse])
				}
				object checkValidCreds {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataSourcesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): checkValidCreds = new checkValidCreds(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataSources/${dataSourcesId}:checkValidCreds").addQueryStringParameters("name" -> name.toString))
				}
			}
		}
	}
}
