package com.boresjo.play.api.google.connectors

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://connectors.googleapis.com/"

	object projects {
		object locations {
			object connections {
				class exchangeAuthCode(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withExchangeAuthCodeRequest(body: Schema.ExchangeAuthCodeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ExchangeAuthCodeResponse])
				}
				object exchangeAuthCode {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): exchangeAuthCode = new exchangeAuthCode(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:exchangeAuthCode")).addQueryStringParameters("name" -> name.toString))
				}
				class executeSqlQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withExecuteSqlQueryRequest(body: Schema.ExecuteSqlQueryRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ExecuteSqlQueryResponse])
				}
				object executeSqlQuery {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, connection: String)(using auth: AuthToken, ec: ExecutionContext): executeSqlQuery = new executeSqlQuery(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:executeSqlQuery")).addQueryStringParameters("connection" -> connection.toString))
				}
				class checkStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CheckStatusResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.CheckStatusResponse])
				}
				object checkStatus {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): checkStatus = new checkStatus(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:checkStatus")).addQueryStringParameters("name" -> name.toString))
					given Conversion[checkStatus, Future[Schema.CheckStatusResponse]] = (fun: checkStatus) => fun.apply()
				}
				class checkReadiness(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CheckReadinessResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.CheckReadinessResponse])
				}
				object checkReadiness {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): checkReadiness = new checkReadiness(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:checkReadiness")).addQueryStringParameters("name" -> name.toString))
					given Conversion[checkReadiness, Future[Schema.CheckReadinessResponse]] = (fun: checkReadiness) => fun.apply()
				}
				class refreshAccessToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRefreshAccessTokenRequest(body: Schema.RefreshAccessTokenRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.RefreshAccessTokenResponse])
				}
				object refreshAccessToken {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): refreshAccessToken = new refreshAccessToken(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:refreshAccessToken")).addQueryStringParameters("name" -> name.toString))
				}
				object actions {
					class execute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withExecuteActionRequest(body: Schema.ExecuteActionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ExecuteActionResponse])
					}
					object execute {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, actionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): execute = new execute(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/actions/${actionsId}:execute")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListActionsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListActionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/actions")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
						given Conversion[list, Future[Schema.ListActionsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Action]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Action])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, actionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/actions/${actionsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Action]] = (fun: get) => fun.apply()
					}
				}
				object entityTypes {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EntityType]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.EntityType])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.EntityType]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEntityTypesResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListEntityTypesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
						given Conversion[list, Future[Schema.ListEntityTypesResponse]] = (fun: list) => fun.apply()
					}
					object entities {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withEntity(body: Schema.Entity) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Entity])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities")).addQueryStringParameters("parent" -> parent.toString))
						}
						class deleteEntitiesWithConditions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = req.execute("POST").map(_.json.as[Schema.Empty])
						}
						object deleteEntitiesWithConditions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, entityType: String, conditions: String)(using auth: AuthToken, ec: ExecutionContext): deleteEntitiesWithConditions = new deleteEntitiesWithConditions(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities:deleteEntitiesWithConditions")).addQueryStringParameters("entityType" -> entityType.toString, "conditions" -> conditions.toString))
							given Conversion[deleteEntitiesWithConditions, Future[Schema.Empty]] = (fun: deleteEntitiesWithConditions) => fun.apply()
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, entitiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities/${entitiesId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Entity]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.Entity])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, entitiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities/${entitiesId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Entity]] = (fun: get) => fun.apply()
						}
						class updateEntitiesWithConditions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withEntity(body: Schema.Entity) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.UpdateEntitiesWithConditionsResponse])
						}
						object updateEntitiesWithConditions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, entityType: String, conditions: String)(using auth: AuthToken, ec: ExecutionContext): updateEntitiesWithConditions = new updateEntitiesWithConditions(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities:updateEntitiesWithConditions")).addQueryStringParameters("entityType" -> entityType.toString, "conditions" -> conditions.toString))
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withEntity(body: Schema.Entity) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Entity])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, entitiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities/${entitiesId}")).addQueryStringParameters("name" -> name.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEntitiesResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ListEntitiesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, entityTypesId :PlayApi, parent: String, pageSize: Int, pageToken: String, sortBy: String, conditions: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/entityTypes/${entityTypesId}/entities")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "sortBy" -> sortBy.toString, "conditions" -> conditions.toString))
							given Conversion[list, Future[Schema.ListEntitiesResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
		}
	}
}
