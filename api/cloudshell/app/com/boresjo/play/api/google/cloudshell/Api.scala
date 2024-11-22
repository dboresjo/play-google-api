package com.boresjo.play.api.google.cloudshell

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://cloudshell.googleapis.com/"

	object operations {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
		}
		object list {
			def apply(name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCancelOperationRequest(body: Schema.CancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object cancel {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
		}
	}
	object users {
		object environments {
			class authorize(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAuthorizeEnvironmentRequest(body: Schema.AuthorizeEnvironmentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object authorize {
				def apply(usersId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): authorize = new authorize(ws.url(BASE_URL + s"v1/users/${usersId}/environments/${environmentsId}:authorize").addQueryStringParameters("name" -> name.toString))
			}
			class removePublicKey(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRemovePublicKeyRequest(body: Schema.RemovePublicKeyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object removePublicKey {
				def apply(usersId :PlayApi, environmentsId :PlayApi, environment: String)(using auth: AuthToken, ec: ExecutionContext): removePublicKey = new removePublicKey(ws.url(BASE_URL + s"v1/users/${usersId}/environments/${environmentsId}:removePublicKey").addQueryStringParameters("environment" -> environment.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Environment]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Environment])
			}
			object get {
				def apply(usersId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/users/${usersId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Environment]] = (fun: get) => fun.apply()
			}
			class addPublicKey(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAddPublicKeyRequest(body: Schema.AddPublicKeyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object addPublicKey {
				def apply(usersId :PlayApi, environmentsId :PlayApi, environment: String)(using auth: AuthToken, ec: ExecutionContext): addPublicKey = new addPublicKey(ws.url(BASE_URL + s"v1/users/${usersId}/environments/${environmentsId}:addPublicKey").addQueryStringParameters("environment" -> environment.toString))
			}
			class start(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withStartEnvironmentRequest(body: Schema.StartEnvironmentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object start {
				def apply(usersId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): start = new start(ws.url(BASE_URL + s"v1/users/${usersId}/environments/${environmentsId}:start").addQueryStringParameters("name" -> name.toString))
			}
		}
	}
}
