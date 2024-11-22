package com.boresjo.play.api.google.composer

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

	private val BASE_URL = "https://composer.googleapis.com/"

	object projects {
		object locations {
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
			object environments {
				/** Triggers database failover (only for highly resilient environments). */
				class databaseFailover(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDatabaseFailoverRequest(body: Schema.DatabaseFailoverRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object databaseFailover {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using signer: RequestSigner, ec: ExecutionContext): databaseFailover = new databaseFailover(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:databaseFailover").addQueryStringParameters("environment" -> environment.toString))
				}
				/** Polls Airflow CLI command execution and fetches logs. */
				class pollAirflowCommand(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withPollAirflowCommandRequest(body: Schema.PollAirflowCommandRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PollAirflowCommandResponse])
				}
				object pollAirflowCommand {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using signer: RequestSigner, ec: ExecutionContext): pollAirflowCommand = new pollAirflowCommand(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:pollAirflowCommand").addQueryStringParameters("environment" -> environment.toString))
				}
				/** Creates a snapshots of a Cloud Composer environment. As a result of this operation, snapshot of environment's state is stored in a location specified in the SaveSnapshotRequest. */
				class saveSnapshot(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSaveSnapshotRequest(body: Schema.SaveSnapshotRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object saveSnapshot {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using signer: RequestSigner, ec: ExecutionContext): saveSnapshot = new saveSnapshot(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:saveSnapshot").addQueryStringParameters("environment" -> environment.toString))
				}
				/** Check if an upgrade operation on the environment will succeed. In case of problems detailed info can be found in the returned Operation. */
				class checkUpgrade(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCheckUpgradeRequest(body: Schema.CheckUpgradeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object checkUpgrade {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using signer: RequestSigner, ec: ExecutionContext): checkUpgrade = new checkUpgrade(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:checkUpgrade").addQueryStringParameters("environment" -> environment.toString))
				}
				/** Delete an environment. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Fetches database properties. */
				class fetchDatabaseProperties(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchDatabasePropertiesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchDatabasePropertiesResponse])
				}
				object fetchDatabaseProperties {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using signer: RequestSigner, ec: ExecutionContext): fetchDatabaseProperties = new fetchDatabaseProperties(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:fetchDatabaseProperties").addQueryStringParameters("environment" -> environment.toString))
					given Conversion[fetchDatabaseProperties, Future[Schema.FetchDatabasePropertiesResponse]] = (fun: fetchDatabaseProperties) => fun.apply()
				}
				/** Get an existing environment. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Environment]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Environment])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Environment]] = (fun: get) => fun.apply()
				}
				/** Update an environment. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEnvironment(body: Schema.Environment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Stops Airflow CLI command execution. */
				class stopAirflowCommand(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withStopAirflowCommandRequest(body: Schema.StopAirflowCommandRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.StopAirflowCommandResponse])
				}
				object stopAirflowCommand {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using signer: RequestSigner, ec: ExecutionContext): stopAirflowCommand = new stopAirflowCommand(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:stopAirflowCommand").addQueryStringParameters("environment" -> environment.toString))
				}
				/** List environments. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEnvironmentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEnvironmentsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListEnvironmentsResponse]] = (fun: list) => fun.apply()
				}
				/** Loads a snapshot of a Cloud Composer environment. As a result of this operation, a snapshot of environment's specified in LoadSnapshotRequest is loaded into the environment. */
				class loadSnapshot(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withLoadSnapshotRequest(body: Schema.LoadSnapshotRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object loadSnapshot {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using signer: RequestSigner, ec: ExecutionContext): loadSnapshot = new loadSnapshot(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:loadSnapshot").addQueryStringParameters("environment" -> environment.toString))
				}
				/** Create a new environment. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEnvironment(body: Schema.Environment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Executes Airflow CLI command. */
				class executeAirflowCommand(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withExecuteAirflowCommandRequest(body: Schema.ExecuteAirflowCommandRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ExecuteAirflowCommandResponse])
				}
				object executeAirflowCommand {
					def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, environment: String)(using signer: RequestSigner, ec: ExecutionContext): executeAirflowCommand = new executeAirflowCommand(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}:executeAirflowCommand").addQueryStringParameters("environment" -> environment.toString))
				}
				object workloads {
					/** Lists workloads in a Cloud Composer environment. Workload is a unit that runs a single Composer component. This method is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListWorkloadsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum number of environments to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The next_page_token value returned from a previous List request, if any. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The list filter. Currently only supports equality on the type field. The value of a field specified in the filter expression must be one ComposerWorkloadType enum option. It's possible to get multiple types using "OR" operator, e.g.: "type=SCHEDULER OR type=CELERY_WORKER". If not specified, all items are returned. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListWorkloadsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/workloads").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListWorkloadsResponse]] = (fun: list) => fun.apply()
					}
				}
				object userWorkloadsSecrets {
					/** Creates a user workloads Secret. This method is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUserWorkloadsSecret(body: Schema.UserWorkloadsSecret) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UserWorkloadsSecret])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsSecrets").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a user workloads Secret. This method is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, userWorkloadsSecretsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsSecrets/${userWorkloadsSecretsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets an existing user workloads Secret. Values of the "data" field in the response are cleared. This method is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UserWorkloadsSecret]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UserWorkloadsSecret])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, userWorkloadsSecretsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsSecrets/${userWorkloadsSecretsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.UserWorkloadsSecret]] = (fun: get) => fun.apply()
					}
					/** Updates a user workloads Secret. This method is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUserWorkloadsSecret(body: Schema.UserWorkloadsSecret) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.UserWorkloadsSecret])
					}
					object update {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, userWorkloadsSecretsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsSecrets/${userWorkloadsSecretsId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists user workloads Secrets. This method is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUserWorkloadsSecretsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum number of Secrets to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The next_page_token value returned from a previous List request, if any. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUserWorkloadsSecretsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsSecrets").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListUserWorkloadsSecretsResponse]] = (fun: list) => fun.apply()
					}
				}
				object userWorkloadsConfigMaps {
					/** Creates a user workloads ConfigMap. This method is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUserWorkloadsConfigMap(body: Schema.UserWorkloadsConfigMap) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UserWorkloadsConfigMap])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsConfigMaps").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a user workloads ConfigMap. This method is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, userWorkloadsConfigMapsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsConfigMaps/${userWorkloadsConfigMapsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets an existing user workloads ConfigMap. This method is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UserWorkloadsConfigMap]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UserWorkloadsConfigMap])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, userWorkloadsConfigMapsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsConfigMaps/${userWorkloadsConfigMapsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.UserWorkloadsConfigMap]] = (fun: get) => fun.apply()
					}
					/** Updates a user workloads ConfigMap. This method is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUserWorkloadsConfigMap(body: Schema.UserWorkloadsConfigMap) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.UserWorkloadsConfigMap])
					}
					object update {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, userWorkloadsConfigMapsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsConfigMaps/${userWorkloadsConfigMapsId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists user workloads ConfigMaps. This method is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUserWorkloadsConfigMapsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum number of ConfigMaps to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The next_page_token value returned from a previous List request, if any. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUserWorkloadsConfigMapsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/environments/${environmentsId}/userWorkloadsConfigMaps").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListUserWorkloadsConfigMapsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object imageVersions {
				/** List ImageVersions for provided location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListImageVersionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListImageVersionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, includePastReleases: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageVersions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "includePastReleases" -> includePastReleases.toString))
					given Conversion[list, Future[Schema.ListImageVersionsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
