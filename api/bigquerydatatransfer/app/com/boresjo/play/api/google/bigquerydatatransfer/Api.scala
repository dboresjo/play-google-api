package com.boresjo.play.api.google.bigquerydatatransfer

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://bigquerydatatransfer.googleapis.com/"

	object projects {
		class enrollDataSources(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEnrollDataSourcesRequest(body: Schema.EnrollDataSourcesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
		}
		object enrollDataSources {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): enrollDataSources = new enrollDataSources(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}:enrollDataSources")).addQueryStringParameters("name" -> name.toString))
		}
		object dataSources {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DataSource]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.DataSource])
			}
			object get {
				def apply(projectsId :PlayApi, dataSourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/dataSources/${dataSourcesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.DataSource]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDataSourcesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListDataSourcesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/dataSources")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListDataSourcesResponse]] = (fun: list) => fun.apply()
			}
			class checkValidCreds(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCheckValidCredsRequest(body: Schema.CheckValidCredsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CheckValidCredsResponse])
			}
			object checkValidCreds {
				def apply(projectsId :PlayApi, dataSourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): checkValidCreds = new checkValidCreds(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/dataSources/${dataSourcesId}:checkValidCreds")).addQueryStringParameters("name" -> name.toString))
			}
		}
		object transferConfigs {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Deprecated: Authorization code was required when `transferConfig.dataSourceId` is 'youtube_channel' but it is no longer used in any data sources. Use `version_info` instead. Optional OAuth2 authorization code to use with this transfer configuration. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' and new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain authorization_code, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=authorization_code&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to create the transfer config. */
				def withAuthorizationCode(authorizationCode: String) = new create(req.addQueryStringParameters("authorizationCode" -> authorizationCode.toString))
				/** Optional version info. This parameter replaces `authorization_code` which is no longer used in any data sources. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' &#42;or&#42; new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain version info, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=version_info&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to create the transfer config. */
				def withVersionInfo(versionInfo: String) = new create(req.addQueryStringParameters("versionInfo" -> versionInfo.toString))
				/** Optional service account email. If this field is set, the transfer config will be created with this service account's credentials. It requires that the requesting user calling this API has permissions to act as this service account. Note that not all data sources support service account credentials when creating a transfer config. For the latest list of data sources, read about [using service accounts](https://cloud.google.com/bigquery-transfer/docs/use-service-accounts). */
				def withServiceAccountName(serviceAccountName: String) = new create(req.addQueryStringParameters("serviceAccountName" -> serviceAccountName.toString))
				def withTransferConfig(body: Schema.TransferConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TransferConfig])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs")).addQueryStringParameters("parent" -> parent.toString))
			}
			class scheduleRuns(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withScheduleTransferRunsRequest(body: Schema.ScheduleTransferRunsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ScheduleTransferRunsResponse])
			}
			object scheduleRuns {
				def apply(projectsId :PlayApi, transferConfigsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): scheduleRuns = new scheduleRuns(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}:scheduleRuns")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, transferConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TransferConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.TransferConfig])
			}
			object get {
				def apply(projectsId :PlayApi, transferConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.TransferConfig]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Deprecated: Authorization code was required when `transferConfig.dataSourceId` is 'youtube_channel' but it is no longer used in any data sources. Use `version_info` instead. Optional OAuth2 authorization code to use with this transfer configuration. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' and new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain authorization_code, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=authorization_code&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to update the transfer config. */
				def withAuthorizationCode(authorizationCode: String) = new patch(req.addQueryStringParameters("authorizationCode" -> authorizationCode.toString))
				/** Optional version info. This parameter replaces `authorization_code` which is no longer used in any data sources. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' &#42;or&#42; new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain version info, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=version_info&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to update the transfer config. */
				def withVersionInfo(versionInfo: String) = new patch(req.addQueryStringParameters("versionInfo" -> versionInfo.toString))
				/** Optional service account email. If this field is set, the transfer config will be created with this service account's credentials. It requires that the requesting user calling this API has permissions to act as this service account. Note that not all data sources support service account credentials when creating a transfer config. For the latest list of data sources, read about [using service accounts](https://cloud.google.com/bigquery-transfer/docs/use-service-accounts). */
				def withServiceAccountName(serviceAccountName: String) = new patch(req.addQueryStringParameters("serviceAccountName" -> serviceAccountName.toString))
				def withTransferConfig(body: Schema.TransferConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.TransferConfig])
			}
			object patch {
				def apply(projectsId :PlayApi, transferConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class startManualRuns(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withStartManualTransferRunsRequest(body: Schema.StartManualTransferRunsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.StartManualTransferRunsResponse])
			}
			object startManualRuns {
				def apply(projectsId :PlayApi, transferConfigsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): startManualRuns = new startManualRuns(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}:startManualRuns")).addQueryStringParameters("parent" -> parent.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTransferConfigsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListTransferConfigsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, dataSourceIds: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs")).addQueryStringParameters("parent" -> parent.toString, "dataSourceIds" -> dataSourceIds.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListTransferConfigsResponse]] = (fun: list) => fun.apply()
			}
			object runs {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TransferRun]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.TransferRun])
				}
				object get {
					def apply(projectsId :PlayApi, transferConfigsId :PlayApi, runsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}/runs/${runsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.TransferRun]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, transferConfigsId :PlayApi, runsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}/runs/${runsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTransferRunsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListTransferRunsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, transferConfigsId :PlayApi, parent: String, states: String, pageToken: String, pageSize: Int, runAttempt: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}/runs")).addQueryStringParameters("parent" -> parent.toString, "states" -> states.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "runAttempt" -> runAttempt.toString))
					given Conversion[list, Future[Schema.ListTransferRunsResponse]] = (fun: list) => fun.apply()
				}
				object transferLogs {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTransferLogsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListTransferLogsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, transferConfigsId :PlayApi, runsId :PlayApi, parent: String, pageToken: String, pageSize: Int, messageTypes: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/transferConfigs/${transferConfigsId}/runs/${runsId}/transferLogs")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "messageTypes" -> messageTypes.toString))
						given Conversion[list, Future[Schema.ListTransferLogsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object locations {
			class enrollDataSources(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withEnrollDataSourcesRequest(body: Schema.EnrollDataSourcesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
			}
			object enrollDataSources {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): enrollDataSources = new enrollDataSources(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:enrollDataSources")).addQueryStringParameters("name" -> name.toString))
			}
			class unenrollDataSources(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUnenrollDataSourcesRequest(body: Schema.UnenrollDataSourcesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
			}
			object unenrollDataSources {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): unenrollDataSources = new unenrollDataSources(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:unenrollDataSources")).addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object transferConfigs {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Deprecated: Authorization code was required when `transferConfig.dataSourceId` is 'youtube_channel' but it is no longer used in any data sources. Use `version_info` instead. Optional OAuth2 authorization code to use with this transfer configuration. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' and new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain authorization_code, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=authorization_code&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to create the transfer config. */
					def withAuthorizationCode(authorizationCode: String) = new create(req.addQueryStringParameters("authorizationCode" -> authorizationCode.toString))
					/** Optional version info. This parameter replaces `authorization_code` which is no longer used in any data sources. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' &#42;or&#42; new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain version info, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=version_info&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to create the transfer config. */
					def withVersionInfo(versionInfo: String) = new create(req.addQueryStringParameters("versionInfo" -> versionInfo.toString))
					/** Optional service account email. If this field is set, the transfer config will be created with this service account's credentials. It requires that the requesting user calling this API has permissions to act as this service account. Note that not all data sources support service account credentials when creating a transfer config. For the latest list of data sources, read about [using service accounts](https://cloud.google.com/bigquery-transfer/docs/use-service-accounts). */
					def withServiceAccountName(serviceAccountName: String) = new create(req.addQueryStringParameters("serviceAccountName" -> serviceAccountName.toString))
					def withTransferConfig(body: Schema.TransferConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TransferConfig])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs")).addQueryStringParameters("parent" -> parent.toString))
				}
				class scheduleRuns(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withScheduleTransferRunsRequest(body: Schema.ScheduleTransferRunsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ScheduleTransferRunsResponse])
				}
				object scheduleRuns {
					def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): scheduleRuns = new scheduleRuns(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}:scheduleRuns")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TransferConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.TransferConfig])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.TransferConfig]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Deprecated: Authorization code was required when `transferConfig.dataSourceId` is 'youtube_channel' but it is no longer used in any data sources. Use `version_info` instead. Optional OAuth2 authorization code to use with this transfer configuration. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' and new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain authorization_code, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=authorization_code&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to update the transfer config. */
					def withAuthorizationCode(authorizationCode: String) = new patch(req.addQueryStringParameters("authorizationCode" -> authorizationCode.toString))
					/** Optional version info. This parameter replaces `authorization_code` which is no longer used in any data sources. This is required only if `transferConfig.dataSourceId` is 'youtube_channel' &#42;or&#42; new credentials are needed, as indicated by `CheckValidCreds`. In order to obtain version info, make a request to the following URL: https://bigquery.cloud.google.com/datatransfer/oauthz/auth?redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=version_info&client_id=client_id&scope=data_source_scopes &#42; The client_id is the OAuth client_id of the data source as returned by ListDataSources method. &#42; data_source_scopes are the scopes returned by ListDataSources method. Note that this should not be set when `service_account_name` is used to update the transfer config. */
					def withVersionInfo(versionInfo: String) = new patch(req.addQueryStringParameters("versionInfo" -> versionInfo.toString))
					/** Optional service account email. If this field is set, the transfer config will be created with this service account's credentials. It requires that the requesting user calling this API has permissions to act as this service account. Note that not all data sources support service account credentials when creating a transfer config. For the latest list of data sources, read about [using service accounts](https://cloud.google.com/bigquery-transfer/docs/use-service-accounts). */
					def withServiceAccountName(serviceAccountName: String) = new patch(req.addQueryStringParameters("serviceAccountName" -> serviceAccountName.toString))
					def withTransferConfig(body: Schema.TransferConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.TransferConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class startManualRuns(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStartManualTransferRunsRequest(body: Schema.StartManualTransferRunsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.StartManualTransferRunsResponse])
				}
				object startManualRuns {
					def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): startManualRuns = new startManualRuns(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}:startManualRuns")).addQueryStringParameters("parent" -> parent.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTransferConfigsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListTransferConfigsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, dataSourceIds: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs")).addQueryStringParameters("parent" -> parent.toString, "dataSourceIds" -> dataSourceIds.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListTransferConfigsResponse]] = (fun: list) => fun.apply()
				}
				object runs {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TransferRun]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.TransferRun])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, runsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}/runs/${runsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.TransferRun]] = (fun: get) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, runsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}/runs/${runsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTransferRunsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListTransferRunsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, parent: String, states: String, pageToken: String, pageSize: Int, runAttempt: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}/runs")).addQueryStringParameters("parent" -> parent.toString, "states" -> states.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "runAttempt" -> runAttempt.toString))
						given Conversion[list, Future[Schema.ListTransferRunsResponse]] = (fun: list) => fun.apply()
					}
					object transferLogs {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTransferLogsResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ListTransferLogsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, transferConfigsId :PlayApi, runsId :PlayApi, parent: String, pageToken: String, pageSize: Int, messageTypes: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/transferConfigs/${transferConfigsId}/runs/${runsId}/transferLogs")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "messageTypes" -> messageTypes.toString))
							given Conversion[list, Future[Schema.ListTransferLogsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object dataSources {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DataSource]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.DataSource])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataSourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataSources/${dataSourcesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.DataSource]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDataSourcesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListDataSourcesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataSources")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListDataSourcesResponse]] = (fun: list) => fun.apply()
				}
				class checkValidCreds(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCheckValidCredsRequest(body: Schema.CheckValidCredsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CheckValidCredsResponse])
				}
				object checkValidCreds {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataSourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): checkValidCreds = new checkValidCreds(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataSources/${dataSourcesId}:checkValidCreds")).addQueryStringParameters("name" -> name.toString))
				}
			}
		}
	}
}
