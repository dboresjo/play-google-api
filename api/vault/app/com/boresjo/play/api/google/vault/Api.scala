package com.boresjo.play.api.google.vault

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://vault.googleapis.com/"

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
	object matters {
		class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUndeleteMatterRequest(body: Schema.UndeleteMatterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Matter])
		}
		object undelete {
			def apply(matterId: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v1/matters/${matterId}:undelete").addQueryStringParameters())
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withMatter(body: Schema.Matter) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Matter])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/matters").addQueryStringParameters())
		}
		class reopen(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReopenMatterRequest(body: Schema.ReopenMatterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReopenMatterResponse])
		}
		object reopen {
			def apply(matterId: String)(using auth: AuthToken, ec: ExecutionContext): reopen = new reopen(ws.url(BASE_URL + s"v1/matters/${matterId}:reopen").addQueryStringParameters())
		}
		class addPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMatterPermissionsRequest(body: Schema.AddMatterPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.MatterPermission])
		}
		object addPermissions {
			def apply(matterId: String)(using auth: AuthToken, ec: ExecutionContext): addPermissions = new addPermissions(ws.url(BASE_URL + s"v1/matters/${matterId}:addPermissions").addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Matter]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Matter])
		}
		object get {
			def apply(matterId: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/matters/${matterId}").addQueryStringParameters("view" -> view.toString))
			given Conversion[get, Future[Schema.Matter]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withMatter(body: Schema.Matter) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Matter])
		}
		object update {
			def apply(matterId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/matters/${matterId}").addQueryStringParameters())
		}
		class close(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCloseMatterRequest(body: Schema.CloseMatterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CloseMatterResponse])
		}
		object close {
			def apply(matterId: String)(using auth: AuthToken, ec: ExecutionContext): close = new close(ws.url(BASE_URL + s"v1/matters/${matterId}:close").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMattersResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListMattersResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, view: String, state: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/matters").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString, "state" -> state.toString))
			given Conversion[list, Future[Schema.ListMattersResponse]] = (fun: list) => fun.apply()
		}
		class count(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCountArtifactsRequest(body: Schema.CountArtifactsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object count {
			def apply(matterId: String)(using auth: AuthToken, ec: ExecutionContext): count = new count(ws.url(BASE_URL + s"v1/matters/${matterId}:count").addQueryStringParameters())
		}
		class removePermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRemoveMatterPermissionsRequest(body: Schema.RemoveMatterPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object removePermissions {
			def apply(matterId: String)(using auth: AuthToken, ec: ExecutionContext): removePermissions = new removePermissions(ws.url(BASE_URL + s"v1/matters/${matterId}:removePermissions").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Matter]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Matter])
		}
		object delete {
			def apply(matterId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/matters/${matterId}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Matter]] = (fun: delete) => fun.apply()
		}
		object exports {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withExport(body: Schema.Export) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Export])
			}
			object create {
				def apply(matterId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/matters/${matterId}/exports").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(matterId: String, exportId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/matters/${matterId}/exports/${exportId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Export]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Export])
			}
			object get {
				def apply(matterId: String, exportId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/matters/${matterId}/exports/${exportId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Export]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListExportsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListExportsResponse])
			}
			object list {
				def apply(matterId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/matters/${matterId}/exports").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListExportsResponse]] = (fun: list) => fun.apply()
			}
		}
		object holds {
			class addHeldAccounts(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAddHeldAccountsRequest(body: Schema.AddHeldAccountsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AddHeldAccountsResponse])
			}
			object addHeldAccounts {
				def apply(matterId: String, holdId: String)(using auth: AuthToken, ec: ExecutionContext): addHeldAccounts = new addHeldAccounts(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}:addHeldAccounts").addQueryStringParameters())
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withHold(body: Schema.Hold) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Hold])
			}
			object create {
				def apply(matterId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/matters/${matterId}/holds").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(matterId: String, holdId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Hold]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Hold])
			}
			object get {
				def apply(matterId: String, holdId: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}").addQueryStringParameters("view" -> view.toString))
				given Conversion[get, Future[Schema.Hold]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withHold(body: Schema.Hold) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Hold])
			}
			object update {
				def apply(matterId: String, holdId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}").addQueryStringParameters())
			}
			class removeHeldAccounts(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRemoveHeldAccountsRequest(body: Schema.RemoveHeldAccountsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RemoveHeldAccountsResponse])
			}
			object removeHeldAccounts {
				def apply(matterId: String, holdId: String)(using auth: AuthToken, ec: ExecutionContext): removeHeldAccounts = new removeHeldAccounts(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}:removeHeldAccounts").addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListHoldsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListHoldsResponse])
			}
			object list {
				def apply(matterId: String, pageSize: Int, pageToken: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/matters/${matterId}/holds").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
				given Conversion[list, Future[Schema.ListHoldsResponse]] = (fun: list) => fun.apply()
			}
			object accounts {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withHeldAccount(body: Schema.HeldAccount) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.HeldAccount])
				}
				object create {
					def apply(matterId: String, holdId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}/accounts").addQueryStringParameters())
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(matterId: String, holdId: String, accountId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}/accounts/${accountId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListHeldAccountsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListHeldAccountsResponse])
				}
				object list {
					def apply(matterId: String, holdId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}/accounts").addQueryStringParameters())
					given Conversion[list, Future[Schema.ListHeldAccountsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object savedQueries {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSavedQuery(body: Schema.SavedQuery) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SavedQuery])
			}
			object create {
				def apply(matterId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/matters/${matterId}/savedQueries").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(matterId: String, savedQueryId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/matters/${matterId}/savedQueries/${savedQueryId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SavedQuery]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SavedQuery])
			}
			object get {
				def apply(matterId: String, savedQueryId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/matters/${matterId}/savedQueries/${savedQueryId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.SavedQuery]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSavedQueriesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSavedQueriesResponse])
			}
			object list {
				def apply(matterId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/matters/${matterId}/savedQueries").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListSavedQueriesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
