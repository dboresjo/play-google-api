package com.boresjo.play.api.google.composer

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://composer.googleapis.com/"

	object projects {
		object locations {
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
			object environments {
				class databaseFailover(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDatabaseFailoverRequest(body: Schema.DatabaseFailoverRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object databaseFailover {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using auth: AuthToken, ec: ExecutionContext): databaseFailover = new databaseFailover(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:databaseFailover")).addQueryStringParameters("environment" -> environment.toString))
				}
				class pollAirflowCommand(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withPollAirflowCommandRequest(body: Schema.PollAirflowCommandRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.PollAirflowCommandResponse])
				}
				object pollAirflowCommand {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using auth: AuthToken, ec: ExecutionContext): pollAirflowCommand = new pollAirflowCommand(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:pollAirflowCommand")).addQueryStringParameters("environment" -> environment.toString))
				}
				class saveSnapshot(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSaveSnapshotRequest(body: Schema.SaveSnapshotRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object saveSnapshot {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using auth: AuthToken, ec: ExecutionContext): saveSnapshot = new saveSnapshot(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:saveSnapshot")).addQueryStringParameters("environment" -> environment.toString))
				}
				class checkUpgrade(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCheckUpgradeRequest(body: Schema.CheckUpgradeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object checkUpgrade {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using auth: AuthToken, ec: ExecutionContext): checkUpgrade = new checkUpgrade(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:checkUpgrade")).addQueryStringParameters("environment" -> environment.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class fetchDatabaseProperties(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FetchDatabasePropertiesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.FetchDatabasePropertiesResponse])
				}
				object fetchDatabaseProperties {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using auth: AuthToken, ec: ExecutionContext): fetchDatabaseProperties = new fetchDatabaseProperties(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:fetchDatabaseProperties")).addQueryStringParameters("environment" -> environment.toString))
					given Conversion[fetchDatabaseProperties, Future[Schema.FetchDatabasePropertiesResponse]] = (fun: fetchDatabaseProperties) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Environment]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Environment])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Environment]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEnvironment(body: Schema.Environment) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class stopAirflowCommand(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStopAirflowCommandRequest(body: Schema.StopAirflowCommandRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.StopAirflowCommandResponse])
				}
				object stopAirflowCommand {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using auth: AuthToken, ec: ExecutionContext): stopAirflowCommand = new stopAirflowCommand(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:stopAirflowCommand")).addQueryStringParameters("environment" -> environment.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEnvironmentsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListEnvironmentsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListEnvironmentsResponse]] = (fun: list) => fun.apply()
				}
				class loadSnapshot(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLoadSnapshotRequest(body: Schema.LoadSnapshotRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object loadSnapshot {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using auth: AuthToken, ec: ExecutionContext): loadSnapshot = new loadSnapshot(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:loadSnapshot")).addQueryStringParameters("environment" -> environment.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEnvironment(body: Schema.Environment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments")).addQueryStringParameters("parent" -> parent.toString))
				}
				class executeAirflowCommand(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withExecuteAirflowCommandRequest(body: Schema.ExecuteAirflowCommandRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ExecuteAirflowCommandResponse])
				}
				object executeAirflowCommand {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using auth: AuthToken, ec: ExecutionContext): executeAirflowCommand = new executeAirflowCommand(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:executeAirflowCommand")).addQueryStringParameters("environment" -> environment.toString))
				}
				object workloads {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWorkloadsResponse]) {
						/** Optional. The maximum number of environments to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The next_page_token value returned from a previous List request, if any. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The list filter. Currently only supports equality on the type field. The value of a field specified in the filter expression must be one ComposerWorkloadType enum option. It's possible to get multiple types using "OR" operator, e.g.: "type=SCHEDULER OR type=CELERY_WORKER". If not specified, all items are returned. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListWorkloadsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/workloads")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListWorkloadsResponse]] = (fun: list) => fun.apply()
					}
				}
				object userWorkloadsSecrets {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUserWorkloadsSecret(body: Schema.UserWorkloadsSecret) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.UserWorkloadsSecret])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsSecrets")).addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, userWorkloadsSecretsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsSecrets/${userWorkloadsSecretsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UserWorkloadsSecret]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.UserWorkloadsSecret])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, userWorkloadsSecretsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsSecrets/${userWorkloadsSecretsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.UserWorkloadsSecret]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUserWorkloadsSecret(body: Schema.UserWorkloadsSecret) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.UserWorkloadsSecret])
					}
					object update {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, userWorkloadsSecretsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsSecrets/${userWorkloadsSecretsId}")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUserWorkloadsSecretsResponse]) {
						/** Optional. The maximum number of Secrets to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The next_page_token value returned from a previous List request, if any. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListUserWorkloadsSecretsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsSecrets")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListUserWorkloadsSecretsResponse]] = (fun: list) => fun.apply()
					}
				}
				object userWorkloadsConfigMaps {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUserWorkloadsConfigMap(body: Schema.UserWorkloadsConfigMap) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.UserWorkloadsConfigMap])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsConfigMaps")).addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, userWorkloadsConfigMapsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsConfigMaps/${userWorkloadsConfigMapsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UserWorkloadsConfigMap]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.UserWorkloadsConfigMap])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, userWorkloadsConfigMapsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsConfigMaps/${userWorkloadsConfigMapsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.UserWorkloadsConfigMap]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUserWorkloadsConfigMap(body: Schema.UserWorkloadsConfigMap) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.UserWorkloadsConfigMap])
					}
					object update {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, userWorkloadsConfigMapsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsConfigMaps/${userWorkloadsConfigMapsId}")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUserWorkloadsConfigMapsResponse]) {
						/** Optional. The maximum number of ConfigMaps to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The next_page_token value returned from a previous List request, if any. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListUserWorkloadsConfigMapsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsConfigMaps")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListUserWorkloadsConfigMapsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object imageVersions {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListImageVersionsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListImageVersionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, includePastReleases: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageVersions")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "includePastReleases" -> includePastReleases.toString))
					given Conversion[list, Future[Schema.ListImageVersionsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
