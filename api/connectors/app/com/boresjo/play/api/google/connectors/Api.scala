package com.boresjo.play.api.google.connectors

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

	private val BASE_URL = "https://connectors.googleapis.com/"

	object projects {
		object locations {
			object connections {
				/** ExchangeAuthCode exchanges the OAuth authorization code (and other necessary data) for an access token (and associated credentials). */
				class exchangeAuthCode(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withExchangeAuthCodeRequest(body: Schema.ExchangeAuthCodeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ExchangeAuthCodeResponse])
				}
				object exchangeAuthCode {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): exchangeAuthCode = new exchangeAuthCode(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:exchangeAuthCode").addQueryStringParameters("name" -> name.toString))
				}
				/** Executes a SQL statement specified in the body of the request. An example of this SQL statement in the case of Salesforce connector would be 'select &#42; from Account a, Order o where a.Id = o.AccountId'. */
				class executeSqlQuery(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withExecuteSqlQueryRequest(body: Schema.ExecuteSqlQueryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ExecuteSqlQueryResponse])
				}
				object executeSqlQuery {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, connection: String)(using signer: RequestSigner, ec: ExecutionContext): executeSqlQuery = new executeSqlQuery(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:executeSqlQuery").addQueryStringParameters("connection" -> connection.toString))
				}
				/** Reports the status of the connection. Note that when the connection is in a state that is not ACTIVE, the implementation of this RPC method must return a Status with the corresponding State instead of returning a gRPC status code that is not "OK", which indicates that ConnectionStatus itself, not the connection, failed. */
				class checkStatus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CheckStatusResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CheckStatusResponse])
				}
				object checkStatus {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): checkStatus = new checkStatus(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:checkStatus").addQueryStringParameters("name" -> name.toString))
					given Conversion[checkStatus, Future[Schema.CheckStatusResponse]] = (fun: checkStatus) => fun.apply()
				}
				/** Reports readiness status of the connector. Similar logic to GetStatus but modified for kubernetes health check to understand. */
				class checkReadiness(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CheckReadinessResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CheckReadinessResponse])
				}
				object checkReadiness {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): checkReadiness = new checkReadiness(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:checkReadiness").addQueryStringParameters("name" -> name.toString))
					given Conversion[checkReadiness, Future[Schema.CheckReadinessResponse]] = (fun: checkReadiness) => fun.apply()
				}
				/** RefreshAccessToken exchanges the OAuth refresh token (and other necessary data) for a new access token (and new associated credentials). */
				class refreshAccessToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRefreshAccessTokenRequest(body: Schema.RefreshAccessTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RefreshAccessTokenResponse])
				}
				object refreshAccessToken {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): refreshAccessToken = new refreshAccessToken(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:refreshAccessToken").addQueryStringParameters("name" -> name.toString))
				}
				object actions {
					/** Executes an action with the name specified in the request. The input parameters for executing the action are passed through the body of the ExecuteAction request. */
					class execute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withExecuteActionRequest(body: Schema.ExecuteActionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ExecuteActionResponse])
					}
					object execute {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, actionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): execute = new execute(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/actions/${actionsId}:execute").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets the schema of all the actions supported by the connector. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListActionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListActionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/actions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
						given Conversion[list, Future[Schema.ListActionsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the schema of the given action. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Action]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Action])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, actionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/actions/${actionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Action]] = (fun: get) => fun.apply()
					}
				}
				object entityTypes {
					/** Gets metadata of given entity type */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EntityType]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EntityType])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.EntityType]] = (fun: get) => fun.apply()
					}
					/** Lists metadata related to all entity types present in the external system. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEntityTypesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEntityTypesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
						given Conversion[list, Future[Schema.ListEntityTypesResponse]] = (fun: list) => fun.apply()
					}
					object entities {
						/** Creates a new entity row of the specified entity type in the external system. The field values for creating the row are contained in the body of the request. The response message contains a `Entity` message object returned as a response by the external system. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withEntity(body: Schema.Entity) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Entity])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes entities based on conditions specified in the request and not on entity id. */
						class deleteEntitiesWithConditions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Empty])
						}
						object deleteEntitiesWithConditions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, entityType: String, conditions: String)(using signer: RequestSigner, ec: ExecutionContext): deleteEntitiesWithConditions = new deleteEntitiesWithConditions(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities:deleteEntitiesWithConditions").addQueryStringParameters("entityType" -> entityType.toString, "conditions" -> conditions.toString))
							given Conversion[deleteEntitiesWithConditions, Future[Schema.Empty]] = (fun: deleteEntitiesWithConditions) => fun.apply()
						}
						/** Deletes an existing entity row matching the entity type and entity id specified in the request. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, entitiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities/${entitiesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						/** Gets a single entity row matching the entity type and entity id specified in the request. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Entity]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Entity])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, entitiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities/${entitiesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Entity]] = (fun: get) => fun.apply()
						}
						/** Updates entities based on conditions specified in the request and not on entity id. */
						class updateEntitiesWithConditions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withEntity(body: Schema.Entity) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UpdateEntitiesWithConditionsResponse])
						}
						object updateEntitiesWithConditions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, entityType: String, conditions: String)(using signer: RequestSigner, ec: ExecutionContext): updateEntitiesWithConditions = new updateEntitiesWithConditions(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities:updateEntitiesWithConditions").addQueryStringParameters("entityType" -> entityType.toString, "conditions" -> conditions.toString))
						}
						/** Updates an existing entity row matching the entity type and entity id specified in the request. The fields in the entity row that need to be modified are contained in the body of the request. All unspecified fields are left unchanged. The response message contains a `Entity` message object returned as a response by the external system. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withEntity(body: Schema.Entity) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Entity])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, entitiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities/${entitiesId}").addQueryStringParameters("name" -> name.toString))
						}
						/** Lists entity rows of a particular entity type contained in the request. Note: 1. Currently, only max of one 'sort_by' column is supported. 2. If no 'sort_by' column is provided, the primary key of the table is used. If zero or more than one primary key is available, we default to the unpaginated list entities logic which only returns the first page. 3. The values of the 'sort_by' columns must uniquely identify an entity row, otherwise undefined behaviors may be observed during pagination. 4. Since transactions are not supported, any updates, inserts or deletes during pagination can lead to stale data being returned or other unexpected behaviors. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEntitiesResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEntitiesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, parent: String, pageSize: Int, pageToken: String, sortBy: String, conditions: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "sortBy" -> sortBy.toString, "conditions" -> conditions.toString))
							given Conversion[list, Future[Schema.ListEntitiesResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
		}
	}
}
