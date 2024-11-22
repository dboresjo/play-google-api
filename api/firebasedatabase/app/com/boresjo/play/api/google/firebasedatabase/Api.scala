package com.boresjo.play.api.google.firebasedatabase

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://firebasedatabase.googleapis.com/"

	object projects {
		object locations {
			object instances {
				class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUndeleteDatabaseInstanceRequest(body: Schema.UndeleteDatabaseInstanceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DatabaseInstance])
				}
				object undelete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:undelete").addQueryStringParameters("name" -> name.toString))
				}
				class reenable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withReenableDatabaseInstanceRequest(body: Schema.ReenableDatabaseInstanceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DatabaseInstance])
				}
				object reenable {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): reenable = new reenable(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:reenable").addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDatabaseInstance(body: Schema.DatabaseInstance) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DatabaseInstance])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, databaseId: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances").addQueryStringParameters("parent" -> parent.toString, "databaseId" -> databaseId.toString, "validateOnly" -> validateOnly.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DatabaseInstance]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.DatabaseInstance])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.DatabaseInstance]] = (fun: delete) => fun.apply()
				}
				class disable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDisableDatabaseInstanceRequest(body: Schema.DisableDatabaseInstanceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DatabaseInstance])
				}
				object disable {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:disable").addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DatabaseInstance]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DatabaseInstance])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.DatabaseInstance]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDatabaseInstancesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDatabaseInstancesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, showDeleted: Boolean, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/projects/${projectsId}/locations/${locationsId}/instances").addQueryStringParameters("pageSize" -> pageSize.toString, "showDeleted" -> showDeleted.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDatabaseInstancesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
