package com.boresjo.play.api.google.vault

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
		"""https://www.googleapis.com/auth/ediscovery""" /* Manage your eDiscovery data */,
		"""https://www.googleapis.com/auth/ediscovery.readonly""" /* View your eDiscovery data */
	)

	private val BASE_URL = "https://vault.googleapis.com/"

	object operations {
		/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""", """https://www.googleapis.com/auth/ediscovery.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
		}
		object list {
			def apply(name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
		}
		/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""", """https://www.googleapis.com/auth/ediscovery.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
			/** Perform the request */
			def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object cancel {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
		}
	}
	object matters {
		/** Undeletes the specified matter. Returns the matter with updated state. */
		class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
			/** Perform the request */
			def withUndeleteMatterRequest(body: Schema.UndeleteMatterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Matter])
		}
		object undelete {
			def apply(matterId: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v1/matters/${matterId}:undelete").addQueryStringParameters())
		}
		/** Creates a matter with the given name and description. The initial state is open, and the owner is the method caller. Returns the created matter with default view. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
			/** Perform the request */
			def withMatter(body: Schema.Matter) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Matter])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/matters").addQueryStringParameters())
		}
		/** Reopens the specified matter. Returns the matter with updated state. */
		class reopen(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
			/** Perform the request */
			def withReopenMatterRequest(body: Schema.ReopenMatterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReopenMatterResponse])
		}
		object reopen {
			def apply(matterId: String)(using signer: RequestSigner, ec: ExecutionContext): reopen = new reopen(ws.url(BASE_URL + s"v1/matters/${matterId}:reopen").addQueryStringParameters())
		}
		/** Adds an account as a matter collaborator. */
		class addPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
			/** Perform the request */
			def withAddMatterPermissionsRequest(body: Schema.AddMatterPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.MatterPermission])
		}
		object addPermissions {
			def apply(matterId: String)(using signer: RequestSigner, ec: ExecutionContext): addPermissions = new addPermissions(ws.url(BASE_URL + s"v1/matters/${matterId}:addPermissions").addQueryStringParameters())
		}
		/** Gets the specified matter. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Matter]) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""", """https://www.googleapis.com/auth/ediscovery.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Matter])
		}
		object get {
			def apply(matterId: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/matters/${matterId}").addQueryStringParameters("view" -> view.toString))
			given Conversion[get, Future[Schema.Matter]] = (fun: get) => fun.apply()
		}
		/** Updates the specified matter. This updates only the name and description of the matter, identified by matter ID. Changes to any other fields are ignored. Returns the default view of the matter. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
			/** Perform the request */
			def withMatter(body: Schema.Matter) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Matter])
		}
		object update {
			def apply(matterId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/matters/${matterId}").addQueryStringParameters())
		}
		/** Closes the specified matter. Returns the matter with updated state. */
		class close(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
			/** Perform the request */
			def withCloseMatterRequest(body: Schema.CloseMatterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CloseMatterResponse])
		}
		object close {
			def apply(matterId: String)(using signer: RequestSigner, ec: ExecutionContext): close = new close(ws.url(BASE_URL + s"v1/matters/${matterId}:close").addQueryStringParameters())
		}
		/** Lists matters the requestor has access to. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMattersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""", """https://www.googleapis.com/auth/ediscovery.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMattersResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, view: String, state: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/matters").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString, "state" -> state.toString))
			given Conversion[list, Future[Schema.ListMattersResponse]] = (fun: list) => fun.apply()
		}
		/** Counts the accounts processed by the specified query. */
		class count(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
			/** Perform the request */
			def withCountArtifactsRequest(body: Schema.CountArtifactsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object count {
			def apply(matterId: String)(using signer: RequestSigner, ec: ExecutionContext): count = new count(ws.url(BASE_URL + s"v1/matters/${matterId}:count").addQueryStringParameters())
		}
		/** Removes an account as a matter collaborator. */
		class removePermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
			/** Perform the request */
			def withRemoveMatterPermissionsRequest(body: Schema.RemoveMatterPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object removePermissions {
			def apply(matterId: String)(using signer: RequestSigner, ec: ExecutionContext): removePermissions = new removePermissions(ws.url(BASE_URL + s"v1/matters/${matterId}:removePermissions").addQueryStringParameters())
		}
		/** Deletes the specified matter. Returns the matter with updated state. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Matter]) {
			val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Matter])
		}
		object delete {
			def apply(matterId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/matters/${matterId}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Matter]] = (fun: delete) => fun.apply()
		}
		object exports {
			/** Creates an export. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
				/** Perform the request */
				def withExport(body: Schema.Export) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Export])
			}
			object create {
				def apply(matterId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/matters/${matterId}/exports").addQueryStringParameters())
			}
			/** Deletes an export. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(matterId: String, exportId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/matters/${matterId}/exports/${exportId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets an export. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Export]) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""", """https://www.googleapis.com/auth/ediscovery.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Export])
			}
			object get {
				def apply(matterId: String, exportId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/matters/${matterId}/exports/${exportId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Export]] = (fun: get) => fun.apply()
			}
			/** Lists details about the exports in the specified matter. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListExportsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""", """https://www.googleapis.com/auth/ediscovery.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListExportsResponse])
			}
			object list {
				def apply(matterId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/matters/${matterId}/exports").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListExportsResponse]] = (fun: list) => fun.apply()
			}
		}
		object holds {
			/** Adds accounts to a hold. Returns a list of accounts that have been successfully added. Accounts can be added only to an existing account-based hold. */
			class addHeldAccounts(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
				/** Perform the request */
				def withAddHeldAccountsRequest(body: Schema.AddHeldAccountsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AddHeldAccountsResponse])
			}
			object addHeldAccounts {
				def apply(matterId: String, holdId: String)(using signer: RequestSigner, ec: ExecutionContext): addHeldAccounts = new addHeldAccounts(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}:addHeldAccounts").addQueryStringParameters())
			}
			/** Creates a hold in the specified matter. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
				/** Perform the request */
				def withHold(body: Schema.Hold) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Hold])
			}
			object create {
				def apply(matterId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/matters/${matterId}/holds").addQueryStringParameters())
			}
			/** Removes the specified hold and releases the accounts or organizational unit covered by the hold. If the data is not preserved by another hold or retention rule, it might be purged. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(matterId: String, holdId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets the specified hold. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Hold]) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""", """https://www.googleapis.com/auth/ediscovery.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Hold])
			}
			object get {
				def apply(matterId: String, holdId: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}").addQueryStringParameters("view" -> view.toString))
				given Conversion[get, Future[Schema.Hold]] = (fun: get) => fun.apply()
			}
			/** Updates the scope (organizational unit or accounts) and query parameters of a hold. You cannot add accounts to a hold that covers an organizational unit, nor can you add organizational units to a hold that covers individual accounts. If you try, the unsupported values are ignored. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
				/** Perform the request */
				def withHold(body: Schema.Hold) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Hold])
			}
			object update {
				def apply(matterId: String, holdId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}").addQueryStringParameters())
			}
			/** Removes the specified accounts from a hold. Returns a list of statuses in the same order as the request. */
			class removeHeldAccounts(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
				/** Perform the request */
				def withRemoveHeldAccountsRequest(body: Schema.RemoveHeldAccountsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RemoveHeldAccountsResponse])
			}
			object removeHeldAccounts {
				def apply(matterId: String, holdId: String)(using signer: RequestSigner, ec: ExecutionContext): removeHeldAccounts = new removeHeldAccounts(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}:removeHeldAccounts").addQueryStringParameters())
			}
			/** Lists the holds in a matter. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListHoldsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""", """https://www.googleapis.com/auth/ediscovery.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListHoldsResponse])
			}
			object list {
				def apply(matterId: String, pageSize: Int, pageToken: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/matters/${matterId}/holds").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
				given Conversion[list, Future[Schema.ListHoldsResponse]] = (fun: list) => fun.apply()
			}
			object accounts {
				/** Adds an account to a hold. Accounts can be added only to a hold that does not have an organizational unit set. If you try to add an account to an organizational unit-based hold, an error is returned. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
					/** Perform the request */
					def withHeldAccount(body: Schema.HeldAccount) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.HeldAccount])
				}
				object create {
					def apply(matterId: String, holdId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}/accounts").addQueryStringParameters())
				}
				/** Removes an account from a hold. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(matterId: String, holdId: String, accountId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}/accounts/${accountId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Lists the accounts covered by a hold. This can list only individually-specified accounts covered by the hold. If the hold covers an organizational unit, use the [Admin SDK](https://developers.google.com/admin-sdk/). to list the members of the organizational unit on hold. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListHeldAccountsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""", """https://www.googleapis.com/auth/ediscovery.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListHeldAccountsResponse])
				}
				object list {
					def apply(matterId: String, holdId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/matters/${matterId}/holds/${holdId}/accounts").addQueryStringParameters())
					given Conversion[list, Future[Schema.ListHeldAccountsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object savedQueries {
			/** Creates a saved query. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
				/** Perform the request */
				def withSavedQuery(body: Schema.SavedQuery) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SavedQuery])
			}
			object create {
				def apply(matterId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/matters/${matterId}/savedQueries").addQueryStringParameters())
			}
			/** Deletes the specified saved query. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(matterId: String, savedQueryId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/matters/${matterId}/savedQueries/${savedQueryId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Retrieves the specified saved query. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SavedQuery]) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""", """https://www.googleapis.com/auth/ediscovery.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SavedQuery])
			}
			object get {
				def apply(matterId: String, savedQueryId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/matters/${matterId}/savedQueries/${savedQueryId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.SavedQuery]] = (fun: get) => fun.apply()
			}
			/** Lists the saved queries in a matter. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSavedQueriesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/ediscovery""", """https://www.googleapis.com/auth/ediscovery.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSavedQueriesResponse])
			}
			object list {
				def apply(matterId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/matters/${matterId}/savedQueries").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListSavedQueriesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
